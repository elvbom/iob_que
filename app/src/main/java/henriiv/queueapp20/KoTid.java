//Tar tid från det att en person klickar på en klubb, tiden stoppas du personen klickar på inne
//Ska senare gå att lämna kön samt varna för en in en ut

package henriiv.queueapp20;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class KoTid extends Activity implements Serializable {

    public Chronometer koChronometer;
    private String objectID;


    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ko_tid);


        final ProgressDialog ringProgressDialog = ProgressDialog.show(KoTid.this, "Laddar events...", "Laddar events ...", true);
        ringProgressDialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Chronometer koChronometer = (Chronometer)findViewById(R.id.chronometer);
                    koChronometer.setBase(SystemClock.elapsedRealtime());
                    koChronometer.start();

                    Button buttonStop = (Button)findViewById(R.id.inneButton);
                    Button buttonVarning = (Button)findViewById(R.id.enInEnUtButton);
                    Button buttonLamna = (Button)findViewById(R.id.lamnaKoButton);

        /*När en klickar på "inne" stoppas tiden (denna tid ska sparas och skickas till Parse, har ännu
        inte lyckats med detta)*/
                    buttonStop.setOnClickListener(new Button.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            koChronometer.stop();
                            Toast.makeText(KoTid.this, "Tack för att du har uppdaterat kötiden och ha en rolig kväll!",
                                    Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(KoTid.this, Start.class);
                            KoTid.this.startActivity(intent);
                        }});
                    //När en klickar på "Lämnar kön" stoppas tiden men ingen info skickas till Parse

        /*Detta är för att spara tiden som går (myElapsedMillis),
        det är denna info som skickas till Parse*/
                    koChronometer.setOnChronometerTickListener(
                            new Chronometer.OnChronometerTickListener(){

                                @Override
                                public void onChronometerTick(Chronometer chronometer) {
                                    // TODO Auto-generated method stub
                                    long myElapsedMillis = SystemClock.elapsedRealtime() - koChronometer.getBase();
                                    long myElapsedMinutes = TimeUnit.MINUTES.convert(myElapsedMillis, TimeUnit.MILLISECONDS);
                                    String strElapsedMillis = ""+ myElapsedMinutes;
                                    String strElapsedMinutes ="Du har köat i "+ myElapsedMinutes + " minuter" ;
                                    TextView e = (TextView)findViewById(R.id.kotid);
                                    e.setText(strElapsedMinutes);
                                    ParseQuery query = new ParseQuery("Event");
                                    ParseObject o = null;

                                    Intent j = getIntent();
                                    objectID = (String)j.getSerializableExtra("event");

                                    try {
                                        o = query.get(objectID);
                                    } catch (com.parse.ParseException f) {
                                        f.printStackTrace();
                                    }
                                    o.put("koTid", strElapsedMillis);
                                    try {
                                        o.save();
                                    } catch (com.parse.ParseException f) {
                                        f.printStackTrace();
                                    }

                                }
                            });


                    //När en klickar på "Varning! En-in-en-ut" skickas info till Parse, då uppdateras när någon
                    // senast rapporterade eninenut
                    //-->Röd markering i appen?
                    buttonVarning.setOnClickListener(new Button.OnClickListener(){

                        //Hämtar tid så att den kan rapporteras till eninenut i Parse
                        Calendar c = Calendar.getInstance(Locale.UK);
                        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                        String time = df.format(c.getTime());
                        @Override
                        public void onClick(View v) {
                            koChronometer.stop();
                            ParseQuery query = new ParseQuery("Event");
                            ParseObject o = null;

                            try {
                                o = query.get(objectID);
                            } catch (com.parse.ParseException f) {
                                f.printStackTrace();
                            }
                            o.put("enInEnUt", time);
                            try {
                                o.save();
                            } catch (com.parse.ParseException f) {
                                f.printStackTrace();
                            }

                            Toast.makeText(KoTid.this, "Tack för att du varnar för en-in-en-ut!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(KoTid.this, Start.class);
                            KoTid.this.startActivity(intent);
                        }


                    });

                    buttonLamna.setOnLongClickListener(new Button.OnLongClickListener(){

                        public boolean onLongClick(View v){
                            Toast.makeText(KoTid.this, "Om du vill sluta köa klicka här", Toast.LENGTH_LONG).show();
                            return true;
                        }
                    });

                    buttonLamna.setOnClickListener(new Button.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            koChronometer.stop();
                            Toast.makeText(KoTid.this, "Du har nu lämnat kön", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(KoTid.this, Start.class);
                            KoTid.this.startActivity(intent);
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
