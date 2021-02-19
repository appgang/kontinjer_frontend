package com.example.test;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Database;
import com.example.test.ui.statistics.RecycledItems;
import com.example.test.ui.statistics.RecycledItemsDAO;

@Database(entities = {RecycledItems.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    public static synchronized AppDatabase getInstance(Context context){
        if(instance==null){
            //cheat... sredi .allowMainThreadQueries()
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"recycleditems").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
        return instance;
    }
    public abstract RecycledItemsDAO recycledItemsDAO();

}
