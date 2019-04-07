package id.harisgempong.qupaschallenge.database.methodhelper;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import id.harisgempong.qupaschallenge.database.DatabaseHelper;

class DatabaseTableHelper {
    private final DatabaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    DatabaseTableHelper(Context context){
        this.dataBaseHelper = new DatabaseHelper(context);
    }

    void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }
    void close(){
        dataBaseHelper.close();
    }

    SQLiteDatabase getDatabase() {
        return database;
    }
}
