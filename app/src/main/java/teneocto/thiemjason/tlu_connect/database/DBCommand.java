package teneocto.thiemjason.tlu_connect.database;

public class DBCommand {

    // TABLE - USER
    public static String DB_CREATE_USER = "CREATE TABLE IF NOT EXISTS " + DBConst.USER_TABLE_NAME + "("
            + DBConst.USER_ID + " TEXT PRIMARY KEY,"
            + DBConst.USER_IMAGE + " BLOB,"
            + DBConst.USER_EMAIL + " TEXT NOT NULL,"
            + DBConst.USER_FIRST_NAME + " TEXT,"
            + DBConst.USER_LAST_NAME + " TEXT,"
            + DBConst.USER_POS + " TEXT,"
            + DBConst.USER_COMPANY + " TEXT"
            + ");";


    // TABLE - SCANNING_HISTORY
    public static String DB_CREATE_SCAN_HISTORY = "CREATE TABLE IF NOT EXISTS " + DBConst.SCAN_TABLE_NAME + "("
            + DBConst.SCAN_ID + " TEXT PRIMARY KEY,"
            + DBConst.SCAN_USER_ID + " TEXT NOT NULL,"
            + DBConst.SCAN_SOCIAL_NETWORK_ID + " TEXT NOT NULL,"
            + DBConst.SCAN_USER_NAME + " TEXT NOT NULL,"
            + DBConst.SCAN_USER_IMAGE + " TEXT NOT NULL,"
            + DBConst.SCAN_URL + " TEXT NOT NULL,"
            + " FOREIGN KEY (" + DBConst.SCAN_SOCIAL_NETWORK_ID + ") REFERENCES " + DBConst.SN_TABLE_NAME + " (" + DBConst.SN_ID + "), "
            + " FOREIGN KEY (" + DBConst.SCAN_USER_ID + ") REFERENCES " + DBConst.USER_TABLE_NAME + " (" + DBConst.USER_ID + ")"
            + ");";

    // TABLE - NOTIFICATION
    public static String DB_CREATE_NOTIFICATION = "CREATE TABLE IF NOT EXISTS " + DBConst.NOTIFICATION_TABLE_NAME + "("
            + DBConst.NOTIFICATION_ID + " TEXT PRIMARY KEY ,"
            + DBConst.NOTIFICATION_USER_ID + " TEXT NOT NULL,"
            + DBConst.NOTIFICATION_TITLE + " TEXT,"
            + DBConst.NOTIFICATION_CONTENT + " TEXT,"
            + DBConst.NOTIFICATION_IMAGE + " BLOB,"
            + DBConst.NOTIFICATION_URL + " TEXT,"
            + " FOREIGN KEY (" + DBConst.NOTIFICATION_USER_ID + ") REFERENCES " + DBConst.USER_TABLE_NAME + " (" + DBConst.USER_ID + ") "
            + ");";

    // TABLE - SHARED
    public static String DB_CREATE_SHARED = "CREATE TABLE IF NOT EXISTS " + DBConst.SHARED_TABLE_NAME + "("
            + DBConst.SHARED_ID + " TEXT PRIMARY KEY ,"
            + DBConst.SHARED_SN_ID + " TEXT NOT NULL,"
            + DBConst.SHARED_USER_ID + " TEXT NOT NULL,"
            + DBConst.SHARED_URL + " TEXT NOT NULL,"
            + " FOREIGN KEY (" + DBConst.SHARED_USER_ID + ") REFERENCES " + DBConst.USER_TABLE_NAME + " (" + DBConst.USER_ID + "), "
            + " FOREIGN KEY (" + DBConst.SHARED_SN_ID + ") REFERENCES " + DBConst.SN_TABLE_NAME + " (" + DBConst.SN_ID + ") "
            + ");";

    // TABLE - SOCIAL_NETWORK
    public static String DB_CREATE_SN = "CREATE TABLE IF NOT EXISTS " + DBConst.SN_TABLE_NAME + "("
            + DBConst.SN_ID + " TEXT PRIMARY KEY,"
            + DBConst.SN_IMAGE + " BLOB,"
            + DBConst.SN_NAME + " TEXT NOT NULL"
            + ");";
}
