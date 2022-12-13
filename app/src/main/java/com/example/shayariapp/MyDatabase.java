package com.example.shayariapp;

import static kotlin.random.RandomKt.Random;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {
    ArrayList<Modelclass> modelclassArrayList = new ArrayList<Modelclass>();
    ArrayList<shayariModelClass> modelClassArray = new ArrayList<>();
    ArrayList<shayariModelClass> MoedlArrayList = new ArrayList<>();
    ArrayList<shayariModelClass> ModelList = new ArrayList<>();
    private static final String TAG = "MyDatabase";
    private static String DB_NAME = "shayaridb.db";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase mDataBase;
    private boolean mNeedUpdate = false;

    private Context mContext;

    public MyDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        if (Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;

        copyDataBase();

        this.getReadableDatabase();
    }


    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    //    TODO copy file
    private void copyDBFile() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;
    }


    //    TODO update database
    public void updateDataBase() throws IOException {
        if (mNeedUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();
            copyDataBase();
            mNeedUpdate = false;
        }
    }


    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    public ArrayList<Modelclass> readData() {

        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from categorytb";
        Cursor c = db.rawQuery(sql, null);

        if (c.moveToFirst()) {
            do {
                int categoryId = c.getInt(0);
                String categoryName = c.getString(1);
                Modelclass modelArray = new Modelclass(categoryId, categoryName);
                modelclassArrayList.add(modelArray);
//                Log.e(TAG, "readData:==> "+ StudentId +"   "+StudentName  +  "  "+StudentDob);
            } while (c.moveToNext());
        }
        return modelclassArrayList;
    }

    public ArrayList<shayariModelClass> categoryClick(int Id) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from shayaritb where categoryid=" + Id;
        Cursor c = db.rawQuery(sql, null);

        if (c.moveToFirst()) {
            do {
                int shayariId = c.getInt(0);
                String shayari = c.getString(1);
                int status = c.getInt(3);
                shayariModelClass model = new shayariModelClass(shayariId, shayari, status);
                modelClassArray.add(model);
            } while (c.moveToNext());
        }
        return modelClassArray;
    }
    public ArrayList<shayariModelClass> AllCategory() {


        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from shayaritb";
        Cursor c = db.rawQuery(sql, null);


        if (c.moveToFirst()) {
            do {
                int shayariId = c.getInt(0);
                String shayari = c.getString(1);
                int status = c.getInt(3);
                shayariModelClass modelClasslist = new shayariModelClass(shayariId, shayari, status);
                ModelList.add(modelClasslist);
                Log.e(TAG, "readData:==> " + shayariId + "   " + shayari);
            } while (c.moveToNext());
        }
        return ModelList;
    }

    public void likeUnlike(int i, int shayariId) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", i);
        db.update("shayaritb", contentValues, "shayariId=?", new String[]{String.valueOf(shayariId)});

    }

    public ArrayList<shayariModelClass> displayLikeShayari() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "Select * from shayaritb where status = 1";
        Cursor c = db.rawQuery(sql, null);

        if (c.moveToFirst()) {
            do {
                int shayariId = c.getInt(0);
                String shayari = c.getString(1);
                int status = c.getInt(3);
                shayariModelClass Model= new shayariModelClass(shayariId, shayari, status);
                MoedlArrayList.add(Model);


            } while (c.moveToNext());
        }
        return MoedlArrayList;

    }



}
