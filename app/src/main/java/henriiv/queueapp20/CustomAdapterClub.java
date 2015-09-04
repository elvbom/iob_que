//Den anpassaden queryn, hämtar event som startar och slutar idag

package henriiv.queueapp20;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class CustomAdapterClub extends ParseQueryAdapter<ParseObject> {

    public CustomAdapterClub(Context context) {

        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>(){

            //Kollar dagens datum
            Calendar c = Calendar.getInstance(Locale.UK);
            SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd");
            String formattedDate = df.format(c.getTime());


            public ParseQuery create() {
                Calendar c = Calendar.getInstance(Locale.UK);
                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                String time = df.format(c.getTime());

                //Hämtar event som startar idag
                ParseQuery query1 = new ParseQuery("Event");
                query1.whereEqualTo("start", formattedDate);
                query1.whereEqualTo("type", "Klubb");
                query1.whereLessThanOrEqualTo("startTime", time);

                //Nu försvinner inte event som slutar innan kl 24..

                //Hämtar event som slutar idag, detta för att event som pågår under natten inte ska försvinna
                ParseQuery query2 = new ParseQuery("Event");
                query2.whereEqualTo("end", formattedDate);
                query2.whereEqualTo("type", "Klubb");
                query2.whereGreaterThanOrEqualTo("endTime", time);

                List<ParseQuery<ParseObject>> allMatchesQueryList = new ArrayList<>();
                allMatchesQueryList.add(query1);
                allMatchesQueryList.add(query2);
                ParseQuery query = ParseQuery.or(allMatchesQueryList);

                return query;


            }
        });
    }

    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent) {

        v = View.inflate(getContext(), R.layout.listelement, null);

        // Klubbens namn läggs in
        TextView titleTextView = (TextView) v.findViewById(R.id.textView1);
        titleTextView.setText(object.getString("name"));

        //Nation
        TextView nationView = (TextView) v.findViewById(R.id.textView6);
        nationView.setText(object.getString("nation"));

        // Övrig information läggs in
        TextView timestampView = (TextView) v.findViewById(R.id.textView2);
        timestampView.setText(object.getString("info"));

        //Start- och sluttid läggs in
        TextView tidView = (TextView) v.findViewById(R.id.textView3);
        tidView.setText(object.getString("start") + ", klockan " + object.getString("startTime") + "-"
                 + object.getString("endTime"));

        //Hämtar kötid
        TextView koTidView = (TextView) v.findViewById(R.id.textView4);
        if(object.getString("koTid") != null) {
            koTidView.setText("Senast rapporterad k &ouml; tid: " + object.getString("koTid") + " minuter");
        } else {}

        //Hämta typ av event
        TextView eventType = (TextView) v.findViewById(R.id.textView7);
        eventType.setText(object.getString("type"));

        //Kollar om eninenut
        String varning = object.getString("enInEnUt");
        if(varning!=null){
            TextView enInEnUtView = (TextView) v.findViewById(R.id.textView5);
            enInEnUtView.setText("Eninenut-varning senast kl " + varning);
        }

        return v;

    }

}