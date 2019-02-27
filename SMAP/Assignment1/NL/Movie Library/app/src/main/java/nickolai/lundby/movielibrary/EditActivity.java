package nickolai.lundby.movielibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EditActivity extends AppCompatActivity {

    // Variables

    // Widgets
    Button btnOkay, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Variable initialization

        // Widget initialization
        btnOkay = findViewById(R.id.edit_buttonOkay);
        btnCancel = findViewById(R.id.edit_buttonCancel);

        // Listeners
        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnOkayClick();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnCancelClick();
            }
        });
    }

    private void BtnOkayClick()
    {

    }

    private void BtnCancelClick()
    {

    }
}
