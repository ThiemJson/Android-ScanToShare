package teneocto.thiemjason.tlu_connect.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.Base64;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.models.NotificationDTO;
import teneocto.thiemjason.tlu_connect.models.ScanningHistoryDTO;
import teneocto.thiemjason.tlu_connect.models.SharedDTO;
import teneocto.thiemjason.tlu_connect.models.SocialNetworkDTO;
import teneocto.thiemjason.tlu_connect.models.UserDTO;
import teneocto.thiemjason.tlu_connect.utils.AppConst;

public class DBHelper extends SQLiteOpenHelper {
    private Context context;
    private static String TAG = "SQLite Helper";

    public DBHelper(Context context) {
        super(context, DBConst.DB_NAME, null, DBConst.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBCommand.DB_CREATE_SN);
        db.execSQL(DBCommand.DB_CREATE_USER);
        db.execSQL(DBCommand.DB_CREATE_SCAN_HISTORY);
        db.execSQL(DBCommand.DB_CREATE_NOTIFICATION);
        db.execSQL(DBCommand.DB_CREATE_SHARED);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXiSTS %s", DBConst.NOTIFICATION_TABLE_NAME));
        db.execSQL(String.format("DROP TABLE IF EXiSTS %s", DBConst.USER_TABLE_NAME));
        db.execSQL(String.format("DROP TABLE IF EXiSTS %s", DBConst.SN_TABLE_NAME));
        db.execSQL(String.format("DROP TABLE IF EXiSTS %s", DBConst.SCAN_TABLE_NAME));
        db.execSQL(String.format("DROP TABLE IF EXiSTS %s", DBConst.SHARED_TABLE_NAME));
    }

    /**
     * Drop database
     *
     * @param dbName database name
     */
    public void dropDatabase(String dbName) {
        Log.i(AppConst.TAG_DBHelper, " ==> Database drop");
        context.deleteDatabase(dbName);
    }


