package com.woleapp.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import io.reactivex.functions.Consumer;

public class ErrorOccurredConsumer implements Consumer<Throwable> {

    Context context;
    public ErrorOccurredConsumer(Context context) {
        this.context = context;
    }

    @Override
    public void accept(Throwable throwable) throws Exception {
        Log.e("ErrorOccurredConsumer", "error occurred", throwable);
        Toast.makeText(context, "An expected error occurred please check that you connected to the internet and try again.", Toast.LENGTH_LONG).show();
    }
}
