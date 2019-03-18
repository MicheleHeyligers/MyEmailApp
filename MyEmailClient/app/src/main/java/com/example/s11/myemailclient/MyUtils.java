package com.example.s11.myemailclient;

import android.icu.util.Calendar;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MyUtils {

    public MyUtils() {

    }

    public static void myStaticFunction(){
        // Konsolenausgabe in Android
        System.out.println("Ausgabe mit System.out");
        Log.i("Logausgabe", "log.i ausgeben");
    }

    public EmailProvider setCurrentEmailProvider(int position) {

        String name = "";
        int receivingPort = 0;
        String receivingHost = "";
        String receivingProtocol = "";
        int sendingPort = 0;
        String sendingHost = "";
        String sendingProtocol = "";

        switch(position) {
            case 0:
                name = "GMail";
                receivingPort = 993;
                receivingHost = "imap.gmail.com";
                receivingProtocol = "imaps";
                sendingPort = 465;
                sendingHost = "smtp.gmail.com";
                sendingProtocol = "smtp";

                break;
            case 1:
                name = "GMX";
                receivingPort = 993;
                receivingHost = "imap.gmx.net";
                receivingProtocol = "imaps";
                sendingPort = 587;
                sendingHost = "mail.gmx.net";
                sendingProtocol = "mail";

                break;
            case 2:
                name = "WEB.DE";
                receivingPort = 993;
                receivingHost = "imap.web.de";
                receivingProtocol = "imaps";
                sendingPort = 587;
                sendingHost = "smtp.web.de";
                sendingProtocol = "smtp";

                break;

            default:
                break;
        }

        EmailProvider provider =  new EmailProvider(name, receivingPort, receivingHost, receivingProtocol, sendingPort, sendingHost, sendingProtocol);

        return provider;
    }


    //  help methods for filling lists
    // Email-Eingang
    public ArrayList<Email> ladeEingangsListe(){
        ArrayList<Email> myEmailsList = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            Email myEmail = new Email();

            myEmail.setFrom("von" + i);
            myEmail.setSubject("Betreff Eingang" + i);
            myEmail.setBody("Nachricht Nr. " + i);

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat dateFormater = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy", Locale.GERMANY);
            String formatedDate = dateFormater.format(cal.getTime());

            myEmail.setSentDate(formatedDate);

            myEmailsList.add(myEmail);
        }
        return myEmailsList;
    }

    // gesendetet Emails
    public ArrayList<Email> ladeGesendetListe(){
        ArrayList<Email> myEmailsList = new ArrayList<>();

        for(int i = 0; i < 2; i++) {
            Email myEmail = new Email();

            myEmail.setTo(new String[]{"an" + i});
            myEmail.setSubject("Betreff Gesendet" + i);
            myEmail.setBody("Nachricht Nr. " + i);

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat dateFormater = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy", Locale.GERMANY);
            String formatedDate = dateFormater.format(cal.getTime());

            myEmail.setSentDate(formatedDate);

            myEmailsList.add(myEmail);
        }
        return myEmailsList;
    }

    // Entwürfe
    public ArrayList<Email> ladeEntwuerfeListe(){
        ArrayList<Email> myEmailsList = new ArrayList<>();

        for(int i = 0; i < 2; i++) {
            Email myEmail = new Email();

            myEmail.setFrom("von" + i);
            myEmail.setSubject("Betreff Entwurf" + i);
            myEmail.setBody("Nachricht Nr. " + i);

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat dateFormater = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy", Locale.GERMANY);
            String formatedDate = dateFormater.format(cal.getTime());

            myEmail.setSentDate(formatedDate);

            myEmailsList.add(myEmail);
        }
        return myEmailsList;
    }

    // Spam
    public ArrayList<Email> ladeSpamListe(){
        ArrayList<Email> myEmailsList = new ArrayList<>();

        for(int i = 0; i < 3; i++) {
            Email myEmail = new Email();

            myEmail.setFrom("von" + i);
            myEmail.setSubject("Betreff Spam" + i);
            myEmail.setBody("Nachricht Nr. " + i);

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat dateFormater = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy", Locale.GERMANY);
            String formatedDate = dateFormater.format(cal.getTime());

            myEmail.setSentDate(formatedDate);

            myEmailsList.add(myEmail);
        }
        return myEmailsList;
    }

    // Papierkorb
    public ArrayList<Email> ladePapierkorbListe(){
        ArrayList<Email> myEmailsList = new ArrayList<>();

        for(int i = 0; i < 3; i++) {
            Email myEmail = new Email();

            myEmail.setFrom("von" + i);
            myEmail.setSubject("Betreff Papierkorb " + i);
            myEmail.setBody("Nachricht Nr. " + i);

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat dateFormater = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy", Locale.GERMANY);
            String formatedDate = dateFormater.format(cal.getTime());

            myEmail.setSentDate(formatedDate);

            myEmailsList.add(myEmail);
        }
        return myEmailsList;
    }
}
