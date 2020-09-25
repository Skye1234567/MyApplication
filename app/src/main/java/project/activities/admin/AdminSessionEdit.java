package project.activities.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import project.objects.database.SessionDatabaseReference;
import project.objects.models.SessionID_Model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminSessionEdit extends AppCompatActivity {
    private SessionID_Model SIM;
    private Context context;
    private ArrayAdapter<String> arrayAdapter;
    private Button edit;
    private SessionDatabaseReference SDR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        SDR = (SessionDatabaseReference) getApplication();

        setContentView(R.layout.activity_admin_session_edit);
        SIM = new ViewModelProvider(this).get(SessionID_Model.class);
        SIM.getSessions().observe(this, new Observer<ArrayList<String>>() {
                    @Override
                    public void onChanged(ArrayList<String> strings) {
                        update_array_adapter(strings);

                    }
                });
        edit = (Button) findViewById(R.id.create_new_sess);
        SDR.setGlobalVarValue("adminhub222");


        arrayAdapter =
                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,new ArrayList<String>());
        // Set The Adapter
        ListView listView = findViewById(R.id.session_id_list);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SDR.setGlobalVarValue(parent.getItemAtPosition(position).toString());

                Intent intent  = new Intent(context, AdminHub.class);
                intent.putExtra("ses_id", parent.getItemIdAtPosition(position));
                startActivity(intent);
            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, New_Session.class);
                startActivity(intent);
            }



    });
    }
    private void update_array_adapter(ArrayList<String> sess_id){
        arrayAdapter.clear();
        arrayAdapter.addAll(sess_id);

    }

}
