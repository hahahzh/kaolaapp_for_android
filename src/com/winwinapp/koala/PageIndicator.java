package com.winwinapp.koala;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class PageIndicator extends View {

	private Bitmap mDefault;
    private Bitmap mFocus;
    private int mPages = -1;
    private int mPos;
    private int mPointGap;
    private Paint mPaint;
    
    public PageIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
    }

    public PageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs,0);
        // TODO Auto-generated constructor stub
    }

    public PageIndicator(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    {
        mDefault = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.page_indicator_default);
        mFocus = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.page_indicator_focused);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        mPaint.setColor(0xFFFFFFFF);
        mPaint.setStrokeWidth(this.getResources().getDisplayMetrics().density);
        mPointGap = this.getResources().getDimensionPixelSize(R.dimen.page_indicator_padding_left);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        //super.onDraw(canvas);
        if(mDefault == null || mFocus == null || mPages <= 0){
            return;
        }
        
        int left = (getWidth() - mPages * mDefault.getWidth() - mPointGap *(mPages-1))/2;
        int hDefault = (getHeight() - mDefault.getHeight())/2;
        int hFocus = (getHeight() - mFocus.getHeight())/2;
        
        for(int i=0;i<mPages;i++){
            if(i!= mPos){
                mPaint.setAlpha(0);
                canvas.drawBitmap(mDefault, left, hDefault,null);
            }else{
                mPaint.setAlpha(0xFF);
                canvas.drawBitmap(mFocus, left, hFocus,mPaint);
            }
            left += mDefault.getWidth() + mPointGap;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
            int bottom) {
        // TODO Auto-generated method stub
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        if(mDefault != null){
            int h = Math.max(mDefault.getHeight(), heightMeasureSpec);
            setMinimumHeight(h);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    
    public void setPages(int pages){
        mPages = pages;
    }
    
    public void setCurrentPage(int pos){
        if(mPos != pos){
            mPos = pos;
            invalidate();
        }
    }
}
