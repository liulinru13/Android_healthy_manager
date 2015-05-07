package mmrx.com.metrolayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by mmrx on 2015/4/30.
 */
public class MetroView extends View {

    private String mTitle;
    private String mDetail;
    private Bitmap mIcon;//图标
    private Bitmap mCheckIcon;//打卡图标
    private boolean mIsCheck;//是否打卡
    private int mBackgroundColor;
    private int mWeight;//权重
    private int mStyle;//1 2 3 样式
    private int mTitleTextSize;
    private int mDetailTextSize;

    private Paint mPaint;
    private Paint mPaint_check;

    private Rect mTitleRect;
    private Rect mDetailRect;
    private Rect mIconRect;
    private Rect mTextRect;
    private Rect mcheckRect;

    private int mWidth;
    private int mHeight;

    private int MARGIN_TB;//上下边框尺寸
    private int MARGIN_LR;//左右边框尺寸


    public MetroView(Context context){
        this(context,null);
    }

    public MetroView(Context context,AttributeSet attr){
        this(context,attr,0);
    }

    public MetroView(Context context,AttributeSet attr,int defStyle){
        super(context,attr,defStyle);
        TypedArray ta = context.getTheme().obtainStyledAttributes(attr,
                R.styleable.metroView, defStyle, 0);
        int num = ta.getIndexCount();
        mIsCheck = false;
        //样式默认为 icon 文字 水平
        mStyle = 1;
        //背景色默认为红色
        mBackgroundColor = ta.getColor(R.styleable.metroView_metroBackground,
                context.getResources().getColor(R.color.red));
        mTitleTextSize = ta.getDimensionPixelSize(R.styleable.metroView_metroTitleTextSize,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                        MetroConstant.DEFAULT_TITLE_SIZE, getResources().getDisplayMetrics()));
        mDetailTextSize = ta.getDimensionPixelSize(R.styleable.metroView_metroTitleTextSize,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                        MetroConstant.DEFAULT_DETAIL_SIZE, getResources().getDisplayMetrics()));

        //icon
        int iconId = ta.getResourceId(R.styleable.metroView_metroIconRef,MetroConstant.DEFAULT_ICON);
        mIcon = BitmapCache.getInstance().getBitmap(iconId,context);
        int checkId = ta.getResourceId(R.styleable.metroView_metroIsCheckIcon,MetroConstant.DEFAULT_CHECK_ICON);
        mCheckIcon = BitmapCache.getInstance().getBitmap(checkId,context);

        mDetail = "";
        mTitle = "";
        for(int i=0;i<num;i++){
            int index = ta.getIndex(i);
            if(index == R.styleable.metroView_metroTitle)
                mTitle = ta.getString(index);
            else if(index == R.styleable.metroView_metroDetail)
                mDetail = ta.getString(index);
            else if(index == R.styleable.metroView_metroStyle)
                mStyle = ta.getInt(index, 1);
        }

        ta.recycle();
        initRect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int iconWidht = 0,iconHeight = 0,textWidth = 0,textHeight = 0;

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMod = MeasureSpec.getMode(widthMeasureSpec);

        if(widthMod == MeasureSpec.EXACTLY){
            mWidth = width;
        }
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMod = MeasureSpec.getMode(heightMeasureSpec);
        if(heightMod == MeasureSpec.EXACTLY){
            mHeight = height;
        }
        //获得icon的尺寸
        iconHeight = mIcon.getHeight();
        iconWidht = mIcon.getWidth();

        //获得文字的高度
        textWidth = mTextRect.width();
        textHeight = mTextRect.height();

        int h = 0,w = 0;
        //icon和文字水平放置
        if( mStyle == 1 && (!mTitle.equals("") || !mDetail.equals("")) ){
            w = MetroConstant.BETWEEN_ICON_TEXT + textWidth + iconWidht;
            h = textHeight >= iconHeight?textHeight:iconHeight;
        }
        //icon和文字竖直放置
        else if( mStyle == 2 && (!mTitle.equals("") || !mDetail.equals("")) ){
            w = textWidth >= iconWidht?textWidth:iconWidht;
            h = MetroConstant.BETWEEN_ICON_TEXT + textHeight + iconHeight;
        }
        //只有icon
        else{
            w = iconWidht;
            h = iconHeight;
        }

        mWidth = mWidth>=w?mWidth:w;
        mHeight = mHeight>=h?mHeight:h;

        MARGIN_TB = mHeight/10;
        MARGIN_LR = mWidth/10;
