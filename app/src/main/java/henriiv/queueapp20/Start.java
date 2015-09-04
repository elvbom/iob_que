package henriiv.queueapp20;

//Klass där användare välja att se vilka klubbar och pubar som händer idag,
//och admins och subusers kan välja att logga in.

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.parse.Parse;
import com.parse.ParseUser;


public class Start extends ActionBarActivity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Parse.initialize(this, "wj5TLSCSPyL9Zu2gRoNNytLUtUnhal4MPwxI3Xvo", "Aq8kFYQnfo6mYv77lPATH4f2SXkFjmiNnbtQRG3F");
        //Parse.enableLocalDatastore(this);
        //ParseAnalytics.trackAppOpenedInBackground(getIntent()); //För analys i Parse av hur ofta appen öppnas

        /*// Enable Crash Reporting
        ParseCrashReporting.enable(this);
        // Setup Parse
        Parse.initialize(this, "parseAppId", "parseClientKey");
        throw new RuntimeException("Test Exception!");

        Consider adding the following code to your application startup:
        ParseUser.enableAutomaticUser();
        */

        setContentView(R.layout.activity_start);

        View x = findViewById(R.id.adminButton);
        x.setOnClickListener(this);

        View y = findViewById(R.id.knappKlubb);
        y.setOnClickListener(this);

        View z =findViewById(R.id.knappPub);
        z.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {

        /*if (isNetworkAvailable() == Boolean.FALSE) {
            Toast.makeText(getApplicationContext(),
                    "Du måste ha en aktiv internetuppkoppling för att använda den här appen. Mot world wide web!",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(),
                    "World wide web!",
                    Toast.LENGTH_LONG).show();*/

            //till adminsidan
            if (arg0.getId() == R.id.adminButton) {
                //Intent intent = new Intent(this, AdminLogin.class);
                //this.startActivity(intent);
                ParseUser currentUser = ParseUser.getCurrentUser();
                if (currentUser != null) { //om användaren har loggat in på appen förut loggas hen in automatiskt
                    Intent intent = new Intent(this, AdminDashboard.class);
                    this.startActivity(intent);
                } else { //om användaren inte loggat in på appen förut måste hen logga in med användarnamn och lösenord
                    Intent intent = new Intent(this, AdminLogin.class);
                    this.startActivity(intent);
                }
            }
            //till klubbsidan
            if (arg0.getId() == R.id.knappKlubb) {
                Intent intent = new Intent(this, Klubb.class);

                this.startActivity(intent);
            }
            //till pubsida
            if (arg0.getId() == R.id.knappPub) {
                Intent intent = new Intent(this, Pub.class);

                this.startActivity(intent);
            }

    }

    /*private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }*/
}