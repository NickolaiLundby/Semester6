package nickolai.lisberg.lundby.frags;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import nickolai.lisberg.lundby.frags.DB.CardRepository;

public class MainViewModel extends AndroidViewModel {
    private CardRepository repository;
    private LiveData<List<Card>> allCards;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new CardRepository(application);
        allCards = repository.getAllCards();
    }

    public void insert(Card card) {
        repository.insert(card);
    }

    public void update(Card card) {
        repository.update(card);
    }

    public void delete(Card card) {
        repository.delete(card);
    }

    public void deleteAllCards() {
        repository.deleteAllCards();
    }

    public LiveData<List<Card>> getAllCards() {
        return allCards;
    }
}
