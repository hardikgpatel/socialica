package com.test.socketchat.activity.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.test.socketchat.activity.db.entity.PostEntity;

import java.util.List;

/**
 * Created by lcom151-two on 3/22/2018.
 */

@Dao
public interface PostDao {

    @Insert
    long addFavorite(PostEntity postEntities);

    @Query("SELECT * FROM post")
    List<PostEntity> getFavorite();
}
