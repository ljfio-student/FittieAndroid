package me.fittie.app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import me.fittie.app.models.Meal;
import me.fittie.app.models.Workout;

/**
 * Created by luke on 28/04/2017.
 */

public class SQLDatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "fittieDatabase.db";
    private static final int DATABASE_VERSION = 1;

    public SQLDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Meal.class);
            TableUtils.createTableIfNotExists(connectionSource, Workout.class);
        } catch (SQLException ex) {
            Log.e("SQLDatabaseHelper", ex.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    public Dao<Meal, Integer> getMealDao() throws SQLException {
        return DaoManager.createDao(getConnectionSource(), Meal.class);
    }

    public Dao<Workout, Integer> getWorkoutDao() throws SQLException {
        return DaoManager.createDao(getConnectionSource(), Workout.class);
    }
}
