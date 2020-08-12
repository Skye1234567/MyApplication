package Project.Activities.Admin;

        import Project.Activities.Admin.Set_Parameters;
        import Project.Activities.Admin.Set_Parameters_Schedule;
        import Project.Activities.Admin.Sign_up_admin;
        import Project.Objects.Database.SessionDatabase;
        import Project.Objects.Economics.Session;
        import Project.Objects.Handlers.AdminTime;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

        import com.example.myapplication.R;
        import com.google.firebase.auth.FirebaseAuth;

        import java.util.Observable;
        import java.util.Observer;

public class AdminHub extends AppCompatActivity {
    private Button EditPractice;
    private Button EditRound1;
    private Button EditRound2;
    private Button EditSchedule;
    private  Button back_to_menu;
    private SessionDatabase SD;
    private Context context;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth mauth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_admin_hub);
        if ( mauth.getCurrentUser()==null){
            Intent intent = new Intent(context, Sign_up_admin.class);
            context.startActivity(intent);
        }
        SD =new SessionDatabase();

        SD.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                session = (Session) arg;

            }
        });
        SD.setParam();

        context =this;
        EditSchedule = findViewById(R.id.setschedhub);
        EditRound2 = findViewById(R.id.setround_2hub);
        EditRound1 = findViewById(R.id.setround_1hub);
        EditPractice = findViewById(R.id.setpracticehub);


        EditPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =  new Intent(context, Set_Parameters.class);
                intent.putExtra("child", "practice");
                if (session!=null) intent.putExtra("market", session.getPractice());
                startActivity(intent);

            }
        });
        EditRound1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(context, Set_Parameters.class);
                intent.putExtra("child", "round_1");
                if (session!=null)intent.putExtra("market", session.getRound_1());
                startActivity(intent);

            }
        });
        EditRound2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(context, Set_Parameters.class);
                intent.putExtra("child", "round_2");
                if (session!=null)intent.putExtra("market", session.getRound_2());
                startActivity(intent);

            }
        });

        EditSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(context, Set_Parameters_Schedule.class);

                startActivity(intent);

            }
        });
        back_to_menu = findViewById(R.id.to_menu);
        back_to_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, Admin_Menu.class));
            }
        });



    }
}
