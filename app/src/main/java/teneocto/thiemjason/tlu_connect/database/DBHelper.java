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
     * @param dbName database name
     */
    public void dropDatabase(String dbName) {
        dropDatabase(dbName);
    }


    /**
     * =========================> INSERT
     */
//    public boolean USER_Insert(String name, int age) {
//        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.facebook);
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("name", name);
//        contentValues.put("age", age);
//        contentValues.put("image", getBitmapAsByteArray(bitmap));
//        long result = sqLiteDatabase.insert("USER", null, contentValues);
//        return result != -1;
//    }



    /**
     * =========================> DELETE
     */

    /**
     * =========================> UPDATE
     */
}
