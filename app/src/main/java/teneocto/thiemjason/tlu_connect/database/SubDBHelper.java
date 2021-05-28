package teneocto.thiemjason.tlu_connect.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

import teneocto.thiemjason.tlu_connect.R;

public class SubDBHelper extends SQLiteOpenHelper {
    Context context;

    public SubDBHelper(Context context) {
        super(context, "main.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USER (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, age INTEGER, image BLOB);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USER");
    }

    public void dropDB(){
        context.deleteDatabase("main.db");
    }

    public boolean insertData(String name, int age) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.facebook);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("age", age);
        contentValues.put("image", getBitmapAsByteArray(bitmap));
        long result = sqLiteDatabase.insert("USER", null, contentValues);
        return result != -1;
    }

    public boolean updateData(String name, int age) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("age", age);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM USER WHERE id = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = sqLiteDatabase.update("USER", contentValues, "name = ? ", new String[]{name});
            return result != -1;
        }
        return false;
    }

    public boolean printData(ImageView imageView) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM USER ", null);
        if (cursor.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                Log.i("DATABASE 0 ", cursor.getString(0));
                Log.i("DATABASE 1 ", cursor.getString(1));
                Log.i("DATABASE 1 ", cursor.getString(2));
                Log.i("DATABASE 1 ", cursor.getBlob(3).toString());

                imageView.setImageBitmap(BitmapFactory.decodeByteArray(cursor.getBlob(3), 0, cursor.getBlob(3).length));
            } while (cursor.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursor.close();
        return false;
    }

//    public void insertImg(int id , Bitmap img ) {
//
//
//        byte[] data = getBitmapAsByteArray(img); // this is a function
//
//        insertStatement_logo.bindLong(1, id);
//        insertStatement_logo.bindBlob(2, data);
//
//        insertStatement_logo.executeInsert();
//        insertStatement_logo.clearBindings() ;
//
//    }
//
//    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        bitmap.compress(CompressFormat.PNG, 0, outputStream);
//        return outputStream.toByteArray();
//    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}
