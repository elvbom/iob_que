//presenterar aktuella klubbar i en lista, klicka på klubben och du står i kö
package henriiv.queueapp20;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.io.Serializable;


public class Klubb extends Activity implements Serializable {
    private ParseQueryAdapter<ParseObject> event;
    private ListView listView;
    //Hämtar klubbar från Parse och gör en lista med deras namn
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klubb);

        final ProgressDialog ringProgressDialog = ProgressDialog.show(Klubb.this, "Laddar klubbar...", "Laddar klubbar ...", true);
        ringProgressDialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    event = new CustomAdapterClub(Klubb.this);
                    event.setTextKey("name");

                    listView = (ListView)findViewById(R.id.list);
                    listView.setAdapter(event);

                    listView.setOnItemClickListener(
                            new AdapterView.OnItemClickListener(){

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    //Klubb text = (Klubb) parent.getItemAtPosition(position);
                                    //Object item = parent.getItemAtPosition(position);
                                    //String value = item.toString();
                                    // ViewGroup row = (ViewGroup) listView.getChildAt(position);
                                    //TextView value = (TextView) row.findViewById((Integer) item);
                                    Toast.makeText(Klubb.this, "Du köar", Toast.LENGTH_LONG).show();

                                    ParseObject selection = (ParseObject) parent.getItemAtPosition(position);
                                    String objectId = selection.getObjectId();

                                    Intent j = new Intent(Klubb.this, KoTid.class);
                                    j.putExtra("event", objectId);

                                    //Tidtagning av kön börjar-->KoTid

                                    startActivity(j);
                                }

                            });

                    Thread.sleep(5000);

                    ringProgressDialog.dismiss();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "Något gick fel när eventen skulle laddas. Försök igen!",
                            Toast.LENGTH_LONG).show();

                    ringProgressDialog.dismiss();
                }
            }
        }).start();

    }
}