    /**
     * =========================> INSERT
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean USER_Insert(UserDTO userDTO) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        byte[] imageBase64 = Base64.getDecoder().decode(userDTO.getImageBase64());

        contentValues.put(DBConst.USER_ID, userDTO.getId());
        contentValues.put(DBConst.USER_EMAIL, userDTO.getEmail());
        contentValues.put(DBConst.USER_FIRST_NAME, userDTO.getFirstName());
        contentValues.put(DBConst.USER_LAST_NAME, userDTO.getLastName());
        contentValues.put(DBConst.USER_POS, userDTO.getPosition());
        contentValues.put(DBConst.USER_COMPANY, userDTO.getCompany());
        contentValues.put(DBConst.USER_IMAGE, imageBase64);
        long result = sqLiteDatabase.insert(DBConst.USER_TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public boolean SCANNING_HISTORY_Insert(ScanningHistoryDTO scanningHistoryDTO) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConst.SCAN_ID, scanningHistoryDTO.getId());
        contentValues.put(DBConst.SCAN_LOCAL_USER_ID, scanningHistoryDTO.getLocalUserID());
        contentValues.put(DBConst.SCAN_REMOTE_USER_ID, scanningHistoryDTO.getRemoteUserID());
        long result = sqLiteDatabase.insert(DBConst.SCAN_TABLE_NAME, null, contentValues);
        return result != -1;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean SOCIAL_NETWORK_Insert(SocialNetworkDTO socialNetworkDTO) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        byte[] imageBase64 = Base64.getDecoder().decode(socialNetworkDTO.getImageBase64());
        contentValues.put(DBConst.SN_ID, socialNetworkDTO.getId());
        contentValues.put(DBConst.SN_NAME, socialNetworkDTO.getName());
        contentValues.put(DBConst.SN_IMAGE, imageBase64);
        contentValues.put(DBConst.SN_IMAGE, socialNetworkDTO.getName());
        long result = sqLiteDatabase.insert(DBConst.SN_TABLE_NAME, null, contentValues);
        return result != -1;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean NOTIFICATION_Insert(NotificationDTO notificationDTO) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        byte[] imageBase64 = Base64.getDecoder().decode(notificationDTO.getImageBase64());
        contentValues.put(DBConst.NOTIFICATION_ID, notificationDTO.getId());
        contentValues.put(DBConst.NOTIFICATION_IMAGE, imageBase64);
        contentValues.put(DBConst.NOTIFICATION_USER_ID, notificationDTO.getUserID());
        contentValues.put(DBConst.NOTIFICATION_CONTENT, notificationDTO.getContent());
        contentValues.put(DBConst.NOTIFICATION_TITLE, notificationDTO.getTitle());
        long result = sqLiteDatabase.insert(DBConst.NOTIFICATION_TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public boolean SHARED_Insert(SharedDTO sharedDTO) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConst.SHARED_ID, sharedDTO.getId());
        contentValues.put(DBConst.SHARED_USER_ID, sharedDTO.getUserID());
        contentValues.put(DBConst.SHARED_URL, sharedDTO.getUrl());
        contentValues.put(DBConst.SHARED_SN_ID, sharedDTO.getSocialNetWorkID());
        long result = sqLiteDatabase.insert(DBConst.SHARED_TABLE_NAME, null, contentValues);
        return result != -1;
    }

    /**
     * =========================> QUERY
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<UserDTO> USER_Query(int userID) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ArrayList<UserDTO> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DBConst.USER_TABLE_NAME + " WHERE " + DBConst.USER_ID + " = ?", new String[]{userID + ""});
        if (cursor.moveToFirst()) {
            do {
                UserDTO userDTO = new UserDTO(
                        cursor.getString(0),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(2),
                        cursor.getString(5),
                        cursor.getString(5),
                        Base64.getEncoder().encodeToString(cursor.getBlob(1)));
                arrayList.add(userDTO);
            } while (cursor.moveToNext());
            // moving our cursor to next.
        }
        cursor.close();
        return arrayList;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<UserDTO> USER_Query() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ArrayList<UserDTO> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DBConst.USER_TABLE_NAME + " ;", null);
        if (cursor.moveToFirst()) {
            do {
                UserDTO userDTO = new UserDTO(
                        cursor.getString(0),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(2),
                        cursor.getString(5),
                        cursor.getString(6),
                        Base64.getEncoder().encodeToString(cursor.getBlob(1)));
                arrayList.add(userDTO);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<NotificationDTO> NOTIFICATION_Query() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ArrayList<NotificationDTO> arrayList = new ArrayList<>();

        String selection = DBConst.NOTIFICATION_USER_ID + " = ?";
        String[] selectionArgs = {AppConst.SP_CURRENT_USER_ID + ""};

        Cursor cursor = sqLiteDatabase.query(DBConst.NOTIFICATION_TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                byte[] imageBase64 = cursor.getBlob(4);
                String imageBase64String = Base64.getEncoder().encodeToString(imageBase64);

                NotificationDTO notificationDTO = new NotificationDTO(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(3),
                        cursor.getString(2),
                        imageBase64String
                );
                arrayList.add(notificationDTO);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<ScanningHistoryDTO> SCANNING_HISTORY_Query() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ArrayList<ScanningHistoryDTO> arrayList = new ArrayList<>();

        String selection = DBConst.SCAN_LOCAL_USER_ID + " = ?";
        String[] selectionArgs = {AppConst.SP_CURRENT_USER_ID + ""};

        Cursor cursor = sqLiteDatabase.query(DBConst.SCAN_TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                ScanningHistoryDTO scanningHistoryDTO = new ScanningHistoryDTO(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2)
                );
                arrayList.add(scanningHistoryDTO);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<SocialNetworkDTO> SOCIAL_NETWORK_Query() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ArrayList<SocialNetworkDTO> arrayList = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(DBConst.SN_TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                byte[] imageBase64 = cursor.getBlob(1);
                String imageBase64String = Base64.getEncoder().encodeToString(imageBase64);

                SocialNetworkDTO socialNetworkDTO = new SocialNetworkDTO(
                        cursor.getString(0),
                        cursor.getString(2),
                        imageBase64String
                );
                arrayList.add(socialNetworkDTO);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<SharedDTO> SHARED_Query() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ArrayList<SharedDTO> arrayList = new ArrayList<>();

        String selection = DBConst.SHARED_USER_ID + " = ?";
        String[] selectionArgs = {AppConst.SP_CURRENT_USER_ID + ""};

        Cursor cursor = sqLiteDatabase.query(DBConst.SHARED_TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                SharedDTO sharedDTO = new SharedDTO(
                        cursor.getString(0),
                        cursor.getString(2),
                        cursor.getString(1),
                        cursor.getString(3)
                );
                arrayList.add(sharedDTO);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }

    /**
     * =========================> DELETE
     */



    /**
     * =========================> UPDATE
     */
}
