package teneocto.thiemjason.tlu_connect.database;

public class DBConst {
    public static String DB_NAME = "TLUConnectDB.db ";
    public static int DB_VERSION = 1;

    // TABLE - USER
    public static String USER_TABLE_NAME = "USER";
    public static String USER_ID = "USER_ID";
    public static String USER_IMAGE_ID = "USER_IMAGE_ID";
    public static String USER_FIRST_NAME = "USER_FIRST_NAME";
    public static String USER_LAST_NAME = "USER_LAST_NAME";
    public static String USER_EMAIL = "USER_EMAIL";
    public static String USER_POS = "USER_POSITION";
    public static String USER_COMPANY = "USER_COMPANY";

    // TABLE - SCANNING_HISTORY
    public static String SCAN_TABLE_NAME = "SCANNING_HISTORY";
    public static String SCAN_ID = "SCANNING_HISTORY_ID";
    public static String SCAN_LOCAL_USER_ID = "SCANNING_LOCAL_USER_ID";
    public static String SCAN_REMOTE_USER_ID = "SCANNING_REMOTE_USER_ID";

    // TABLE - NOTIFICATION
    public static String NOTIFICATION_TABLE_NAME = "NOTIFICATION";
    public static String NOTIFICATION_ID = "NOTIFICATION_ID";
    public static String NOTIFICATION_USER_ID = "NOTIFICATION_USER_ID";
    public static String NOTIFICATION_TITLE = "NOTIFICATION_TITLE";
    public static String NOTIFICATION_CONTENT = "NOTIFICATION_CONTENT";

    // TABLE - SOCIAL_NETWORK
    public static String SN_TABLE_NAME = "SOCIAL_NETWORK";
    public static String SN_ID = "SOCIAL_NETWORK_ID";
    public static String SN_NAME = "SOCIAL_NETWORK_NAME";
    public static String SN_IMAGE_ID = "SOCIAL_NETWORK_IMAGE_ID";

    // TABLE - SHARED
    public static String SHARED_TABLE_NAME = "SHARED";
    public static String SHARED_ID = "SHARED_ID";
    public static String SHARED_USER_ID = "SHARED_USER_ID";
    public static String SHARED_SN_ID = "SHARED_SOCIAL_NETWORK_ID";
    public static String SHARED_URL = "SHARED_URL_LINK";

    // TABLE - IMAGES_AVATAR
    public static String IMG_AVT_TABLE_NAME = "IMAGES_AVT";
    public static String IMG_AVT_ID = "IMAGES_AVT_USER_ID";
    public static String IMG_AVT_IMAGE = "IMAGES_AVT_IMAGE";

    // TABLE - IMAGES_SOCIAL
    public static String IMG_SOCIAL_TABLE_NAME = "IMAGES_SOCIAL";
    public static String IMG_SOCIAL_ID = "IMAGES_SOCIAL_ID";
    public static String IMG_SOCIAL_IMAGE = "IMAGES_SOCIAL_IMAGE";

}
