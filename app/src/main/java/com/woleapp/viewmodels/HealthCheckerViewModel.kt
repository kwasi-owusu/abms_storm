package com.woleapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.woleapp.model.HealthCheckerModel
import com.woleapp.model.HealthCheckerResponse
import com.woleapp.network.HealthCheckerClient
import com.woleapp.util.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber

class HealthCheckerViewModel : ViewModel() {
    val healthCheckerModel = MutableLiveData(HealthCheckerModel())
    var initialOrFollowUpCheck = "Initial"
    var genderCheckId = "male"
    var prefCommId = "SMS"
    val healthCheckerServices = HealthCheckerClient.getHealthCheckerInstance()
    val inProgress = MutableLiveData(false)
    private val _message = MutableLiveData<Event<String>>()
    private val _dialogMessage = MutableLiveData<Event<DialogHelper>>()
    val dialogMessage: LiveData<Event<DialogHelper>>
        get() = _dialogMessage
    val message: LiveData<Event<String>>
        get() = _message
    private val disposables = CompositeDisposable()

    fun payAndSubmit() {
        val healthCheckerPayload = healthCheckerModel.value!!
        with(healthCheckerPayload) {
            gender = genderCheckId
            communicationChannel = prefCommId
            requestType = initialOrFollowUpCheck
        }
        if (healthCheckerPayload.requestType == "followup") {
            if (healthCheckerPayload.followUpToken.isNullOrEmpty()) {
                _message.value = Event("Enter a follow-up code")
                return
            }
        } else {
            if (healthCheckerPayload.nullOrEmptyFields.isEmpty().not()) {
                _message.value = Event("${healthCheckerPayload.nullOrEmptyFields} required")
                return
            }
            healthCheckerPayload.phone = "+234${healthCheckerPayload.phone!!.substring(1)}"
        }
        healthCheckerServices.request(healthCheckerPayload)
            .onErrorResumeNext {
                Timber.e("from on error resume next")
                val responseBodyString = it.getResponseBody()
                val healthCheckerResponse: HealthCheckerResponse? =
                    responseBodyString.getResponseBodyObject(HealthCheckerResponse::class.java)
                healthCheckerResponse?.let {
                    healthCheckerResponse.errors?.let { errors ->
                        val error = errors.joinToString(", ")
                        //"User has not been activated"
                        if (error.contains("User has not been activated")) {
                            Timber.e("User not activated, activate now")
                            return@onErrorResumeNext healthCheckerServices.activateAgent().flatMap {
                                Timber.e(it.toString())
                                healthCheckerServices.request(healthCheckerPayload)
                            }
                        }
                    }
                }
                Single.error(HttpException(Response.error<String>(400, ResponseBody.create(MediaType.parse("application/json"), responseBodyString))))
            }
            .doOnSubscribe {
                inProgress.postValue(true)
            }.doFinally {
                inProgress.postValue(false)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t1, t2 ->
                t1?.let {
                    healthCheckerModel.value = HealthCheckerModel()
                    _dialogMessage.value = Event(DialogHelper(DialogType.SUCCESS, it.message ?: "Request Sent"))
                    Timber.e(it.toString())
                }
                t2?.let {
                    Timber.e("errors found")
                    val responseBodyString = it.getResponseBody()
                    val healthCheckerResponse: HealthCheckerResponse? =
                        responseBodyString.getResponseBodyObject(HealthCheckerResponse::class.java)
                    healthCheckerResponse?.let {
                        Timber.e("health checker response is not null")
                        healthCheckerResponse.message?.let { message ->
                            _message.value = Event(message)
                            _dialogMessage.value = Event(DialogHelper(DialogType.FAILURE, it.message ?: "Request Failed"))
                            return@subscribe
                        }
                        healthCheckerResponse.errors?.let { errors ->
                            val error = errors.joinToString(", ")
                            //"User has not been activated"
                            _message.value = Event(error)
                            _dialogMessage.value = Event(DialogHelper(DialogType.FAILURE, error))
                            return@subscribe
                        }
                        //return@subscribe
                    }
                    _dialogMessage.value = Event(DialogHelper(DialogType.FAILURE, "An error occurred while sending the request"))
                    Timber.e(it)
                }
            }.disposeWith(disposables)
    }

}