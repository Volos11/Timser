package com.example.timser.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.timser.adapter.ListItem;

import java.util.ArrayList;
import java.util.List;

public class MyDbManager {
    private Context context;
    private MyDbHelper myDbHelper;
    private SQLiteDatabase db;

    public MyDbManager(Context context){
        this.context = context;
        myDbHelper = new MyDbHelper(context);
    }
    public void openDb(){
        db = myDbHelper.getWritableDatabase();
    }
    public void insertToDb(String title, String disc){
        ContentValues cv = new ContentValues();
        cv.put(myConstants.TITLE, title);
        cv.put(myConstants.DISC, disc);
        db.insert(myConstants.TABLE_NAME, null, cv);
    }
    //Поправка
        public void delete(int id){
            String selection = myConstants.ID + "=" + id;
//        String selection = myConstants.TITLE + "=" + title;
//        String selection1 = myConstants.DISC + "=" + desc;
        db.delete(myConstants.TABLE_NAME,selection, null);
    }
    public List<ListItem> getFromDb(String searchText){
        List<ListItem> tempList = new ArrayList<>();
        String selection = myConstants.DISC + " like ?";
        Cursor cursor = db.query(myConstants.TABLE_NAME, null,selection, new String[]{"%" + searchText+ "%"},
                null,null,null);

        while(cursor.moveToNext()){
            ListItem item = new ListItem();
            int id = cursor.getInt(cursor.getColumnIndex(myConstants.ID));
//            Integer id = cursor.getInt(cursor.getColumnIndex(myConstants.ID));
//            double _id = cursor.getDouble(cursor.getColumnIndex(myConstants.ID));
//            String _id = cursor.getString(cursor.getColumnIndex(myConstants.ID));
            String title = cursor.getString(cursor.getColumnIndex(myConstants.TITLE));
            String desc = cursor.getString(cursor.getColumnIndex(myConstants.DISC));

//            item.setId(id);
            item.setId(id);
            item.setTitle(title);
            item.setDesc(desc);
//            item.setId(_id);

            tempList.add(item);
        }
        cursor.close();
        return tempList;
    }

    public void closeDb(){
        myDbHelper.close();
    }

}
