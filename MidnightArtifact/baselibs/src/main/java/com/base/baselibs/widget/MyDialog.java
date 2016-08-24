package com.base.baselibs.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import com.base.baselibs.R;

public abstract class MyDialog extends Dialog {
	private Window window;
	private LayoutParams layoutParams;

	protected MyDialog(Context context) {
		super(context, R.style.DT_DIALOG_THEME);
	}

	protected MyDialog(Context context, int theme) {
		super(context, theme);
	}

	protected void createView() {
		super.setContentView(getView());
		window = getWindow();
		layoutParams = window.getAttributes();
		super.setCanceledOnTouchOutside(true);
		window.addFlags(LayoutParams.FLAG_DIM_BEHIND);
		layoutParams.alpha = 0.98765f;
		layoutParams.dimAmount = 0.56789f;
		window.setWindowAnimations(R.style.DT_DIALOG_ANIMATIONS);
	}

	public void setWindowPosition(int xPos, int yPos) {
		layoutParams.x = xPos;
		layoutParams.y = yPos;
	}

	public void setWindowSize(int width, int height) {
		layoutParams.width = width;
		layoutParams.height = height;
	}

	public int getWindowWidth() {
		return layoutParams.width;
	}

	public int getWindowHeight() {
		return layoutParams.height;
	}

	public void setWindowGravity(int gravity) {
		window.setGravity(gravity);
	}

	public void setWindowAnimations(int anims) {
		window.setWindowAnimations(anims);
	}

	public void setWindowAlpha(float alpha) {
		layoutParams.alpha = alpha;
	}

	public void setWindowBgAlpha(float bgAlpha) {
		layoutParams.dimAmount = bgAlpha;
	}

	protected abstract View getView();
}