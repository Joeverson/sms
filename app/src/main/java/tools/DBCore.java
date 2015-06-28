package tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBCore extends SQLiteOpenHelper {

    private static final String NAME_DB = "talkAnyWhere";
    private static final int VERSION = 5;

    public DBCore(Context context) {
        super(context, NAME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table configs(_id integer primary key autoincrement, name text not null, ipServer text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table configs");
        onCreate(db);
    }
}
