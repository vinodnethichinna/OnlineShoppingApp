package com.prac.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class viewWishlist extends AppCompatActivity {
    List<String> names = new ArrayList<>();
    List<String> qty = new ArrayList<>();
    List<String> size = new ArrayList<>();
    List<String> price = new ArrayList<>();

    List<String> res;
    List<Integer> idnum = new ArrayList<>();

    ArrayList<Items> list;
    MyCartCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_wishlist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




//        if (ItemCart.wishlist.size() > 0) {
//            int length = ItemCart.wishlist.size();
//            for (int i = 0; i < length; i++) {
//                String[] data;
//                data = ItemCart.wishlist.get(i).split(",");
//                names.add(data[0]);
//                qty.add(data[1]);
//                size.add(data[2]);
//                price.add(data[3]);
//                Log.d("from wishlist", data[0]);
//            }
//
//        }

        final SQLiteHelper objDB=new SQLiteHelper(this);
        res=objDB.getWishList();

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

        ListView listViewItems = findViewById(R.id.WishlistItems);
        adapter=new MyCartCustomAdapter(this, names, qty, size, price);
        listViewItems.setAdapter(adapter);

        listViewItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long l) {

                CharSequence[] items = {"Delete","Add to Cart"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(viewWishlist.this);

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            showDialogDelete(pos);
                        }
                        if(i == 1)
                        {
                            //ItemCart.cart.add(names.get(pos)+','+qty.get(pos)+','+size.get(pos)+','+price.get(pos));
                            objDB.addtoCart(names.get(pos),qty.get(pos),size.get(pos),price.get(pos));
                            names.remove(pos);
                            price.remove(pos);
                            qty.remove(pos);
                            size.remove(pos);
                            res.remove(pos);
                            int itemNum= idnum.get(pos);
                            //ItemCart.wishlist.remove(pos);
                            objDB.deleteWishListItem(Integer.toString(itemNum));
                            adapter.notifyDataSetChanged();
                        }

                    }
                });
                dialog.show();

                return true;
            }
        });
    }


    private void showDialogDelete(final int idItem){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(viewWishlist.this);
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
                    db.deleteWishListItem(Integer.toString(itemId));

                    //ItemCart.wishlist.remove(idItem);

                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Delete successfully!!!",Toast.LENGTH_SHORT).show();
                } catch (Exception e){
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

    private void updateItemList(){
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
        adapter.notifyDataSetChanged();
    }


    }


