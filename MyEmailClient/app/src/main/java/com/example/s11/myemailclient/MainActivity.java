package com.example.s11.myemailclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private TextView noMailsTV;
    private ListView emailListLV;
    private DrawerLayout drawerLayout;
    private ListView navDrawerMenuLV;
    private ArrayList<Email> inputArrayList, trashArrayList, spamArrayList, sentArrayList, draftsArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainview);
        /* mit setContentView wird das erstellte Layout mit der Activity(java-class) verknüpft,
            muss immer am Anfang stehen, damit z.B. Buttons auch im Layout gefunden werden*/

        // Verknüpfung Java-Objekte(Variable) mit xml(Textelement/Layoutteil)
        this.toolbar = findViewById(R.id.toolbar);
        this.fab = findViewById(R.id.fab);
        this.noMailsTV = findViewById(R.id.empty_mails_listview);
        this.emailListLV = findViewById(R.id.listview_mails);
        this.drawerLayout = findViewById(R.id.drawer);
        this.navDrawerMenuLV = findViewById(R.id.left_drawer);

        // toolbar setzen
        setSupportActionBar(toolbar);
        // Titel vergeben:
        // getSupportActionBar().setTitle("Eingang");
        // Alternative: hole aus Ressourcen das String-Array "nav_drawer_menu_items"
        // und nehme Index 0 (=Eingang)
        getSupportActionBar().setTitle(getResources().getStringArray(R.array.nav_drawer_menu_items)[0]);

        // FAB
        // letztes "this" als Parameter, bezieht sich auf implementierte OnClick-class (oben)
        this.fab.setOnClickListener(this);

        // ARRAY-LISTEN für neue/gesendete Emails, Entwürfe, Spam etc.
        this.inputArrayList = new ArrayList<>();
        this.trashArrayList = new ArrayList<>();
        this.spamArrayList = new ArrayList<>();
        this.sentArrayList = new ArrayList<>();
        this.draftsArrayList = new ArrayList<>();

        // neues Objekt der Klasse MyUtils, um Methoden der Klasse aufzurufen
        MyUtils myUtils = new MyUtils();

        // Zuweisung der generierten Emails zur jeweiligen ArrayListe
        this.inputArrayList = myUtils.ladeEingangsListe();
        this.trashArrayList = myUtils.ladePapierkorbListe();
        this.spamArrayList = myUtils.ladeSpamListe();
        this.sentArrayList = myUtils.ladeGesendetListe();
        this.draftsArrayList = myUtils.ladeEntwuerfeListe();

        // TextView für "keine Emails vorhanden" soll angezeigt werden, wenn vom Server
        // keine Emails zu holen sind (Liste wird auf visible gesetzt)
        if(this.inputArrayList.size() == 0){
            noMailsTV.setVisibility(View.VISIBLE);
        }
        // falls doch emails da sind, wird dieser Textview nicht angezeigt
        /*else {
            noMailsTV.setVisibility(View.GONE);
        }*/

        // EMail-Adapter instanziieren u. für Eingangsliste benutzen
        MyEmailAdapter myEmailAdapter = new MyEmailAdapter(this, R.layout.activity_email_listitem,0, inputArrayList);
        // Listview-Variable mit grade instanzieertem Adapter verbinden
        emailListLV.setAdapter(myEmailAdapter);
    }

    @Override
    public void onClick(View v) {
        // Typecasting für den FAB, dann könnte in if-Bedingung auch "fabClicked.getId()" stehen
        // FloatingActionButton fabClicked = (FloatingActionButton)v;
        if(v.getId()== R.id.fab){
            Intent newEmailIntent = new Intent(this, NewEmailActivity.class);
            startActivity(newEmailIntent);
            finish();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
