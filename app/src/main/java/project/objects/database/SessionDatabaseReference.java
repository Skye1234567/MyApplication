package project.objects.database;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SessionDatabaseReference extends Application {

        private String mGlobalVarValue;

        public DatabaseReference getGlobalVarValue() {
            if (mGlobalVarValue!=null) return FirebaseDatabase.getInstance().getReference(mGlobalVarValue);
            else return null;
        }

        public void setGlobalVarValue(String str) {
            mGlobalVarValue = str;
        }

}

