package com.prac.loginpage;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DummyDataLoad {
    private String filename;
    private SQLiteHelper myDB;

    public void csvToDB(Context ctx) throws IOException {
        InputStream inputStream = ctx.getAssets().open(filename);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        BufferedReader reader = new BufferedReader(inputStreamReader);

        myDB = new SQLiteHelper(ctx);


        String csvLine, userArray[];
        while ((csvLine = reader.readLine()) != null) {
            SQLiteDatabase db = myDB.getWritableDatabase();
            userArray = csvLine.split(",");
            ContentValues values = new ContentValues();
            values.put("name", userArray[0]);
            values.put("password", userArray[1]);
            values.put("phone", userArray[2]);
            values.put("age", userArray[3]);
            values.put("email", userArray[4]);


            try {

                long row = db.insert("USERDETAILS", null, values);
            } catch (SQLiteException e) {
                Log.d("Error: ", e.getMessage());
            }
            db.close();

        }

    }

    public void imageCSV(Context ctx) throws IOException {
        InputStream is = ctx.getAssets().open(filename);
        InputStreamReader isr = new InputStreamReader(is);

        BufferedReader br = new BufferedReader(isr);

        myDB = new SQLiteHelper(ctx);

        String s;
        int rows = 0;
        if(myDB.ifDummyDataNotExists()) {
            while (rows < 116) {
                s = br.readLine();
                openImageInAssets(ctx, s);
                rows++;
            }
        }
        br.close();
    }

    private void openImageInAssets(Context ctx, String line) {

        Log.d("Open : ", line);
        String[] data = line.split(",");
        String imageName = data[0].trim();
        String itemName = data[1];
        String price = data[2];
        InputStream fileStream = null;
        try {
            fileStream = ctx.getAssets().open(imageName);
            if (fileStream != null) {

                Bitmap bitmap = BitmapFactory.decodeStream(fileStream);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                int fileExtensionPosition = imageName.lastIndexOf('.');
                String fileExtension = imageName.substring(fileExtensionPosition + 1);

                if (fileExtension.equalsIgnoreCase("png")) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    Log.d("compres", "fileExtension is PNG");
                } else if (fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("jpeg")) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    Log.d("compres", "fileExtension is JPG");
                }

                byte[] byteArray;
                byteArray = byteArrayOutputStream.toByteArray();
                //insert query call
                SQLiteHelper objDB = new SQLiteHelper(ctx);
                objDB.insertData(itemName, price, byteArray);
                Log.d("Img Loaded :",imageName);
            }
        } catch (IOException e) {
            Log.d("Err :", "Cant Access Asset img." + e.getCause());
        } finally {
            try {
                if (fileStream != null) {
                    fileStream.close();
                    fileStream = null;
                }
            } catch (IOException e) {
                Log.d("Err :", "Cant Access" + e.getMessage());
            }
        }
    }

    public DummyDataLoad(String filename) {
        this.filename = filename;
    }
}
