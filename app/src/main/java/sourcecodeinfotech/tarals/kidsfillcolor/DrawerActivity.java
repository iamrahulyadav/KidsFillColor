package sourcecodeinfotech.tarals.kidsfillcolor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RelativeLayout dashBoard;
    private MyView myView;
    int displayWidth,selectedcolor=0xFFFF0000;
    Display display;
    Button b_red, b_blue, b_green, b_orange, b_next,b_select;
    private ColorPicker colorPicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        myView = new MyView(this);
        b_red = (Button) findViewById(R.id.b_red);
        b_blue = (Button) findViewById(R.id.b_blue);
        b_green = (Button) findViewById(R.id.b_green);
        b_orange = (Button) findViewById(R.id.b_orange);
        b_orange.setBackgroundColor(0xFFFF9900);
        b_next=(Button) findViewById(R.id.button5);
        b_select=(Button)findViewById(R.id.button);
        colorPicker = (ColorPicker) findViewById(R.id.colorPicker);
        dashBoard = (RelativeLayout) findViewById(R.id.dashBoard);
        b_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int initialColor = Color.WHITE;
                ColorPickerDialog colorPickerDialog = new ColorPickerDialog(DrawerActivity.this, initialColor, new ColorPickerDialog.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        selectedcolor=color;
                        myView.changePaintColor(selectedcolor);
                        b_orange.setBackgroundColor(selectedcolor);
                    }
                });
                colorPickerDialog.show();
            }
        });

        b_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedcolor=0xFFFF0000;
                myView.changePaintColor(selectedcolor);
            }
        });

        b_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedcolor=0xFF0000FF;
                myView.changePaintColor(selectedcolor);
            }
        });

        b_green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedcolor=0xFF00FF00;
                myView.changePaintColor(selectedcolor);
            }
        });

        b_orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) b_orange.getBackground();
                selectedcolor= buttonColor.getColor();
                myView.changePaintColor(selectedcolor);
            }
        });

        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap bmp=myView.pallet;
                File folder = new File(Environment.getExternalStorageDirectory().toString());
                boolean success = false;
                if (!folder.exists())
                {
                    success = folder.mkdirs();
                }
                File file = new File(Environment.getExternalStorageDirectory().toString() + "/sample.png");

                if ( !file.exists() )
                {
                    try {
                        success = file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println(success+"file");



                FileOutputStream ostream = null;
                try
                {
                    ostream = new FileOutputStream(file);

                    System.out.println(ostream);
                    View targetView = myView;

                    // myDrawView.setDrawingCacheEnabled(true);
                    //   Bitmap save = Bitmap.createBitmap(myDrawView.getDrawingCache());
                    //   myDrawView.setDrawingCacheEnabled(false);
                    // copy this bitmap otherwise distroying the cache will destroy
                    // the bitmap for the referencing drawable and you'll not
                    // get the captured view
                    //   Bitmap save = b1.copy(Bitmap.Config.ARGB_8888, false);
                    //BitmapDrawable d = new BitmapDrawable(b);
                    //canvasView.setBackgroundDrawable(d);
                    //   myDrawView.destroyDrawingCache();
                    // Bitmap save = myDrawView.getBitmapFromMemCache("0");
                    // myDrawView.setDrawingCacheEnabled(true);
                    //Bitmap save = myDrawView.getDrawingCache(false);
                    Bitmap well = myView.pallet;
                    Bitmap save = Bitmap.createBitmap(320, 480, Bitmap.Config.ARGB_8888);
                    Paint paint = new Paint();
                    paint.setColor(Color.WHITE);
                    Canvas now = new Canvas(save);
                    now.drawRect(new Rect(0,0,320,480), paint);
                    now.drawBitmap(well, new Rect(0,0,well.getWidth(),well.getHeight()), new Rect(0,0,320,480), null);

                    // Canvas now = new Canvas(save);
                    //myDrawView.layout(0, 0, 100, 100);
                    //myDrawView.draw(now);
                    if(save == null) {
                        System.out.println("NULL bitmap save\n");
                    }
                    save.compress(Bitmap.CompressFormat.PNG, 100, ostream);
                    //bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
                    //ostream.flush();
                    //ostream.close();
                }catch (NullPointerException e)
                {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Null error", Toast.LENGTH_SHORT).show();
                }

                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "File error", Toast.LENGTH_SHORT).show();
                }

                catch (IOException e)
                {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "IO error", Toast.LENGTH_SHORT).show();
                }

            }
        });
        dashBoard.addView(myView);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_animals) {
            Intent i=new Intent(DrawerActivity.this,ImageSelectActivity.class);
            i.putExtra("array","animals");
            startActivityForResult(i, 2);
        } else if (id == R.id.nav_birds) {
            Intent i=new Intent(DrawerActivity.this,ImageSelectActivity.class);
            i.putExtra("array","birds");
            startActivityForResult(i, 2);
        } else if (id == R.id.nav_fruits) {
            Intent i=new Intent(DrawerActivity.this,ImageSelectActivity.class);
            i.putExtra("array","fruits");
            startActivityForResult(i, 2);
        } else if (id == R.id.nav_rangoli) {
            Intent i=new Intent(DrawerActivity.this,ImageSelectActivity.class);
            i.putExtra("array","rangoli");
            startActivityForResult(i, 2);
        } else if (id == R.id.nav_scenery) {
            Intent i=new Intent(DrawerActivity.this,ImageSelectActivity.class);
            i.putExtra("array","scenery");
            startActivityForResult(i, 2);
        } else if (id == R.id.nav_about_us) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2)
        {
            int id = data.getIntExtra("image",R.drawable.a1);
            Log.d("Id of image", "" + id);
            myView.invalidate();
            Toast.makeText(getApplicationContext(), "Image id" + id, Toast.LENGTH_LONG).show();
            myView.changeImage(id);
            dashBoard.removeView(myView);
            dashBoard.addView(myView);
        }
    }

    public class MyView extends View {

        private Paint paint;
        private Path path;
        public Bitmap pallet,scaledBitmap;
        public ProgressDialog pd;
        final Point p1 = new Point();
        public Canvas canvas;
        public MyView(Context context) {
            super(context);
            this.paint = new Paint();
            this.paint.setAntiAlias(true);
            pd = new ProgressDialog(context);
            this.paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(5f);
            display = getWindowManager().getDefaultDisplay();
            displayWidth = display.getWidth();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(getResources(),R.drawable.a19,options);
            int width = options.outWidth;
            Log.d("width comp:", "img width: " + width + "  display: " + displayWidth);
            if (width > displayWidth) {
                int widthRatio = (int)Math.round((float) width / (float) displayWidth);
                options.inSampleSize = widthRatio;
                Log.d("width ratio:", ""+widthRatio);
            }
            options.inJustDecodeBounds = false;
            scaledBitmap =  BitmapFactory.decodeResource(getResources(),R.drawable.a19, options);
            pallet = Bitmap.createScaledBitmap(scaledBitmap,scaledBitmap.getHeight(),displayWidth,true);
            this.path = new Path();
        }
        @Override
        protected void onDraw(Canvas canvas) {
            this.canvas = canvas;
            this.paint.setColor(selectedcolor);
            canvas.drawBitmap(pallet, 0, 0, paint);
        }
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    p1.x = (int) x;
                    p1.y = (int) y;
                    final int sourceColor = pallet.getPixel((int) x, (int) y);
                    Log.d("source color: ", "  "+sourceColor);
                    final int targetColor = paint.getColor();
                    if(sourceColor>=-16777216)
                        new TheTask(pallet, p1, sourceColor, targetColor).execute();
                    invalidate();
            }
            return true;
        }
        public void clear() {
            path.reset();
            invalidate();
        }
        public int getCurrentPaintColor() {
            return paint.getColor();
        }

        public void changePaintColor(int color) {
            this.paint.setColor(color);
        }
        public void changeImage(int id) {
            Log.d("MY TAG", "IN ON change image");
            scaledBitmap.recycle();
            pallet.recycle();
            path.reset();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(getResources(),id, options);
            int width = options.outWidth;
            Log.d("width comp:", "img width: " + width + "  display: " + displayWidth);
            if (width > displayWidth) {
                int widthRatio = (int)Math.ceil((float) width / (float) displayWidth);
                options.inSampleSize = widthRatio;
                Log.d("width ratio:", ""+widthRatio);
            }
            options.inJustDecodeBounds = false;
            scaledBitmap =  BitmapFactory.decodeResource(getResources(),id, options);
            pallet = Bitmap.createScaledBitmap(scaledBitmap, scaledBitmap.getHeight(), displayWidth, true);
            this.path = new Path();
            invalidate();
            canvas.drawBitmap(pallet, 0, 0, paint);
        }

        class TheTask extends AsyncTask<Void, Integer, Void> {
            Bitmap bmp;
            Point pt;
            int replacementColor, targetColor;
            public TheTask(Bitmap bm, Point p, int sc, int tc) {
                this.bmp = bm;
                this.pt = p;
                this.replacementColor = tc;
                this.targetColor = sc;
                pd.setMessage("Filling....");
                pd.show();
            }
            @Override
            protected void onPreExecute() {
                pd.show();
            }
            @Override
            protected void onProgressUpdate(Integer... values) {
            }
            @Override
            protected Void doInBackground(Void... params) {
                QueueLinearFloodFiller filler = new QueueLinearFloodFiller(bmp, targetColor, replacementColor);
                filler.setTolerance(10);
                filler.floodFill(pt.x, pt.y);
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {
                pd.dismiss();
                invalidate();
            }
        }
    }
}