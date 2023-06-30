package com.woleapp.util;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 2, d1 = {"\u0000\u008c\u0001\n\u0000\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001e\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t\u001a\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\t\u001a\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\t\u001a\u0010\u0010\u0011\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0012\u001a\u00020\u0013\u001a\u000e\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\t\u001a\u000e\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\t\u001a\u001c\u0010\u0018\u001a\u00020\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b2\u0006\u0010\u001d\u001a\u00020\t\u001a\u000e\u0010\u001e\u001a\u00020\t2\u0006\u0010\u001f\u001a\u00020\t\u001a \u0010 \u001a\u0004\u0018\u00010\t2\u0006\u0010!\u001a\u00020\u00072\u0006\u0010\"\u001a\u00020\t2\u0006\u0010#\u001a\u00020\t\u001a\u000e\u0010$\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u0007\u001a\u000e\u0010%\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u0007\u001a\u0006\u0010&\u001a\u00020\t\u001a\u0016\u0010\'\u001a\u00020\u00012\u0006\u0010(\u001a\u00020\t2\u0006\u0010)\u001a\u00020*\u001a\u0016\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\t2\u0006\u0010.\u001a\u00020\t\u001a\u001e\u0010/\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t002\u0006\u00101\u001a\u00020\u00132\u0006\u00102\u001a\u00020\u0013\u001a$\u00103\u001a\b\u0012\u0004\u0012\u00020,002\u0006\u0010-\u001a\u00020\t2\u0006\u00101\u001a\u00020\u00132\u0006\u00102\u001a\u00020\u0013\u001a\u0016\u00104\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010)\u001a\u00020*\u001a\u0014\u00105\u001a\u00020\u0001*\u0002062\u0006\u00107\u001a\u000208H\u0007\u001a\u0012\u00109\u001a\u00020\u0001*\u00020:2\u0006\u0010;\u001a\u00020<\u001a\n\u0010=\u001a\u00020\t*\u00020>\u001a%\u0010?\u001a\u0004\u0018\u0001H@\"\u0004\b\u0000\u0010@*\u00020\t2\f\u0010A\u001a\b\u0012\u0004\u0012\u0002H@0B\u00a2\u0006\u0002\u0010C\u001a\n\u0010D\u001a\u000208*\u00020>\u001a\u0012\u0010E\u001a\u000208*\u00020>2\u0006\u0010F\u001a\u00020\u0019\u001a\u0014\u0010G\u001a\u00020\u0001*\u00020H2\u0006\u0010I\u001a\u000208H\u0007\"\u0017\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\u00a8\u0006J"}, d2 = {"exhaustive", "", "", "getExhaustive", "(Ljava/lang/Object;)Lkotlin/Unit;", "copyTextToClipboard", "context", "Landroid/content/Context;", "label", "", "text", "createRequestBodyFromString", "Lokhttp3/RequestBody;", "body", "decodeBase64ToBitmap", "Landroid/graphics/Bitmap;", "encodedImage", "encodeImage", "imagefile", "Ljava/io/File;", "encodeQueryValue", "value", "encodeToBase64String", "s", "getColorFromColorList", "", "colors", "", "Lcom/woleapp/model/Color;", "colorName", "getExtension", "path", "getFilePath", "activity", "productName", "extension", "getPlansJson", "getServiceProviderPlansJson", "getSixDigitRandomNumber", "loadImageWithGlide", "url", "imageView", "Landroid/widget/ImageView;", "prepareFilePart", "Lokhttp3/MultipartBody$Part;", "partName", "filePath", "saveAndEncodeImage", "Lio/reactivex/Observable;", "source", "destination", "saveAndGetMultipart", "setDecodedImageToImageView", "buttonInProgress", "Landroid/widget/Button;", "inpProgress", "", "disposeWith", "Lio/reactivex/disposables/Disposable;", "compositeDisposable", "Lio/reactivex/disposables/CompositeDisposable;", "getResponseBody", "", "getResponseBodyObject", "T", "cls", "Ljava/lang/Class;", "(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;", "isHttpException", "isHttpStatusCode", "statusCode", "progressBarInProgress", "Landroid/widget/ProgressBar;", "boolean", "app_debug"})
public final class UtilsAndExtensionsKt {
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String getPlansJson(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String getServiceProviderPlansJson(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String getResponseBody(@org.jetbrains.annotations.NotNull()
    java.lang.Throwable $this$getResponseBody) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public static final <T extends java.lang.Object>T getResponseBodyObject(@org.jetbrains.annotations.NotNull()
    java.lang.String $this$getResponseBodyObject, @org.jetbrains.annotations.NotNull()
    java.lang.Class<T> cls) {
        return null;
    }
    
    public static final boolean isHttpException(@org.jetbrains.annotations.NotNull()
    java.lang.Throwable $this$isHttpException) {
        return false;
    }
    
    public static final boolean isHttpStatusCode(@org.jetbrains.annotations.NotNull()
    java.lang.Throwable $this$isHttpStatusCode, int statusCode) {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public static final java.lang.String encodeImage(@org.jetbrains.annotations.NotNull()
    java.io.File imagefile) {
        return null;
    }
    
    public static final int getColorFromColorList(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends com.woleapp.model.Color> colors, @org.jetbrains.annotations.NotNull()
    java.lang.String colorName) {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public static final android.graphics.Bitmap decodeBase64ToBitmap(@org.jetbrains.annotations.NotNull()
    java.lang.String encodedImage) {
        return null;
    }
    
    public static final void setDecodedImageToImageView(@org.jetbrains.annotations.NotNull()
    java.lang.String encodedImage, @org.jetbrains.annotations.NotNull()
    android.widget.ImageView imageView) {
    }
    
    public static final void loadImageWithGlide(@org.jetbrains.annotations.NotNull()
    java.lang.String url, @org.jetbrains.annotations.NotNull()
    android.widget.ImageView imageView) {
    }
    
    public static final void disposeWith(@org.jetbrains.annotations.NotNull()
    io.reactivex.disposables.Disposable $this$disposeWith, @org.jetbrains.annotations.NotNull()
    io.reactivex.disposables.CompositeDisposable compositeDisposable) {
    }
    
    public static final void copyTextToClipboard(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String label, @org.jetbrains.annotations.NotNull()
    java.lang.String text) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String getSixDigitRandomNumber() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String encodeToBase64String(@org.jetbrains.annotations.NotNull()
    java.lang.String s) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String encodeQueryValue(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String getExtension(@org.jetbrains.annotations.NotNull()
    java.lang.String path) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public static final java.lang.String getFilePath(@org.jetbrains.annotations.NotNull()
    android.content.Context activity, @org.jetbrains.annotations.NotNull()
    java.lang.String productName, @org.jetbrains.annotations.NotNull()
    java.lang.String extension) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final io.reactivex.Observable<java.lang.String> saveAndEncodeImage(@org.jetbrains.annotations.NotNull()
    java.io.File source, @org.jetbrains.annotations.NotNull()
    java.io.File destination) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final io.reactivex.Observable<okhttp3.MultipartBody.Part> saveAndGetMultipart(@org.jetbrains.annotations.NotNull()
    java.lang.String partName, @org.jetbrains.annotations.NotNull()
    java.io.File source, @org.jetbrains.annotations.NotNull()
    java.io.File destination) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final okhttp3.MultipartBody.Part prepareFilePart(@org.jetbrains.annotations.NotNull()
    java.lang.String partName, @org.jetbrains.annotations.NotNull()
    java.lang.String filePath) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final okhttp3.RequestBody createRequestBodyFromString(@org.jetbrains.annotations.NotNull()
    java.lang.String body) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final kotlin.Unit getExhaustive(@org.jetbrains.annotations.Nullable()
    java.lang.Object $this$exhaustive) {
        return null;
    }
    
    @androidx.databinding.BindingAdapter(value = {"buttonInProgress"})
    public static final void buttonInProgress(@org.jetbrains.annotations.NotNull()
    android.widget.Button $this$buttonInProgress, boolean inpProgress) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"progressBarInProgress"})
    public static final void progressBarInProgress(@org.jetbrains.annotations.NotNull()
    android.widget.ProgressBar $this$progressBarInProgress, boolean p1_32355860) {
    }
}