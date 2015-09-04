package henriiv.queueapp20;

//Klassen där info om ett specifikt event visas. Eventet kan även raderas.

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.Serializable;

public class SubuserView extends Activity  implements View.OnClickListener, Serializable {
    private String name;
    private String email;
    private Boolean isAdmin;
    private ParseObject currentSubuser;
    private String objectID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subuserview);

        View w = findViewById(R.id.deleteUserButton);
        w.setOnClickListener(this);

        Intent i = getIntent();
        objectID = (String)i.getSerializableExtra("user");

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.getInBackground(objectID, new GetCallback<ParseUser>() {
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    name = user.getString("username");
                    email = user.getString("email");
                    isAdmin = user.getBoolean("isAdmin");

                    TextView userName = (TextView) findViewById(R.id.userName);
                    userName.setText(name);

                    TextView userEmail = (TextView) findViewById(R.id.userEmail);
                    userEmail.setText(email);

                    TextView userIsAdmin = (TextView) findViewById(R.id.userIsAdmin);
                    if (isAdmin == Boolean.TRUE) {
                        userIsAdmin.setText("Användaren har administratörsstatus");
                    } else {
                        userIsAdmin.setText("Användaren har inte administratörsstatus");
                    }

                    currentSubuser = user;
                }
            }
        });
    }

    public void onClick(View arg0) {
        if(arg0.getId() == R.id.deleteUserButton){
            if (isAdmin = Boolean.TRUE) {
                Toast.makeText(getApplicationContext(),
                        "Du kan inte radera andra administratörer. Kontakta systemadministratörerna för hjälp.", Toast.LENGTH_LONG)
                        .show();
            } else {
                new AlertDialog.Builder(SubuserView.this)
                        .setTitle("Radera användare?")
                        .setMessage("Vill du verkligen radera användaren?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                currentSubuser.deleteInBackground();

                                Toast.makeText(getApplicationContext(),
                                        "Användaren har raderats", Toast.LENGTH_LONG)
                                        .show();

                                Intent intent = new Intent(SubuserView.this, SubuserList.class);
                                SubuserView.this.startActivity(intent);
                                finish();
                            }})
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {}})
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }
    }
}
