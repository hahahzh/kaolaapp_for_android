package com.winwinapp.calendar;

import java.util.Calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;

import com.winwinapp.koala.R;

public class DateWidgetDayHeader extends View {
	// fields
	private final static int	iDayHeaderFontSize	= 30;

	// fields
	private Paint pt = new Paint();
	private RectF rect = new RectF();
	private int iWeekDay = -1;
	private boolean bHoliday = false;

	// methods
	public DateWidgetDayHeader(Context context, int iWidth, int iHeight) {
		super(context);
		setLayoutParams(new LayoutParams(iWidth, iHeight));
	}

	public void setData(int iWeekDay) {
		this.iWeekDay = iWeekDay;
		this.bHoliday = false;
		if ((iWeekDay == Calendar.SATURDAY) || (iWeekDay == Calendar.SUNDAY))
			this.bHoliday = true;
	}

	private void drawDayHeader(Canvas canvas) {
		if (iWeekDay != -1) {
			pt.setColor(Color.WHITE);
			canvas.drawRect(rect, pt);

			
			WindowManager wm = (WindowManager) getContext()
                    .getSystemService(Context.WINDOW_SERVICE);
 
			int width = wm.getDefaultDisplay().getWidth();
			int height = wm.getDefaultDisplay().getHeight();
     
			int size = 35;
			if(width==1080 && height==1920)size = 55;
			// text
			pt.setTypeface(null);
			pt.setTextSize(size);
			pt.setAntiAlias(true);
			pt.setFakeBoldText(true);
			pt.setColor(DayStyle.getColorTextHeader(bHoliday,iWeekDay));

			final int iTextPosY = getTextHeight();
			final String sDayName = DayStyle.getWeekDayName(iWeekDay);

			// draw day name
			final int iDayNamePosX = (int) rect.left
					+ ((int) rect.width() >> 1)
					- ((int) pt.measureText(sDayName) >> 1);
			canvas.drawText(sDayName, iDayNamePosX, rect.top + iTextPosY + 15,
					pt);
			
			
		}
		this.setBackgroundResource(R.color.white);
	}

	private int getTextHeight() {
		return (int) (-pt.ascent() + pt.descent());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// init rectangles
		rect.set(0, 0, this.getWidth(), this.getHeight());
		rect.inset(1, 1);

		// drawing
		drawDayHeader(canvas);
	}

}

