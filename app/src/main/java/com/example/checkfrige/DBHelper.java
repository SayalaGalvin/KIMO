package com.example.checkfrige;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.Toast;

class DBHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "GroderyList.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "myList";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "_item";
    private static final String COLUMN_AMOUNT = "_amount";

     DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreate = "CREATE TABLE "+ TABLE_NAME +
                " ("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +COLUMN_TITLE+" TEXT, "
                +COLUMN_AMOUNT+" TEXT);";

        db.execSQL(queryCreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addItem(String title, String amount){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title );
        cv.put(COLUMN_AMOUNT, amount);

        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Add Item Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor getItem(){
        SQLiteDatabase db = this.getReadableDatabase();

        String readQuery = "SELECT * FROM "+ TABLE_NAME;
        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(readQuery,null);
        }

        return cursor;
    }

    void updateItem(String row_id, String title, String amount){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title );
        cv.put(COLUMN_AMOUNT, amount);

        long result = db.update(TABLE_NAME,cv,"_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Updated Item Successfully", Toast.LENGTH_SHORT).show();
        }
    }


    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME,"_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Delete Item Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_NAME);
    }

}
