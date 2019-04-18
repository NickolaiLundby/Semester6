package nickolai.lisberg.lundby.au259814movies.Models;

public class Constants {
    // Request constants
    public final static int REQUEST_EDIT = 100;
    public final static int REQUEST_DETAIL = 101;

    // Broadcasts
    public final static String BROADCAST_DATABASE_UPDATED = "Broadcast.Helper.Database.Updated";

    // Locale
    public static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";

    // Notifications
    public static final String CHANNEL_ID = "Movie.Notification.Channel.Id";
    public static final String CHANNEL_NAME = "Movie.Channel";
    public static final String CHANNEL_DESCRIPTION = "Movie.Notification.Channel";
    public static final int NOTIFICATION_ID = 1000371;

    // Debugging constants
    public static final String DEBUG_API_TAG = "Debug.Api.Tag";
    public static final String DEBUG_API_NULL = "Null response";
    public static final String DEBUG_API_TITLE = "Movie title: ";
    public static final String DEBUG_API_RAW = "Raw response: ";
    public static final String DEBUG_DATABASE_TAG = "Debug.DB.Tag";
    public static final String DEBUG_DATABASE_ADDED = "Added: ";
    public static final String DEBUG_DATABASE_NOT_ADDED = "Not added: ";
    public static final String DEBUG_DATABASE_DELETED = "Deleted: ";
    public static final String DEBUG_DATABASE_UPDATED = "Updated: ";
    public static final String DEBUG_BROADCAST_TAG = "Debug.Broadcast.Tag";
    public static final String DEBUG_BROADCAST_SENT = "Sent broadcast from Service";
    public static final String DEBUG_BROADCAST_RECEIVED = "Received broadcast in activity";
    public static final String DEBUG_EXCEPTION_TAG = "Debug.Exception.Tag";
    public static final String DEBUG_NOTIFICATION_TAG = "Debug.Notification.Tag";
    public static final String DEBUG_NOTIFICATION_CREATED = "Notification Created";
    public static final String DEBUG_NOTIFICATION_RECEIVED = "Notification Received";
}
