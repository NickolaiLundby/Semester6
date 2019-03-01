package nickolai.lisberg.lundby.alldatabasesbelongtous;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("SELECT * FROM task WHERE place LIKE :place")
    Task findByPlace(String place);

    @Query("SELECT * FROM task WHERE uid LIKE :uid")
    Task findById(int uid);

    @Insert
    void insertTask(Task task);

    @Delete
    void delete(Task task);
}
