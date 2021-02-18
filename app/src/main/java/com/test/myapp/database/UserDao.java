package com.test.myapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.test.myapp.data.model.Datum;

import java.util.ArrayList;
import java.util.List;


@Dao
public interface UserDao {

    @Query("Select * from user")
    LiveData<List<Datum>> getUserList();

    @Insert
    void insertUser(Datum datum);
}
