package nickolai.lisberg.lundby.afragmentedworld.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import nickolai.lisberg.lundby.afragmentedworld.Activities.MainActivity;
import nickolai.lisberg.lundby.afragmentedworld.Models.Movie;
import nickolai.lisberg.lundby.afragmentedworld.R;
import nickolai.lisberg.lundby.afragmentedworld.Utilities.CSVReader;
import nickolai.lisberg.lundby.afragmentedworld.Utilities.MovieAdapter;

import java.io.InputStream;
import java.util.ArrayList;

public class OverviewFragment extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<Movie> arrayOfMovies = new ArrayList<>();
        InputStream inputStream = getResources().openRawResource(R.raw.movielist);
        CSVReader csvReader = new CSVReader(inputStream);
        arrayOfMovies = csvReader.read();
        setListAdapter(new MovieAdapter(getActivity(), arrayOfMovies));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(MainActivity.MOVIE_KEY, (Movie)l.getSelectedItem());
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_list, detailsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
