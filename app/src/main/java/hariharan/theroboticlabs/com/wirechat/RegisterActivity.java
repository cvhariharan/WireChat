package hariharan.theroboticlabs.com.wirechat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import hariharan.theroboticlabs.com.wirechat.Utils.FirebaseUtils;
import hariharan.theroboticlabs.com.wirechat.Utils.User;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private EditText nameField, email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameField = findViewById(R.id.name_edittext);
        email = findViewById(R.id.email_edittext);
        password = findViewById(R.id.password_edittext);
        Button register = (Button) findViewById(R.id.register_btn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailId = email.getText().toString();
                String passw = password.getText().toString();
                String name = nameField.getText().toString();
                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(emailId)
                        && !TextUtils.isEmpty(passw)) {
                    Log.d(TAG, "onCreate: All is well");
                    signUp(emailId, passw, name);
                }
                else
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_LONG);
            }
        });
    }
    private void signUp(String email, String password, final String name) {

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUtils firebaseUtils = new FirebaseUtils();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            //Set name as display name
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name).build();
                            user.updateProfile(profileUpdates);

                            Toast.makeText(RegisterActivity.this, "Welcome",
                                    Toast.LENGTH_LONG).show();
                            firebaseUtils.addUser(new User(name, user.getUid()));
                            Intent intent = new Intent(RegisterActivity.this, HomePage.class);
                            startActivity(intent);


                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Registration failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}
