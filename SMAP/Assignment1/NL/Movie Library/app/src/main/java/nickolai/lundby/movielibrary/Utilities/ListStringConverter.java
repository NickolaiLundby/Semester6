package nickolai.lundby.movielibrary.Utilities;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

// Need to convert the List<String> that is Movie Genres to something that can be handled by Room DB.
// Android developer: https://developer.android.com/reference/android/arch/persistence/room/TypeConverter
// Stackoverflow example: https://stackoverflow.com/questions/44986626/android-room-database-how-to-handle-arraylist-in-an-entity
public class ListStringConverter {

    @TypeConverter
    public static List<String> fromString(String value) {
        Gson gson = new Gson();
        List<String> result = new ArrayList<>();

        if (value == null) {
            return result;
        }

        Type listType = new TypeToken<List<String>>() {}.getType();

        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String objectListToString(List<String> object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
