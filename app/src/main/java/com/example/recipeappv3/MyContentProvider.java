package com.example.recipeappv3;

/**
 * Created by Christopher Feghali on 16/12/2017.
 */

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.widget.Toast;

import java.util.HashMap;

import static com.example.recipeappv3.myDatabaseHelper.TABLE_NAME;
/*
Content provider is used to share its contents across multiple apps or even classes, this specific class contains methods associated with content providers
with things such as insert,update,delete,query which are used in SQLite databases for data manipulation.
*/

public class MyContentProvider extends ContentProvider{

    static final String PROVIDER_NAME = "com.example.recipeappv3.MyContentProvider";
    static final String URL ="content://" + PROVIDER_NAME +"/" + TABLE_NAME;
    static final Uri CONTENT_URL = Uri.parse(URL);
    static final String id = myDatabaseHelper._ID;
    static final String name = myDatabaseHelper.RECIPE_TITLE;
    static final String ingredient = myDatabaseHelper.RECIPE_INGREDIENTS;
    static final int uriCode = 1;

    private  static HashMap<String,String> values;
    private SQLiteDatabase db;

    static final UriMatcher uriMatcher;
    static
    {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, TABLE_NAME, uriCode);
    }

    @Override
    public boolean onCreate() {
        myDatabaseHelper dbHelper = new myDatabaseHelper(getContext());
        db = dbHelper.getWritableDatabase();
        if(db != null)
        {
            return true;
        }
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_NAME);
        switch (uriMatcher.match(uri))
        {
            case uriCode:
                queryBuilder.setProjectionMap(values);
                break;
            default:
                throw new IllegalArgumentException("URI not supported" + uri );
        }
        Cursor cursor = queryBuilder.query(db,projection,selection,selectionArgs,null,null,sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri))
        {
            case uriCode:
                return "vnd.android.cursor.dir/" + TABLE_NAME;
            default:
                throw new IllegalArgumentException("URI not supported" + uri );
        }    }

    @Override
    public Uri insert(Uri uri, ContentValues values) { // insert values in database that uses contentProvider
        long rowID = db.insert(TABLE_NAME, null, values);
        if(rowID > 0)
        {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URL,rowID);
            getContext().getContentResolver().notifyChange(_uri,null);
            return _uri;
        }
        Toast.makeText(getContext(),"Row Insert Failed",Toast.LENGTH_LONG).show();
        return null;    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) { // delete values in database that uses contentProvider
        int rowsDeleted = 0;
        switch (uriMatcher.match(uri))
        {
            case uriCode:
                rowsDeleted = db.delete(TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Uri not supported " + uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) { // updates values in database that uses contentProvider
        int rowsUpdated = 0;
        switch (uriMatcher.match(uri))
        {
            case uriCode:
                rowsUpdated = db.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Uri not supported " + uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return rowsUpdated;
    }
}
