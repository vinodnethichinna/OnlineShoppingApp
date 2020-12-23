package com.prac.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SelectedItemActivity extends AppCompatActivity {

    SQLiteHelper objSqlHelper = new SQLiteHelper(this) ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_item);

        ImageView imgSelected = (ImageView)findViewById(R.id.imgViewSelectedItem);
        final Spinner spnQty = (Spinner)findViewById(R.id.spinnerQty);
        final Spinner spnSize = (Spinner)findViewById(R.id.spinnerSize);
        Button btnCart = (Button)findViewById(R.id.btnAddtoCart);
        Button btnWishlist = (Button)findViewById(R.id.btnWishList);
        TextView txtViewItemName = (TextView)findViewById(R.id.txtViewItemName);
        TextView txtViewItemPrice = (TextView)findViewById(R.id.txtViewItemPrice);
        final String name,price;

        Bundle extras=getIntent().getExtras();

        name = extras.getString("Item_Name");
        price = extras.getString("Item_Price");
        txtViewItemName.setText(name);
        txtViewItemPrice.setText(price);

        Toast.makeText(this, "name is : "+name, Toast.LENGTH_SHORT).show();
        Cursor cursor;
        cursor = objSqlHelper.getData("SELECT * FROM ITEMS WHERE name = '"+name+"';");
        cursor.moveToNext();
        byte[] image = cursor.getBlob(3);
        final Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

        imgSelected.setImageBitmap(bitmap);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemCart.cart.add(name+','+spnQty.getSelectedItem()+','+spnSize.getSelectedItem()+','+price);
                objSqlHelper.addtoCart(name,spnQty.getSelectedItem().toString(),spnSize.getSelectedItem().toString(),price);


                Toast.makeText(SelectedItemActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
        btnWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemCart.wishlist.add(name+','+spnQty.getSelectedItem()+','+spnSize.getSelectedItem()+','+price);
                Toast.makeText(SelectedItemActivity.this, "Added to WishList", Toast.LENGTH_SHORT).show();
                objSqlHelper.addtoWishList(name,spnQty.getSelectedItem().toString(),spnSize.getSelectedItem().toString(),price);

                finish();

            }
        });

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

            case R.id.menuLogout:
                Intent intent4 = new Intent(this,Logout.class);
                this.startActivity(intent4);
                return true;

            case R.id.menuOrders:
                Intent intent5 = new Intent(this,Orders.class);
                this.startActivity(intent5);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
