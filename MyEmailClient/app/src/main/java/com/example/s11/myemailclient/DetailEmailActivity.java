package com.example.s11.myemailclient;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class DetailEmailActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private User currentUser;
    private Email currentEmail;

    private TextView fromTV;
    private TextView subjectTV;
    private TextView bodyTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailview);


        // get current user Object
        Bundle b = getIntent().getExtras();
        // nur wenn Objekt 'b' nicht NULL ist, werden Extras mit get() rausgeholt
        if(b != null) {
            // currentUser-Wert wird aus Bundle (Päckchen) rausgesucht u. in Variable currentUser
            // abgespeichert
            currentUser = (User) b.get("currentUser");
            currentEmail = (Email) b.get("pickedEmail");

            // XML mit java verknüpfen (für Email-Objekt
            fromTV =findViewById(R.id.detail_tv_from);
            subjectTV =findViewById(R.id.detail_tv_subject);
            bodyTV = findViewById(R.id.detail_tv_body);
            // Variablen mit Text füllen aus Email-Objekt
            fromTV.setText(currentEmail.getFrom());
            subjectTV.setText(currentEmail.getSubject());
            bodyTV.setText(currentEmail.getBody());
        }

        // toolbar (xml mit java) verknüpfen
        this.toolbar = findViewById(R.id.toolbar_detailview);

        // toolbar setzen
        setSupportActionBar(toolbar);
        // Titel vergeben:
        getSupportActionBar().setTitle(R.string.detail_email);
        //this.toolbar.setTitle(R.string.detail_email);

    }
    // Methode, um Menu in der Toolbar anzuzeigen (Menu.xml mit Java-Code verknüpfen)
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail_email, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle item selection
        switch (item.getItemId()){
            case R.id.action_delete_email:
                deleteEmail();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed(){
        goBackToMain(2);
    }

    private void deleteEmail(){
        // Popup-Methode für Abfrage, ob wirklich gelöscht werden soll
        showDialog();
    }

    private void showDialog(){
        // neues Objekt der Klasse AlertDialog
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(this);
        // Text und Titel setzen
        deleteDialog.setMessage(R.string.deleteDialogText);
        deleteDialog.setTitle(R.string.title_dialog_delete);

        // ja-Button mit onClickListener setzen
        deleteDialog.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    goBackToMain(3);
            }
        });

        // nein-Button mit onClickListener setzen
        deleteDialog.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Pop-up wird einfach geschlossen
                dialogInterface.cancel();

            }
        });

        // AlertDialog-Objekt erzeugen und anzeigen lassen
        AlertDialog d = deleteDialog.create();
        d.show();
    }

    private void goBackToMain(int pos){
        Intent intent = new Intent(DetailEmailActivity.this, MainActivity.class);
        intent.putExtra("currentEmail", currentEmail);
        // zurück in MainActivity mit result-Code 3 (EMAILDELETE) und Intent mit Mail-Objekt
        setResult(pos, intent );
        finish();
    }
}


