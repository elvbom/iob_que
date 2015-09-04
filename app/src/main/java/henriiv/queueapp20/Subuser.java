package henriiv.queueapp20;

/**
 * Created by Henri IV on 2015-04-19.
 */
public class Subuser {

   /*// Armor.java
import com.parse.ParseObject;
import com.parse.ParseClassName;

@ParseClassName("Armor")
public class Armor extends ParseObject {
}

// App.java
import com.parse.Parse;
import android.app.Application;

public class App extends Application {
  @Override
  public void onCreate() {
    super.onCreate();

    ParseObject.registerSubclass(Armor.class);
    Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);
  }
}



// Armor.java
@ParseClassName("Armor")
public class Armor extends ParseObject {
  public String getDisplayName() {
    return getString("displayName");
  }
  public void setDisplayName(String value) {
    put("displayName", value);
  }
}


Armor armorReference = ParseObject.createWithoutData(Armor.class, armor.getObjectId());


Consider the following code, which sets up a very simple ParseQueryAdapter to display data in a ListView. You can be up and running with a functional ListView full of data with very little configuration.
// Inside an Activity
public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  // Uses a layout with a ListView (id: "listview"), which uses our Adapter.
  setContentView(R.layout.main);

  ParseQueryAdapter<ParseObject> adapter = new ParseQueryAdapter<ParseObject>(this, "Instrument");
  adapter.setTextKey("name");
  adapter.setImageKey("photo");

  ListView listView = (ListView) findViewById(R.id.listview);
  listView.setAdapter(adapter);
}



See below for an example setting up a ParseQueryAdapter to display only punk and metal bands with four or more members, ordered by number of records sold:
ParseQueryAdapter<ParseObject> adapter =
  new ParseQueryAdapter<ParseObject>(this, new ParseQueryAdapter.QueryFactory<ParseObject>() {
    public ParseQuery<ParseObject> create() {
      // Here we can configure a ParseQuery to our heart's desire.
      ParseQuery query = new ParseQuery("Band");
      query.whereContainedIn("genre", Arrays.asList({ "Punk", "Metal" }));
      query.whereGreaterThanOrEqualTo("memberCount", 4);
      query.orderByDescending("albumsSoldCount");
      return query;
    }
  });*/
}
