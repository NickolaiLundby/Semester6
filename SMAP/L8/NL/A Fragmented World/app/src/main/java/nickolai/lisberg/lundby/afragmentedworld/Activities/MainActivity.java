package nickolai.lisberg.lundby.afragmentedworld.Activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import nickolai.lisberg.lundby.afragmentedworld.Fragment.DetailsFragment;
import nickolai.lisberg.lundby.afragmentedworld.Fragment.OverviewFragment;
import nickolai.lisberg.lundby.afragmentedworld.R;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;

    public final static String MOVIE_KEY = "Movie.Key.Helper";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();

        if(savedInstanceState == null){
            fragmentManager.beginTransaction()
                    .add(R.id.container, new OverviewFragment())
                    .commit();
        }
    }
}
