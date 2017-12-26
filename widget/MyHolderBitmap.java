package cn.zdxiang.sexygirl.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.SizeUtils;

/**
 * Created by JM on 2017/12/7 0007.上午 9:57
 * Description:
 */

public class MyHolderBitmap extends View {

    // 声明Paint对象
    private Paint mPaint = null;

    private Rect mBound;

    private String content = "继续滑动查看下一组图片";

    public MyHolderBitmap(Context context) {
        super(context);
        init();
    }

    public MyHolderBitmap(Context context, String content) {
        super(context);
        init();
        this.content = content;
    }

    public MyHolderBitmap(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyHolderBitmap(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBound = new Rect();
        mPaint = new Paint();
        mPaint.getTextBounds(content, 0, content.length(), mBound);
        mPaint.setAntiAlias(true);
        // 设置字体的尺寸
        mPaint.setTextSize(SizeUtils.dp2px(18));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            mPaint.getTextBounds(content, 0, content.length(), mBound);
            float textWidth = mBound.width();
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            mPaint.getTextBounds(content, 0, content.length(), mBound);
            float textHeight = mBound.height();
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            height = desired;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 设置Paint为无锯齿


//        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        canvas.drawText(content, getWidth() / 2, getHeight() / 2, mPaint);
    }
}
