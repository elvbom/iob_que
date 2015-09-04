//presenterar aktuella cafeer och restauranger i en lista, klicka på cafet/restaurangen och du väntar på mat
package henriiv.queueapp20;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.io.Serializable;


public class Pub extends Activity implements Serializable {
    private static final String TAG="Lovisasmed";

    //Hämtar cafeer och restauranger från Parse och gör en lista med deras namn
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub);

        final ProgressDialog ringProgressDialog = ProgressDialog.show(Pub.this,
                "Laddar caféer och restauranger...", "Laddar caféer och restauranger...", true);
        ringProgressDialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ParseQueryAdapter<ParseObject> food = new CustomAdapterFood(Pub.this);
                    food.setTextKey("name");

                    ListView listView = (ListView)findViewById(R.id.list);
                    listView.setAdapter(food);
                    listView.setOnItemClickListener(
                            new AdapterView.OnItemClickListener(){

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Toast.makeText(Pub.this, "Du väntar på mat", Toast.LENGTH_LONG).show();

                                    ParseObject selection1 = (ParseObject) parent.getItemAtPosition(position);
                                    String objectId = selection1.getObjectId();

                                    Intent k = new Intent(Pub.this, KoTidPub.class);
                                    k.putExtra("food", objectId);

                                    //Tidtagning av kön börjar-->KoTid
                                    startActivity(k);
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