package com.winwinapp.bids;

import java.util.ArrayList;

import com.winwinapp.koala.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

public class BidsSlideTextView extends LinearLayout {

	Context mContext;
	TextView mTextViewAbove;
	TextView mTextViewFirst;
	TextView mTextViewSecond;
	TextView mTextViewThird;
	TextView mTextViewBottom;
	TextView[] mTextArray = new TextView[5];
	int[] mIndex = new int[5];
	int mCurrentTopIndex = 0;
	int mCurrentBotIndex = 0;
	
	float mSelectTextSize = 20;
	float mCommonTextSize = 15;
	int mAbove = 0;
	int mFirst = 1;
	int mSecond;
	int mThird;
	int mBottom;
	int mBoundTop;
	int mBoundBottom;
	private VelocityTracker velocityTracker;
	private Scroller mScroller;  
    private static final int SNAP_VELOCITY = 200;
    private int mTouchSlop;
    LinearLayout mPopLL;
    private boolean isSlide = false;
    private ArrayList<String> mRawData;
    Rect mTouchRect = new Rect();
    /** 
     * 手指按下X的坐标 
     */  
    private int downY;  
    /** 
     * 手指按下Y的坐标 
     */  
    private int downX;  
	
	public BidsSlideTextView(Context context) {
		super(context);
		// TODO 自动生成的构造函数存根
		mContext = context;
	}

	public BidsSlideTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO 自动生成的构造函数存根
		mContext = context;
        
