package com.example.bigoder.Formal_Knowledge_1;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class MainActivity extends ActionBarActivity implements DataApi.DataListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient mGoogleAppiClient;
    private SoundPool sp;//声明一个SoundPool
    private int music;//定义一个整型用load（）；来设置suondID
    int count = 0;
    int motionNum;
    int spacialNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGoogleAppiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        final Button button_M1 = (Button) findViewById(R.id.button_M1);
        final Button button_M2 = (Button) findViewById(R.id.button_M2);
        final Button button_M3 = (Button) findViewById(R.id.button_M3);
        final Button button_S1 = (Button) findViewById(R.id.button_S1);
        final Button button_S2 = (Button) findViewById(R.id.button_S2);
        final Button button_S3 = (Button) findViewById(R.id.button_S3);
        final Button buttonStart = (Button) findViewById(R.id.buttonStart);
        final Button buttonEnd = (Button) findViewById(R.id.buttonEnd);

        button_M1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                motionNum = 1;
                final TextView textView = (TextView) findViewById(R.id.textViewMotion);
                textView.setText("1");
            }
        });

        button_M2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                motionNum = 2;
                final TextView textView = (TextView) findViewById(R.id.textViewMotion);
                textView.setText("2");
            }
        });

        button_M3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                motionNum = 3;
                final TextView textView = (TextView) findViewById(R.id.textViewMotion);
                textView.setText("3");
            }
        });

        button_S1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                spacialNum = 1;
                final TextView textView = (TextView) findViewById(R.id.textViewSpacial);
                textView.setText("1");
            }
        });

        buttonStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playSoundsStart();
                buttonEnd.setVisibility(View.VISIBLE);
                buttonStart.setVisibility(View.GONE);
                button_M1.setVisibility(View.INVISIBLE);
                button_M2.setVisibility(View.INVISIBLE);
                button_M3.setVisibility(View.INVISIBLE);
                button_S1.setVisibility(View.INVISIBLE);
                button_S2.setVisibility(View.INVISIBLE);
                button_S3.setVisibility(View.INVISIBLE);
                try {
                    taskStateStart();
                } catch (IOException e) {e.printStackTrace();}
            }
        });

        buttonEnd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playSoundsEnd();
                buttonStart.setVisibility(View.VISIBLE);
                buttonEnd.setVisibility(View.GONE);
                try {
                    taskStateEnd();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (count >= 5){

                    button_M1.setVisibility(View.VISIBLE);
                    button_M2.setVisibility(View.VISIBLE);
                    button_M3.setVisibility(View.VISIBLE);
                    button_S1.setVisibility(View.VISIBLE);
                    button_S2.setVisibility(View.VISIBLE);
                    button_S3.setVisibility(View.VISIBLE);
                    try {
                        textPrintXY("＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    count = 0;
                }
            }
        });
    }

    public void playSoundsStart(){
        sp= new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);//第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        music = sp.load(this, R.raw.bgm1, 1); //把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sp.play(music, 1, 1, 0, 0, 1);
    }

    public void playSoundsEnd(){
        sp= new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);//第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        int r = (int)(2 + Math.random()*(6));
        int mResId = R.raw.bgm4;
        if(r == 2){mResId = R.raw.bgm2;}
        else if(r == 3){mResId = R.raw.bgm3;}
        else if(r == 4){mResId = R.raw.bgm4;}
        else if(r == 5){mResId = R.raw.bgm5;}
        else if(r == 6){mResId = R.raw.bgm6;}
        else if(r == 7){mResId = R.raw.bgm7;}
        music = sp.load(this, mResId, 1); //把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sp.play(music, 1, 1, 0, 0, 1);
    }

    private void taskStateStart() throws IOException {
        count = count + 1;
        TextView text = (TextView) this.findViewById(R.id.textViewRRR);
        text.setText("任务" + String.valueOf(count) + ", 进行中");
        text.invalidate();
        textPrintXY("任务" + String.valueOf(count) + ", 开始");
    }

    private void taskStateEnd() throws IOException {
        TextView text = (TextView) this.findViewById(R.id.textViewRRR);
        text.setText("任务" + String.valueOf(count) + ", 结束");
        text.invalidate();
        textPrintXY("任务" + String.valueOf(count) + ", 结束");
    }

    private void textPrintXY(String content) throws IOException {
        FileOutputStream phone_outStream =openFileOutput("M" + String.valueOf(motionNum)+ "S"+ String.valueOf(spacialNum) + ".txt",
                Context.MODE_APPEND); //追加模式继续写
        phone_outStream.write((content + "\r\n").getBytes());
    }

    private void textPrintXYLine(String content) throws IOException {
        FileOutputStream phone_outStream =openFileOutput("Line_M" + String.valueOf(motionNum)+ "S"+ String.valueOf(spacialNum) + ".txt",
                Context.MODE_APPEND); //追加模式继续写
        phone_outStream.write((content + "\r\n").getBytes());
    }

    @Override
    protected void onStart() {
        mGoogleAppiClient.connect();
        super.onStart();
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        for(DataEvent event: dataEventBuffer){
            if(event.getType() == DataEvent.TYPE_DELETED){

            }else if(event.getType() == DataEvent.TYPE_CHANGED){
                DataMap dataMap = DataMapItem.fromDataItem(event.getDataItem()).getDataMap();
                if(event.getDataItem().getUri().getPath().equals("/only_phone")){
                    String content = dataMap.get("content");
                    long currentTimeMillis = System.currentTimeMillis();
                    Date date = new Date(currentTimeMillis);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss SSS");
                    if(content.substring(0,3).equals("pia")){
                        try {
                            textPrintXYLine(content + "," + simpleDateFormat.format(date));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        try {
                            textPrintXY(content + "," + simpleDateFormat.format(date));
                            textPrintXYLine("******************************************");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
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

    protected void onStop(){
        if(null != mGoogleAppiClient && mGoogleAppiClient.isConnected()){
            Wearable.DataApi.removeListener(mGoogleAppiClient,this);
            mGoogleAppiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

}

