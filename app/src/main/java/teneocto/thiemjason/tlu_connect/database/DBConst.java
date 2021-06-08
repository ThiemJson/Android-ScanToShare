package teneocto.thiemjason.tlu_connect.database;

public class DBConst {
    public static String DB_NAME = "TLUConnectDB.db ";
    public static int DB_VERSION = 1;

    // TABLE - USER
    public static String USER_TABLE_NAME = "USER";
    public static String USER_ID = "USER_ID";
    public static String USER_IMAGE = "USER_IMAGE";
    public static String USER_FIRST_NAME = "USER_FIRST_NAME";
    public static String USER_LAST_NAME = "USER_LAST_NAME";
    public static String USER_EMAIL = "USER_EMAIL";
    public static String USER_POS = "USER_POSITION";
    public static String USER_COMPANY = "USER_COMPANY";

    // TABLE - SCANNING_HISTORY
    public static String SCAN_TABLE_NAME = "SCANNING_HISTORY";
    public static String SCAN_ID = "SCANNING_HISTORY_ID";
    public static String SCAN_USER_ID = "SCANNING_USER_ID";
    public static String SCAN_SOCIAL_NETWORK_ID = "SCANNING_SOCIAL_NETWORK_ID";
    public static String SCAN_URL = "SCANNING_URL";
    public static String SCAN_USER_NAME = "SCANNING_USER_NAME";
    public static String SCAN_USER_IMAGE = "SCANNING_USER_IMAGE";

    // TABLE - NOTIFICATION
    public static String NOTIFICATION_TABLE_NAME = "NOTIFICATION";
    public static String NOTIFICATION_ID = "NOTIFICATION_ID";
    public static String NOTIFICATION_IMAGE = "NOTIFICATION_IMAGE";
    public static String NOTIFICATION_USER_ID = "NOTIFICATION_USER_ID";
    public static String NOTIFICATION_TITLE = "NOTIFICATION_TITLE";
    public static String NOTIFICATION_CONTENT = "NOTIFICATION_CONTENT";
    public static String NOTIFICATION_URL = "NOTIFICATION_URL";

    // TABLE - SOCIAL_NETWORK
    public static String SN_TABLE_NAME = "SOCIAL_NETWORK";
    public static String SN_ID = "SOCIAL_NETWORK_ID";
    public static String SN_NAME = "SOCIAL_NETWORK_NAME";
    public static String SN_IMAGE = "SOCIAL_NETWORK_IMAGE";

    // TABLE - SHARED
    public static String SHARED_TABLE_NAME = "SHARED";
    public static String SHARED_ID = "SHARED_ID";
    public static String SHARED_USER_ID = "SHARED_USER_ID";
    public static String SHARED_SN_ID = "SHARED_SOCIAL_NETWORK_ID";
    public static String SHARED_URL = "SHARED_URL_LINK";
}
