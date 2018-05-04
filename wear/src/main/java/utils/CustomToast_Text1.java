package utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast_Text1 {
	private WindowManager wdm;
	private double time;
	private View mView;
	private WindowManager.LayoutParams params;
	private Handler mHandler;
	private Runnable mRun;
	private boolean isAttach = false;
	private int mtime = 2500;
	private static Toast toast;

	@SuppressLint("ShowToast")
	private CustomToast_Text1(Context context, String text) {
		wdm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		this.mHandler = new Handler();

		mRun = new MRunnable();

//		if (toast != null){
//			toast.setText(text);
//		}
//		else{
		toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
//		}
		mView = toast.getView();

		LinearLayout linearLayout = (LinearLayout) toast.getView();
		TextView messageTextView = (TextView) linearLayout.getChildAt(0);
		messageTextView.setTextSize(25);
		messageTextView.setAnimation(shakeAnimation(2));


		params = new WindowManager.LayoutParams();
		//params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.height = 68;
		params.width = WindowManager.LayoutParams.FILL_PARENT;
		params.format = PixelFormat.TRANSLUCENT;
		params.windowAnimations = android.R.style.Animation_Toast;
		params.type = WindowManager.LayoutParams.TYPE_TOAST;
		params.setTitle("Toast");
		params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
		params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
		params.y = 0;
		this.time = mtime;
	}


	public static CustomToast_Text1 makeText(Context context, String text) {
		CustomToast_Text1 toastCustom = new CustomToast_Text1(context, text);
		return toastCustom;
	}

	public void show() {
		if (isAttach)
			return;
		isAttach = true;
		wdm.addView(mView, params);
		mHandler.postDelayed(mRun, (long) time);
	}

	public void cancel() {
		if (isAttach) {
			wdm.removeView(mView);
			isAttach = false;
		}
		mHandler.removeCallbacks(mRun);
	}

	class MRunnable implements Runnable{

		@Override
		public void run() {
			isAttach = false;
			wdm.removeView(mView);
		}
	}

	public static Animation shakeAnimation(int counts) {
		Animation translateAnimation = new TranslateAnimation(0, 22, 0, 0);
		//设置一个循环加速器，使用传入的次数就会出现摆动的效果。
		translateAnimation.setInterpolator(new CycleInterpolator(counts));
		translateAnimation.setDuration(400);
		return translateAnimation;
	}
}
