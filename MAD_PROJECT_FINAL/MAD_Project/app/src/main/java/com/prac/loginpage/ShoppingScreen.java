package com.prac.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ShoppingScreen extends AppCompatActivity implements RecyclerAdapter.ImageViewHolder.OnListItemClickListener {

    private RecyclerView recyclerView;
    private int[] images =
            {R.drawable.women,
                    R.drawable.men,
                    R.drawable.kids,
                    R.drawable.infants,
                    R.drawable.beauty,
                    R.drawable.accessories,
                    R.drawable.footwear,
                    R.drawable.winter,
            };

    private String[] image_text = {"Women", "Men", "Kids", "Infants",
            "Beauty", "Accessories", "Footwear", "Winter"};

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_screen);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new RecyclerAdapter(images, image_text,this,this);
        recyclerView.setAdapter(myAdapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    @Override
    public void OnListItemClick(int position) {

        Log.d("On Click", "OnListItemClick: clicked. " + position );

        /* int img_pos = images[position]; */

        Intent intent = new Intent(ShoppingScreen.this, CategoryListActivity.class);

        switch (position)
        {
            case 0:
                intent.putExtra("Pattern","WMN");
                break;

            case 1:
                intent.putExtra("Pattern","MEN");
                break;

            case 2:
                intent.putExtra("Pattern","KID");
                break;

            case 3:
                intent.putExtra("Pattern","INFANTS");
                break;

            case 4:
                intent.putExtra("Pattern","MKUP");
                break;

            case 5:
                intent.putExtra("Pattern","ACC");
                break;

            case 6:
                intent.putExtra("Pattern","FOOT");
                break;

            case 7:
                intent.putExtra("Pattern","WINT");
                break;
        }

        startActivity(intent);
    }

}
