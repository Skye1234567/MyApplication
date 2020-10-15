package project.activities.player;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Create_User extends AppCompatActivity {
    EditText password;
    EditText email;
    FirebaseAuth mAuth;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__user);
        mAuth = FirebaseAuth.getInstance();
        context =this;
        password = findViewById(R.id.player_password);
        email = findViewById(R.id.player_email);
        final Button button = findViewById(R.id.sign_in_player_button);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

        email.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    email.clearFocus();
                    password.selectAll();

                    return true;
                }
                return false;
            }
        });
        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    button.performClick();


                    return true;
                }
                return false;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String ps = password.getText().toString();
                final String em = email.getText().toString() + "@market.com";
                if (ps == null) return;
                if (em == null) return;
                if (0 != ps.compareTo("") && 0 != em.compareTo("")) {
                    Activity activity = (Activity) context;

                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(em, ps)
                            .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information



                                    } else {


                                        Toast.makeText(context, "Sign up failed.", Toast.LENGTH_SHORT).show();
                                    }
                                    Intent intent = new Intent(context, GameMenu.class);
                                    context.startActivity(intent);
                                }
                            });
                } else {
                    Toast.makeText(context, "Invalid entries",Toast.LENGTH_SHORT).show();
                }

            }
        });




    }
}
