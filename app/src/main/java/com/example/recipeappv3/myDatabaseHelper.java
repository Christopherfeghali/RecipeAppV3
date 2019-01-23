package com.example.recipeappv3;

/**
 * Created by Christopher Feghali on 16/12/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*Database helpers are used to create sqlite tables, this class simply initializes the table by creating colums and rows names as well as its contents, primary key,etc.*/

public class myDatabaseHelper extends SQLiteOpenHelper{

    public static final String TABLE_NAME = "names";
    public static final String DATABASE_NAME = "myRecipe";

    public static final String _ID = "_id";
    public static final String RECIPE_TITLE = "recipe_title";
    public static final String RECIPE_INGREDIENTS = "recipe_ingredients";

    static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + RECIPE_TITLE + " TEXT NOT NULL, " + RECIPE_INGREDIENTS + " TEXT);";

    myDatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) { // on creation execute sql function of creating the specified table above
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}