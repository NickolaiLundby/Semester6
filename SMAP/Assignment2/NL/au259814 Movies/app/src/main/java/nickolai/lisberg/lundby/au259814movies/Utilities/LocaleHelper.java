package nickolai.lisberg.lundby.au259814movies.Utilities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Locale;

// I wish to have in-app functionality of changing the language, eg. at runtime.
// Inspiration for externalizing all the locale functions goes to DevDeeds:
// http://devdeeds.com/android-change-language-at-runtime/
public class LocaleHelper {
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

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return updateResources(context, language);
        }

        return updateResourcesDeprecated(context, language);
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

    // New SDK uses the configuration.setLocale
    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration config = context.getResources().getConfiguration();
        config.setLocale(locale);
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());

        return context.createConfigurationContext(config);
    }

    // Old SDK uses the configuration.locale, which has been deprecated
    // but since min API is 16, we need to support this
    @SuppressWarnings("deprecation")
    private static Context updateResourcesDeprecated(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration config = context.getResources().getConfiguration();
        config.locale = locale;

        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());

        return context;
    }
}