        mScroller = new Scroller(mContext);  
        mTouchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
        
	}

	public BidsSlideTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO 自动生成的构造函数存根
		mContext = context;
	}

	public void setRawData(ArrayList<String> raw){
		mRawData = raw;
		//initArray();
	}
	
	private void addVelocityTracker(MotionEvent event) {  
        if (velocityTracker == null) {  
            velocityTracker = VelocityTracker.obtain();  
        }  
  
        velocityTracker.addMovement(event);  
    }
	
	private void recycleVelocityTracker() {  
        if (velocityTracker != null) {  
            velocityTracker.recycle();  
            velocityTracker = null;  
        }  
    } 
	
	
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO 自动生成的方法存根
		//Toast.makeText(mContext, "enter int onTouchEvent,action="+ev.getAction(), Toast.LENGTH_LONG).show();
		Log.i("chenjian","action="+ev.getAction());
		if (isSlide) {
			
            requestDisallowInterceptTouchEvent(true);  
            addVelocityTracker(ev);  
            final int action = ev.getAction();  
            //Log.i("chenjian","action="+action);
            int x = (int) ev.getX();
            int y = (int) ev.getY();
            switch (action) {  
            case MotionEvent.ACTION_DOWN:  
                break;  
            case MotionEvent.ACTION_MOVE:  
                  
                MotionEvent cancelEvent = MotionEvent.obtain(ev);  
                cancelEvent.setAction(MotionEvent.ACTION_CANCEL |  
                           (ev.getActionIndex()<< MotionEvent.ACTION_POINTER_INDEX_SHIFT));  
                onTouchEvent(cancelEvent);
                cancelEvent.recycle();
                  
                int deltaY = downY - y;  
                downY = y;
  
                // 手指拖动itemView滚动, deltaX大于0向左滚动，小于0向右滚  
                //itemView.scrollBy(deltaX, 0);
                ScrollWithDelta(0,-deltaY,false);
                //Toast.makeText(mContext, "x="+downX+",y="+downY +",deltaY="+deltaY, Toast.LENGTH_LONG).show();
                  
                return true;  //拖动的时候ListView不滚动  
            //case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
            	
                int velocityX = getScrollVelocity();  
                if (velocityX > SNAP_VELOCITY) {  
                    //scrollRight();  
                } else if (velocityX < -SNAP_VELOCITY) {  
                    //scrollLeft();  
                } else {  
                      
                } 
                scrollByDistanceY();
                isSlide = false;
                recycleVelocityTracker();  
                // 手指离开的时候就不响应左右滚动  
                break;  
            }  
        }else{
        	if(MotionEvent.ACTION_MOVE == ev.getAction())
        	if (Math.abs(ev.getY() - downY) > mTouchSlop) {  
                isSlide = true;
            }
        }
  
        return true;  
	}
	
	private int getScrollVelocity() {  
        velocityTracker.computeCurrentVelocity(1000);  
        int velocity = (int) velocityTracker.getYVelocity();  
        return velocity;  
    } 
	
	private void scrollByDistanceY(){
		int minabs = Integer.MAX_VALUE;
		int minY = 0;
		if(Math.abs(mTextViewFirst.getY()-mAbove) < minabs){
			minY = (int) (mTextViewFirst.getY()-mAbove);
			minabs = Math.abs(minY);
		}
		if(Math.abs(mTextViewFirst.getY()-mFirst) < minabs){
			minY = (int) (mTextViewFirst.getY()-mFirst);
			minabs = Math.abs(minY);
		}
		if(Math.abs(mTextViewFirst.getY()-mSecond) < minabs){
			minY = (int) (mTextViewFirst.getY()-mSecond);
			minabs = Math.abs(minY);
		}
		if(Math.abs(mTextViewFirst.getY()-mThird) < minabs){
			minY = (int) (mTextViewFirst.getY()-mThird);
			minabs = Math.abs(minY);
		}
		if(Math.abs(mTextViewFirst.getY()-mBottom) < minabs){
			minY = (int) (mTextViewFirst.getY()-mBottom);
			minabs = Math.abs(minY);
		}
		Log.i("chenjian","begin,scrollByDistanceY,minY="+(-minY));
		ScrollWithDelta(0,-minY,true);
		Log.i("chenjian","end,scrollByDistanceY,minY="+(-minY));
	}
	
	private boolean shouldScroll(int deltaX,int deltaY){
		boolean bl = true;
		float next;
		int i = 0;
		for(TextView tmp:mTextArray){
			if(mIndex[i] == -1){
				next = tmp.getY()+deltaY;
				tmp.setVisibility(View.INVISIBLE);
				if(next> mFirst && next< mThird){
					bl = false;
				}
			}
			i++;
			
		}
		return bl;
	}
	
	private void ScrollWithDelta(int deltaX, int deltaY,boolean skip){
		synchronized(this){
			while(deltaY > (mSecond-mFirst)){
				deltaY -= (mSecond-mFirst);
			}
			
			while(deltaY < -(mSecond-mFirst)){
				deltaY += mSecond-mFirst;
			}
			boolean bl = shouldScroll(deltaX,deltaY);
			Log.i("chenjian","ScrollWithDelta,deltaY="+deltaY+",bl="+bl);
			if((bl || skip) && isSlide){
				int index = 0;
				boolean up = true;
				for(TextView tmp:mTextArray){
					tmp.setTranslationY(tmp.getTranslationY()+deltaY);
					if(deltaY > 0)
					{
						up = false;
					}
					setTextSize(tmp,index,up);
					index++;
		//		mTextViewAbove.setTranslationY(mTextViewAbove.getTranslationY()+deltaY);
		//		mTextViewFirst.setTranslationY(mTextViewFirst.getTranslationY()+deltaY);
		//		mTextViewSecond.setTranslationY(mTextViewSecond.getTranslationY()+deltaY);
		//		mTextViewThird.setTranslationY(mTextViewThird.getTranslationY()+deltaY);
		//		mTextViewBottom.setTranslationY(mTextViewBottom.getTranslationY()+deltaY);
		//		setTextSize(mTextViewAbove);
		//		setTextSize(mTextViewFirst);
		//		setTextSize(mTextViewSecond);
		//		setTextSize(mTextViewThird);
		//		setTextSize(mTextViewBottom);
				}
				
				for(int i=0;i<mTextArray.length;i++){
					if(mIndex[i]!=-1 &&( ((mIndex[i] & 0x1000) != 0) || (mIndex[i] & 02000) != 0)){
						rearangeArray();
					}
				}
			}
		}
	}
	
	public void rearangeArray(){
		if(mRawData != null && mRawData.size() > 0){
        	int size = mRawData.size();
        	int startIndex = 0;
        	for(int i=0;i<mTextArray.length;i++){
        		if( mTextArray[i].getY()>= mSecond && mTextArray[i].getY() <= mThird){
        			startIndex = i;
        		}
        	}
        	
        	if(startIndex < 0 || startIndex > mRawData.size()){
        		for(int i=0;i<mTextArray.length;i++){
            		if(mIndex[i]> 0 && mIndex[i]<mRawData.size()){
            			startIndex = mIndex[i];
            		}
            	}
        	}
        	
        	if(startIndex < 0 || startIndex > mRawData.size()){
        		startIndex = 2;
        		mIndex[startIndex] = size/2;
        		mTextArray[startIndex].setText(mRawData.get(mIndex[startIndex]));
        	}
        	
        	if(size == 1){
        		mIndex[startIndex] = 0;
        		mTextArray[startIndex].setText(mRawData.get(0));
        		mCurrentTopIndex = mCurrentBotIndex = 0;
        	}else{
        		mCurrentTopIndex = 0;
        		int index = startIndex;
        		int arrIndex = mIndex[startIndex];
        		index--;
        		arrIndex--;
        		while(index >= 0 && arrIndex >= 0){
        			mIndex[index] = arrIndex;
        			mTextArray[index].setText(mRawData.get(arrIndex));
        			mTextArray[index].setVisibility(View.VISIBLE);
        			mCurrentTopIndex = arrIndex;
        			index--;
        			arrIndex--;
        		}
        		index = startIndex;
        		arrIndex = mIndex[startIndex];
        		index++;
        		arrIndex++;
        		while(index < 5 && arrIndex < size){
        			mIndex[index] = arrIndex;
        			mTextArray[index].setText(mRawData.get(arrIndex));
        			mTextArray[index].setVisibility(View.VISIBLE);
        			mCurrentBotIndex = arrIndex;
        			index++;
        			arrIndex++;
        		}	
        		//Log.i("chenjian","layout,mCurrentTopIndex="+mCurrentTopIndex+",mCurrentBotIndex="+mCurrentBotIndex);
        	}
        }
	}
		
	public void setTextSize(TextView tmp,int index,boolean up){
		float size = mCommonTextSize;
		
		//Log.i("chenjian","Enter into setTextSize,top="+tmp.getY()+",mFirst="+mFirst+",third="+mThird);
		if(tmp.getY() >= mFirst && tmp.getY() <= mThird){
			size = (mSelectTextSize - (mSelectTextSize - mCommonTextSize)*Math.abs(mSecond-tmp.getY())/(mThird-mSecond));
			tmp.setScaleX(size/mCommonTextSize);
			tmp.setScaleY(size/mCommonTextSize);
		}else{
			tmp.setScaleX(1);
			tmp.setScaleY(1);
		}
		
		if(tmp.getY() < mAbove && up){
			tmp.setTranslationY(tmp.getTranslationY()+mBoundBottom-mBoundTop);
			updateTextContent(tmp,true,index);
		}else if(tmp.getY() > mBottom && !up){
			tmp.setTranslationY(tmp.getTranslationY()-(mBoundBottom-mBoundTop));
			updateTextContent(tmp,false,index);
		}
	}
	
	public void updateTextContent(TextView textView,boolean isTop,int index){
		Log.i("chenjian","before updateTextContent,mCurrentTopIndex="+mCurrentTopIndex+",mCurrentBotIndex="+mCurrentBotIndex+",index="+index+",isTop="+isTop+",value="+mIndex[index]);
		
		if(isTop){//from top to bottom
			if(mIndex[index] != -1){
				if(mCurrentTopIndex == (mIndex[index]&0xFFF)){
					mCurrentTopIndex++;
				}else{
					mIndex[index] |= 0x1000;//add to temp list,will update later
					return;
				}
			}
			if(mCurrentBotIndex < mRawData.size()-1){
				mCurrentBotIndex++;
				mIndex[index] = mCurrentBotIndex;
				textView.setText(mRawData.get(mCurrentBotIndex));
				textView.setVisibility(View.VISIBLE);
			}else{
				mIndex[index] = -1;
				textView.setVisibility(View.INVISIBLE);
			}
		}else{//from bottom to top
			if(mIndex[index] != -1){
				if(mCurrentBotIndex == (mIndex[index]&0xFFF)){
					mCurrentBotIndex--;
				}else{
					mIndex[index] |= 0x2000;
					return;
				}
			}
			if(mCurrentTopIndex > 0){
				mCurrentTopIndex--;
				mIndex[index] = mCurrentTopIndex;
				textView.setText(mRawData.get(mCurrentTopIndex));
				textView.setVisibility(View.VISIBLE);
			}else{
				mIndex[index] = -1;
				textView.setVisibility(View.INVISIBLE);
			}
		}
			Log.i("chenjian","after updateTextContent,mCurrentTopIndex="+mCurrentTopIndex+",mCurrentBotIndex="+mCurrentBotIndex);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		// TODO 自动生成的方法存根
		switch (event.getAction()) {  
        case MotionEvent.ACTION_DOWN: {  
            addVelocityTracker(event);  
            // 假如scroller滚动还没有结束，我们直接返回  
            if (!mScroller.isFinished()) {  
                return super.onInterceptTouchEvent(event);  
            }  
            downX = (int) event.getX();  
            downY = (int) event.getY();  
  
            //Toast.makeText(mContext, "x="+downX+",y="+downY +","+mTouchRect.contains(downX,downY), Toast.LENGTH_LONG).show();
            //slidePosition = pointToPosition(downX, downY);  
  
            // 获取我们点击的item view  
            //itemView = getChildAt(slidePosition - getFirstVisiblePosition());  
            break;  
        }  
        case MotionEvent.ACTION_MOVE: {  
            if (Math.abs(event.getY() - downY) > mTouchSlop) {  
                isSlide = true;      
            }
            //Toast.makeText(mContext, "y="+event.getY()+",downY="+downY+",slop="+mTouchSlop, Toast.LENGTH_SHORT).show();
            break;  
        }  
        case MotionEvent.ACTION_UP:  
            recycleVelocityTracker();  
            break;  
        }  
  
        return true; 
	}

	@Override
	protected void onFinishInflate() {
		// TODO 自动生成的方法存根
		super.onFinishInflate();
		
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO 自动生成的方法存根
		super.onLayout(changed, l, t, r, b);
		mTextViewAbove = (TextView)findViewById(R.id.popup_bid_above);
        mTextViewFirst = (TextView)findViewById(R.id.popup_bid_first);
        mTextViewSecond = (TextView)findViewById(R.id.popup_bid_second);
        mTextViewThird = (TextView)findViewById(R.id.popup_bid_third);
        mTextViewBottom = (TextView)findViewById(R.id.popup_bid_bottom);
        
        mTextArray[0] = mTextViewAbove;
        mTextArray[1] = mTextViewFirst;
        mTextArray[2] = mTextViewSecond;
        mTextArray[3] = mTextViewThird;
        mTextArray[4] = mTextViewBottom;
        mIndex[0] = -1;
        mIndex[1] = -1;
        mIndex[2] = -1;
        mIndex[3] = -1;
        mIndex[4] = -1;
        for(int i=0;i<mTextArray.length;i++){
        	mTextArray[i].setVisibility(View.INVISIBLE);
        }
           
        initArray();
        
        mTextViewSecond.setScaleX(((float)mSelectTextSize)/mCommonTextSize);
        mTextViewSecond.setScaleY(((float)mSelectTextSize)/mCommonTextSize);
		
		mAbove = mTextViewAbove.getTop();
        mFirst = mTextViewFirst.getTop();
        mSecond = mTextViewSecond.getTop();
        mThird = mTextViewThird.getTop();
        mBottom = mTextViewBottom.getTop();
        mBoundTop = mAbove;
        mBoundBottom = mTextViewBottom.getBottom();
        this.getHitRect(mTouchRect);
        Log.i("chenjian",""+mAbove+","+mFirst+","+mSecond+","+mThird+","+mBottom+"");
        //Toast.makeText(mContext, ""+mAbove+","+mFirst+","+mSecond+","+mThird+","+mBottom+",b="+mBoundBottom+",top="+mTouchRect.top+",bot="+mTouchRect.bottom, Toast.LENGTH_SHORT).show();
	}

	public void initArray(){
		if(mRawData != null && mRawData.size() > 0){
        	int size = mRawData.size();
        	if(size == 1){
        		mIndex[2] = 0;
        		mTextArray[2].setText(mRawData.get(0));
        		mTextArray[2].setVisibility(View.VISIBLE);
        		mCurrentTopIndex = mCurrentBotIndex = 0;
        	}else{
        		mCurrentTopIndex = 0;
        		mIndex[2] = size/2;
        		mTextArray[2].setText(mRawData.get(mIndex[2]));
        		mTextArray[2].setVisibility(View.VISIBLE);
        		int index = 2;
        		int arrIndex = mIndex[2];
        		index--;
        		arrIndex--;
        		while(index >= 0 && arrIndex >= 0){
        			mIndex[index] = arrIndex;
        			mTextArray[index].setText(mRawData.get(arrIndex));
        			mTextArray[index].setVisibility(View.VISIBLE);
        			mCurrentTopIndex = arrIndex;
        			index--;
        			arrIndex--;
        		}
        		index = 2;
        		arrIndex = mIndex[2];
        		index++;
        		arrIndex++;
        		while(index < 5 && arrIndex < size){
        			mIndex[index] = arrIndex;
        			mTextArray[index].setText(mRawData.get(arrIndex));
        			mTextArray[index].setVisibility(View.VISIBLE);
        			mCurrentBotIndex = arrIndex;
        			index++;
        			arrIndex++;
        		}	
        		//Log.i("chenjian","layout,mCurrentTopIndex="+mCurrentTopIndex+",mCurrentBotIndex="+mCurrentBotIndex);
        	}
        }
	}
	
	@Override
	public void computeScroll() {
		// TODO 自动生成的方法存根
		super.computeScroll();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO 自动生成的方法存根
		
		super.onDraw(canvas);
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		// TODO 自动生成的方法存根
		canvas.clipRect(this.getLeft(), mFirst, this.getRight(), mBottom, Op.REPLACE);
		super.dispatchDraw(canvas);
	}
	
	public String getCurrentSelect(){
		for(int i=0;i<mTextArray.length;i++){
			if( mTextArray[i].getY()>= mSecond && mTextArray[i].getY() <= mThird){
				return mTextArray[i].getText().toString();
			}
		}
		return null;
	}
	
}
