package nickolai.lisberg.lundby.frags;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class FragsFragment extends Fragment {
    private String text;
    private int number;
    private MainViewModel mainViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frags, container, false);
        final CardAdapter adapter = new CardAdapter();

        RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getAllCards().observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable List<Card> cards) {
                adapter.submitList(cards);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                mainViewModel.delete(adapter.getCardAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Card card) {
                Intent intent = new Intent(getActivity(), CardActivity.class);
                intent.putExtra(Constants.EDIT_EXTRA_ID, card.getCaId());
                intent.putExtra(Constants.EDIT_EXTRA_TITLE, card.getTitle());
                intent.putExtra(Constants.EDIT_EXTRA_SERIES, card.getSeries());
                intent.putExtra(Constants.EDIT_EXTRA_TEXT, card.getText());

                startActivityForResult(intent, Constants.EDIT_CARD_REQUEST);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Constants.ADD_CARD_REQUEST:
                if (resultCode == RESULT_OK) {
                    String title = data.getStringExtra(Constants.EDIT_EXTRA_TITLE);
                    String series = data.getStringExtra(Constants.EDIT_EXTRA_SERIES);
                    String text = data.getStringExtra(Constants.EDIT_EXTRA_TEXT);

                    Card card = new Card(title, series, text);
                    Toast.makeText(getActivity(), "Card saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Card not saved", Toast.LENGTH_SHORT).show();
                }
                break;
            case Constants.EDIT_CARD_REQUEST:
                if (resultCode == RESULT_OK) {
                    String title = data.getStringExtra(Constants.EDIT_EXTRA_TITLE);
                    String series = data.getStringExtra(Constants.EDIT_EXTRA_SERIES);
                    String text = data.getStringExtra(Constants.EDIT_EXTRA_TEXT);

                    int id = data.getIntExtra(Constants.EDIT_EXTRA_ID, -1);
                    if (id == -1) {
                        Toast.makeText(getActivity(), "Data corrupted", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Card card = new Card(title, series, text);
                    card.setCaId(id);

                    //mainViewModel.update(card);
                    Toast.makeText(getActivity(), "Returned from EDIT", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
