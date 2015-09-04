package henriiv.queueapp20;

//Sida där admins och subusers kan skicka push notes om specifika events.

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SendPushNote extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subuserlist);
    }
}
