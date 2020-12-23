package com.prac.loginpage;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyCartCustomAdapter extends BaseAdapter {

    List<String> itemNames = new ArrayList<>();
    List<String> itemQunatity = new ArrayList<>();
    List<String> itemSize = new ArrayList<>();
    List<String> itemPrice = new ArrayList<>();
    Context context;

    public MyCartCustomAdapter(Context anyContext,List<String> anyName,List<String> anyQty,List<String> anySize,List<String> anyPrice)
    {
        context = anyContext;
        itemNames = anyName;
        itemQunatity=anyQty;
        itemSize = anySize;
        itemPrice= anyPrice;
    }

    @Override
    public int getCount() {
        return itemNames.size();
    }

    @Override
    public Object getItem(int i) {
        return itemNames.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null)
        {
            LayoutInflater myLayoutInflator = LayoutInflater.from(context);
            view = myLayoutInflator.inflate(R.layout.activity_view_cart,viewGroup,false);
        }
        TextView name = view.findViewById(R.id.txtViewCartItemName);
        TextView price = view.findViewById(R.id.txtViewCartItemPrice);
        TextView qty = view.findViewById(R.id.txtViewCartItemQty);
        TextView size = view.findViewById(R.id.txtViewItemCartSize);
        TextView fprice = view.findViewById(R.id.txtViewFinalPrice);
        ImageView imgItem = view.findViewById(R.id.imgViewCartItem);

        name.setText(itemNames.get(i));
        size.setText(itemSize.get(i));
        qty.setText(itemQunatity.get(i));
        price.setText(itemPrice.get(i));

        double cost = Integer.parseInt(qty.getText().toString()) * Integer.parseInt(price.getText().subSequence(1,3).toString());
        fprice.setText("CAD : "+cost);

        Cursor cursor;
        cursor = new SQLiteHelper(context).getData("SELECT * FROM ITEMS WHERE name = '"+name.getText()+"';");
        cursor.moveToNext();
        byte[] image = cursor.getBlob(3);
        final Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        imgItem.setImageBitmap(bitmap);

        return view;
    }
}
