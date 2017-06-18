package com.bornearth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.bornearth.gallery.GalleryActivity;
import com.bornearth.utils.BornEarthSharePrefs;
import com.bornearth.utils.BornearthConst;
import com.bornearth.utils.ProfileForm;
import com.example.kevin.bornearth.R;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final long UNE_SECONDE = 1000;

    private static final int PROFILE_FORM_RESULT = 997;
    public static final String JOURS_ECOULES_DEPUIS_VOTRE_NAISSANCE = "Jours écoulés depuis votre naissance : \n%1$d";

    private TextView dateTextView;
    private TextView yearTextView;
    //private TextView monthTextView;
    //private TextView dayTextView;
    private TextView personalInfo;
    private TextView nbrTotalJours;

    private TextView hourMinSecTextView;
    private AppCompatButton photoGalleryBtn;

    private int day;
    private int year;
    private int month;
    private int hour = 0;
    private int minute = 0;
    private int second = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BornEarthSharePrefs.init(this);

        AppCompatButton selectDateButton = (AppCompatButton) findViewById(R.id.button_select_date);
        assert selectDateButton != null;
        selectDateButton.setTransformationMethod(null);
        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(MainActivity.this, ProfileForm.class);
                startActivityForResult(profileIntent, PROFILE_FORM_RESULT);
            }
        });

        photoGalleryBtn = (AppCompatButton) findViewById(R.id.button_go_to_photo_gallery);
        photoGalleryBtn.setVisibility(View.INVISIBLE);
        photoGalleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoGalleryIntent = new Intent(MainActivity.this, GalleryActivity.class);
                startActivity(photoGalleryIntent);
            }
        });

        // Texte view permettant d'afficher l'heure ou le message
        // d'accueil selon qu'on soit connecté ou pas
        dateTextView = (TextView) findViewById(R.id.date_text_area);

        yearTextView = (TextView) findViewById(R.id.year_text_area);
        //monthTextView = (TextView) findViewById(R.id.month_text_area);
        //dayTextView = (TextView) findViewById(R.id.day_text_area);
        personalInfo = (TextView) findViewById(R.id.personal_info);

        hourMinSecTextView = (TextView) findViewById(R.id.bornearth_hour_min_sec);

        nbrTotalJours = (TextView) findViewById(R.id.total_nbr_jours);

        // Charge les infos de l'utilisateur :
        // Date de naissance et identité
        loadPreferences(false);
    }

    /**
     * Charge les information depuis les préférences.
     * Si reload vaut {@code true}, les préférence sont réinitialisées
     * @param reload reload
     */
    private void loadPreferences(boolean reload) {
        if (reload) {
            BornEarthSharePrefs.reloadPreferences();
        }

        if (BornEarthSharePrefs.birthDateAvailable()) {
            day = BornEarthSharePrefs.day;
            month = BornEarthSharePrefs.monht;
            year = BornEarthSharePrefs.year;
            photoGalleryBtn.setVisibility(View.VISIBLE);

            populateViews();
        } else {
            dateTextView.setText(R.string.parlez_vous_frnc);
            dateTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PROFILE_FORM_RESULT) {
            if (resultCode == RESULT_OK) {
                loadPreferences(true);
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private void calculerValeursPourVues(LocalDateTime localDateTime, int year, int month, int day) {
        LocalDateTime diff = minusLocalDateTime(new LocalDateTime(year, month, day, minute, month, second), localDateTime);
        int nbrDays = Days.daysBetween(new LocalDate(year, month, day), LocalDate.now()).getDays();
        //dayTextView.setText(formatterAffichage(diff.getDayOfMonth(), " jours"));
        //monthTextView.setText(formatterAffichage(diff.getMonthOfYear(), " mois"));
        yearTextView.setText(String.format(BornearthConst.TEXTE_AFFICHE_ANNEE_MOIS_JOUR, diff.getYear(), diff.getMonthOfYear(), diff.getDayOfMonth()));
        hourMinSecTextView.setText(String.format("%1$dh %2$dm %3$ds", diff.getHourOfDay(), diff.getMinuteOfHour(), diff.getSecondOfMinute()));
        nbrTotalJours.setText(String.format(JOURS_ECOULES_DEPUIS_VOTRE_NAISSANCE, nbrDays));
    }

    private String formatterAffichage(int val, String suffixe) {
        return "Vous avez : " + val + suffixe;
    }

    private class BornearthTimerTask extends TimerTask {

        BornearthTimerTask() {
            computeValues();
        }

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    computeValues();
                }
            });
        }

        private void computeValues() {
            LocalDateTime tmpDateTime = LocalDateTime.now();
            LocalDateTime periodDiff = minusLocalDateTime(new LocalDateTime(year, month, day, hour, minute, second), tmpDateTime);

            int hoursDiff = periodDiff.getHourOfDay();
            int minDiff = periodDiff.getMinuteOfHour();
            int secDiff = periodDiff.getSecondOfMinute();

            int dayDiff = periodDiff.getDayOfMonth();
            int yearDiff = periodDiff.getYear();
            int monthDiff = periodDiff.getMonthOfYear();

            if (monthDiff == 1) {
                yearTextView.setText(String.format(BornearthConst.TEXTE_AFFICHE_ANNEE_MOIS_JOUR, yearDiff, monthDiff, dayDiff));
            }/*
            if (dayDiff > 29) {
                monthTextView.setText(formatterAffichage(monthDiff, " mois"));
            }
            if (hoursDiff == 0) {
                dayTextView.setText(formatterAffichage(dayDiff, " jours"));
            }*/

            hourMinSecTextView.setText(String.format("%1$dh %2$dm %3$ds", hoursDiff, minDiff, secDiff));
        }
    }

    @SuppressLint("SetTextI18n")
    private void populateViews() {
        if (!BornEarthSharePrefs.nom.trim().equals("")) {
            personalInfo.setText("Bonjour " + BornEarthSharePrefs.prenom + " " + BornEarthSharePrefs.nom);
        }

        dateTextView.setText(new StringBuilder()
                .append("Votre date de naissance : ")
                .append(year).append("-")
                .append(month).append("-")
                .append(day));

        LocalDateTime localDateTime = LocalDateTime.now();

        calculerValeursPourVues(localDateTime, year, month, day);

        Timer timer = new Timer("Bornearth timer", true);
        timer.scheduleAtFixedRate(new BornearthTimerTask(), new Date(), UNE_SECONDE);
    }

    private LocalDateTime minusLocalDateTime(LocalDateTime startT, LocalDateTime endT) {
        return endT
                .minusYears(startT.getYear())
                .minusMonths(startT.getMonthOfYear())
                .minusDays(startT.getDayOfMonth())
                .minusHours(startT.getHourOfDay())
                .minusMinutes(startT.getMinuteOfHour())
                .minusSeconds(startT.getSecondOfMinute());
    }
}
