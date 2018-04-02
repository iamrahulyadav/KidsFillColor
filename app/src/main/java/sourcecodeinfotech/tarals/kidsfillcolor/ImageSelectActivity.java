package sourcecodeinfotech.tarals.kidsfillcolor;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import sourcecodeinfotech.tarals.kidsfillcolor.GridViewAdapter;
import sourcecodeinfotech.tarals.kidsfillcolor.ImageItem;
import sourcecodeinfotech.tarals.kidsfillcolor.R;

public class ImageSelectActivity extends Activity {
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    int[] imageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_select);
        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Log.d("img","image selected at "+ position);
                ImageItem item = (ImageItem) parent.getItemAtPosition(position);
                Intent intent = new Intent();
                Log.d("img","image id "+ imageId[position]);
                intent.putExtra("image", imageId[position]);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs=null;
        Intent intent=getIntent();
        String arr=intent.getStringExtra("array");
        if(arr.equals("animals")) {
            imgs = getResources().obtainTypedArray(R.array.animal_ids);
        }
        else if (arr.equals("birds")){
            imgs = getResources().obtainTypedArray(R.array.bird_ids);
        }
        else if (arr.equals("fruits")){
            imgs = getResources().obtainTypedArray(R.array.fruits_ids);
        }
        else if (arr.equals("rangoli")){
            imgs = getResources().obtainTypedArray(R.array.rangoli_ids);
        }
        else {
            imgs = getResources().obtainTypedArray(R.array.scenery_ids);
        }
        imageId=new int[imgs.length()];
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap,imgs.getResourceId(i,-1)));
            imageId[i]=imgs.getResourceId(i,-1);
        }
        return imageItems;
    }
}