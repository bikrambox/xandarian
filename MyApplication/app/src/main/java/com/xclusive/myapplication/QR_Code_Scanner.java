package com.xclusive.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import android.content.Intent;
import android.widget.Toast;

public class QR_Code_Scanner extends AppCompatActivity {

    CodeScannerView codeScannerView;
    CodeScanner codeScanner;
    private TextView output;
    private ImageButton qrrefreshbtn;
    private Button donebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r__code__scanner);

        qrrefreshbtn = findViewById(R.id.qrrefreshbtn);
        donebtn = findViewById(R.id.qrdonebtn);

        codeScannerView = findViewById(R.id.scannerview);
        codeScanner = new CodeScanner(this, codeScannerView);
        output = findViewById(R.id.output2);


        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                if(result!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            output.setText(result.getText());
                        }
                    });
                }
                else
                {

                }

            }
        });

        qrrefreshbtn.setOnClickListener(V->{
            codeScanner.startPreview();
        });

        donebtn.setOnClickListener(V->{
            String results = output.getText().toString();
            Intent intent = new Intent(QR_Code_Scanner.this, Product_tracking.class);

            intent.putExtra("ID",results);
            startActivity(intent);
            finish();
        });




    }

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();

        if (output.getText().toString()!=null){
            qrrefreshbtn.setVisibility(View.VISIBLE);
            donebtn.setVisibility(View.VISIBLE);
        }
    }
}