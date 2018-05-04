package com.example.bigoder.Formal_Knowledge_1;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

/**
 * Created by mofan on 16/3/11.
 */
public class Activity_Mail extends Activity implements DataApi.DataListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient mGoogleAppiClient;

    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;
    protected int activityCloseEnterAnimation;
    protected int activityCloseExitAnimation;
    public static int firstCircleNum;
    public static Context mContext;

    LinearLayout mNumLayout;
    ImageView mPreSelectedBt;
    public static ViewPager viewPager;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acti_mail_main);
        initViewPager();
        mContext = getApplicationContext();
        new CustomToast_Text(mContext, "未读邮件");
        TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[] {android.R.attr.windowAnimationStyle});
        int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);
        activityStyle.recycle();
        activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[] {android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation});
        activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);
        activityCloseExitAnimation = activityStyle.getResourceId(1, 0);
        activityStyle.recycle();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mGoogleAppiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }
    public void setToast(int viewsCurrent) {

        if (viewsCurrent == 1) {
            new CustomToast_Text(mContext, "未读邮件");
        }else if(viewsCurrent == 2){
            new CustomToast_Text(mContext, "语音发送");
        }else if(viewsCurrent == 3){
            new CustomToast_Text(mContext, "星级邮件");
        }else if(viewsCurrent == 4){
            new CustomToast_Text(mContext, "收件箱");
        }else if(viewsCurrent == 5){
            new CustomToast_Text(mContext, "发件箱");
        }else if(viewsCurrent == 6){
            new CustomToast_Text(mContext, "垃圾箱");
        }else if(viewsCurrent == 7){
            new CustomToast_Text(mContext, "设置");
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
                System.out.println("0" + "," + String.valueOf(x1) + "," + String.valueOf(y1)
                        + "," + String.valueOf(x1) + "," + String.valueOf(y1));
            }else if (Math.abs(x1 - x2) > 30 & x1 < x2 & Math.abs(y1 - y2) <= 150) {
                sendTextToPhone("-1" + "," + xy);
            }else if (Math.abs(x1 - x2) > 30 & x1 > x2 & Math.abs(y1 - y2) <= 150) {
                sendTextToPhone("+1" + "," + xy);
            }else if (Math.abs(y1 - y2) > 150 & y2 < y1) {
                sendTextToPhone("-2" + "," + xy);
            }else if (Math.abs(y1 - y2) > 150 & y2 > y1) {
                sendTextToPhone("+2" + "," + xy);
                sendTextToPhone("＊主菜单＊,0,0,0,0");
                overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
                finish();
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        View view1 = LayoutInflater.from(this).inflate(R.layout.acti_mail_1, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.acti_mail_2, null);
        View view3 = LayoutInflater.from(this).inflate(R.layout.acti_mail_3, null);
        View view4 = LayoutInflater.from(this).inflate(R.layout.acti_mail_4, null);
        View view5 = LayoutInflater.from(this).inflate(R.layout.acti_mail_5, null);
        View view6 = LayoutInflater.from(this).inflate(R.layout.acti_mail_6, null);
        View view7 = LayoutInflater.from(this).inflate(R.layout.acti_mail_7, null);

        viewPager.setPageTransformer(true, new CustmerAnimation_Mail());

        ArrayList<View> views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
        views.add(view5);
        views.add(view6);
        views.add(view7);

        MYViewPagerAdapter_Mail adapter = new MYViewPagerAdapter_Mail();
        adapter.setViews(views);
        viewPager.setAdapter(adapter);
        firstCircleNum = viewPager.getCurrentItem() + 1;

        mNumLayout = (LinearLayout) findViewById(R.id.mall_dot);

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

                if (mPreSelectedBt != null) {
                    mPreSelectedBt.setImageResource(R.drawable.zzz_page_dot_normal);
                }

                ImageView currentBt = (ImageView) mNumLayout.getChildAt(position);
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

class MYViewPagerAdapter_Mail extends PagerAdapter {

    public static int  viewsCount = 0;
    public static int viewsCurrentItem = 0;

    private ArrayList<View> views;

    public void setViews(ArrayList<View> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        viewsCount = views.size();
        Activity_Mail ma = new Activity_Mail();
        viewsCurrentItem = ma.viewPager.getCurrentItem();
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


class CustmerAnimation_Mail implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;
    private static final float ROT_MAX = 20.0f;
    private float mRot;

    public void transformPage(View view, float position) {
        int viewsCount;
        int viewsCurrent;

        Activity_Mail ma = new Activity_Mail();
        viewsCount = MYViewPagerAdapter_Mail.viewsCount;
        viewsCurrent = MYViewPagerAdapter_Mail.viewsCurrentItem + 1;

        if(Activity_Mail.firstCircleNum != viewsCurrent){
            Activity_Mail.firstCircleNum = viewsCurrent;
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
            if (position < 0)
            {
                mRot = (ROT_MAX * position);
                view.setPivotX(view.getMeasuredWidth() * 0.5f);
                view.setPivotY(view.getMeasuredHeight());
                view.setRotation(mRot);
            } else
            {
            }
            mRot = (ROT_MAX * position);
            view.setPivotX(view.getMeasuredWidth() * 0.5f);
            view.setPivotY(view.getMeasuredHeight());
            view.setRotation(mRot);
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