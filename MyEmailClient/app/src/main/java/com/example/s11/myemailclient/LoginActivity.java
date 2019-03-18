package com.example.s11.myemailclient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private EditText inputUsername;
    private EditText inputPassword;
    private Button cancelBtn;
    private Button loginBtn;
    private Spinner loginSpinner;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private boolean firstRunnable;
    private int currentProviderPos; //globale Variable, damit sie auch in anderen Methoden benutzt werden kann

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get-sharedpref Object, um Boolean abzuspeichern
        // es wird gespeichert, ob User schon angemeldet ist in der App oder
        // ob er die App zum 1. Mal startet
        this.sp = getPreferences(Context.MODE_PRIVATE);
        this.editor = sp.edit();
        // get value for firstRun-key from file, gibt es hier keinen Wert --> true
        this.firstRunnable = sp.getBoolean("firstRun", true);

        if(firstRunnable) {
            /* mit setContentView wird das erstellte Layout mit der Activity(java) verknüpft,
            muss immer am Anfang stehen, damit z.B. Buttons auch im Layout gefunden werden*/
            setContentView(R.layout.activity_login);

            // Verknüpfung Java-Objekt(Variable) mit xml(Textelement/Layoutteil)
            // falls nicht verknüpft bleibt oben deklarierte Variable leer(d.h. null, führt
            // dann zu "null-pointer-exception" bei Zugriff!!!
            // R...steht für 'Ressourcen", alle Id's der Layout-Elemente sind in Ressourcen-Datei,
            // auch andere Elemente ohne Id werden in Ressourcen-Datei gespeichert u. bekommen eine Nr.
            this.inputUsername = findViewById(R.id.login_input_username);
            this.inputPassword = findViewById(R.id.login_input_password);

            this.cancelBtn = findViewById(R.id.login_btn_escape);
            /*1. Weg, Anweisung für Button zu codieren - bei zu vielen Buttons zu unübersichtlich
            this.cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  code (Anweisung, was beim klicken passieren soll)
                }
            });*/
            /* 2. Weg, Anweisung für Button zu codieren mit this (global oben in class-
            Deklaration eingefügt mit "implements View.OnClickListener"* - ist BESSER!!*/
            this.cancelBtn.setOnClickListener(this);

            this.loginBtn = findViewById(R.id.login_btn_signIn);
            this.loginBtn.setOnClickListener(this);

            this.loginSpinner = findViewById(R.id.login_spinner_emailprovider);
            this.loginSpinner.setOnItemSelectedListener(this);
        }
        else{
            // bei wiederholtem Aufrufen direkt in else-Zweig (nicht beim ersten Aufruf!)
            // Mainactivity aufrufen mit explizitem Intent von "this"(= activity_login)
            // zu activity_mainview
            Intent intentMainActivity = new Intent(this, MainActivity.class);
            // Werte aus SharedPref. werden eingelesen in Variablen gespeichert
            String userName = sp.getString("username",  "");
            String userPassword = sp.getString("pas", "");

            // User-Objekt anlegen u. Name/Passwort übergeben
            // nicht nötig, wenn Daten per sharedPref. übergeben werden
            User user = new User(userName, userPassword);
            intentMainActivity.putExtra("currentUser", user);

            // Übergabe der einzelnen Attribute nicht nötig, weil sie mit dem User-Objekt zusammen übergeben werden
            //intentMainActivity.putExtra("username", userName);
            //intentMainActivity.putExtra("password", userPassword);
            intentMainActivity.putExtra("providerpos", currentProviderPos);
            // Mainactivity wird gestartet
            startActivity(intentMainActivity);
            // login_activity wird geschlossen
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        // Button-Klick-Auswertung; View v geht, weil Button von Klasse View erbt
        // Id wird verglichen beim click
        if(v.getId() == R.id.login_btn_signIn){
            //login-logik, zuerst eingegebenen Text holen u. in String umwandeln
            String userName = inputUsername.getText().toString();
            String userPassword = inputPassword.getText().toString();

            if (!userName.isEmpty() && !userPassword.isEmpty()){
                // wenn beide nicht leer sind, dann mache das u. das...
                if(userName.equals("lotta@gmx.de") && userPassword.equals("12345")){
                    // inputs korrekt
                    // Mainactivity aufrufen mit explizitem Intent, von "this"(= activity_login)
                    // zu activity_mainview
                    Intent intentMainActivity = new Intent(this, MainActivity.class);

                    // SharedPref., Strings holen und mit commit()-Methode abspeichern
                    editor.putString("username", userName);
                    editor.putString("pas", userPassword);
                    // boolean mit key "firstRun" auf false setzen, d.h. es ist nicht die 1. Anmeldung
                    editor.putBoolean("firstRun", false);
                    editor.commit();
                    // es geht auch statt commit() editor.apply()-Methode

                    // Daten mitschicken (Username, Passwort, Spinnerauswahl-Providername)
                    // User-Objekt anlegen u. Name/Passwort übergeben/mitschicken
                    User user = new User(userName, userPassword);
                    intentMainActivity.putExtra("currentUser", user);

                    // Übergabe der einzelnen Attribute nicht nötig, weil sie mit dem User-Objekt zusammen übergeben werden
                    //intentMainActivity.putExtra("username", userName);
                    //intentMainActivity.putExtra("password", userPassword);
                    intentMainActivity.putExtra("providerpos", currentProviderPos);
                    // Mainactivity wird gestartet
                    startActivity(intentMainActivity);
                    // login_activity wird geschlossen
                    finish();
                }
                else{
                    Toast.makeText(this,  R.string.inputs_wrong,Toast.LENGTH_LONG).show();
                }
            }
            else {
                // Info/Notification, verschwindet von selbst wieder(nach kurzer o. längerer Zeit)
                // wichtig: am Ende show()-Methode ran, sonst ist der Toast nicht sichtbar
                Toast.makeText(this,  R.string.empty_inputs_toast,Toast.LENGTH_LONG).show();
            }
        }
        else if(v.getId() == R.id.login_btn_escape){
            // app schließen
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.currentProviderPos = position;
    }

    @Override // wird nix reingeschrieben, da bei Nicht-Auswahl auch nix passiert
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
