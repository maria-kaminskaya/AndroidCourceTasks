package com.kmnvxh222.task4;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

import androidx.annotation.Nullable;

/**
 * Этапы инициализации View
 * 1) onAttachedToWindow
 * 2) onMeasure
 * 3) onLayout
 * 4) onDraw
 */
public class CustomView extends View {

    interface TouchActionListener {
        void onTouchDown(int x, int y);
    }

    private int radius = 0;
    private int radiusIn = 0;
    private int widthCenter = 0;
    private int heightCenter = 0;

    private Paint paint1 = new Paint();
    private Paint paint2 = new Paint();
    private Paint paint3 = new Paint();
    private Paint paint4 = new Paint();
    private Paint paint5 = new Paint();
    private RectF rect = new RectF();

    private TouchActionListener touchActionListener;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getAttrs(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(attrs);
    }

    private void getAttrs(@Nullable AttributeSet attrs) {
        if(attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomViewParams);
            radius = typedArray.getDimensionPixelSize(R.styleable.CustomViewParams_circle_radius, 0);
            radiusIn = radius / 3;
            typedArray.recycle();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        widthCenter = w / 2;
        heightCenter = h / 2;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        setColors();
    }

    private void setColors() {
        paint1.setColor(getResources().getColor(R.color.color1));
        paint2.setColor(getResources().getColor(R.color.color2));
        paint3.setColor(getResources().getColor(R.color.color3));
        paint4.setColor(getResources().getColor(R.color.color4));
        paint5.setColor(getResources().getColor(R.color.color5));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        rect.set(widthCenter - radius, heightCenter - radius, widthCenter + radius, heightCenter + radius);

        paint1.setStyle(Paint.Style.FILL);
        paint2.setStyle(Paint.Style.FILL);
        paint3.setStyle(Paint.Style.FILL);
        paint4.setStyle(Paint.Style.FILL);
        paint5.setStyle(Paint.Style.FILL);

        canvas.drawArc(rect, 0, 90, true, paint3);
        canvas.drawArc(rect, 90, 90, true, paint4);
        canvas.drawArc(rect, 180, 90, true, paint1);
        canvas.drawArc(rect, 270, 90, true, paint2);

        canvas.drawCircle(widthCenter, heightCenter, radiusIn, paint5);

        super.onDraw(canvas);
    }

    private int randomColor() {
        Random r = new Random();
        int red = r.nextInt(256);
        int green = r.nextInt(256);
        int blue = r.nextInt(256);
        return Color.rgb(red, green, blue);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if(touchActionListener != null) {
                touchActionListener.onTouchDown((int) event.getX(), (int) event.getY());
                float xTouch = event.getX();
                float yTouch = event.getY();
                changeColors(xTouch, yTouch);
            }
        }
        return super.onTouchEvent(event);
    }

    public void setTouchActionListener(TouchActionListener touchActionListener) {
        this.touchActionListener = touchActionListener;
    }

    private void changeColors(float xTouch, float yTouch) {
        //(x – a)^2 + (y – b)^2 = R^2
        int rTouch = (int) (Math.pow((xTouch - widthCenter), 2) + Math.pow((yTouch - heightCenter), 2));
        double vectorTouch = Math.toDegrees(Math.atan((yTouch - heightCenter) / (xTouch - widthCenter)));

        if(rTouch <= Math.pow(radiusIn, 2)) {
            paint1.setColor(randomColor());
            paint2.setColor(randomColor());
            paint3.setColor(randomColor());
            paint4.setColor(randomColor());
        }else if(rTouch <= Math.pow(radius, 2)) {
            if(0 < vectorTouch && vectorTouch < 90 && heightCenter > yTouch) {
                paint1.setColor(randomColor());
            }
            if(-90 < vectorTouch && vectorTouch < 0 && widthCenter < xTouch) {
                paint2.setColor(randomColor());
            }
            if(0 < vectorTouch && vectorTouch < 90 && heightCenter < yTouch) {
                paint3.setColor(randomColor());
            }
            if(-90 < vectorTouch && vectorTouch < 0 && widthCenter > xTouch) {
                paint4.setColor(randomColor());
            }
        }
        invalidate();
    }
}
