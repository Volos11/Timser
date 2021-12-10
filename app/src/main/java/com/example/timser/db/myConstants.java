package com.example.timser.db;

public class myConstants {
    public static final String EDIT_STATE = "edit_state";
    public static final String LIST_ITEM_INTENT = "list_item_intent";
    public static final String TABLE_NAME = "my_table";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DISC = "disc";
    public static final String DB_NAME = "my_db.db";
    public static final int DB_VERSION = 8;
    //СТРУКТУРА
    public static final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY," +
            TITLE + " TEXT," + DISC + " TEXT)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
