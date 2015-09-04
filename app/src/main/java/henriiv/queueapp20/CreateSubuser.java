package henriiv.queueapp20;

//Klassen där admins kan skapa nya subusers. Användaren fyller i subuserns
//- namn
//- "position", vilket ex. kan vara 1Q eller namnet på den klubb subusern anordnar
//- email
//När subusern skapas får hen ett mail med ett automatiskt genererat lösenord.

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class CreateSubuser extends Activity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_createsubuser);

        View v = findViewById(R.id.createNewSubuserButton);
        v.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        if(arg0.getId() == R.id.createNewSubuserButton) {

            ParseUser user = new ParseUser();
            user.setUsername("puss");
            user.setPassword("hej");
            user.setEmail("hejpuss@hej.se");

            //boolean isEmailValid(CharSequence email) {
            //    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
            //}

            //kontrollera att alla fält är ifyllda
            /*if (name.equals("") || email.equals("") || password.equals("")) {
                Toast.makeText(getApplicationContext(),
                        "Fyll i alla fält!",
                        Toast.LENGTH_LONG).show();
            }*/

            user.signUpInBackground(new SignUpCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(getApplicationContext(),
                                "Successfully Signed up, please log in.", Toast.LENGTH_LONG)
                                .show();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "" + e, Toast.LENGTH_LONG)
                                .show();
                    }
                }
            });

            Intent i = new Intent(CreateSubuser.this, AdminDashboard.class);
            CreateSubuser.this.startActivity(i);
        }
    }
}