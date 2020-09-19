package Project.Objects.Database;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SessionDatabaseReference extends Application {

        private String mGlobalVarValue="wank";

        public DatabaseReference getGlobalVarValue() {
            return FirebaseDatabase.getInstance().getReference(mGlobalVarValue);
        }

        public void setGlobalVarValue(String str) {
            mGlobalVarValue = str;
        }

}

