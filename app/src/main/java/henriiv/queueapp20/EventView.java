package henriiv.queueapp20;

//Klassen där info om ett specifikt event visas. Eventet kan även raderas.

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.Serializable;

public class EventView extends Activity  implements View.OnClickListener, Serializable {
    private String name;
    private String info;
    private String start;
    private String end;
    private String type;
    private ParseObject currentEvent;
    private String objectID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventview);

        View v = findViewById(R.id.updateEventButton);
        v.setOnClickListener(this);
        View w = findViewById(R.id.deleteEventButton);
        w.setOnClickListener(this);

        Intent i = getIntent();
        objectID = (String)i.getSerializableExtra("event");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");
        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    name = object.getString("name");
                    info = object.getString("info");
                    start = object.getString("start");
                    end = object.getString("end");
                    type = object.getString("type");

                    TextView eventName = (TextView) findViewById(R.id.eventName);
                    eventName.setText(name);

                    TextView eventType = (TextView) findViewById(R.id.eventType);
                    eventType.setText(type);

                    TextView eventInfo = (TextView) findViewById(R.id.eventInfo);
                    eventInfo.setText(info);

                    TextView timeDate = (TextView) findViewById(R.id.eventTimeDate);
                    timeDate.setText("Datum och tid: " + start + "-" + end);

                    currentEvent = object;
                }
            }
        });
    }

    public void onClick(View arg0) {
        if(arg0.getId() == R.id.updateEventButton){
            Intent intent = new Intent(EventView.this, EventUpdate.class);
            intent.putExtra("event", objectID);
            EventView.this.startActivity(intent);
        }

        if(arg0.getId() == R.id.deleteEventButton){
            new AlertDialog.Builder(EventView.this)
                    .setTitle("Radera event?")
                    .setMessage("Vill du verkligen radera eventet?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            currentEvent.deleteInBackground();

                            Toast.makeText(getApplicationContext(),
                                    "Eventet har raderats", Toast.LENGTH_LONG)
                                    .show();

                            Intent intent = new Intent(EventView.this, EventList.class);
                            EventView.this.startActivity(intent);
                            finish();
                        }})
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {}})
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
}
