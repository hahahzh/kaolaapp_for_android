package com.winwinapp.calendar;

import java.util.Calendar;

import android.R;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;

public class DayStyle {
	
	// methods
	private static String[] getWeekDayNames() {
		String[] vec = new String[10];

		vec[Calendar.SUNDAY] = "��";
		vec[Calendar.MONDAY] = "һ";
		vec[Calendar.TUESDAY] = "��";
		vec[Calendar.WEDNESDAY] = "��";
		vec[Calendar.THURSDAY] = "��";
		vec[Calendar.FRIDAY] = "��";
		vec[Calendar.SATURDAY] = "��";
		return vec;
	}

	public static String getWeekDayName(int iDay) {
		return vecStrWeekDayNames[iDay];
	}

	// fields
	private final static String[] vecStrWeekDayNames = getWeekDayNames();

	// fields
	public final static int			iColorFrameHeader			= 0xffcccccc;
	public final static int			iColorFrameHeaderHoliday	= 0xffcccccc;
	public final static int			iColorTextHeader			= 0xff000000;
	
	public final static int			iColorText					= 0xff333333;
	public final static int			iColorBkg					= 0xffffffff;
	public final static int			iColorBkgHoliday			= 0xffffffff;

	public final static int			iColorTextToday				= 0xff002200;
	public final static int			iColorBkgToday				= 0xff88bb88;

	public final static int			iColorTextSelected			= 0xff001122;

	public final static int			iColorTextFocused			= 0xff221100;
	 

	// methods
	public static int getColorFrameHeader(boolean bHoliday) {
		if (bHoliday) {
			return iColorFrameHeaderHoliday;
		}
		return iColorFrameHeader;
	}

	public static int getColorTextHeader(boolean bHoliday,int iWeekDay) {
		if (bHoliday) {
			if (iWeekDay == Calendar.SATURDAY) 
				return Color.RED;
			else
				return Color.RED;
		}
		return iColorTextHeader;
	}

	public static int getColorText(boolean bHoliday, boolean bToday,int iDayOfWeek) {
		if (bToday)
			return iColorTextToday;
		if (bHoliday) {
			if (iDayOfWeek == Calendar.SATURDAY) {
				return Color.RED;
			}
			else
			    return Color.RED;
		}    
		return iColorText;
	}

	public static int getColorBkg(boolean bHoliday, boolean bToday) {
		if (bToday)
			return iColorBkgToday;
		if (bHoliday) 
			return iColorBkgHoliday;
		return iColorBkg;
	}

	public static int getWeekDay(int index, int iFirstDayOfWeek) {
		int iWeekDay = -1;
		if (iFirstDayOfWeek == Calendar.MONDAY) {
			iWeekDay = index + Calendar.MONDAY;
			if (iWeekDay > Calendar.SATURDAY)
				iWeekDay = Calendar.SUNDAY;
		}
		if (iFirstDayOfWeek == Calendar.SUNDAY) {
			iWeekDay = index + Calendar.SUNDAY;
		}
		return iWeekDay;
	}
}
