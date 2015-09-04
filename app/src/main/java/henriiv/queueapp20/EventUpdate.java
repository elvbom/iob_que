package henriiv.queueapp20;

//Klassen där en admin eller subuser kan uppdatera ett event. Användaren kan redigera följande info om eventet:
//- vilken typ av event det är: lunch, pub, klubb
//- när eventet börjar och slutar (tid och datum)
//- ett eventnamn
//- eventinfo

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class EventUpdate extends Activity implements View.OnClickListener {
    private String objectID;
    private String name;
    private String info;
    private String start;
    private String startTime;
    private String end;
    private String endTime;
    private String type;
    private String s;
    private String st;
    private String en;
    private String ent;
    private String n;
    private String i;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventupdate);

        View v = findViewById(R.id.updateEventButton);
        v.setOnClickListener(this);

        Intent i = getIntent();
        objectID = (String)i.getSerializableExtra("event");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");
        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    name = object.getString("name");
                    info = object.getString("info");
                    start = object.getString("start");
                    startTime = object.getString("startTime");
                    end = object.getString("end");
                    endTime = object.getString("endTime");
                    type = object.getString("type");

                    if (type.equals("Lunch")) {
                        RadioGroup group = (RadioGroup) findViewById(R.id.radioEventType);
                        group.check(R.id.radioLunch);
                    } else if (type.equals("Pub")) {
                        RadioGroup group = (RadioGroup) findViewById(R.id.radioEventType);
                        group.check(R.id.radioPub);
                    } else if (type.equals("Klubb")) {
                        RadioGroup group = (RadioGroup) findViewById(R.id.radioEventType);
                        group.check(R.id.radioClub);
                    }

                    TextView eventName = (TextView) findViewById(R.id.inputEventName);
                    eventName.setText(name);

                    TextView eventInfo = (TextView) findViewById(R.id.inputEventInfo);
                    eventInfo.setText(info);

                    TextView dateStart = (TextView) findViewById(R.id.inputStartDate);
                    dateStart.setText(start);

                    TextView timeStart = (TextView) findViewById(R.id.inputStartTime);
                    timeStart.setText(startTime);

                    TextView dateEnd = (TextView) findViewById(R.id.inputEndDate);
                    dateEnd.setText(end);

                    TextView timeEnd = (TextView) findViewById(R.id.inputEndTime);
                    timeEnd.setText(endTime);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Fel: " + e, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onClick(View arg0) {
        if(arg0.getId() == R.id.updateEventButton) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");

            query.getInBackground(objectID, new GetCallback<ParseObject>() {
                public void done(ParseObject event, ParseException e) {
                    if (e == null) {
                        int checkedType = ((RadioGroup) findViewById(R.id.radioEventType)).getCheckedRadioButtonId();
                        RadioButton checkedTypeRadio = (RadioButton) findViewById(checkedType);
                        type = checkedTypeRadio.getText().toString();

                        EditText start = (EditText) findViewById(R.id.inputStartDate);
                        s = start.getText().toString();

                        EditText startTime = (EditText) findViewById(R.id.inputStartTime);
                        st = startTime.getText().toString();

                        EditText end = (EditText) findViewById(R.id.inputEndDate);
                        en = end.getText().toString();

                        EditText endTime = (EditText) findViewById(R.id.inputEndTime);
                        ent = endTime.getText().toString();

                        EditText name = (EditText) findViewById(R.id.inputEventName);
                        n = name.getText().toString();
                        EditText info = (EditText) findViewById(R.id.inputEventInfo);
                        i = info.getText().toString();

                        //kontrollera att alla fält är ifyllda
                        if (n.equals("") || i.equals("") || s.equals("") || en.equals("")) {
                            Toast.makeText(getApplicationContext(),
                                    "Fyll i alla fält!",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            //spara ny uppdaterad information om eventet
                            event.put("name", n);
                            event.put("info", i);
                            event.put("type", type);
                            event.put("start", s);
                            event.put("startTime", st);
                            event.put("end", en);
                            event.put("endTime", ent);
                            //event.put("repeat", rep);
                            event.saveInBackground();

                            Toast.makeText(getApplicationContext(),
                                    "Ditt event har uppdaterats!", Toast.LENGTH_LONG)
                                    .show();

                            Intent intent = new Intent(EventUpdate.this, AdminDashboard.class);
                            EventUpdate.this.startActivity(intent);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Fel! " + e, Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
