package henriiv.queueapp20;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class CustomAdapterAdminEvent  extends ParseQueryAdapter<ParseObject> {
    public CustomAdapterAdminEvent(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>(){
            public ParseQuery create(){
                Calendar c = Calendar.getInstance(Locale.UK);
                SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd");
                String formattedDate = df.format(c.getTime());

                ParseQuery queryDelete = new ParseQuery("Event");
                //bara events från nationen användaren tillhör laddas
                queryDelete.whereLessThan("start", formattedDate);
                //queryDelete.;

                ParseQuery query = new ParseQuery("Event");
                //bara events från nationen användaren tillhör laddas
                query.whereEqualTo("nation", ParseUser.getCurrentUser().getString("nation"));
                return query;
            }
        });
    }

    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent) {

        v = View.inflate(getContext(), R.layout.listelement_event, null);

        //Hämta eventnamn
        TextView eventName = (TextView) v.findViewById(R.id.textView1);
        eventName.setText(object.getString("name"));

        //Hämta datum
        TextView eventDate = (TextView) v.findViewById(R.id.textView2);
        eventDate.setText(object.getString("start") + "-" + object.getString("end"));

        //Hämta typ av event
        TextView eventType = (TextView) v.findViewById(R.id.textView3);
        eventType.setText(object.getString("type"));

        return v;

    }
}
