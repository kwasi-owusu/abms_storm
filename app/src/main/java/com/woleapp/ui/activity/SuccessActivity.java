package com.woleapp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.woleapp.R;

public class SuccessActivity extends AppCompatActivity {

    Context context = this;
    ImageView imgStatus;
    TextView tvStatus;
    Toolbar toolbar;
    Boolean transactionStatus;
    String displayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        if (getIntent() != null) {
            transactionStatus = getIntent().getBooleanExtra("status", false);
            displayText = getIntent().getStringExtra("message");
        }
        inIt();
    }

    private void inIt() {

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v->{onBackPressed();});
        tvStatus = findViewById(R.id.tvTransactionStatus);
        imgStatus = findViewById(R.id.imgTransactionStatus);

        tvStatus.setText(displayText);
        imgStatus.setImageDrawable(transactionStatus ? getResources().getDrawable(R.drawable.tick) : getResources().getDrawable(R.drawable.ic_cancel_black_24dp));

    }

}
