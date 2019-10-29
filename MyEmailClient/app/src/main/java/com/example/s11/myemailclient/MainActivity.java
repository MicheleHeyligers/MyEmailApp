package com.example.s11.myemailclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{


    public static int EMAILSENT = 1; // Variablen für ResultCode (Konstante!)
    public static int EMAILESC  = 2;
    public static int EMAILDELETE  = 3;


    private Toolbar toolbar;
    private FloatingActionButton fab;
    private TextView noMailsTV;
    private ListView emailListLV;
    private NavDrawerAdapter navDrawerAdapter;
    private ArrayList<String> navDrawerMenuList;
    // actionBarDrawerToggle drawerlistener for the drawer layout
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private ListView navDrawerMenuLV;
    private ArrayList<Email> inputArrayList, trashArrayList, spamArrayList, sentArrayList, draftsArrayList;
    private User currentUser;
    private MyEmailAdapter myEmailAdapter;

    private MyUtils myUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainview);
        /* mit setContentView wird das erstellte Layout mit der Activity(java-class) verknüpft,
            muss immer am Anfang stehen, damit z.B. Buttons auch im Layout gefunden werden*/

        // convert array to arrayList (kurz), da Variable navDrawerMenuList eine Array-LISTE ist u. kein Array !!
        this.navDrawerMenuList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.nav_drawer_menu_items)));
        // navDrawerAdapter wird instanziiert (mit Layout u. Daten)
        navDrawerAdapter = new NavDrawerAdapter(this, R.layout.activity_drawer_listitem, 0, navDrawerMenuList);


        // get current User-Object, statische getIntent-Methode (Inhalt holen)
        // darauf dann bundle.getExtra-Methode aufrufen ("Bündel-Päckchen" wird zurück gegeben)
        Bundle b = getIntent().getExtras();
        // dann wird Bundle b in currentUser geschrieben
        // typecast (User) --> in Datentyp User umwandeln
        // b.get-Methode gibt ein Object per Angabe des Keys zurück, per Typecast auf Datentyp User gesetzt
        currentUser = (User) b.get("currentUser");


        // Verknüpfung Java-Objekte(Variable) mit xml-Elementen(Textelement/Layoutteil) aus activity_mainview.xml
        this.toolbar = findViewById(R.id.toolbar);
        this.fab = findViewById(R.id.fab);
        this.noMailsTV = findViewById(R.id.empty_mails_listview);
        this.drawerLayout = findViewById(R.id.drawer);
        this.emailListLV = findViewById(R.id.listview_mails);
        this.navDrawerMenuLV = findViewById(R.id.left_drawer);

        // Adapter wird der Listview-Variable zugeordnet
        navDrawerMenuLV.setAdapter(navDrawerAdapter);

        // hier wird der DrawerListView mit dem OnItemClickListener verbunden
        // 'this' bezieht sich auf die Klasse, da der Click-Listener global oben implementiert ist
        // gleiches gilt für Email-Listview
        navDrawerMenuLV.setOnItemClickListener(this);
        emailListLV.setOnItemClickListener(this);


        // toolbar setzen
        setSupportActionBar(toolbar);
        // Titel vergeben:
        // getSupportActionBar().setTitle("Eingang");
        // Alternative: hole aus Ressourcen das String-Array "nav_drawer_menu_items"
        // und nehme Index 0 (=Eingang)
        getSupportActionBar().setTitle(getResources().getStringArray(R.array.nav_drawer_menu_items)[0]);

        // Drawerlayout einbinden
        // Drawerlistener, benutzt actionBarDrawerToggle ('Sandwich'-Button oben li. in der App)
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer,R.string.close_drawer);
        actionBarDrawerToggle.syncState();   // ---------------    Hier weitermachen
        // macht Sandwich-Button sichtbar
        this.drawerLayout.addDrawerListener(actionBarDrawerToggle);


        // FAB
        // letztes "this" als Parameter, bezieht sich auf implementierte OnClick-class (oben)
        // this bedeutet: der Listener ist in diesem Fall unsere Klasse View.OnClickListener
        this.fab.setOnClickListener(this);

        // ARRAY-LISTEN für neue/gesendete Emails, Entwürfe, Spam etc.
        this.inputArrayList = new ArrayList<>();
        this.trashArrayList = new ArrayList<>();
        this.spamArrayList = new ArrayList<>();
        this.sentArrayList = new ArrayList<>();
        this.draftsArrayList = new ArrayList<>();

        // neues Objekt der Klasse MyUtils, um Methoden der Klasse aufzurufen
        myUtils = new MyUtils();

        // Zuweisung der generierten Emails zur jeweiligen ArrayListe
        this.inputArrayList = myUtils.ladeEingangsListe();
        this.trashArrayList = myUtils.ladePapierkorbListe();
        this.spamArrayList = myUtils.ladeSpamListe();
        this.sentArrayList = myUtils.ladeGesendetListe();
        this.draftsArrayList = myUtils.ladeEntwuerfeListe();

        // Listviews arbeiten mit Adaptern zusammen, immer! Braucht man, um Liste zu füllen
        // EMail-Adapter instanziieren u. für Eingangsliste benutzen, Liste wird gefüllt, Layout einer einzelnen Zeile (email-listitem) wird übergeben
        // this bedeutet: der context (woher kommt es? Von wo wird es aufgerufen?) ist in diesem Fall von unserer Klasse View.OnClickListener
        // Adapter wird hier erstellt, damit er auf jeden Fall angelegt ist, egal ob Emails da sind oder nicht
        myEmailAdapter = new MyEmailAdapter(this, R.layout.activity_email_listitem,0, inputArrayList);

        // TextView für "keine Emails vorhanden" soll angezeigt werden, wenn vom Server
        // keine Emails zu holen sind (TextView wird auf visible gesetzt)
        if(this.inputArrayList.size() == 0){
            noMailsTV.setVisibility(View.VISIBLE);
        }
        // falls doch Emails da sind, wird dieser TextView nicht angezeigt, sondern die Emails
        else {
            noMailsTV.setVisibility(View.GONE);
            // Listview-Variable mit grade instanziiertem Adapter verbinden
            emailListLV.setAdapter(myEmailAdapter);
        }
    }

    @Override
    public void onClick(View v) {
        // Typecasting für den FAB, dann könnte in if-Bedingung auch "fabClicked.getId()" stehen
        // FloatingActionButton fabClicked = (FloatingActionButton)v;
        if(v.getId()== R.id.fab){
            Intent newEmailIntent = new Intent(this, NewEmailActivity.class);
            // name:"currentUser", currentUser --> das 1. ist der Schlüssel, das 2. der Wert
            // key-value-Paar
            newEmailIntent.putExtra("currentUser", currentUser);
            //startActivity(newEmailIntent); -- Übergabe des Intents, Start der Activity
            //startActivityForResult -- Übergabe des Intents, Start der Activity plus requestCode
            // requestCode (z.B. 1) - gibt an, in welche Activity (z.B. NewEmail) man möchte;
            // der requestCode dient als eine Art Identifier, wenn man zurück kommt in die
            // 'onActivityResult-Methode'
            startActivityForResult(newEmailIntent, 1);

        }
    }

    // Methode, um Menu in der Toolbar anzuzeigen (Menu.xml mit Java-Code verknüpfen)
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //Handle item selection
        switch (item.getItemId()){
            case R.id.action_refresh_emaillist:
                refreshEmaillist();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    // Pop-up mit "jo", wenn refresh-Button gedrückt wird
    private void refreshEmaillist(){
        Toast.makeText(this, "aktualisiert", Toast.LENGTH_LONG).show();
    }

    // Rücksprung-Punkt (CALL-BACK !!!!), wenn man z.B. aus der NewEmail-Activity wieder hierher zurück kommt
    // request-Code kommt aus der Weiterleitung in die nächste Activity, result-Code kommt von
    // dort zurück, Intent beinhaltet die Daten
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // wenn der Back-Button o. Senden-Button in der NewEmailActivity geklickt wurde
        super.onActivityResult(requestCode, resultCode, data);
        // Daten (Email) aus dem Intent aus der NewEmailActivity (Parameter s.oben: data) rausholen
        // (1.Zeile) u. das Objekt mit "sentMail"-Key  aus Bundle b rausholen u.
        // in myEmail (2.Zeile) reinschreiben
        Bundle b = data.getExtras();

        if(b!= null) {
            Email myEmail = (Email) b.get("currentEmail");

            // requestCode == 100 bedeutet, dass wir aus der NewEmail-Activity zurück kommen
            if (requestCode == 100) {
                if (resultCode == EMAILSENT) {
                    // Email wurde abgesendet, dann kommt man hierher zurück
                    // Email wird in Liste "gesendete Emails" gespeichert
                    this.sentArrayList.add(myEmail);
                } else if (resultCode == EMAILESC) {
                    // Email-schreiben wurde abgebrochen, dann kommt man hierher zurück
                    // Email wird in der Entwurfs-Liste gespeichert
                    this.draftsArrayList.add(myEmail);
                }
            }
            // requestCode == 200 bedeutet, dass wir aus der DetailEmail-Activity zurück kommen
            else if (requestCode == 200) {
                if (resultCode == EMAILDELETE) {
                    if (toolbar.getTitle().equals(getResources().getStringArray(R.array.nav_drawer_menu_items)[0])) {
                        // Email aus aktueller Liste rausholen und in die Papierkorb-Liste packen
                        this.inputArrayList.remove(myEmail);
                        this.trashArrayList.add(myEmail);
                    } else if (toolbar.getTitle().equals(getResources().getStringArray(R.array.nav_drawer_menu_items)[1])) {
                        this.sentArrayList.remove(myEmail);
                        this.trashArrayList.add(myEmail);
                    } else if (toolbar.getTitle().equals(getResources().getStringArray(R.array.nav_drawer_menu_items)[2])) {
                        this.draftsArrayList.remove(myEmail);
                        this.trashArrayList.add(myEmail);
                    } else if (toolbar.getTitle().equals(getResources().getStringArray(R.array.nav_drawer_menu_items)[3])) {
                        this.spamArrayList.remove(myEmail);
                        this.trashArrayList.add(myEmail);
                    } else if (toolbar.getTitle().equals(getResources().getStringArray(R.array.nav_drawer_menu_items)[4])) {
                        // Email endgültig aus dem Papierkorb löschen
                        this.trashArrayList.remove(myEmail);
                    }

                }
            }
        }
    }

    // Methode, um Click auf Items auszuwerten (z.B. auf Drawer-Item-Liste)
    // Übergabeparameter: AdapterView parent = Listview von den Emails oder von Drawerliste;
    // View view ist einzelnes Item
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Email clickedEmail = this.inputArrayList.get(position);
        // Vergleich: Id des angeklickten Listview == Id des Drawer-Listviews
        // um zu unterscheiden, welcher Listview (Emailliste o. DrawerMenueliste) geklickt wurde
        if(parent.getId() == R.id.left_drawer){
            openMenu(position);
        }
        else {
            openEmail(position);
        }
    }

    // 'openMenu' zeigt die gewählte Email-Liste an, je nachdem welcher Drawer-Menüeintrag gewählt wurde
    private void openMenu(int pos){
        // geht auch mit switch-case
        if(pos == 0){
            this.inputArrayList = myUtils.ladeEingangsListe();
            // öffne 1. Drawermenueeintrag (Eingang)
            // TextView für "keine Emails vorhanden" soll angezeigt werden, wenn es keine
            // gesendeten Emails gibt(TextView wird auf visible gesetzt)
            if(this.inputArrayList.size() == 0){
                noMailsTV.setVisibility(View.VISIBLE);
            }
            // falls doch Emails da sind, wird dieser TextView nicht angezeigt, sondern die Emails
            else {
                noMailsTV.setVisibility(View.GONE);
                // mit clear()-Methode, wird der Inhalt von der Variablen myEmailAdapter gelöscht
                myEmailAdapter.clear();
                // Adapter-Variable mit neuer Liste füllen
                myEmailAdapter.addAll(inputArrayList);
                // Listview-Variable mit grade neu gefülltem Adapter verbinden
                emailListLV.setAdapter(myEmailAdapter);
            }
        }
        else if(pos == 1){
            // öffne 2. Drawermenue-Eintrag (gesendet)
            if(this.sentArrayList.size() == 0){
                noMailsTV.setVisibility(View.VISIBLE);
            }
            else {
                noMailsTV.setVisibility(View.GONE);
                myEmailAdapter.clear();
                myEmailAdapter.addAll(sentArrayList);
                emailListLV.setAdapter(myEmailAdapter);
            }
        }
        else if(pos == 2) {
            // öffne 3. Drawermenue-Eintrag (Entwürfe)
            if (this.draftsArrayList.size() == 0) {
                noMailsTV.setVisibility(View.VISIBLE);
            }
            else {
                noMailsTV.setVisibility(View.GONE);
                myEmailAdapter.clear();
                myEmailAdapter.addAll(draftsArrayList);
                emailListLV.setAdapter(myEmailAdapter);
            }
        }
        else if(pos == 3){
            // öffne 4. Drawermenue-Eintrag (Spam)
            if(this.spamArrayList.size() == 0){
                noMailsTV.setVisibility(View.VISIBLE);
            }
            else {
                noMailsTV.setVisibility(View.GONE);
                myEmailAdapter.clear();
                myEmailAdapter.addAll(spamArrayList);
                emailListLV.setAdapter(myEmailAdapter);
            }
        }
        else if(pos == 4){
            // öffne 5. Drawermenue-Eintrag (Papierkorb)
            if(this.trashArrayList.size() == 0){
                noMailsTV.setVisibility(View.VISIBLE);
            }
            else {
                noMailsTV.setVisibility(View.GONE);
                myEmailAdapter.clear();
                myEmailAdapter.addAll(trashArrayList);
                emailListLV.setAdapter(myEmailAdapter);
            }
        }
        // Überschrift in der Toolbar ändert sich, entsprechend der Auswahl (Eingang, gesendet....)
        getSupportActionBar().setTitle(getResources().getStringArray(R.array.nav_drawer_menu_items)[pos]);
        // damit das DrawerLayout wieder automatisch geschlossen wird
        drawerLayout.closeDrawer(this.navDrawerMenuLV);
    }

    // 'openEmail' zeigt ausgewählte Email an, je nachdem welche Liste gewählt wurde
    private void openEmail(int pos){
        Email pickedEmail = null;
        // getActionBar() gibt Objekt vom Typ ActionBar zurück, dann hole den Titel aus der Toolbar (ActionBar)
        // und vergleiche ('equals') ihn mit den Texten des String-Arrays zu den
        // Drawer-List-Items-Einträgen --> dann wissen wir das es z.B. die Eingangsliste ist
        if(toolbar.getTitle().equals(getResources().getStringArray(R.array.nav_drawer_menu_items)[0])) {
            // 'pickedEmail' wird mit Email an gewählter Position gefüllt
            pickedEmail = this.inputArrayList.get(pos);
        }
        else if(toolbar.getTitle().equals(getResources().getStringArray(R.array.nav_drawer_menu_items)[1])){
            pickedEmail = this.sentArrayList.get(pos);
        }
        else if(toolbar.getTitle().equals(getResources().getStringArray(R.array.nav_drawer_menu_items)[2])){
            pickedEmail = this.draftsArrayList.get(pos);
        }
        else if(toolbar.getTitle().equals(getResources().getStringArray(R.array.nav_drawer_menu_items)[3])){
            pickedEmail = this.spamArrayList.get(pos);
        }
        else if(toolbar.getTitle().equals(getResources().getStringArray(R.array.nav_drawer_menu_items)[4])){
            pickedEmail = this.trashArrayList.get(pos);
        }

        // 'this' im neuen Intent-Objekt steht für aktuelle Activity, aus der wir kommen
        Intent i = new Intent(this, DetailEmailActivity.class);
        i.putExtra("pickedEmail", pickedEmail);
        //startActivity(i);
        startActivityForResult(i, 200);
    }
}

