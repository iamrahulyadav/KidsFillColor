package sourcecodeinfotech.tarals.kidsfillcolor;

import sourcecodeinfotech.tarals.kidsfillcolor.ColorPickerDialog.OnColorSelectedListener;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import com.sourcecodeinfotech.adapter.NavDrawerListAdapter;
import com.sourcecodeinfotech.slidingmenu.NavDrawerItem;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout dashBoard;
    private MyView myView;
    int displayWidth,selectedcolor=0xFFFF0000;
    Display display;
    Button b_red, b_blue, b_green, b_orange, b_next,b_select;
    private ColorPicker colorPicker;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle,mTitle;
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
			mTitle = mDrawerTitle = getTitle();
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		navDrawerItems = new ArrayList<NavDrawerItem>();
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(7, -1)));
		navMenuIcons.recycle();
		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
		adapter = new NavDrawerListAdapter(getApplicationContext(),navDrawerItems);
		mDrawerList.setAdapter(adapter);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer,
				R.string.app_name,
				R.string.app_name) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		if (savedInstanceState == null) {
			displayView(0);
		}
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
                ColorPickerDialog colorPickerDialog = new ColorPickerDialog(MainActivity.this, initialColor, new OnColorSelectedListener() {
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
                    Intent i=new Intent(MainActivity.this,ImageSelectActivity.class);
                    startActivityForResult(i, 2);
                }
            });
            dashBoard.addView(myView);
    }
	
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			displayView(position);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}
	private void displayView(int position) {
	/*	Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			break;
		case 1:
			fragment = new EnglishCaps();
			break;
		case 2:
			fragment = new EnglishSmall();
			break;
		case 3:
			fragment = new EnglishDigits();
			break;
		case 4:
			fragment = new GujConsonants();
			break;
		case 5:
			fragment = new GujDigits();
			break;
		case 6:
			fragment = new HindiConsonants();
			break;
		case 7:
			fragment = new HindiDigits();
			break;
		case 8:
			try {
			      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
			    } catch (android.content.ActivityNotFoundException anfe) {
			      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
			    }
			break;
		case 9:
			fragment= new AboutUsFragment();	  
		default:
			break;
		}
		if (fragment != null) {
			fragmentManager = getFragmentManager();
			fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.frame_container, fragment);
			fragmentTransaction.addToBackStack(null);  
			fragmentTransaction.commit();
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
		}*/
	}
	
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==2)
        {
            int id = getIntent().getIntExtra("image",R.drawable.a1);
            Log.d("Id of image",""+id);
            myView=null;
            myView=new MyView(MainActivity.this);
            Toast.makeText(getApplicationContext(),"Image id"+id,Toast.LENGTH_LONG).show();
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
            BitmapFactory.decodeResource(getResources(),R.drawable.a1,options);
            int width = options.outWidth;
            Log.d("width comp:", "img width: " + width + "  display: " + displayWidth);
            if (width > displayWidth) {
                int widthRatio = (int)Math.ceil((float) width / (float) displayWidth);
                options.inSampleSize = widthRatio;
                Log.d("width ratio:", ""+widthRatio);
            }
            options.inJustDecodeBounds = false;
            scaledBitmap =  BitmapFactory.decodeResource(getResources(),R.drawable.a1, options);
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