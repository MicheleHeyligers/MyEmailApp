package com.example.s11.myemailclient;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyEmailAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<Email> mailsList;

    public MyEmailAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull ArrayList<Email> mailsList) {
        super(context, resource, textViewResourceId, mailsList);
        this.context = context;
        this.mailsList = mailsList;
    }

    // Methode getView ruft Layout auf, was im Adapter-Konstruktor unter "ressource" hinterlegt ist
    // mit dieser Methode werden z.B. alle Emails aufgerufen u. entsprechend in Layouts dargestellt
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Email myemail = mailsList.get(position);

        if(convertView == null){
            // layoutinflater setzt convertView manuell, falls keiner da ist
            // .from ist eine statische Klassenmethode der Klasse LayoutInflater
            // es wurde zuvor kein Objekt der Klasser erzeugt, Methode wird sofort aufgerufen
            LayoutInflater inflater = LayoutInflater.from(context);
            // convertView wird befüllt(inflated) mit Layout
            convertView = inflater.inflate(R.layout.activity_email_listitem, null);
        }

        // Verknüpfung der XML-Elemente mit java
        TextView fromTV = convertView.findViewById(R.id.from);
        TextView subjectTV = convertView.findViewById(R.id.subject);
        TextView msgTV = convertView.findViewById(R.id.messagetext);
        TextView sentDateTV = convertView.findViewById(R.id.sentdate);

        // Daten von einer Email werden in die Textfelder des Layouts rein geschrieben
        fromTV.setText(myemail.getFrom());
        subjectTV.setText(myemail.getSubject());
        msgTV.setText(myemail.getBody());
        sentDateTV.setText(myemail.getSentDate());

        return convertView;
    }
}

