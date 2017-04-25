package com.bornearth.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.kevin.bornearth.R;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.util.Calendar;

import static com.bornearth.utils.BornearthConst.*;

public class ProfileForm extends AppCompatActivity {

    private int day;
    private int year;
    private int month;
    private int hour = 0;
    private int minute = 0;

    private EditText nom;
    private EditText prenom;

    private TextView dateNaissanceView;
    private TextView timeNaissanecView;

    private boolean isReadyToRegister = false;

    private AppCompatButton enregistrerButton;
    private AppCompatButton dateNaissanceButton;
    private AppCompatButton timeNaissanceButton;

    private static final int DATE_DIALOG_ID = 999;
    private static final int TIME_DIALOG_ID = 998;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_form);

        nom = (EditText) findViewById(R.id.nom);
        prenom = (EditText) findViewById(R.id.prenom);

        dateNaissanceView = (TextView) findViewById(R.id.saisi_date_naissance);
        timeNaissanecView = (TextView) findViewById(R.id.saisi_heure_naissance);

        dateNaissanceButton = (AppCompatButton) findViewById(R.id.saisi_date_de_naissance_button);
        timeNaissanceButton = (AppCompatButton) findViewById(R.id.saisi_heure_de_naissance_button);
        enregistrerButton = (AppCompatButton) findViewById(R.id.saisi_enregistrer_vos_informations_button);

        dateNaissanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        timeNaissanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });
        
        enregistrerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIsReadyToRegister()) {
                    BornEarthSharePrefs.storeBirthDate(year, month, day);
                    BornEarthSharePrefs.storeBirthTime(hour, minute, 0);
                    BornEarthSharePrefs.storePersonalInfo(nom.getText().toString(), prenom.getText().toString());

                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Remplissez correctement le formulaire", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @SuppressLint("InlinedApi")
    @Override
    protected Dialog onCreateDialog(int id) {
        // set date / time picker as current date
        final Calendar c = Calendar.getInstance();
        LocalDateTime ldt = new LocalDateTime(c.getTimeInMillis());

        switch (id) {
            case DATE_DIALOG_ID:
                int year_ = ldt.getYear();
                int month_ = ldt.getMonthOfYear();
                int day_ = ldt.getDayOfMonth();

                LocalDate maxDate = LocalDate.now().minusDays(ONE_DAY);

                DatePickerDialog dialog = new DatePickerDialog(this,
                        AlertDialog.THEME_HOLO_DARK, datePickerListener,
                        year_, month_, day_);
                dialog.getDatePicker().setMaxDate(maxDate.toDateTimeAtCurrentTime().getMillis());

                // TODO Use Theme_Material_Dialog_Alert instead of AlertDialog.THEME_HOLO_DARK
                return dialog;

            case TIME_DIALOG_ID:
                int hour_ = ldt.getHourOfDay();
                int minute_ = ldt.getMinuteOfHour();

                return new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int selectedHourOfDay, int selectedMinuteOfHour) {
                        hour = selectedHourOfDay;
                        minute = selectedMinuteOfHour;
                        
                        StringBuilder timeBdr = new StringBuilder();
                        timeBdr.append(selectedHourOfDay).append(" : ").append(selectedMinuteOfHour).append(" : ").append("00");
                        timeNaissanecView.setText(timeBdr.toString());
                    }
                }, hour_, minute_, true);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener =
                        new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth + 1;
            day = selectedDay;

            StringBuilder dateBdr = new StringBuilder();
            dateBdr.append(selectedDay).append(" / ").append(selectedMonth + 1).append(" / ").append(selectedYear);
            dateNaissanceView.setText(dateBdr.toString());

            isReadyToRegister = true;
        }
    };

    private boolean getIsReadyToRegister() {
        return nom != null
                && nom.getText() != null
                && !nom.getText().toString().trim().equals("")
                && prenom != null
                && prenom.getText() != null
                && !prenom.getText().toString().trim().equals("")
                && isReadyToRegister;
    }
}
