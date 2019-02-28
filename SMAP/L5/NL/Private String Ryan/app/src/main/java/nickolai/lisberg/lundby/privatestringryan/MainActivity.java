package nickolai.lisberg.lundby.privatestringryan;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    // Widgets
    EditText firstName, lastName, age, phoneNumber;

    // Variables
    String strFirstName, strLastName;
    int intAge, intPhoneNumber;

    // Shared preference
    public final static String SHARED_PREFERENCE = "SharedPreference";
    public final static String CONTENT_FIRST_NAME = "ContentFirstName";
    public final static String CONTENT_LAST_NAME = "ContentLastName";
    public final static String CONTENT_AGE = "ContentAge";
    public final static String CONTENT_PHONE_NUMBER = "ContentPhoneNumber";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Widgets
        firstName = findViewById(R.id.et_firstname);
        lastName = findViewById(R.id.et_lastname);
        age = findViewById(R.id.et_age);
        phoneNumber = findViewById(R.id.et_phonenumber);

        // Get shared preference
        SharedPreferences myPref = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE);
        strFirstName = myPref.getString(CONTENT_FIRST_NAME, "First name");
        strLastName = myPref.getString(CONTENT_LAST_NAME, "Last name");
        intAge = myPref.getInt(CONTENT_AGE, 0);
        intPhoneNumber = myPref.getInt(CONTENT_PHONE_NUMBER, 88888888);

        firstName.setText(strFirstName);
        lastName.setText(strLastName);
        age.setText(String.valueOf(intAge));
        phoneNumber.setText(String.valueOf(intPhoneNumber));

    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences.Editor preferenceEditor = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE).edit();

        // Set shared preference
        preferenceEditor.putString(CONTENT_FIRST_NAME, firstName.getText().toString());
        preferenceEditor.putString(CONTENT_LAST_NAME, lastName.getText().toString());
        preferenceEditor.putInt(CONTENT_AGE, Integer.parseInt(age.getText().toString()));
        preferenceEditor.putInt(CONTENT_PHONE_NUMBER, Integer.parseInt(phoneNumber.getText().toString()));
        preferenceEditor.apply();
    }
}
