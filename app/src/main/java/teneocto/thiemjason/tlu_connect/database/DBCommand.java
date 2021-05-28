package teneocto.thiemjason.tlu_connect.database;

public class DBCommand {

    // TABLE - USER
    public static String DB_CREATE_USER = "CREATE TABLE IF NOT EXISTS " + DBConst.USER_TABLE_NAME + "("
            + DBConst.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DBConst.USER_IMAGE + " BLOB,"
            + DBConst.USER_EMAIL + " TEXT NOT NULL,"
            + DBConst.USER_FIRST_NAME + " TEXT,"
            + DBConst.USER_LAST_NAME + " TEXT,"
            + DBConst.USER_POS + " TEXT,"
            + DBConst.USER_COMPANY + " TEXT"
            + ");";

    public static String DB_INSERT_USER() {
        return "CREATE TABLE IF NOT EXISTS " + DBConst.USER_TABLE_NAME + "("
                + DBConst.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DBConst.USER_IMAGE + " BLOB,"
                + DBConst.USER_EMAIL + " TEXT NOT NULL,"
                + DBConst.USER_FIRST_NAME + " TEXT,"
                + DBConst.USER_LAST_NAME + " TEXT,"
                + DBConst.USER_POS + " TEXT,"
                + DBConst.USER_COMPANY + " TEXT"
                + ");";
    }


    // TABLE - SCANNING_HISTORY
    public static String DB_CREATE_SCAN_HISTORY = "CREATE TABLE IF NOT EXISTS " + DBConst.SCAN_TABLE_NAME + "("
            + DBConst.SCAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DBConst.SCAN_LOCAL_USER_ID + " INTEGER NOT NULL,"
            + DBConst.SCAN_REMOTE_USER_ID + " INTEGER NOT NULL,"
            + " FOREIGN KEY (" + DBConst.SCAN_LOCAL_USER_ID + ") REFERENCES " + DBConst.USER_TABLE_NAME + " (" + DBConst.USER_ID + "), "
            + " FOREIGN KEY (" + DBConst.SCAN_REMOTE_USER_ID + ") REFERENCES " + DBConst.USER_TABLE_NAME + " (" + DBConst.USER_ID + ")"
            + ");";

    // TABLE - NOTIFICATION
    public static String DB_CREATE_NOTIFICATION = "CREATE TABLE IF NOT EXISTS " + DBConst.NOTIFICATION_TABLE_NAME + "("
            + DBConst.NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DBConst.NOTIFICATION_USER_ID + " INTEGER NOT NULL,"
            + DBConst.NOTIFICATION_TITLE + " TEXT,"
            + DBConst.NOTIFICATION_CONTENT + " TEXT,"
            + DBConst.NOTIFICATION_IMAGE + " BLOB,"
            + " FOREIGN KEY (" + DBConst.NOTIFICATION_USER_ID + ") REFERENCES " + DBConst.USER_TABLE_NAME + " (" + DBConst.USER_ID + ") "
            + ");";

    // TABLE - SHARED
    public static String DB_CREATE_SHARED = "CREATE TABLE IF NOT EXISTS " + DBConst.SHARED_TABLE_NAME + "("
            + DBConst.SHARED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DBConst.SHARED_SN_ID + " INTEGER NOT NULL,"
            + DBConst.SHARED_USER_ID + " INTEGER NOT NULL,"
            + DBConst.SHARED_URL + " TEXT NOT NULL,"
            + " FOREIGN KEY (" + DBConst.SHARED_USER_ID + ") REFERENCES " + DBConst.USER_TABLE_NAME + " (" + DBConst.USER_ID + "), "
            + " FOREIGN KEY (" + DBConst.SHARED_SN_ID + ") REFERENCES " + DBConst.SN_TABLE_NAME + " (" + DBConst.SN_ID + ") "
            + ");";

    // TABLE - SOCIAL_NETWORK
    public static String DB_CREATE_SN = "CREATE TABLE IF NOT EXISTS " + DBConst.SN_TABLE_NAME + "("
            + DBConst.SN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DBConst.SN_IMAGE + " BLOB,"
            + DBConst.SN_NAME + " TEXT NOT NULL"
            + ");";
}
