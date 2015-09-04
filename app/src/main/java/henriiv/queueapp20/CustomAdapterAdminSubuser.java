package henriiv.queueapp20;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

public class CustomAdapterAdminSubuser extends ParseQueryAdapter<ParseObject> {
    public CustomAdapterAdminSubuser(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>(){
            public ParseQuery create(){
                ParseQuery query = new ParseQuery("ParseUser");
                return query;
            }
        });
    }

    public View getItemView(ParseUser user, View v, ViewGroup parent) {

        v = View.inflate(getContext(), R.layout.listelement, null);

        //Hämta eventnamn
        TextView username = (TextView) v.findViewById(R.id.textView1);
        username.setText(user.getString("username"));

        //Hämta datum
        TextView userPosition = (TextView) v.findViewById(R.id.textView2);
        userPosition.setText(user.getString("position"));

        return v;

    }
}
