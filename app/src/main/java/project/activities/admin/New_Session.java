package project.activities.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.google.firebase.database.FirebaseDatabase;

public class New_Session extends AppCompatActivity {
    private EditText new_id;
    private Button submit;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context =this;
        setContentView(R.layout.activity_new__session);
        new_id = findViewById(R.id.enter_new_session);
        submit = findViewById(R.id.button_submit_session);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new_id.getText()!=null){
                String new_sess = new_id.getText().toString();
                FirebaseDatabase.getInstance().getReference().child(new_sess).setValue(
                        true
                );
                Intent intent = new Intent(context, AdminSessionEdit.class);
                startActivity(intent);}
            }
        });


    }
}
