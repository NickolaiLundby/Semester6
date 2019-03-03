package nickolai.lisberg.lundby.alldatabasesbelongtous;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;

import java.util.List;
import java.util.Random;

@Entity
public class Task {

    @PrimaryKey
    private int uid;

    private String place;
    private String description;
    private List<String> dogs;

    public Task(String place, String description, List<String> dogs){
        Random r = new Random();
        this.uid = r.nextInt(999999);
        this.place = place;
        this.description = description;
        this.dogs = dogs;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getDogs() {
        return dogs;
    }

    public void setDogs(List<String> dogs) {
        this.dogs = dogs;
    }
}
