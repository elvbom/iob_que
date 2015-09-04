package henriiv.queueapp20;

//Klassen där en admin eller subuser kan skapa ett nytt event. Användaren fyller i följande info om eventet:
//- vilken typ av event det är: lunch, pub, klubb
//- om klubb: vad det är för inträde
//- när eventet börjar och slutar (tid och datum)
//- om eventet ska upprepas, och isf hur ofta
//- ett eventnamn
//- eventinfo

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Calendar;
import java.util.Date;

public class CreateEvent extends Activity implements View.OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createevent);
        View v = findViewById(R.id.createNewEventButton);
        v.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        if(arg0.getId() == R.id.createNewEventButton){

            ParseObject event = new ParseObject("Event");

            int checkedType = ((RadioGroup)findViewById( R.id.radioEventType)).getCheckedRadioButtonId();
            RadioButton checkedTypeRadio = (RadioButton) findViewById(checkedType);
            String type = checkedTypeRadio.getText().toString();

            /*if(type == "Klubb") {
                EditText coverCharge = (EditText) findViewById(R.id.coverCharge);
                String cc = coverCharge.getText().toString();
                event.put("cover charge", cc);
            } else {
                event.put("cover charge", null);
            }*/

            EditText start = (EditText) findViewById(R.id.inputStartDate);
            String s = start.getText().toString();
            EditText startTime = (EditText) findViewById(R.id.inputStartTime);
            String st = startTime.getText().toString();
            EditText end = (EditText) findViewById(R.id.inputEndDate);
            String e = end.getText().toString();
            EditText endTime = (EditText) findViewById(R.id.inputEndTime);
            String et = endTime.getText().toString();

            /*int checkedRepeat = ((RadioGroup)findViewById( R.id.repeatEvent)).getCheckedRadioButtonId();
            RadioButton checkedRepeatRadio = (RadioButton) findViewById(checkedRepeat);
            String repeat = checkedRepeatRadio.getText().toString();
            if(repeat == "Ja") {
                rep = Boolean.TRUE;
            } else {
                rep = Boolean.FALSE;
            }*/

            EditText name = (EditText) findViewById(R.id.inputEventName);
            String n = name.getText().toString();
            EditText info = (EditText) findViewById(R.id.inputEventInfo);
            String i = info.getText().toString();

            //kontrollera att alla fält är ifyllda
            if (name.equals("") || i.equals("") || type.equals("") || s.equals("") || e.equals("")) {
                Toast.makeText(getApplicationContext(),
                        "Fyll i alla fält!",
                        Toast.LENGTH_LONG).show();
            } else {
                //lägger in data om eventet
                event.put("name", n);
                event.put("info", i);
                event.put("type", type);
                event.put("start", s);
                event.put("startTime", st);
                event.put("end", e);
                event.put("endTime", et);
                //binder eventet till den nation användaren har
                event.put("nation", ParseUser.getCurrentUser().getString("nation"));
                //event.put("repeat", rep);
                event.saveInBackground();

                Toast.makeText(getApplicationContext(),
                        "Ditt event har sparats!", Toast.LENGTH_LONG)
                        .show();

                Intent intent = new Intent(CreateEvent.this, AdminDashboard.class);
                CreateEvent.this.startActivity(intent);
            }
        }
    }
}
