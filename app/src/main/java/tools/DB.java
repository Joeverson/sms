package tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class DB {

    private SQLiteDatabase db;
    private String table = "configs";

    public DB(Context context){
        DBCore dbCore = new DBCore(context);
        this.db = dbCore.getWritableDatabase(); // pega a instacia do db
    }



    public boolean setData(String name, String ipServer){
        Cursor c;
        ContentValues args  = new ContentValues();
        args.put("name", name);
        args.put("ipServer", ipServer);

        //para atualizar ou criar se não existir
        if((c = selectInfo()) == null){
            if(db.insert(this.table, null, args) == -1)
                return false;
            return true;
        }else{
            if(db.update(this.table, args, "_id="+c.getString(2), null) > 0)
                return true;

            return false;
        }
    }

    public Cursor selectInfo(){
        String[] cols = new String[]{"name", "ipServer","_id"};
        Cursor pointer = db.query(this.table, cols, null, null,null, null, null);

        if(pointer.getCount() <= 0)
            return null;

        pointer.moveToFirst();

        return pointer;
    }

}
