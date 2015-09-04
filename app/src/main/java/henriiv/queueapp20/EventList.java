package henriiv.queueapp20;

//Klassen där en lista med events laddas för admins och subusers, där de kan gå vidare till EventView för
//specifika events.

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EventList extends Activity implements Serializable {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventlist);

        final ProgressDialog ringProgressDialog = ProgressDialog.show(EventList.this, "Laddar events...", "Laddar events ...", true);
        ringProgressDialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {


                    ParseQueryAdapter event = new CustomAdapterAdminEvent(EventList.this);
                    event.setTextKey("name");

                    ListView listView = (ListView)findViewById(R.id.list);
                    listView.setAdapter(event);

                    listView.setOnItemClickListener(
                            new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");
                                    ParseObject selection = (ParseObject) parent.getItemAtPosition(position);
                                    String objectId = selection.getObjectId();

                                    Intent i = new Intent(EventList.this, EventView.class);
                                    i.putExtra("event", objectId);
                                    startActivity(i);
                                }
                            });
                    Thread.sleep(5000);

                    ringProgressDialog.dismiss();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Något gick fel när eventen skulle laddas. Försök igen!",
                            Toast.LENGTH_LONG).show();

                    ringProgressDialog.dismiss();
                }
            }
        }).start();
    }
}