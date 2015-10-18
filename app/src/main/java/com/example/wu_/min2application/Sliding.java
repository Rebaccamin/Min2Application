package com.example.wu_.min2application;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;


public class Sliding extends HorizontalScrollView {
    private LinearLayout mylayout ;
    private ViewGroup mycontent;
    private ViewGroup mymenu;
    private int sreenwid;
    private int menurightmargin =50;
    private boolean once=false;
    private int menuwidth;
    public Sliding(Context context,AttributeSet attrs) {
        this(context, attrs,0);

    }

    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        if(!once){
            mylayout=(LinearLayout)getChildAt(0);
            mymenu=(ViewGroup)mylayout. getChildAt(0);
            mycontent=(ViewGroup)mylayout.getChildAt(1);
            menuwidth=mymenu.getLayoutParams().width=sreenwid-menurightmargin;
            mycontent.getLayoutParams().width=sreenwid;
            once=true;
        }
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);

    }
    //设置偏移量，将menu隐藏
    protected void onLayout(boolean changed,int a,int b,int c,int d){
        super.onLayout(changed,a,b,c,d);
        if(changed){
            this.scrollTo(menuwidth,0);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action=ev.getAction();
        switch (action){
            case MotionEvent.ACTION_UP:
                int scroll=getScrollX();
                if(scroll>=menuwidth/2)
                {
                    this.smoothScrollTo(menuwidth,0);
                }
                else{
                    this.smoothScrollTo(0,0);
                }return  true;
        }

        return super.onTouchEvent(ev);

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float sacle=l*1.0f/menuwidth;
        mymenu.setTranslationX(menuwidth*sacle);
    }

    public Sliding(Context context) {
        this(context,null);
    }

    public Sliding(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取我们定义的属性
        TypedArray a=context.getTheme().obtainStyledAttributes(attrs, R.styleable.Sliding, defStyleAttr, 0);
        int n=a.getIndexCount();
        for(int i=0;i<n;i++){
            int b=a.getIndex(i);
            switch (b){
                case R.styleable.Sliding_rightPadding:
                    menurightmargin=a.getDimensionPixelOffset(b,(int)TypedValue.applyDimension
                            (TypedValue.COMPLEX_UNIT_DIP,50,context.getResources().getDisplayMetrics()));
                    break;
                default:break;
            }
        }
        a.recycle();//释放
        WindowManager wm= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dp=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dp);
        sreenwid=dp.widthPixels;
    }
}

