package com.xclusive.ParcelTracking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

class sqlDB extends SQLiteOpenHelper {
    public static final String DBNAME="ORDERS";
    public static final String TABLENAME ="SAVED_ORDERS";
    public static final String COL3 ="URL";
    public static final String COL1 ="ID1";
    public static final String COL2 ="CARRIER_NAME";
    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use for locating paths to the the database
     * @param name    of the database file, or null for an in-memory database
     * @param factory to use for creating cursor objects, or null for the default
     * @param version number of the database (starting at 1); if the database is older,
     *                {@link #onUpgrade} will be used to upgrade the database; if the database is
     *                newer, {@link #onDowngrade} will be used to downgrade the database
     */
    public sqlDB( Context context) {
        super(context, DBNAME, null, 1);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLENAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ID1 TEXT,CARRIER_NAME TEXT,URL TEXT)");


    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLENAME);
        onCreate(db);
    }

    public Integer insertdata(String id,String name,String url){
        Long res1 = null;

        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        contentValues.put(COL1,id);
        contentValues.put(COL2,name);
        contentValues.put(COL3,url);
        res1 = db.insert(TABLENAME,null,contentValues);
        if (res1==-1){
            return 0;
        }
        else {
            return 1;
        }



    }

    public Integer deletedata(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(TABLENAME,"ID1 = ?",new String[]{id});
        if (res==0){
            return 0;
        }
        else {
            return 1;
        }

    }

    public Cursor getAlldata(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from SAVED_ORDERS;",null);
        return result;
    }
}
