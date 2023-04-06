package com.woleapp.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.woleapp.R
import com.woleapp.model.Color
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.apache.commons.io.FileUtils
import retrofit2.HttpException
import timber.log.Timber
import java.io.*
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import kotlin.random.Random


object Singletons {
    @JvmStatic
    fun getGsonInstance() = Gson()
}

fun getPlansJson(context: Context): String = context.resources.openRawResource(R.raw.plans)
    .bufferedReader().use { it.readText() }

fun getServiceProviderPlansJson(context: Context): String =
    context.resources.openRawResource(R.raw.data_plans)
        .bufferedReader().use { it.readText() }

fun Throwable.getResponseBody(): String {
    return if (isHttpException()) {
        val body: ResponseBody? =
            (this as HttpException).response()!!.errorBody()
        try {
            var sBody = body?.string()
            if (sBody.isNullOrEmpty())
                sBody = message
            Timber.e("body $sBody")
            sBody ?: "{\"message\": \"An unexpected error occurred, Please try again\"}"
        } catch (e1: Exception) {
            // userResponseResult.setValue(Error(Throwable("An unexpected error occurred")))
            "{\"message\": \"An unexpected error occurred, Please try again\"}"
        } finally {
            body?.close()
        }
    } else
        "{\"message\": \"An unexpected error occurred, Please try again\"}"
}

fun <T> String.getResponseBodyObject(cls: Class<T>): T? = try {
    Timber.e("get response body object")
    Singletons.getGsonInstance().fromJson(this, cls)
} catch (e: Exception) {
    Timber.e("Error occurred")
    Timber.e(e.localizedMessage)
    cls.newInstance()
}

fun Throwable.isHttpException(): Boolean =
    (this is HttpException && this.code() in 400..599)

fun Throwable.isHttpStatusCode(statusCode: Int): Boolean =
    (this is HttpException && this.code() == statusCode)

fun encodeImage(imagefile: File): String? {
    val fis: FileInputStream?
    try {
        fis = FileInputStream(imagefile)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        return "ERROR"
    }
    val bm = BitmapFactory.decodeStream(fis)
    val baos = ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val b: ByteArray = baos.toByteArray()
    //Base64.de
    return Base64.encodeToString(b, Base64.DEFAULT)
}

fun getColorFromColorList(colors: List<Color>, colorName: String) =
    colors.indexOf(colors.find { it.name == colorName })

fun decodeBase64ToBitmap(encodedImage: String): Bitmap? {
    val decodedString: ByteArray = Base64.decode(encodedImage, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
}

fun setDecodedImageToImageView(encodedImage: String, imageView: ImageView) {
    Single.create { emitter: SingleEmitter<Bitmap?> ->
        val bitmap = decodeBase64ToBitmap(encodedImage)
        if (bitmap != null) {
            emitter.onSuccess(bitmap)
        } else emitter.onSuccess(
            BitmapFactory.decodeResource(
                imageView.context.resources,
                R.drawable.ic_store_manager
            )
        )
    }.subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : SingleObserver<Bitmap?> {
            override fun onSubscribe(d: Disposable) {}

            override fun onError(e: Throwable) {
                Log.e("TAG", "Error: " + e.localizedMessage)
            }

            override fun onSuccess(t: Bitmap) {
                imageView.setImageBitmap(t)
            }
        })
}

fun loadImageWithGlide(url: String, imageView: ImageView) {
    val circularProgressDrawable = CircularProgressDrawable(imageView.context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    Glide.with(imageView.context)
        .load(url)
        .placeholder(circularProgressDrawable)
        .into(imageView)
}

fun Disposable.disposeWith(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

fun copyTextToClipboard(context: Context, label: String, text: String) {
    val clipboard: ClipboardManager? =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
    val clip = ClipData.newPlainText(label, text)
    clipboard!!.setPrimaryClip(clip)
}

fun getSixDigitRandomNumber() = String.format("%06d", Random.nextInt(999999))

fun encodeToBase64String(s: String): String {
    return Base64.encodeToString(s.toByteArray(charset("UTF-8")), Base64.DEFAULT)
}

fun encodeQueryValue(value: String): String {
    return try {
        URLEncoder.encode(value, StandardCharsets.UTF_8.name())
    } catch (ex: UnsupportedEncodingException) {
        Log.e("TAG", "unsupported encoding exeption: " + ex.localizedMessage)
        "null"
    }
}

fun getExtension(path: String): String {
    val idx = path.lastIndexOf('.')
    return if (idx > 0) {
        path.substring(idx + 1)
    } else ""
}

fun getFilePath(activity: Context, productName: String, extension: String): String? {
    val folder = File(activity.getExternalFilesDir(null), "/inventory_pictures")
    val directoryExists: Boolean
    directoryExists = if (folder.exists()) {
        true
    } else {
        folder.mkdir()
    }
    if (directoryExists) {
        var path = folder.absolutePath
        path =
            "$path/INVENTORY_$productName.$extension" // + ".pdf";// path where pdf will be stored
        return path
    }
    return null
}

fun saveAndEncodeImage(source: File, destination: File): Observable<String?> {
    return Observable.create { emitter: ObservableEmitter<String?> ->
        FileUtils.copyFile(source, destination)
        emitter.onNext(encodeImage(source)!!)
        emitter.onComplete()
    }
}

fun saveAndGetMultipart(
    partName: String,
    source: File,
    destination: File
): Observable<MultipartBody.Part> {
    return Observable.create { emitter: ObservableEmitter<MultipartBody.Part> ->
        FileUtils.copyFile(source, destination)
        emitter.onNext(prepareFilePart(partName, destination.path))
        emitter.onComplete()
    }
}

fun prepareFilePart(partName: String, filePath: String): MultipartBody.Part {
    val file = File(filePath)
    val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
    return MultipartBody.Part.createFormData(partName, file.name, requestFile)
}

fun createRequestBodyFromString(body: String): RequestBody = RequestBody.create(
    MultipartBody.FORM, body
)

val Any?.exhaustive get() = Unit

@BindingAdapter("buttonInProgress")
fun Button.buttonInProgress(inpProgress: Boolean) {
    isEnabled = inpProgress.not()
}

@BindingAdapter("progressBarInProgress")
fun ProgressBar.progressBarInProgress(boolean: Boolean) {
    visibility = if (boolean)
        View.VISIBLE
    else View.GONE
}

data class DialogHelper(
    val dialogType: DialogType,
    val message: String,
    val actionName: String = "Retry",
    val action: (() -> Unit)? = null
)

enum class DialogType(val title: String, val icon: Int) {
    SUCCESS(
        "Success",
        R.drawable.animated_check
    ),
    FAILURE("Failure", R.drawable.ic_error),
    CONFIRMATION("Confirm", R.drawable.ic_error)
}