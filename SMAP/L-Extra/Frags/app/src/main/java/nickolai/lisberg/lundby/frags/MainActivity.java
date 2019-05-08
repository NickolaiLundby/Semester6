package nickolai.lisberg.lundby.frags;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragsFragment fragment = new FragsFragment();
        Bundle args = new Bundle();
        args.putString("argText", "Hej fra Stanie");
        args.putInt("argNumber", 123);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
