package com.winwinapp.calendar;

import java.util.Calendar;

import com.winwinapp.koala.R;
import com.winwinapp.util.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class DateWidgetDayCell extends View {
	// types
	public interface OnItemClick {
		public void OnClick(DateWidgetDayCell item);
	}

	public static int ANIM_ALPHA_DURATION = 100;
	// fields
	private final static float	fTextSize			= 20;
	private final static int	iMargin				= 1;
	private final static int iAlphaInactiveMonth = 0x00;

	Context mContext;
	// fields
	private int iDateYear = 0;
	private int iDateMonth = 0;
	private int iDateDay = 0;
	private int iDayOfWeek = 0;

	// fields
	private OnItemClick itemClick = null;
	private Paint pt = new Paint();
	private Paint mPtSeparator = new Paint();
	private RectF rect = new RectF();
	private String sDate = "";

	// fields
	private boolean bSelected = false;
	private boolean bIsActiveMonth = false;
	private boolean bToday = false;
	private boolean bHoliday = false;
	private boolean bTouchedDown = false;

	private Bitmap mBitmapSelect;
	private boolean mFlag_ongoing = true;
	private int mBitmapWidth;
	private int mBitmapHeight;
	private Paint mBackgroundPt = new Paint();
	private Paint mCirclePt = new Paint();
	// methods
	public DateWidgetDayCell(Context context, int iWidth, int iHeight) {
		super(context);
		mContext = context;
		mBitmapSelect = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.calendar_select_flag);
		mBitmapWidth = mBitmapSelect.getWidth();
		mBitmapHeight = mBitmapSelect.getHeight();
		//Toast.makeText(mContext, "wid="+mBitmapWidth+",height="+mBitmapHeight, Toast.LENGTH_SHORT).show();
		mPtSeparator.setColor(0xffcccccc);
		mBackgroundPt.setColor(0xFF000000);
		mCirclePt.setColor(0xFF000000);
		mCirclePt.setStyle(Paint.Style.STROKE);
		setFocusable(true);
		setLayoutParams(new LayoutParams(iWidth, iHeight));
	}

	public boolean getSelected() {
		return this.bSelected;
	}

	@Override
	public void setSelected(boolean bEnable) {
		if (this.bSelected != bEnable) {
			this.bSelected = bEnable;
			this.invalidate();
		}
	}

	public void setData(int iYear, int iMonth, int iDay, boolean bToday,
			boolean bHoliday, int iActiveMonth,int iDayOfWeek) {
		iDateYear = iYear;
		iDateMonth = iMonth;
		iDateDay = iDay;

		this.sDate = Integer.toString(iDateDay);
		this.bIsActiveMonth = (iDateMonth == iActiveMonth);
		this.bToday = bToday;
		this.bHoliday = bHoliday;
		this.iDayOfWeek = iDayOfWeek;
	}

	public void setItemClick(OnItemClick itemClick) {
		this.itemClick = itemClick;
	}

	private int getTextHeight() {
		return (int) (-pt.ascent() + pt.descent());
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean bResult = super.onKeyDown(keyCode, event);
		if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
				|| (keyCode == KeyEvent.KEYCODE_ENTER)) {
			doItemClick();
		}
		return bResult;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		boolean bResult = super.onKeyUp(keyCode, event);
		return bResult;
	}

	public void doItemClick() {
		if (itemClick != null)
			itemClick.OnClick(this);
	}

	@Override
	protected void onFocusChanged(boolean gainFocus, int direction,
			Rect previouslyFocusedRect) {
		super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
		invalidate();
	}

	public Calendar getDate() {
		Calendar calDate = Calendar.getInstance();
		calDate.clear();
		calDate.set(Calendar.YEAR, iDateYear);
		calDate.set(Calendar.MONTH, iDateMonth);
		calDate.set(Calendar.DAY_OF_MONTH, iDateDay);
		return calDate;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// init rectangles
		rect.set(0, 0, this.getWidth(), this.getHeight());
		rect.inset(1, 1);

		// drawing
		final boolean bFocused = IsViewFocused();

		drawSeparator(canvas);
		drawDayView(canvas, bFocused);
		drawDayNumber(canvas, bFocused);
	}
	
	private void drawSeparator(Canvas canvas){
		canvas.drawLine(0, rect.bottom, rect.right, rect.bottom, mPtSeparator);
	}

	private void drawDayView(Canvas canvas, boolean bFocused) {
		//		if (bSelected || bFocused) {
		//			LinearGradient lGradBkg = null;
		//
		//			if (bFocused) {
		//				lGradBkg = new LinearGradient(rect.left, 0, rect.right, 0,
		//						DayStyle.iColorBkgFocusDark,
		//						DayStyle.iColorBkgFocusLight, Shader.TileMode.CLAMP);
		//			}
		//
		//			if (bSelected) {
		//				lGradBkg = new LinearGradient(rect.left, 0, rect.right, 0,
		//						DayStyle.iColorBkgSelectedDark,
		//						DayStyle.iColorBkgSelectedLight, Shader.TileMode.MIRROR);
		//			}
		//
		//			if (lGradBkg != null) {
		//				pt.setShader(lGradBkg);
		//				canvas.drawRect(rect, pt);
		//			}
		//
		//			pt.setShader(null);
		//		} else {
		//
		//			pt.setColor(DayStyle.getColorBkg(bHoliday, bToday));
		//			if (!bIsActiveMonth)
		//				pt.setAlpha(iAlphaInactiveMonth);
		//			canvas.drawRect(rect, pt);
		//		}
		//this.setBackgroundResource(R.drawable.button_calendar01);
		
		if (bToday) {
			//			canvas.drawBitmap(((BitmapDrawable) getResources().getDrawable(R.drawable.calendar_today)).getBitmap(), 0, 0, pt);
			//this.setBackgroundResource(R.drawable.icon_calendar_today);
		}
		if (bSelected) {
			//this.setBackgroundResource(R.drawable.button_calendar02_click);
		}
	}

	public void drawDayNumber(Canvas canvas, boolean bFocused) {
		// draw day number
		
		pt.setTypeface(null);
		pt.setAntiAlias(true);
		pt.setShader(null);
		pt.setFakeBoldText(true);
		pt.setTextSize(Utils.sp2px(mContext, fTextSize));

		pt.setUnderlineText(false);
		if (bToday)
			pt.setUnderlineText(true);

		int iTextPosX = (int) rect.right - (int) pt.measureText(sDate);
		int iTextPosY = (int) rect.bottom + (int) (-pt.ascent())
				- getTextHeight();

		iTextPosX -= ((int) rect.width() >> 1)
				- ((int) pt.measureText(sDate) >> 1);
		iTextPosY -= ((int) rect.height() >> 1) - (getTextHeight() >> 1);

		iTextPosY = iTextPosY - (mBitmapHeight + 10)/2;//the below flag
		// draw text
		if (bSelected || bFocused) {
			if (bSelected)
				pt.setColor(DayStyle.iColorTextSelected);
			if (bFocused)
				pt.setColor(DayStyle.iColorTextFocused);
		} else {
			pt.setColor(DayStyle.getColorText(bHoliday, bToday,iDayOfWeek));
		}

//		if(bToday){
//			pt.setColor(0xFFFFFFFF);
//		}
		
		if (!bIsActiveMonth){
			pt.setAlpha(iAlphaInactiveMonth);
		}
			
		
		if (bToday) {//draw background circle
			pt.setColor(0xFF000000);
			float radius = Math.min(rect.width()/2,
					(rect.height() - mBitmapHeight - 10-15)/2);//circle paddingTop = 10, paddingBottom = 5
			
			canvas.drawCircle(rect.width()/2, 10 + radius ,radius , mCirclePt);
		}
		
		if (bSelected) {
			pt.setColor(0xFFFFFFFF);
			float radius = Math.min(rect.width()/2,
					(rect.height() - mBitmapHeight - 10-15)/2);//circle paddingTop = 10, paddingBottom = 5
			
			canvas.drawCircle(rect.width()/2, 10 + radius ,radius , mBackgroundPt);
		}
		canvas.drawText(sDate, iTextPosX, iTextPosY + iMargin, pt);

		pt.setUnderlineText(false);
		
		if(mFlag_ongoing && bIsActiveMonth){
			//mBitmapSelect.
			float left = (rect.width() - mBitmapWidth)/2;
			float top = rect.height() - mBitmapHeight - 5;
			canvas.drawBitmap(mBitmapSelect, left, top, null);
		}
	}

	public boolean IsViewFocused() {
		return (this.isFocused() || bTouchedDown);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean bHandled = false;
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			bHandled = true;
			bTouchedDown = true;
			invalidate();
			startAlphaAnimIn(DateWidgetDayCell.this);
		}
		if (event.getAction() == MotionEvent.ACTION_CANCEL) {
			bHandled = true;
			bTouchedDown = false;
			invalidate();
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			bHandled = true;
			bTouchedDown = false;
			invalidate();
			doItemClick();
		}
		return bHandled;
	}

	public static void startAlphaAnimIn(View view) {
		AlphaAnimation anim = new AlphaAnimation(0.5F, 1);
		anim.setDuration(ANIM_ALPHA_DURATION);
		anim.startNow();
		view.startAnimation(anim);
	}

	public int getiDateMonth() {
		return iDateMonth;
	}

	public void setiDateMonth(int iDateMonth) {
		this.iDateMonth = iDateMonth;
	}
	
}

