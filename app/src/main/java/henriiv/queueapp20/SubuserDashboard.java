package henriiv.queueapp20;

//Klass som är dashboard för subusers. De kan skapa nya events, redigera events, se över sina kontouppgifter eller logga ut.

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;


public class SubuserDashboard extends ActionBarActivity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admindashboard);

        View v = findViewById(R.id.createEventButton);
        v.setOnClickListener(this);
        View w = findViewById(R.id.manageEventButton);
        w.setOnClickListener(this);
        View a = findViewById(R.id.manageMyAccount);
        a.setOnClickListener(this);
        View y = findViewById(R.id.logoutButton);
        y.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        if(arg0.getId() == R.id.createEventButton){
            Intent intent = new Intent(this, henriiv.queueapp20.CreateEvent.class);

            this.startActivity(intent);
        }
        if(arg0.getId() == R.id.manageEventButton){
            Intent intent = new Intent(this, henriiv.queueapp20.EventList.class);

            this.startActivity(intent);
        }
        if(arg0.getId() == R.id.manageMyAccount){
            Intent intent = new Intent(this, henriiv.queueapp20.MyAccount.class);

            this.startActivity(intent);
        }
        if(arg0.getId() == R.id.logoutButton){
            Intent intent = new Intent(this, henriiv.queueapp20.Start.class);

            this.startActivity(intent);
        }
    }
}
