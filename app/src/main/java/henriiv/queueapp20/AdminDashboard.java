package henriiv.queueapp20;

//Klassen där admins kan skapa nya events, redigera sina events, skapa nya subusers, redigera sina subusers
//kontrollera sina kontouppgifter samt logga ut.

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class AdminDashboard extends ActionBarActivity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParseUser user = ParseUser.getCurrentUser();
        //if (user.getBoolean("isAdmin") == Boolean.TRUE) {
            setContentView(R.layout.activity_admindashboard);
        /*} else {
            setContentView(R.layout.activity_subuserdashboard);
        }*/

        View v = findViewById(R.id.createEventButton);
        v.setOnClickListener(this);
        View w = findViewById(R.id.manageEventButton);
        w.setOnClickListener(this);
        /*View z = findViewById(R.id.createSubuserButton);
        z.setOnClickListener(this);
        View x = findViewById(R.id.manageSubusersButton);
        x.setOnClickListener(this);*/
        View a = findViewById(R.id.manageMyAccount);
        a.setOnClickListener(this);
        View y = findViewById(R.id.logoutButton);
        y.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        if(arg0.getId() == R.id.createEventButton){
            Intent intent = new Intent(this, CreateEvent.class);
            this.startActivity(intent);
        }
        if(arg0.getId() == R.id.manageEventButton){
            Intent intent = new Intent(this, EventList.class);
            this.startActivity(intent);
        }
        /*if(arg0.getId() == R.id.createSubuserButton){
            Intent intent = new Intent(this, CreateSubuser.class);
            this.startActivity(intent);
        }
        if(arg0.getId() == R.id.manageSubusersButton){
            Intent intent = new Intent(this, SubuserList.class);
            this.startActivity(intent);
        }*/
        if(arg0.getId() == R.id.manageMyAccount){
            Intent intent = new Intent(this, MyAccount.class);
            this.startActivity(intent);
        }
        if(arg0.getId() == R.id.logoutButton){ //användaren loggas ut
            ParseUser.logOut();
            Intent intent = new Intent(this,Start.class);
            this.startActivity(intent);
        }
    }
}
