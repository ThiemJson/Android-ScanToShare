package teneocto.thiemjason.tlu_connect.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.security.acl.LastOwnerException;

public class DBHelper extends SQLiteOpenHelper {
    private static String TAG = "SQLite Helper";

    public DBHelper(Context context) {
        super(context, DBConst.DB_NAME, null, DBConst.DB_VERSION);
        Log.i(TAG, "==> DB On Constructor");
    }

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public DBHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "==> DB On Create");
        db.execSQL(DBCommand.DB_CREATE_IMG_AVT);
        db.execSQL(DBCommand.DB_CREATE_IMG_SN);
        db.execSQL(DBCommand.DB_CREATE_SN);
        db.execSQL(DBCommand.DB_CREATE_USER);
        db.execSQL(DBCommand.DB_CREATE_SCAN_HISTORY);
        db.execSQL(DBCommand.DB_CREATE_NOTIFICATION);
        db.execSQL(DBCommand.DB_CREATE_SHARED);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "==> DB On Upgrade");
        db.execSQL(String.format("DROP TABLE IF EXiSTS %s", DBConst.IMG_AVT_TABLE_NAME));
        db.execSQL(String.format("DROP TABLE IF EXiSTS %s", DBConst.IMG_SOCIAL_TABLE_NAME));
        db.execSQL(String.format("DROP TABLE IF EXiSTS %s", DBConst.NOTIFICATION_TABLE_NAME));
        db.execSQL(String.format("DROP TABLE IF EXiSTS %s", DBConst.USER_TABLE_NAME));
        db.execSQL(String.format("DROP TABLE IF EXiSTS %s", DBConst.SN_TABLE_NAME));
        db.execSQL(String.format("DROP TABLE IF EXiSTS %s", DBConst.SCAN_TABLE_NAME));
        db.execSQL(String.format("DROP TABLE IF EXiSTS %s", DBConst.SHARED_TABLE_NAME));
    }
}
