package sv.org.arrupe.holaandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText nameInput;
    private EditText birthDateInput;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.nameInput);
        birthDateInput = findViewById(R.id.birthDateInput);
        Button greetButton = findViewById(R.id.greetButton);
        resultText = findViewById(R.id.resultText);

        greetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGreeting();
            }
        });
    }

    private void showGreeting() {

        String name = nameInput.getText().toString().trim();
        String birthDateText =
                birthDateInput.getText().toString().trim();

        if (name.isEmpty()) {
            nameInput.setError("Ingresa tu nombre");
            nameInput.requestFocus();
            return;
        }

        if (birthDateText.isEmpty()) {
            birthDateInput.setError(
                    "Ingresa tu fecha de nacimiento"
            );
            birthDateInput.requestFocus();
            return;
        }

        SimpleDateFormat dateFormat =
                new SimpleDateFormat(
                        "dd/MM/yyyy",
                        Locale.getDefault()
                );

        dateFormat.setLenient(false);

        try {
            Date birthDate = dateFormat.parse(birthDateText);

            if (birthDate == null) {
                birthDateInput.setError("Fecha inválida");
                return;
            }

            Calendar birthCalendar = Calendar.getInstance();
            birthCalendar.setTime(birthDate);

            Calendar today = Calendar.getInstance();

            if (birthCalendar.after(today)) {
                birthDateInput.setError(
                        "La fecha no puede estar en el futuro"
                );
                birthDateInput.requestFocus();
                return;
            }

            int age =
                    today.get(Calendar.YEAR)
                            - birthCalendar.get(Calendar.YEAR);

            int currentMonth = today.get(Calendar.MONTH);
            int birthMonth = birthCalendar.get(Calendar.MONTH);

            int currentDay =
                    today.get(Calendar.DAY_OF_MONTH);

            int birthDay =
                    birthCalendar.get(Calendar.DAY_OF_MONTH);

            boolean hasNotHadBirthday =
                    currentMonth < birthMonth
                            || (currentMonth == birthMonth
                            && currentDay < birthDay);

            if (hasNotHadBirthday) {
                age--;
            }

            String greeting =
                    "Hola " + name
                            + ",\nTu edad actual es "
                            + age
                            + " años";

            resultText.setText(greeting);
            resultText.setVisibility(View.VISIBLE);

        } catch (ParseException error) {
            birthDateInput.setError(
                    "Usa el formato dd/MM/yyyy"
            );
            birthDateInput.requestFocus();
        }
    }
}