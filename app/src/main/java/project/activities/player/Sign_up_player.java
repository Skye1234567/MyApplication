 package project.activities.player;

 import android.app.Activity;
 import android.content.Context;
 import android.content.Intent;
 import android.os.Bundle;
 import android.util.Log;
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
 import com.google.firebase.auth.FirebaseUser;
 import com.google.firebase.database.DataSnapshot;
 import com.google.firebase.database.DatabaseError;
 import com.google.firebase.database.ValueEventListener;

 import androidx.activity.OnBackPressedCallback;
 import project.activities.investors.MarketPlace;
 import project.activities.managers.Manager_Home_Page;
 import project.business_logic.New_Game;
 import project.objects.database.SessionDatabaseReference;
 import project.objects.handlers.StringBuilderRandom;
 import project.objects.personel.Investor;
 import project.objects.personel.Manager;
 import project.objects.personel.Player;
 import androidx.annotation.NonNull;
 import androidx.appcompat.app.AppCompatActivity;


 public class Sign_up_player extends AppCompatActivity {
     final private static String TAG = "SignUPPlayer";
     Context context;
     FirebaseAuth mAuth;
     EditText email;
     EditText password;
     String UID;

     long manager_num;
     SessionDatabaseReference SDR ;



     @Override
     protected void onCreate(Bundle savedInstanceState) {

         super.onCreate(savedInstanceState);
         SDR =   (SessionDatabaseReference) getApplication();
         context =this;
         if (SDR.getGlobalVarValue()==null){
             Intent intent = new Intent(context, MainActivity.class);
             startActivity(intent);
         }
         setContentView(R.layout.activity_sign_up_player);
         OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
             @Override
             public void handleOnBackPressed() {
                 // Handle the back button event
             }
         };
         getOnBackPressedDispatcher().addCallback(this, callback);






         final Button button = findViewById(R.id.sign_in_player_button);
         Button back = findViewById(R.id.back_button);
         back.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(context, GameMenu.class);
                 startActivity(intent);
             }
         });


         password = findViewById(R.id.player_password);
         email = findViewById(R.id.player_email);

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

         mAuth = FirebaseAuth.getInstance();

         if (mAuth.getCurrentUser()!=null){
             email.setVisibility(View.INVISIBLE);
             password.setVisibility(View.INVISIBLE);

             get_PlayerType();
            }else{


         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 password = findViewById(R.id.player_password);
                 email = findViewById(R.id.player_email);
                 final String ps =password.getText().toString();
                 final String em = email.getText().toString()+"@market.com";
                 if (ps==null) return;
                 if(em==null)return;
                 if ( 0!=ps.compareTo("") && 0!=em.compareTo("")) {
                     Activity activity = (Activity) context;
                     mAuth = FirebaseAuth.getInstance();
                     mAuth.signInWithEmailAndPassword(em, ps)
                             .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                                 @Override
                                 public void onComplete(@NonNull Task<AuthResult> task) {
                                     if (task.isSuccessful()) {
                                         // Sign in success, update UI with the signed-in user's information

                                         final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                         UID =user.getUid();
                                         SDR.getGlobalVarValue().child("markets").child("starting_sum").addListenerForSingleValueEvent(
                                                 new ValueEventListener() {
                                                     @Override
                                                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                         if (snapshot!=null){
                                                             Player player = new Player(UID);
                                                             player.setCash( snapshot.getValue(Integer.class));
                                                             SDR.getGlobalVarValue().child("player_list")
                                                                     .child(user.getUid()).setValue(player).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                 @Override
                                                                 public void onComplete(@NonNull Task<Void> task) {

                                                                     get_PlayerType();



                                                                 }
                                                             });
                                                         }

                                                     }

                                                     @Override
                                                     public void onCancelled(@NonNull DatabaseError error) {

                                                     }
                                                 }
                                         );



                                     } else {
                                         Toast.makeText(context, "Sign in failed",Toast.LENGTH_SHORT).show();




                                     }
                                 }
                             });
                 }
                 else{
                     Toast.makeText(context, "Invalid entries",Toast.LENGTH_SHORT).show();
                 }

             }
         });


     }}

     private void get_PlayerType() {
         final String id = mAuth.getCurrentUser().getUid();
         SDR.getGlobalVarValue().child("Managers").addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 manager_num = dataSnapshot.getChildrenCount();
                 if (dataSnapshot.hasChild(id)) {
                     Manager manager = dataSnapshot.child(id).getValue(Manager.class);

                 Intent intentman = new Intent(context, Manager_Home_Page.class);
                 intentman.putExtra("manager", manager);
                 context.startActivity(intentman);

                 finish();
                 } else get_investors();


             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {
                 Log.d(TAG, databaseError.getMessage());

             }
         });





     }

private void get_investors(){
         final String id = mAuth.getCurrentUser().getUid();

    SDR.getGlobalVarValue().child("Investors").addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if (dataSnapshot.hasChild(id)) {
                Investor i = dataSnapshot.child(id).getValue(Investor.class);

                Intent intent = new Intent(context, MarketPlace.class);
                intent.putExtra("investor", i);
                context.startActivity(intent);
                finish();

            } else setType(new Player(id));


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.d(TAG, databaseError.getMessage());

        }
    });



}





     private void setType(Player player){



         if (manager_num<4){player.setType("M");
             Manager m = new Manager(player.getID());
             String current_company_symbol = new StringBuilderRandom(3).buildString();
             m.setCompany_symbol(current_company_symbol);

             SDR.getGlobalVarValue() .child("Managers").child(player.getID()).setValue(m);
             Intent intent = new Intent(context, Manager_Home_Page.class);

             intent.putExtra("manager", m);
             context.startActivity(intent);}
         else {
             player.setType("I");
            New_Game NG = new New_Game(player.getID(),SDR.getGlobalVarValue());
            new Thread(NG).start();

             Investor investor = new Investor(player.getID());

             SDR.getGlobalVarValue().child("Investors").child(player.getID()).setValue(investor);
             Intent intent = new Intent(context, MarketPlace.class);

             intent.putExtra("investor", investor);

             context.startActivity(intent);

         }

     }





 }


