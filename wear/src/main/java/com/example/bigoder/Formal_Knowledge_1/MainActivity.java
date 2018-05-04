package com.example.bigoder.Formal_Knowledge_1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;

import utils.CustomToast_Text;

public class MainActivity extends Activity implements DataApi.DataListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    GoogleApiClient mGoogleAppiClient;
    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;

    private Path mPath;
    private float mPosX,mPosY;

    public static ViewPager viewPager;
    public static int firstCircleNum;
    public static Context mContext;

    LinearLayout mNumLayout;
    ImageView mPreSelectedBt;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewPager();
        mContext = getApplicationContext();
        new CustomToast_Text(mContext, "健康生活");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mGoogleAppiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    public void setToast(int viewsCurrent) {

        if (viewsCurrent == 1) {
            new CustomToast_Text(mContext, "健康生活");
        }else if(viewsCurrent == 2){
            new CustomToast_Text(mContext, "社交");
        }else if(viewsCurrent == 3){
            new CustomToast_Text(mContext, "社交");
        }else if(viewsCurrent == 4){
            new CustomToast_Text(mContext, "娱乐");
        }else if(viewsCurrent == 5){
            new CustomToast_Text(mContext, "常用工具");
        }else if(viewsCurrent == 6){
            new CustomToast_Text(mContext, "常用工具");
        }else if(viewsCurrent == 7){
            new CustomToast_Text(mContext, "系统");
        }
    }

    private void sendTextToPhone(String content){
        PutDataMapRequest request1 = PutDataMapRequest.create("/only_phone");
        DataMap dataMap1 = request1.getDataMap();
        dataMap1.putLong("time", System.currentTimeMillis());
        dataMap1.putString("content", content);
        Wearable.DataApi.putDataItem(mGoogleAppiClient, request1.asPutDataRequest());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();

            String xy = String.valueOf(x1) + "," + String.valueOf(y1) + "," + String.valueOf(x2) + "," + String.valueOf(y2);

            if(Math.abs(y1 - y2) < 30 & Math.abs(x1 - x2) < 30){
                sendTextToPhone("0" + "," + String.valueOf(x1) + "," + String.valueOf(y1)
                        + "," + String.valueOf(x1) + "," + String.valueOf(y1));
            }else if (Math.abs(x1 - x2) > 30 & x1 < x2 & Math.abs(y1 - y2) <= 150) {
                sendTextToPhone("-1" + "," + xy);
            }else if (Math.abs(x1 - x2) > 30 & x1 > x2 & Math.abs(y1 - y2) <= 150) {
                sendTextToPhone("+1" + "," + xy);
            }else if (Math.abs(y1 - y2) > 150 & y2 > y1) {
                sendTextToPhone("+2" + "," + xy);
            }else if (Math.abs(y1 - y2) > 150 & y2 < y1) {
                sendTextToPhone("-2" + "," + xy);
            }
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            float x = event.getX();
            float y = event.getY();
            sendTextToPhone("pia," + x + "," + y);
        }
        return super.dispatchTouchEvent(event);
    }

    private void initViewPager(){

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        View view1 = LayoutInflater.from(this).inflate(R.layout.activity_1st, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.activity_2nd, null);
        View view3 = LayoutInflater.from(this).inflate(R.layout.activity_3rd, null);
        View view4 = LayoutInflater.from(this).inflate(R.layout.activity_4th, null);
        View view5 = LayoutInflater.from(this).inflate(R.layout.activity_5th, null);
        View view6 = LayoutInflater.from(this).inflate(R.layout.activity_6th, null);
        View view7 = LayoutInflater.from(this).inflate(R.layout.activity_7th, null);

        viewPager.setPageTransformer(true, new CustmerAnimation());

        final Button button1 = (Button) view1.findViewById(R.id.b_exercise);
        final Button button2 = (Button) view1.findViewById(R.id.b_sleep);
        final Button button3 = (Button) view1.findViewById(R.id.b_stopwatch);
        final Button button4 = (Button) view1.findViewById(R.id.b_chess);
        final Button button5 = (Button) view2.findViewById(R.id.b_qq);
        final Button button6 = (Button) view2.findViewById(R.id.b_wechat);
        final Button button7 = (Button) view2.findViewById(R.id.b_messages);
        final Button button8 = (Button) view2.findViewById(R.id.b_mail);

        final Button button9 = (Button) view3.findViewById(R.id.b_phone);
        final Button button10 = (Button) view3.findViewById(R.id.b_contacts);
        final Button button11 = (Button) view3.findViewById(R.id.b_weibo);
        final Button button12 = (Button) view3.findViewById(R.id.b_ailpay);
        final Button button13 = (Button) view4.findViewById(R.id.b_camera);
        final Button button14 = (Button) view4.findViewById(R.id.b_music);
        final Button button15 = (Button) view4.findViewById(R.id.b_video);
        final Button button16 = (Button) view4.findViewById(R.id.b_photos);

        final Button button17 = (Button) view5.findViewById(R.id.b_browser);
        final Button button18 = (Button) view5.findViewById(R.id.b_calculator);
        final Button button19 = (Button) view5.findViewById(R.id.b_passbook);
        final Button button20 = (Button) view5.findViewById(R.id.b_note);
        final Button button21 = (Button) view6.findViewById(R.id.b_stocks);
        final Button button22 = (Button) view6.findViewById(R.id.b_calendar);
        final Button button23 = (Button) view6.findViewById(R.id.b_weather);
        final Button button24 = (Button) view6.findViewById(R.id.b_compass);

        final Button button25 = (Button) view7.findViewById(R.id.b_settings);
        final Button button26 = (Button) view7.findViewById(R.id.b_maps);
        final Button button27 = (Button) view7.findViewById(R.id.b_clock);
        final Button button28 = (Button) view7.findViewById(R.id.b_downloads);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_Exercise.class);
                startActivity(intent);
                sendTextToPhone("健康运动,0,0,0,0");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_Sleep.class);
                startActivity(intent);
                sendTextToPhone("睡眠,0,0,0,0");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_Stopwatch.class);
                startActivity(intent);
                sendTextToPhone("运动秒表,0,0,0,0");
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_Chess.class);
                startActivity(intent);
                sendTextToPhone("国际象棋,0,0,0,0");
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_QQ.class);
                startActivity(intent);
                sendTextToPhone("腾讯QQ,0,0,0,0");
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_Wechat.class);
                startActivity(intent);
                sendTextToPhone("微信,0,0,0,0");
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_Messages.class);
                startActivity(intent);
                sendTextToPhone("短信息,0,0,0,0");
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_Mail.class);
                startActivity(intent);
                sendTextToPhone("邮箱,0,0,0,0");
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_Phone.class);
                startActivity(intent);
                sendTextToPhone("电话,0,0,0,0");
            }
        });
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_Contacts.class);
                startActivity(intent);
                sendTextToPhone("联系人,0,0,0,0");
            }
        });
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_Weibo.class);
                startActivity(intent);
                sendTextToPhone("新浪微博,0,0,0,0");
            }
        });
        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_AilPay.class);
                startActivity(intent);
                sendTextToPhone("支付宝,0,0,0,0");
            }
        });

        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_Camera.class);
                startActivity(intent);
                sendTextToPhone("相机,0,0,0,0");
            }
        });
        button14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_Music.class);
                startActivity(intent);
                sendTextToPhone("音乐,0,0,0,0");
            }
        });
        button15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_Video.class);
                startActivity(intent);
                sendTextToPhone("视频,0,0,0,0");
            }
        });
        button16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_Photos.class);
                startActivity(intent);
                sendTextToPhone("相册,0,0,0,0");
            }
        });

        button17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_Browser.class);
                startActivity(intent);
                sendTextToPhone("浏览器,0,0,0,0");
            }
        });
        button18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_Calculator.class);
                startActivity(intent);
                sendTextToPhone("计算器,0,0,0,0");
            }
        });
        button19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_Passbook.class);
                startActivity(intent);
                sendTextToPhone("卡包,0,0,0,0");
            }
        });
        button20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_Note.class);
                startActivity(intent);
                sendTextToPhone("笔记,0,0,0,0");
            }
        });

        button21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_Stocks.class);
                startActivity(intent);
                sendTextToPhone("股票,0,0,0,0");
            }
        });
        button22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_Calendar.class);
                startActivity(intent);
                sendTextToPhone("日历,0,0,0,0");
            }
        });
        button23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_Weather.class);
                startActivity(intent);
                sendTextToPhone("天气,0,0,0,0");
            }
        });
        button24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_Compass.class);
                startActivity(intent);
                sendTextToPhone("指南针,0,0,0,0");
            }
        });

        button25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_Settings.class);
                startActivity(intent);
                sendTextToPhone("系统设置,0,0,0,0");
            }
        });
        button26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_Map.class);
                startActivity(intent);
                sendTextToPhone("自带导航,0,0,0,0");
            }
        });
        button27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_Clock.class);
                startActivity(intent);
                sendTextToPhone("系统时钟,0,0,0,0");
            }
        });
        button28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_Downloads.class);
                startActivity(intent);
                sendTextToPhone("下载,0,0,0,0");
            }
        });


        ArrayList<View> views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
        views.add(view5);
        views.add(view6);
        views.add(view7);

        MYViewPagerAdapter adapter = new MYViewPagerAdapter();
        adapter.setViews(views);
        viewPager.setAdapter(adapter);

        firstCircleNum = viewPager.getCurrentItem() + 1;

        mNumLayout = (LinearLayout) findViewById(R.id.main_dot);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.zzz_page_dot_normal);
        for (int i = 0; i < adapter.getCount(); i++) {
            ImageView bt = new ImageView(this);
            bt.setLayoutParams(new ViewGroup.LayoutParams(bitmap.getWidth(),bitmap.getHeight()));
            bt.setImageResource(R.drawable.zzz_page_dot_normal);
            mNumLayout.addView(bt);
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

                if(mPreSelectedBt != null){
                    mPreSelectedBt.setImageResource(R.drawable.zzz_page_dot_normal);
                }

                ImageView currentBt = (ImageView)mNumLayout.getChildAt(position);
                currentBt.setImageResource(R.drawable.zzz_page_dot_select);
                mPreSelectedBt = currentBt;

                //Log.i("INFO", "current item:"+position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    protected void onStart() {
        mGoogleAppiClient.connect();
        super.onStart();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Wearable.DataApi.addListener(mGoogleAppiClient, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    protected void onStop(){
        if(null != mGoogleAppiClient && mGoogleAppiClient.isConnected()){
            Wearable.DataApi.removeListener(mGoogleAppiClient,this);
            mGoogleAppiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {

    }
}


class MYViewPagerAdapter extends PagerAdapter {


    public static int  viewsCount = 0;
    public static int viewsCurrentItem = 0;

    private ArrayList<View> views;

    public void setViews(ArrayList<View> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        viewsCount = views.size();
        MainActivity ma = new MainActivity();
        viewsCurrentItem = MainActivity.viewPager.getCurrentItem();
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(View container, int position) {

        ((ViewPager) container).addView(views.get(position));
        return views.get(position);
    }
}


class CustmerAnimation implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;
    private static final float ROT_MAX = 20.0f;
    private float mRot;

    public void transformPage(View view, float position) {

        int viewsCount;
        int viewsCurrent;

        MYViewPagerAdapter mvpa = new MYViewPagerAdapter();
        viewsCount = MYViewPagerAdapter.viewsCount;
        viewsCurrent = MYViewPagerAdapter.viewsCurrentItem + 1;

        MainActivity ma = new MainActivity();

        if(MainActivity.firstCircleNum != viewsCurrent){
            MainActivity.firstCircleNum = viewsCurrent;
            System.out.println(viewsCurrent + "-" + viewsCount);
            System.out.println(view.getId() + "-" + position);
            ma.setToast(viewsCurrent);
        }

        if (position < -1)

        { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);
            view.setRotation(0);

        } else if (position <= 1) //a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
        { // [-1,1]
            // Modify the default slide transition to shrink the page as well
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
//            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
//            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0)
            {
//                view.setTranslationX(horzMargin - vertMargin / 2);
                mRot = (ROT_MAX * position);
                view.setPivotX(view.getMeasuredWidth() * 0.5f);
                view.setPivotY(view.getMeasuredHeight());
                view.setRotation(mRot);

            } else
            {
//                view.setTranslationX(-horzMargin + vertMargin / 2);
            }

            // Scale the page down (between MIN_SCALE and 1)
//            view.setScaleX(scaleFactor);
//            view.setScaleY(scaleFactor);
            mRot = (ROT_MAX * position);
            view.setPivotX(view.getMeasuredWidth() * 0.5f);
            view.setPivotY(view.getMeasuredHeight());
            view.setRotation(mRot);
            // Fade the page relative to its size.
            view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
                    / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
        } else
        { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
            view.setRotation(0);
        }
    }
}

//MYViewPagerAdapter_Browser mvpa = new MYViewPagerAdapter_Browser();
//viewsCount = mvpa.viewsCount;
//        viewsCurrent = mvpa.viewsCurrentItem + 1;
//
//        float delta;
//        int time = 300;
//        TranslateAnimation animation;
//
//        if (viewsCount == 1){
//
//        }
//        else if(viewsCurrent == 1) {
//        animation = new TranslateAnimation(60, 0, 0, 0);
//        animation.setInterpolator(new BounceInterpolator());
//        animation.setDuration(1000);
//        view.startAnimation(animation);
//        }
//        else if (viewsCount == viewsCurrent){
//        animation = new TranslateAnimation(-60, 0, 0, 0);
//        animation.setInterpolator(new BounceInterpolator());
//        animation.setDuration(1000);
//        view.startAnimation(animation);
//        }
//        else if(viewsCurrent == (viewsCount+1)/2){
//
//        }
//        else if (viewsCurrent < (viewsCount+1)/2){
//        delta = Math.abs(viewsCurrent-(viewsCount+1)/2)*60/(viewsCount-1)+30;
//        animation = new TranslateAnimation(0, delta, 0, 0);
//        animation.setInterpolator(new AccelerateDecelerateInterpolator());
//        animation.setDuration(time);
//        animation.setRepeatCount(1);
//        animation.setRepeatMode(Animation.REVERSE);
//        view.startAnimation(animation);
//        }
//        else if (viewsCurrent > (viewsCount+1)/2){
//        delta = Math.abs(viewsCurrent-(viewsCount+1)/2)*60/(viewsCount-1)+30;
//        animation = new TranslateAnimation(0, -delta, 0, 0);
//        animation.setInterpolator(new AccelerateDecelerateInterpolator());
//        animation.setDuration(time);
//        animation.setRepeatCount(1);
//        animation.setRepeatMode(Animation.REVERSE);
//        view.startAnimation(animation);
//        }