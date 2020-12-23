package com.prac.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewCart extends AppCompatActivity {

    List<String> names = new ArrayList<>();
    List<String> qty = new ArrayList<>();
    List<String> size = new ArrayList<>();
    List<String> price = new ArrayList<>();
    List<Integer> idnum = new ArrayList<>();
    List<String> res;
    ArrayList<Items> list;
    MyCartCustomAdapter adapter;
    private double iterativeCost = 0;
    TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cartlist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button btnProceed = (Button)findViewById(R.id.btnProceedCheckout);

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ViewCart.this,ButtonProceed.class);
                startActivity(intent1);
            }
        });

        total = (TextView) findViewById(R.id.txtViewTotalCost);
        SQLiteHelper objDB=new SQLiteHelper(this);
        res=objDB.getCart();
//        if (ItemCart.cart.size() > 0) {
//            int length = ItemCart.cart.size();
//
//            for (int i = 0; i < length; i++) {
//                String[] data;
//                data = ItemCart.cart.get(i).split(",");
//                names.add(data[0]);
//                qty.add(data[1]);
//                size.add(data[2]);
//                price.add(data[3]);
//            }
//        }
        if (res.size()> 0) {
            int length = res.size();

            for (int i = 0; i < length; i++) {
                String[] data;
                data = res.get(i).split(",");
                idnum.add(Integer.parseInt(data[0]));
                names.add(data[1]);
                qty.add(data[2]);
                size.add(data[3]);
                price.add(data[4]);
            }
        }






        ListView listViewItems = findViewById(R.id.listViewItems);
        adapter = new MyCartCustomAdapter(this, names, qty, size, price);
        listViewItems.setAdapter(adapter);
        calcTotal();
        total.setText(iterativeCost + "");

        listViewItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long l) {

                CharSequence[] items = {"Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(ViewCart.this);

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            showDialogDelete(pos);
                        }
                    }
                });
                dialog.show();

                return true;
            }
        });


    }




    private void calcTotal(){
        for (int i = 0; i < price.size(); i++) {
            iterativeCost+= (Double.parseDouble(price.get(i).replace('$',' ').toString())*(Integer.parseInt(qty.get(i))));
        }
    }

    private void showDialogDelete(final int idItem) {
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(ViewCart.this);
        final SQLiteHelper db=new SQLiteHelper(this);
        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure you want to this delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    names.remove(idItem);
                    price.remove(idItem);
                    qty.remove(idItem);
                    size.remove(idItem);

                    int itemId=(idnum.get(idItem));
                    res.remove(idItem);
                    //ItemCart.cart.remove(idItem);
                    db.deleteCartItem(Integer.toString(itemId));
                    iterativeCost = 0;
                    adapter.notifyDataSetChanged();
                    calcTotal();
                    total.setText(iterativeCost + "");
                    Toast.makeText(getApplicationContext(), "Delete successfully!!!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

    private void updateItemList() {
        // get all data from sqlite
        Cursor cursor = new SQLiteHelper(this).getData("SELECT * FROM ITEMS");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String price = cursor.getString(2);
            byte[] image = cursor.getBlob(3);

            list.add(new Items(name, price, image, id));
        }
        iterativeCost = 0;
        adapter.notifyDataSetChanged();
        calcTotal();
        total.setText(iterativeCost + "");
    }


}

