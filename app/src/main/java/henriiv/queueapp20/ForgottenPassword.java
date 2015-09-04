package henriiv.queueapp20;

//GÖR SÅ ATT DET KONTROLLERAS OM DET ÄR EN GILTIG EPOST

//Klass där admins och subusers kan ange sin email för att få ett nytt lösenord skickat till sig.

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgottenPassword extends Activity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgottenpassword);
        View v = findViewById(R.id.sendPasswordButton);
        v.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        if(arg0.getId() == R.id.sendPasswordButton){
            //skicka lösenord, meddela om att dt skickats, if-sats

            EditText email = (EditText) findViewById(R.id.inputEmail);
            String em = email.getText().toString();

            //kontrollera att alla fält är ifyllda
            if (em.equals("")) {
                Toast.makeText(getApplicationContext(),
                        "Fyll i alla fält!",
                        Toast.LENGTH_LONG).show();
            }

            validateEmail(em);

            ParseUser.requestPasswordResetInBackground(em,
                    new com.parse.RequestPasswordResetCallback() {
                        public void done(com.parse.ParseException e) {
                            if (e == null) {
                                Toast.makeText(getApplicationContext(),
                                        "Ett email med en länk för att återställa ditt lösenord har skickats till den angivna epostadressen.",
                                        Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ForgottenPassword.this,AdminLogin.class);
                                ForgottenPassword.this.startActivity(intent);

                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Någonting gick fel. Försök igen om en stund.",
                                        Toast.LENGTH_LONG).show();

                                ((EditText) findViewById(R.id.inputEmail)).setText("");
                            }
                        }
                    });
        }
    }

    public void validateEmail(String em) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence emailInput = em;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailInput);
        if (matcher.matches()) {
        } else {
            Toast.makeText(getApplicationContext(),
                    "Emailadressen har inte rätt format. Försök igen.", Toast.LENGTH_LONG)
                    .show();

            ((EditText) findViewById(R.id.inputEmail)).setText("");
        }
    }
}