//        mWidth += 2*MARGIN_LR;
//        mHeight += 2*MARGIN_TB;

        mcheckRect = new Rect(MARGIN_LR,MARGIN_TB,
                mWidth-MARGIN_LR,
                mHeight-MARGIN_TB);

        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //先画背景
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mBackgroundColor);
        mPaint.setAntiAlias(true);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);

        //check标记,画上打卡标志
        if(mIsCheck){
            mPaint_check.setAlpha(70);
            canvas.drawBitmap(mCheckIcon,null,mcheckRect,mPaint_check);
        }

        //icon的rect
        mIconRect.left = MARGIN_LR;
        mIconRect.top = MARGIN_TB;
        if(mStyle == 1){
//            mIconRect.right = MARGIN_LR + mIcon.getWidth();
//            mIconRect.bottom = MARGIN_TB + mIcon.getHeight();
            mIconRect.right = getMeasuredWidth()-MARGIN_LR-MetroConstant.BETWEEN_ICON_TEXT-mTextRect.width();
            mIconRect.bottom = getMeasuredHeight()-MARGIN_TB;
        }
        else if(mStyle == 2){
//            mIconRect.right = MARGIN_LR + mIcon.getWidth();
//            mIconRect.bottom = MARGIN_TB + mIcon.getHeight();
            mIconRect.right = getMeasuredWidth()-3*MARGIN_LR;
            mIconRect.bottom = getMeasuredHeight()-MARGIN_TB-MetroConstant.BETWEEN_ICON_TEXT-mTextRect.height();
        }
        else{
            mIconRect.right = getMeasuredWidth()-MARGIN_LR;
            mIconRect.bottom = getMeasuredHeight()-MARGIN_TB;
        }
        //画图标
        canvas.drawBitmap(mIcon,null,mIconRect,mPaint);


        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
//        icon和文字水平放置
        if(mStyle == 1){
            mPaint.setTextSize(mTitleTextSize);
            canvas.drawText(mTitle,
                    mIconRect.right,
                    mHeight/3,mPaint);
            mPaint.setTextSize(mDetailTextSize);
            canvas.drawText(mDetail,
                    mIconRect.right,
                    mHeight/3+mDetailRect.height()*3/2,mPaint);
        }
//        icon和文字垂直放置
        else if(mStyle == 2){
            mPaint.setTextSize(mTitleTextSize);
            canvas.drawText(mTitle,
                    MARGIN_LR,
                    mIconRect.bottom+mTitleRect.height(),mPaint);
            mPaint.setTextSize(mDetailTextSize);
            canvas.drawText(mDetail,
                    MARGIN_LR,
                    mIconRect.bottom+mTitleRect.height()+mDetailRect.height()*3/2,mPaint);
        }
    }

    private void initRect(){
        mPaint = new Paint();
        mPaint_check = new Paint();
        mIconRect = new Rect();
        mDetailRect = new Rect();
        mTitleRect = new Rect();

        //获取文字的尺寸
        mPaint.setTextSize(mTitleTextSize);
        mPaint.getTextBounds(mTitle, 0, mTitle.length(), mTitleRect);
        mPaint.setTextSize(mDetailTextSize);
        mPaint.getTextBounds(mDetail,0,mDetail.length(),mDetailRect);

        mTextRect = new Rect();
        mTextRect.set(0,0,
                mDetailRect.width()>=mTitleRect.width()?mDetailRect.width():mTitleRect.width(),
                mDetailRect.height()*3/2+mTitleRect.height());
    }

    public void setDetail(String detail){
        this.mDetail = detail;
        invalidate();
    }
    public void setTitle(String title){
        this.mTitle = title;
        invalidate();
    }
    public void setCheck(Boolean isCheck){
        this.mIsCheck = isCheck;
        invalidate();
    }

    public void setAttribute(AbsMetroNode amn,Context context){
        this.mTitle = amn.getmTitle();
        this.mDetail = amn.getmDetail();

        this.mIcon = BitmapCache.getInstance().getBitmap(amn.getmIcon(),context);

        final MetroConstant.MetroStyle style = amn.getmStyle();
        switch (style){
            case HORIZONTAL:
                this.mStyle = 1;
                break;
            case VERTICAL:
                this.mStyle = 2;
                break;
            case ICON_ONLY:
                this.mStyle = 3;
        }
        this.mTitleTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                amn.getmTitleSize(), getResources().getDisplayMetrics());
        this.mDetailTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                amn.getmDetailSize(), getResources().getDisplayMetrics());
        this.mBackgroundColor = amn.getmColor();
        this.mIsCheck = amn.ismIsCheck();
        mCheckIcon = BitmapCache.getInstance().getBitmap(MetroConstant.DEFAULT_CHECK_ICON,context);
        initRect();
        postInvalidate();
//        invalidate();
    }


}
