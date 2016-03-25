package com.zhenglee.langfangfavor.android.modules.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.zhenglee.framework.ormlite.ORMLitePersistenceSupport;
import com.zhenglee.langfangfavor.android.modules.user.dao.LocalUser;

import java.sql.SQLException;

/**
 * Created by zhenglee on 15/12/25.
 */
public class FavorPersistenceManager extends ORMLitePersistenceSupport {

    private static final String TAG = "FavorPersistenceManager";

    private static final String DATABASE_NAME = "favor.sqlite";

    private static final int DATABASE_VERSION = 1;

    public FavorPersistenceManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, LocalUser.class);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }
}
