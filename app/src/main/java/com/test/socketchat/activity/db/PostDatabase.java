package com.test.socketchat.activity.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.test.socketchat.activity.db.dao.PostDao;
import com.test.socketchat.activity.db.entity.PostEntity;

/**
 * Created by lcom151-two on 3/22/2018.
 */

@Database(entities = {PostEntity.class},version = 1)
public abstract class PostDatabase extends RoomDatabase {
    public abstract PostDao postDao();
}
