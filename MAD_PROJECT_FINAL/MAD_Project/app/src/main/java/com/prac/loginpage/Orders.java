package com.prac.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Orders extends AppCompatActivity {
    MyCartCustomAdapter adapter;
    List<String> names = new ArrayList<>();
    List<String> qty = new ArrayList<>();
    List<String> size = new ArrayList<>();
    List<String> price = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listOrders = (ListView)findViewById(R.id.listViewOrdersPage);

        SQLiteHelper objDB=new SQLiteHelper(this);


        adapter = new MyCartCustomAdapter(this, names, qty, size, price);
        listOrders.setAdapter(adapter);
    }
}
