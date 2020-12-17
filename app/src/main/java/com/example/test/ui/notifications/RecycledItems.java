package com.example.test.ui.notifications;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recycleditems")
public class RecycledItems {
    @PrimaryKey
    public int rid;
    @ColumnInfo(name="material")
    public String material;
    @ColumnInfo(name="item")
    public String item;

    public RecycledItems(int rid, String material, String item){
        this.rid=rid;
        this.material=material;
        this.item=item;
    }
}
