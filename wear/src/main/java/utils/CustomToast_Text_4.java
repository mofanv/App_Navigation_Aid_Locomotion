package utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class CustomToast_Text_4 {
	private WindowManager wdm;
	private double time;
	private View mView;
	private WindowManager.LayoutParams params;
	private Handler mHandler;
	private Runnable mRun;
	private boolean isAttach = false;

	@SuppressLint("ShowToast")
	private CustomToast_Text_4(Context context, int drawable, int x, int y, double time) {
		wdm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		this.mHandler = new Handler();
		
		mRun = new MRunnable();

		Toast imageToast=new Toast(context);
		ImageView imageToastView = new ImageView(context);
		imageToastView.setImageResource(drawable);
		imageToastView.setMaxHeight(50);
		imageToastView.setMaxWidth(50);
		imageToastView.setAdjustViewBounds(true);
		imageToastView.setScaleType(ImageView.ScaleType.FIT_CENTER);

		imageToast.setView(imageToastView);
		imageToast.setDuration(Toast.LENGTH_SHORT);

		mView = imageToast.getView();

		params = new WindowManager.LayoutParams();
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		params.format = PixelFormat.TRANSLUCENT;
		params.windowAnimations = android.R.style.Animation_Toast;;
		params.type = WindowManager.LayoutParams.TYPE_TOAST;
		params.setTitle("Toast");
		params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
		params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
		params.y = y;
		params.x = x;
		this.time = time;
	}

	public static CustomToast_Text_4 makeText(Context context, int drawable, int x, int y,  double time) {
		CustomToast_Text_4 toastCustom = new CustomToast_Text_4(context, drawable, x, y, time);
		return toastCustom;
	}

	public void show() {
		if(isAttach) 
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
}
