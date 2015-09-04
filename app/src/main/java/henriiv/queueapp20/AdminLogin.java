package henriiv.queueapp20;

//TAR OERHÖRT LÅNG TID ATT FÅ FELMEDDELANDE, VERKAR FUNKA I ÖVRIGT
//GÖR SÅ ATT DET KONTROLLERAS OM DET ÄR EN GILTIG EPOST

//Klassen där admins och subusers loggar in. De kan även gå till "Glömt lösenord" (klass ForgottenPassword).
//Användaren får fylla i sitt användarnamn och lösenord, vilket kollas mot databasen. Stämmer de loggas de in,
//annars dirigeras de om till ForgottenPassword (efter att ha angett fel lösen 10 ggr) och systemadministratörerna larmas.

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AdminLogin extends Activity implements View.OnClickListener {
    private String TAG="Lovisasmed";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "adminlogin");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        View v = findViewById(R.id.loginButton);
        v.setOnClickListener(this);
        View w = findViewById(R.id.forgottenPasswordButton);
        w.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        if (arg0.getId() == R.id.loginButton) {

            EditText username = (EditText) findViewById(R.id.inputUsername);
            String un = username.getText().toString();

            EditText password = (EditText) findViewById(R.id.inputPassword);
            password.setTypeface(Typeface.DEFAULT);
            password.setTransformationMethod(new PasswordTransformationMethod());
            String p = password.getText().toString();

            //kontrollera att alla fält är ifyllda
            if (un.equals("") || p.equals("")) {
                Toast.makeText(getApplicationContext(),
                        "Fyll i alla fält!",
                        Toast.LENGTH_LONG).show();
            }

            ParseUser.logInInBackground(un, p, new LogInCallback() { //användaren loggas in
                public void done(ParseUser user, com.parse.ParseException e) {
                    if (user != null) { //inloggning lyckas
                        Log.i(TAG, "user!=null");
                        Intent intent = new Intent(AdminLogin.this, AdminDashboard.class);
                        AdminLogin.this.startActivity(intent);
                        finish();

                    } else { //inloggingingen misslyckades
                        Toast.makeText(getApplicationContext(),
                                "Någonting gick fel :(. Försök igen!", Toast.LENGTH_LONG)
                                .show();

                        ((EditText) findViewById(R.id.inputUsername)).setText("");
                        ((EditText) findViewById(R.id.inputPassword)).setText("");
                    }
                }
            });
        }
        if (arg0.getId() == R.id.forgottenPasswordButton) {
            Intent intent = new Intent(this, henriiv.queueapp20.ForgottenPassword.class);
            this.startActivity(intent);
        }
    }
}
