 package com.example.myapplication;

 import android.app.Activity;
 import android.content.Context;
 import android.content.Intent;
 import android.os.Bundle;
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
 import com.google.firebase.database.DataSnapshot;
 import com.google.firebase.database.DatabaseError;
 import com.google.firebase.database.FirebaseDatabase;
 import com.google.firebase.database.ValueEventListener;

 import java.util.ArrayList;
 import java.util.Random;

 import Objects.Investor;
 import Objects.Manager;
 import Objects.Player;
 import Objects.SimpleLoginHelper;
 import Objects.StringBuilderRandom;
 import androidx.annotation.NonNull;
 import androidx.appcompat.app.AppCompatActivity;


 public class Sign_up_player extends AppCompatActivity {
     final private static String TAG = "SignUPPlayer";
     Context context;
     FirebaseAuth mAuth;
     EditText email;
     EditText password;
     String UID;
     String player_type;


     @Override
     protected void onCreate(Bundle savedInstanceState) {

         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_sign_up_player);

         context = Sign_up_player.this;
         mAuth = FirebaseAuth.getInstance();

         if (mAuth.getCurrentUser()!=null){

             get_PlayerType();
            }else{





         Button button = findViewById(R.id.sign_in_player_button);

         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 password = (EditText) findViewById(R.id.player_password);
                 email = (EditText) findViewById(R.id.player_email);
                 String ps =password.getText().toString();
                 String em = email.getText().toString();
                 if (ps==null) {ps ="";}
                 if(em==null){em="";}
                 if ( 0!=ps.compareTo("") && 0!=em.compareTo("")) {
                     Activity activity = (Activity) context;
                     mAuth = FirebaseAuth.getInstance();
                     mAuth.signInWithEmailAndPassword(em, ps)
                             .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                                 @Override
                                 public void onComplete(@NonNull Task<AuthResult> task) {
                                     if (task.isSuccessful()) {
                                         // Sign in success, update UI with the signed-in user's information

                                         FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                         UID =user.getUid();
                                         final Player player = new Player(UID);
                                         FirebaseDatabase.getInstance().getReference("player_list").child(user.getUid()).setValue(player).addOnCompleteListener(new OnCompleteListener<Void>() {
                                             @Override
                                             public void onComplete(@NonNull Task<Void> task) {


                                                 SimpleLoginHelper simpleLoginHelper = new SimpleLoginHelper(context);
                                                 simpleLoginHelper.KeepLoggedIn(UID);
                                                 setType(player);



                                             }
                                         });


                                     } else {
                                         Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                     }
                                 }
                             });
                 }
                 else{
                     //Toast.makeText(context, "Whats up",Toast.LENGTH_SHORT).show();
                 }

             }
         });


     }}

     private void get_PlayerType() {
         final String id =  mAuth.getCurrentUser().getUid();

         FirebaseDatabase.getInstance().getReference("Managers").addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 Manager manager = dataSnapshot.child(id).getValue(Manager.class);

                 Intent intentman = new Intent(context, Manager_Instructions.class);
                 intentman.putExtra("user_id", id);
                 intentman.putExtra("c", manager.getCompany_symbol());
                 context.startActivity(intentman);
                 finish();



             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {
                 Log.d(TAG, databaseError.getMessage());

             }
         });

         FirebaseDatabase.getInstance().getReference("Investors").addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 if (dataSnapshot.hasChild(id)){
                     Intent intent = new Intent(context, Manager_Instructions.class);
                     intent.putExtra("user_id", id);
                     context.startActivity(intent);
                     finish();

                 }

             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {
                 Log.d(TAG, databaseError.getMessage());

             }
         });
     }


     private void setType(Player player){
         Random r=new Random();

         ArrayList<Integer> arrayList = new ArrayList<>();
         int i;
         for (i=0; i<8;i++) arrayList.add(0);
         for(i=8; i<10;i++) arrayList.add(1);
         Integer integer = r.nextInt(arrayList.size());
         if (integer==1){player.setType("M");
             Manager m = new Manager(player.getID());
             String current_company_symbol = new StringBuilderRandom(3).buildString();
             m.setCompany_symbol(current_company_symbol);
             FirebaseDatabase.getInstance().getReference("Managers").child(player.getID()).setValue(m);
             Intent intent = new Intent(context, Manager_Instructions.class);
             intent.putExtra("user_id", UID);
             intent.putExtra("c", current_company_symbol);
             context.startActivity(intent);}
         else {
             player.setType("I");
             Investor investor = new Investor(player.getID());
             FirebaseDatabase.getInstance().getReference("Investors").child(player.getID()).setValue(investor);
             Intent intent = new Intent(context, Investor_Instructions.class);
             intent.putExtra("user_id", UID);

             context.startActivity(intent);

         }

     }


 }