package project.activities.admin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;

import androidx.appcompat.app.AppCompatActivity;

public class Enter_Num_Players extends AppCompatActivity {
    EditText admin_entry;
    Button final_submission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter__num__players);
    }
}
