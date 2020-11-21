package com.example.mobile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class FeedbackTouch extends View {

    private Paint paint = new Paint();

    private float x = -100;
    private float y = -100;

    public FeedbackTouch(Context context) {
        super(context);
        init();
    }

    public FeedbackTouch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FeedbackTouch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void init() {
        setWillNotDraw(false);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                x = event.getX();
                y = event.getY();
                FeedbackTouch.this.invalidate();
                return true;
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.GREEN);
        canvas.drawCircle(x,y, 30, paint);
    }


}
