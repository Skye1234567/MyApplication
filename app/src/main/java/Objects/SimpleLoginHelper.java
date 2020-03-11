package Objects;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

import static android.provider.Settings.System.getString;

public class SimpleLoginHelper {
    Context context;



    public SimpleLoginHelper(Context context) {
        this.context=context;

    }

    public void KeepLoggedIn(String user){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("pw", user);


        editor.apply();


    }

    public void simpleLogin(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        String user =sharedPreferences.getString("pw","");

        FirebaseAuth.getInstance().signInWithCustomToken(user);


    }
}
