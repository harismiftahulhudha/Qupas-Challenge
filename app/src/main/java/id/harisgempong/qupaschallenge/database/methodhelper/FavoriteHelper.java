package id.harisgempong.qupaschallenge.database.methodhelper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import id.harisgempong.qupaschallenge.database.DatabaseContract;
import id.harisgempong.qupaschallenge.model.Result;

public class FavoriteHelper extends DatabaseTableHelper {

    public FavoriteHelper(Context context) {
        super(context);
    }

    public List<Result> list() {
        open();
        List<Result> results = new ArrayList<>();
        Cursor cursor = getDatabase().query(DatabaseContract.TABLE_FAVORITE, null, null, null, null, null, DatabaseContract.FavoriteColumns.ID + " DESC", null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            do {

                Result result = new Result(
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TYPE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.YEAR)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.POSTER))
                );
                results.add(result);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        close();
        return results;
    }

    public void insert(Result result) {
        open();
        ContentValues initialValues = new ContentValues();
        initialValues.put(DatabaseContract.FavoriteColumns.ID, result.getImdbID());
        initialValues.put(DatabaseContract.FavoriteColumns.TYPE, result.getType());
        initialValues.put(DatabaseContract.FavoriteColumns.TITLE, result.getTitle());
        initialValues.put(DatabaseContract.FavoriteColumns.YEAR, result.getYear());
        initialValues.put(DatabaseContract.FavoriteColumns.POSTER, result.getPoster());
        getDatabase().insert(DatabaseContract.TABLE_FAVORITE, null, initialValues);
        close();
    }

    public void delete(String id) {
        open();
        getDatabase().delete(DatabaseContract.TABLE_FAVORITE, DatabaseContract.FavoriteColumns.ID + " = '" + id + "'", null);
        close();
    }

    public boolean isAvailable(String id) {
        open();
        String sql = "SELECT COUNT(*) FROM " + DatabaseContract.TABLE_FAVORITE + " WHERE " + DatabaseContract.FavoriteColumns.ID + "='" + id + "'";
        @SuppressLint("Recycle") Cursor cursor = getDatabase().rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        assert cursor != null;
        boolean bol = cursor.getInt(0) != 0;
        close();
        return bol;
    }
}
