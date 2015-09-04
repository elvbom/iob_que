package henriiv.queueapp20;

//Klass som är en lista av alla subusers som är länkade till adminen.

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SubuserList extends Activity implements Serializable {
    private ParseQueryAdapter<ParseUser> user;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subuserlist);

        //hitta användarna kopplade till nationen
        /*ParseQuery<ParseUser> query = ParseUser.getQuery();
        final ArrayList names = new ArrayList<String>();
        query.whereEqualTo("nation", ParseUser.getCurrentUser().getString("nation"));query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> userList, com.parse.ParseException e) {
                if (e == null) {
                    for (int i = 0; i < userList.size(); i++) {
                        names.add(userList.get(i).getUsername());
                    }
                    listView = (ListView) findViewById(R.id.usersListView);
                    ArrayAdapter namesArrayAdapter =
                            new ArrayAdapter<String>(getApplicationContext(), R.layout.layout_user_list, names);
                    listView.setAdapter(namesArrayAdapter);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Kunde inte ladda användare :(",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        /*listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ParseQuery<ParseUser> query = ParseUser.getQuery();
                        ParseUser selection = (ParseUser) parent.getItemAtPosition(position);
                        String objectId = selection.getObjectId();

                        Intent i = new Intent(SubuserList.this, SubuserView.class);
                        i.putExtra("user", objectId);
                        startActivity(i);
                    }
                });*/
    }
}