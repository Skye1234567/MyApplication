package project.activities.admin;

        import androidx.activity.OnBackPressedCallback;
        import project.activities.player.MainActivity;
        import project.objects.database.ROUNDDatabase;
        import project.objects.database.SessionDatabase;
        import project.objects.database.SessionDatabaseReference;
        import project.objects.economics.Session;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

        import com.example.myapplication.R;
        import com.google.firebase.database.DatabaseReference;

        import java.util.Observable;
        import java.util.Observer;

public class AdminHub extends AppCompatActivity {

    private SessionDatabaseReference SDR;
    private Context context;
    private ROUNDDatabase RD;

    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_hub);
        Button EditPractice;
        Button EditRound1;
        Button EditRound2;
        Button back_to_menu;
        Button open_market;
        Button close_market;
        Button viewMarkets;

        open_market = findViewById(R.id.open_markets_admin);
        close_market = findViewById(R.id.close_markets_admin);
        context = this;
        SDR = (SessionDatabaseReference) getApplication();
        if (SDR.getGlobalVarValue()==null){
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        }


        open_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference sess_id = SDR.getGlobalVarValue();
                sess_id.child("ALLOW_TRADES").setValue(true);


            }
        });
        close_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SDR = (SessionDatabaseReference) getApplication();
                DatabaseReference sess_id = SDR.getGlobalVarValue();
                sess_id.child("ALLOW_TRADES").setValue(false);
                RD= new ROUNDDatabase(sess_id);
                RD.increase_round();



            }
        });
        SDR = (SessionDatabaseReference) getApplication();

        SessionDatabase SD = new SessionDatabase(SDR.getGlobalVarValue());

        SD.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                session = (Session) arg;

            }
        });
        SD.setParam();

        context =this;
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


        back_to_menu = findViewById(R.id.to_menu);
        back_to_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, Admin_Menu.class));
            }
        });
        viewMarkets = findViewById(R.id.adminmarket);
       viewMarkets.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(context, MarketPlaceForAdmin.class);
               startActivity(intent);
           }
       });
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Intent intent = new Intent(context, AdminSessionEdit.class);
                startActivity(intent);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);





    }

}
