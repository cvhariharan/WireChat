package hariharan.theroboticlabs.com.wirechat;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.Result;

import org.w3c.dom.Text;

import hariharan.theroboticlabs.com.wirechat.Utils.FirebaseUtils;
import hariharan.theroboticlabs.com.wirechat.Utils.User;

public class Scan extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    private FirebaseUtils firebaseUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        firebaseUtils = new FirebaseUtils();

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        if(checkCameraPermission()) {
            mCodeScanner = new CodeScanner(this, scannerView);
            mCodeScanner.setDecodeCallback(new DecodeCallback() {
                @Override
                public void onDecoded(@NonNull final Result result) {
                    Scan.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String friendUid = result.getText();
                            if (!TextUtils.isEmpty(friendUid)) {
                                firebaseUtils.addFriend(FirebaseAuth.getInstance().getCurrentUser().getUid()
                                        , new User(friendUid));
                                Intent goBack = new Intent(Scan.this, HomePage.class);
                                startActivity(goBack);
                            }
                        }
                    });
                }
            });
            scannerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCodeScanner.startPreview();
                }
            });
        }
        else {
            requestPermission();
            Toast.makeText(this, "Requires Camera Permission", Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.requestPermissions(
                    new String[]{Manifest.permission.CAMERA},
                    10);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mCodeScanner != null)
            mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        if(mCodeScanner != null)
            mCodeScanner.releaseResources();
        super.onPause();
    }
}
