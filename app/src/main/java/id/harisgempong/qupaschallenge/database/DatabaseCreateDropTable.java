package id.harisgempong.qupaschallenge.database;

class DatabaseCreateDropTable {
    static final String CREATE_TABLE_FAVORITE = String.format("CREATE TABLE %s"
                    + " (%s TEXT PRIMARY KEY," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_FAVORITE,
            DatabaseContract.FavoriteColumns.ID,
            DatabaseContract.FavoriteColumns.TYPE,
            DatabaseContract.FavoriteColumns.TITLE,
            DatabaseContract.FavoriteColumns.YEAR,
            DatabaseContract.FavoriteColumns.POSTER
    );
    static final String DROP_TABLE_FAVORITE = "DROP TABLE IF EXISTS " + DatabaseContract.TABLE_FAVORITE;
}
