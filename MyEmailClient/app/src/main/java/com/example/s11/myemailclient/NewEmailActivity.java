package com.example.s11.myemailclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

public class NewEmailActivity extends AppCompatActivity {

    private EditText from;
    private EditText to;
    private EditText subject;
    private EditText message;
    private User currentUser;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_email);

        // get current user Object
        Bundle b = getIntent().getExtras();
        // currentUser-Wert wird aus Bundle (Päckchen) rausgesucht u. in Variable currentUser
        // abgespeichert
        currentUser = (User) b.get("currentUser");

        this.from = findViewById(R.id.sendAdress);
        this.to = findViewById(R.id.destAdress);
        this.subject = findViewById(R.id.subject);
        this.message = findViewById(R.id.emailtext);
        this.toolbar = findViewById(R.id.toolbar);

        // setze Benutzernamen in Absender-EditText-Feld
        this.from.setText(currentUser.getUsername());

        // toolbar setzen
        setSupportActionBar(toolbar);
        // Titel vergeben:
        getSupportActionBar().setTitle(R.string.neue_email);
    }

    // Methode, um Menu in der Toolbar anzuzeigen (Menu.xml mit Java-Code verknüpfen)
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_new_mail, menu);
        return true;
    }

    // wertet Clicks auf der Toolbar aus (je nachdem welches Item geclickt wird)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle item selection (was passiert, wenn der Senden-Button gedrückt wurde)
        switch (item.getItemId()){
            case R.id.action_send_email:
                sendEmail(MainActivity.EMAILSENT); // Email wird mit resultcode 1 (Variable aus
                                                   // MainActivity, ist public)
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // wenn der Back-Button geklickt wurde
    @Override

    public void onBackPressed(){
        // Email wird mit resultcode 2(Variable aus Mainactivity, ist public)
        sendEmail(MainActivity.EMAILESC);
        super.onBackPressed();
    }
    private void sendEmail(int resultcode){
        // this bezieht sich auf das aktuelle Objekt 'from' (Edittext from, s.oben)
        // toString-Methode nötig, weil getText() eine "editable" zurück gibt (von einem editText) u. keinen String
        String from = this.from.getText().toString();
        String to = this.to.getText().toString();
        String subject = this.subject.getText().toString();
        String message = this.message.getText().toString();

        // Überprüfung der Eingaben(from / to darf nicht leer sein, gültige Email-Adressen( mit @)nötig)
        // man kann das auch mit isEmpty()-Methode überprüfen (Subject("Betreff")/ Message soll nicht
        // leer sein)
        if(from.length() > 5 && from.contains("@")
            && to.length() > 5 && to.contains("@")){
            if(!subject.isEmpty() && !message.isEmpty()){
                Intent i  = new Intent (this, MainActivity.class);
                // man kann es auch alles einzeln mit i.putExtra("from", from) ect. übergeben, wäre
                // aber nicht objektorientiert; also erst Objekt erschaffen und Daten gleich reinpacken
                Email myEmail = new Email();
                myEmail.setFrom(from);
                myEmail.setTo(new String[]{to});
                myEmail.setSubject(subject);
                myEmail.setBody(message);

                // Objekt myEmail kann übergeben werden, weil Datentyp Email serializable ist
                // Email wird an Intent i angehängt
                i.putExtra("currentMail", myEmail);

                // mit setResult kommt man zurück zur MainActivity
                // u. übergibt die Email (im Intent i) u. den result-Code
                // mit setRestult wird die 'onActivityResult'-Methode  in der mainActivity aufgerufen
                setResult(resultcode, i);
                finish();
            }
        }
        //Toast.makeText(this, R.string.toast_send, Toast.LENGTH_LONG).show();
    }
}

// regulärer Ausdruck, um Email-Adresse zu überprüfen
// "^[a-zA-Z-_\d]+\@[a-zA-Z-_\d]+\.[a-z]{2,}$"