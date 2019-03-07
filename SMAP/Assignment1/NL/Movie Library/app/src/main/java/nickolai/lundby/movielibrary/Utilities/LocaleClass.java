package nickolai.lundby.movielibrary.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import java.util.Locale;

// Inspiration for externalizing all the locale functions goes to DevDeeds:
// http://devdeeds.com/android-change-language-at-runtime/

public class LocaleClass {
    private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";

    public static Context onAttach(Context context) {
        String language = getPersistedData(context, Locale.getDefault().getLanguage());
        return setLocale(context, language);
    }

    public static String getLanguage(Context context){
        return getPersistedData(context, Locale.getDefault().getLanguage());
    }

    public static Context setLocale(Context context, String language) {
        persist(context, language);

        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration config = context.getResources().getConfiguration();
        config.setLocale(locale);
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());

        return context.createConfigurationContext(config);
    }

    private static void persist(Context context, String language) {
        SharedPreferences myPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = myPref.edit();

        editor.putString(SELECTED_LANGUAGE, language);
        editor.apply();
    }
    private static String getPersistedData(Context context, String defaultLanguage) {
        SharedPreferences myPref = PreferenceManager.getDefaultSharedPreferences(context);
        return myPref.getString(SELECTED_LANGUAGE, defaultLanguage);
    }
}
