package com.example.test.ui.notifications;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecycledItemsDAO {
    @Query("SELECT * FROM recycleditems")
    List<RecycledItems> getAll();
    @Insert
    void insertAll(RecycledItems... recycledItems);
    @Insert
    void insertItem(RecycledItems recycledItems);
    @Delete
    void delete(RecycledItems recycledItems);
}
