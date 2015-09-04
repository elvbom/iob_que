package henriiv.queueapp20;

//Klass där admins och subusers kan se info om sitt användarkonto, samt ändra sitt lösenord.

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

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.StringTokenizer;


public class MyAccount extends Activity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaccount);

        View v = findViewById(R.id.newPasswordButton);
        v.setOnClickListener(this);

        TextView username = (TextView) findViewById(R.id.yourUsername);
        String u = ParseUser.getCurrentUser().getUsername();
        username.setText(u);

        TextView email = (TextView) findViewById(R.id.yourEmail);
        String em = ParseUser.getCurrentUser().getEmail();
        email.setText(em);
    }

    public void onClick(View arg0) {
        if(arg0.getId() == R.id.newPasswordButton) {
            final EditText newPassword1 = (EditText) findViewById(R.id.inputNewPassword1);
            newPassword1.setTypeface(Typeface.DEFAULT);
            final EditText newPassword2 = (EditText) findViewById(R.id.inputNewPassword2);
            newPassword2.setTypeface(Typeface.DEFAULT);

            EditText password = (EditText) findViewById(R.id.inputOldPassword);
            password.setTypeface(Typeface.DEFAULT);
            password.setTransformationMethod(new PasswordTransformationMethod());

            if (password.equals("") || newPassword1.equals("") || newPassword2.equals("")) {
                Toast.makeText(getApplicationContext(),
                        "Alla fälten måste vara ifyllda.",
                        Toast.LENGTH_LONG).show();

                ((EditText) findViewById(R.id.inputOldPassword)).setText("");
                ((EditText) findViewById(R.id.inputNewPassword1)).setText("");
                ((EditText) findViewById(R.id.inputNewPassword2)).setText("");

            } else {
                ParseUser.logInInBackground(ParseUser.getCurrentUser().getUsername(), password.getText().toString(), new LogInCallback() {
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            if (newPassword1.getText().toString().equals(newPassword2.getText().toString())) {
                                user = ParseUser.getCurrentUser();
                                String np = newPassword1.getText().toString();
                                user.setPassword(np);
                                user.saveInBackground();

                                Toast.makeText(getApplicationContext(),
                                        "Ditt lösenord har ändrats.",
                                        Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(MyAccount.this, AdminDashboard.class);
                                MyAccount.this.startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Lösenorden stämde inte överrens. Prova att skriva in dem igen.",
                                        Toast.LENGTH_LONG).show();

                                ((EditText) findViewById(R.id.inputOldPassword)).setText("");
                                ((EditText) findViewById(R.id.inputNewPassword1)).setText("");
                                ((EditText) findViewById(R.id.inputNewPassword2)).setText("");
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Det lösenord du angett stämmer inte. Prova att skriva in det igen.",
                                    Toast.LENGTH_LONG).show();

                            ((EditText) findViewById(R.id.inputOldPassword)).setText("");
                            ((EditText) findViewById(R.id.inputNewPassword1)).setText("");
                            ((EditText) findViewById(R.id.inputNewPassword2)).setText("");
                        }
                    }
                });
            }
        }
    }
}
