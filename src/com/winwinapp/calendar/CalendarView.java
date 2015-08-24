package com.winwinapp.calendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.winwinapp.koala.R;
import com.winwinapp.util.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class CalendarView extends LinearLayout {

	private ArrayList<DateWidgetDayCell>	days					= new ArrayList<DateWidgetDayCell>();
	private Calendar						calStartDate			= Calendar.getInstance();
	private Calendar						calToday				= Calendar.getInstance();
	private Calendar						calCalendar				= Calendar.getInstance();
	private Calendar						calSelected				= Calendar.getInstance();

	private LinearLayout					layContent				= null;
	
	private int								iFirstDayOfWeek			= Calendar.SUNDAY;
	private int								iMonthViewCurrentMonth	= 0;
	private int								iMonthViewCurrentYear	= 0;
	public static final int					SELECT_DATE_REQUEST		= 111;
	private static int				iDayCellSize			= 76;//38
	private static int				iDayHeaderHeight		= 68;//34
	private static int				iDayCellHeight			= 68;//34
	private TextView						yearTextView, monthTextView;
	private int								mYear;
	private int								mMonth;
	private int								mDay;
	Context mContext;
	
	
	public CalendarView(Context context) {
		super(context);
		// TODO 自动生成的构造函数存根
		mContext = context;
		initView();
	}
	
	public CalendarView(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
		// TODO 自动生成的构造函数存根
		mContext = context;
		initView();
	}
	
	public CalendarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO 自动生成的构造函数存根
		mContext = context;
		initView();
	}

	private void initView(){
		iFirstDayOfWeek = Calendar.SUNDAY;
		mYear = calSelected.get(Calendar.YEAR);
		mMonth = calSelected.get(Calendar.MONTH);
		mDay = calSelected.get(Calendar.DAY_OF_MONTH);
		//setContentView(generateContentView());
		generateContentView();
		calStartDate = getCalendarStartDate();
		DateWidgetDayCell daySelected = updateCalendar();
		if (daySelected != null)
			daySelected.requestFocus();
	}
	
	private LinearLayout createLayout(int iOrientation) {
		LinearLayout lay = new LinearLayout(mContext);
		lay.setLayoutParams(new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		lay.setOrientation(iOrientation);
		return lay;
	}


	private void generateTopButtons(LinearLayout layTopControls) {

		LinearLayout centerLayout = new LinearLayout(mContext);
		centerLayout.setOrientation(LinearLayout.VERTICAL);
		LayoutParams param = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		param.weight = 1;
		centerLayout.setLayoutParams(param);
		centerLayout.setGravity(Gravity.CENTER);
		
		monthTextView = new TextView(mContext);
		monthTextView.setTextSize(20);
		//monthTextView.setTextColor(0x00FF00);
		param.gravity = Gravity.CENTER;
		monthTextView.setLayoutParams(param);
		monthTextView.setText(format(mMonth + 1) + "月");
		monthTextView.setTextColor(getResources().getColor(R.color.green));
		
		yearTextView = new TextView(mContext);
		yearTextView.setTextColor(getResources().getColor(R.color.green));
		yearTextView.setLayoutParams(param);
		yearTextView.setText(mYear + "年");
		
		centerLayout.addView(monthTextView);
		centerLayout.addView(yearTextView);
		
		
		//Button btnPrevMonth = new Button(mContext);
		//btnPrevMonth.setBackgroundResource(R.drawable.before);
		ImageView btnPrevMonth = new ImageView(mContext);
		LayoutParams param1 = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		param1.gravity = Gravity.CENTER_VERTICAL;
		param1.leftMargin = Utils.dp2px(mContext, 10);
		btnPrevMonth.setLayoutParams(param1);
		btnPrevMonth.setImageResource(R.drawable.before);
		
		
		
		//Button btnNextMonth = new Button(mContext);
		//btnNextMonth.setBackgroundResource(R.drawable.after);
		ImageView btnNextMonth = new ImageView(mContext);
		btnNextMonth.setLayoutParams(param1);
		param1.rightMargin = Utils.dp2px(mContext, 10);
		btnNextMonth.setImageResource(R.drawable.after);
		
		

		// set events
		btnPrevMonth.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {
				setPrevMonthViewItem();
			}
		});

		btnNextMonth.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {
				setNextMonthViewItem();
			}
		});
		
		

		layTopControls.addView(btnPrevMonth);
		layTopControls.addView(centerLayout);
		layTopControls.addView(btnNextMonth);
	}


	private void generateContentView() {
		//LinearLayout layMain = createLayout(LinearLayout.VERTICAL);
		//layMain.setPadding(8, 8, 8, 8);
		//layMain.setGravity(Gravity.CENTER_HORIZONTAL);
		WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		int cellSize = width / 7;
		iDayCellHeight = iDayCellHeight * cellSize / iDayCellSize;
		iDayCellSize = cellSize;
		iDayHeaderHeight = iDayCellHeight;
		LinearLayout layTopControls = createLayout(LinearLayout.HORIZONTAL);
		layTopControls.setLayoutParams(new LayoutParams(iDayCellSize * 7, LayoutParams.WRAP_CONTENT));
		//layTopControls.setGravity(Gravity.CENTER);

		layContent = createLayout(LinearLayout.VERTICAL);
		layContent.setLayoutParams(new LayoutParams(iDayCellSize * 7, LayoutParams.WRAP_CONTENT));
		layTopControls.setBackgroundResource(R.drawable.layout_border);
		
		generateTopButtons(layTopControls);
		generateCalendar(layContent);
		this.addView(layTopControls);
		this.addView(layContent);
		this.setBackgroundColor(Color.rgb(250, 250, 250));
	}


	private View generateCalendarRow() {
		LinearLayout layRow = createLayout(LinearLayout.HORIZONTAL);
		for (int iDay = 0; iDay < 7; iDay++) {
			DateWidgetDayCell dayCell = new DateWidgetDayCell(mContext,
					iDayCellSize, iDayCellHeight);
			dayCell.setItemClick(mOnDayCellClick);
			days.add(dayCell);
			layRow.addView(dayCell);
		}
		return layRow;
	}


	private View generateCalendarHeader() {
		LinearLayout layRow = createLayout(LinearLayout.HORIZONTAL);
		for (int iDay = 0; iDay < 7; iDay++) {
			DateWidgetDayHeader day = new DateWidgetDayHeader(mContext,
					iDayCellSize, iDayHeaderHeight);
			final int iWeekDay = DayStyle.getWeekDay(iDay, iFirstDayOfWeek);
			day.setData(iWeekDay);
			layRow.addView(day);
		}
		return layRow;
	}


	private void generateCalendar(LinearLayout layContent) {
		layContent.addView(generateCalendarHeader());
		days.clear();
		for (int iRow = 0; iRow < 6; iRow++) {
			layContent.addView(generateCalendarRow());
		}
	}


	private Calendar getCalendarStartDate() {
		calToday.setTimeInMillis(System.currentTimeMillis());
		calToday.setFirstDayOfWeek(iFirstDayOfWeek);

		if (calSelected.getTimeInMillis() == 0) {
			calStartDate.setTimeInMillis(System.currentTimeMillis());
			calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
		} else {
			calStartDate.setTimeInMillis(calSelected.getTimeInMillis());
			calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
		}
		updateStartDateForMonth();

		return calStartDate;
	}


	private DateWidgetDayCell updateCalendar() {
		DateWidgetDayCell daySelected = null;
		boolean bSelected = false;
		final boolean bIsSelection = (calSelected.getTimeInMillis() != 0);
		final int iSelectedYear = calSelected.get(Calendar.YEAR);
		final int iSelectedMonth = calSelected.get(Calendar.MONTH);
		final int iSelectedDay = calSelected.get(Calendar.DAY_OF_MONTH);
		calCalendar.setTimeInMillis(calStartDate.getTimeInMillis());
		for (int i = 0; i < days.size(); i++) {
			final int iYear = calCalendar.get(Calendar.YEAR);
			final int iMonth = calCalendar.get(Calendar.MONTH);
			final int iDay = calCalendar.get(Calendar.DAY_OF_MONTH);
			final int iDayOfWeek = calCalendar.get(Calendar.DAY_OF_WEEK);
			DateWidgetDayCell dayCell = days.get(i);
			// check today
			boolean bToday = false;
			if (calToday.get(Calendar.YEAR) == iYear)
				if (calToday.get(Calendar.MONTH) == iMonth)
					if (calToday.get(Calendar.DAY_OF_MONTH) == iDay)
						bToday = true;
			// check holiday
			boolean bHoliday = false;
			if ((iDayOfWeek == Calendar.SATURDAY)
					|| (iDayOfWeek == Calendar.SUNDAY))
				bHoliday = true;
			if ((iMonth == Calendar.JANUARY) && (iDay == 1))
				bHoliday = true;

			dayCell.setData(iYear, iMonth, iDay, bToday, bHoliday,
					iMonthViewCurrentMonth, iDayOfWeek);
			bSelected = false;
			if (bIsSelection)
				if ((iSelectedDay == iDay) && (iSelectedMonth == iMonth)
						&& (iSelectedYear == iYear)) {
					bSelected = true;
				}
			dayCell.setSelected(bSelected);
			if (bSelected)
				daySelected = dayCell;
			calCalendar.add(Calendar.DAY_OF_MONTH, 1);
			dayCell.invalidate();
		}
		layContent.invalidate();
		return daySelected;
	}


	private void updateStartDateForMonth() {
		iMonthViewCurrentMonth = calStartDate.get(Calendar.MONTH);
		iMonthViewCurrentYear = calStartDate.get(Calendar.YEAR);
		calStartDate.set(Calendar.DAY_OF_MONTH, 1);
		UpdateCurrentMonthDisplay();
		// update days for week
		int iDay = 0;
		int iStartDay = iFirstDayOfWeek;
		if (iStartDay == Calendar.MONDAY) {
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
			if (iDay < 0)
				iDay = 6;
		}
		if (iStartDay == Calendar.SUNDAY) {
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
			if (iDay < 0)
				iDay = 6;
		}
		calStartDate.add(Calendar.DAY_OF_WEEK, -iDay);
	}


	private void UpdateCurrentMonthDisplay() {
		mYear = calCalendar.get(Calendar.YEAR);
	}


	private void setPrevMonthViewItem() {
		iMonthViewCurrentMonth--;
		if (iMonthViewCurrentMonth == -1) {
			iMonthViewCurrentMonth = 11;
			iMonthViewCurrentYear--;
		}
		calStartDate.set(Calendar.DAY_OF_MONTH, 1);
		calStartDate.set(Calendar.MONTH, iMonthViewCurrentMonth);
		calStartDate.set(Calendar.YEAR, iMonthViewCurrentYear);
		updateDate();
		updateCenterTextView(iMonthViewCurrentMonth, iMonthViewCurrentYear);
	}


	private void setNextMonthViewItem() {
		iMonthViewCurrentMonth++;
		if (iMonthViewCurrentMonth == 12) {
			iMonthViewCurrentMonth = 0;
			iMonthViewCurrentYear++;
		}
		calStartDate.set(Calendar.DAY_OF_MONTH, 1);
		calStartDate.set(Calendar.MONTH, iMonthViewCurrentMonth);
		calStartDate.set(Calendar.YEAR, iMonthViewCurrentYear);
		updateDate();
		updateCenterTextView(iMonthViewCurrentMonth, iMonthViewCurrentYear);
	}
	

	private void setPrevYearViewItem() {
		iMonthViewCurrentYear--;
		calStartDate.set(Calendar.DAY_OF_MONTH, 1);
		calStartDate.set(Calendar.MONTH, iMonthViewCurrentMonth);
		calStartDate.set(Calendar.YEAR, iMonthViewCurrentYear);
		updateDate();
		updateCenterTextView(iMonthViewCurrentMonth, iMonthViewCurrentYear);
	}
	

	private void setNextYearViewItem() {
		iMonthViewCurrentYear++;
		calStartDate.set(Calendar.DAY_OF_MONTH, 1);
		calStartDate.set(Calendar.MONTH, iMonthViewCurrentMonth);
		calStartDate.set(Calendar.YEAR, iMonthViewCurrentYear);
		updateDate();
		updateCenterTextView(iMonthViewCurrentMonth, iMonthViewCurrentYear);
	}
	
	private DateWidgetDayCell.OnItemClick	mOnDayCellClick	= new DateWidgetDayCell.OnItemClick() {
			
			public void OnClick(DateWidgetDayCell item) {
				calSelected.setTimeInMillis(item.getDate().getTimeInMillis());
				item.setSelected(true);
				updateCalendar();
				
				
				
				Intent ret = new Intent();
				ret.putExtra("year", calSelected.get(Calendar.YEAR));
				ret.putExtra("month",  calSelected.getTime().getMonth());
				ret.putExtra("day",  calSelected.getTime().getDate());
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Toast.makeText(mContext,format.format(calSelected.getTime()),
						Toast.LENGTH_SHORT).show();
			}
		};
	
	
	private void updateCenterTextView(int iMonthViewCurrentMonth, int iMonthViewCurrentYear) {
		yearTextView.setText(iMonthViewCurrentYear + "年");
		monthTextView.setText(format(iMonthViewCurrentMonth + 1) + "月");
	}
	

	private void updateDate() {
		updateStartDateForMonth();
		updateCalendar();
	}


	private String format(int x) {
		String s = "" + x;
		if (s.length() == 1)
			s = "0" + s;
		return s;
	}
	
}