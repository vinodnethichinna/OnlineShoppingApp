package com.prac.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.ArrayList;

public class CategoryListActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<Items> list;
    ItemsListAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        gridView = (GridView) findViewById(R.id.menGridView);
        list = new ArrayList<>();
        adapter = new ItemsListAdapter(this, R.layout.items_external_layout, list);
        gridView.setAdapter(adapter);

        String key = getIntent().getExtras().getString("Pattern");
        Cursor cursor;
        cursor = new SQLiteHelper(this).getData("SELECT * FROM ITEMS WHERE Name LIKE '"+ key +"_%' ;");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String price = cursor.getString(2);
            byte[] image = cursor.getBlob(3);

            list.add(new Items(name, price, image, id));
        }

        adapter.notifyDataSetChanged();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menuCart:
                Intent intent1 = new Intent(this, ViewCart.class);
                this.startActivity(intent1);
                return true;

            case R.id.menuWishlist:
                Intent intent2 = new Intent(this,viewWishlist.class);
                this.startActivity(intent2);
                return true;

            case R.id.menuHelp:
                Intent intent3 = new Intent(this,Help.class);
                this.startActivity(intent3);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
