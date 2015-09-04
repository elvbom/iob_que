package henriiv.queueapp20;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class KoTidPub extends Activity implements Serializable {

    public Chronometer koChronometer;
    private String objectID;


    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ko_tid_pub);


        final ProgressDialog ringProgressDialog = ProgressDialog.show(KoTidPub.this, "Laddar events...", "Laddar events ...", true);
        ringProgressDialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Chronometer koChronometer = (Chronometer)findViewById(R.id.chronometer);
                    koChronometer.setBase(SystemClock.elapsedRealtime());
                    koChronometer.start();

                    Button buttonStop = (Button)findViewById(R.id.inneButtonPub);
                    Button buttonVarning = (Button)findViewById(R.id.varningPub);
                    Button buttonLamna = (Button)findViewById(R.id.lamnaKoButtonPub);

        /*När en klickar på "inne" stoppas tiden (denna tid ska sparas och skickas till Parse, har ännu
        inte lyckats med detta)*/
                    buttonStop.setOnClickListener(new Button.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            koChronometer.stop();
                            Toast.makeText(KoTidPub.this, "Tack för att du har uppdaterat kötiden och hoppas att maten är god!",
                                    Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(KoTidPub.this, Start.class);
                            KoTidPub.this.startActivity(intent);
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
                                    String strElapsedMinutes ="Du har väntat i "+ myElapsedMinutes + " minuter" ;
                                    TextView e = (TextView)findViewById(R.id.kotidPub);
                                    e.setText(strElapsedMinutes);
                                    ParseQuery query = new ParseQuery("Event");
                                    ParseObject o = null;

                                    Intent k = getIntent();
                                    objectID = (String)k.getSerializableExtra("food");

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

                            Toast.makeText(KoTidPub.this, "Tack för att du uppdaterat kötiden!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(KoTidPub.this, Start.class);
                            KoTidPub.this.startActivity(intent);
                        }


                    });

                    buttonLamna.setOnLongClickListener(new Button.OnLongClickListener(){

                        public boolean onLongClick(View v){
                            Toast.makeText(KoTidPub.this, "Om du vill sluta köa klicka här", Toast.LENGTH_LONG).show();
                            return true;
                        }
                    });

                    buttonLamna.setOnClickListener(new Button.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            koChronometer.stop();
                            Toast.makeText(KoTidPub.this, "Du har nu lämnat kön", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(KoTidPub.this, Start.class);
                            KoTidPub.this.startActivity(intent);
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
