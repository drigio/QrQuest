package com.example.drigio.qrcodescan;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{

    //Declare some initial variables
    private static final String TB_NAME = "mtable";
    private static final int DB_VERSION = 1;
    public static final String HINTNO = "_id";
    public static final String HINT = "hint";


    public DatabaseHelper(Context context) {
        super(context, TB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE "+ TB_NAME + "( " + HINTNO + " INTEGER PRIMARY KEY, " + HINT + " TEXT)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
        onCreate(sqLiteDatabase);
    }

    //Add the data
    public boolean addData(int hintNo, String hint) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HINTNO,hintNo);
        contentValues.put(HINT,hint);

        Log.d("mylog","Added HINT " + hint + " with hintno " + hintNo);

        long result = db.insert(TB_NAME,null,contentValues);

        if(result == -1){return false;} //Check if the values were successfully added to the table
        else {return true;}
    }

    public Cursor getData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "SELECT * FROM " + TB_NAME;
        Cursor data = sqLiteDatabase.query(TB_NAME,new String[] {HINTNO,HINT},null,null,null,null,null);
        data.moveToFirst();
        return data;
    }

    public Cursor getDataByRawQuery() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + TB_NAME;
        Cursor data = sqLiteDatabase.rawQuery(query,null);
        return data;
    }
}
