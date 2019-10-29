package com.example.s11.myemailclient;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NavDrawerAdapter  extends ArrayAdapter {

    private Context context;
    private ArrayList<String> navDrawList;

    public NavDrawerAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull ArrayList<String> navDrawList) {
        super(context, resource, textViewResourceId, navDrawList);
        this.context = context;
        this.navDrawList = navDrawList;
    }

    // Methode getView ruft Layout auf, was im Adapter-Konstruktor unter "ressource" hinterlegt ist
    // mit dieser Methode werden z.B. alle Menüpunkte des Drawer-Menüs aufgerufen u. entsprechend in Layouts dargestellt
    // Methode wird überschrieben, kommt aus ArrayAdapter-Klasse, die oben mit extends implementiert wird
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // hier wird ein einzelner Eintrag in die Variable myDrawlistItem geschrieben
        String myDrawlistItem = navDrawList.get(position);

        if (convertView == null) {
            // layoutinflater setzt convertView manuell, falls keiner da ist
            // .from ist eine statische Klassenmethode der Klasse LayoutInflater,
            // es wurde zuvor kein Objekt der Klasser erzeugt, Methode wird sofort aufgerufen
            LayoutInflater inflater = LayoutInflater.from(context);
            // convertView wird befüllt(inflated) mit Layout
            convertView = inflater.inflate(R.layout.activity_drawer_listitem, null);
        }

        // Verknüpfung des XML-Elements mit java
        TextView listitem = convertView.findViewById(R.id.drawer_listitem);


        // Daten von einzelnem Menüpunkt (in Variable myDrawlistItem gespeichert, s.oben),
        // werden in die Textfelder des Layouts rein geschrieben
        listitem.setText(myDrawlistItem);

        return convertView;
    }
}
