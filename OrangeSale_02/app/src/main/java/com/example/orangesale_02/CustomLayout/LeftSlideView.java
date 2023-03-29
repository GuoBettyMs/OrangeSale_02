package com.example.orangesale_02.CustomLayout;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

/**
 * 左滑删除容器
 */
public class LeftSlideView  extends LinearLayout {

    public static final String TAG = "LeftSlideView"; //tag
    private final Context mContext;
    private int mTouchSlop;//最小触摸距离
    private int mRightCanSlide; //右边可滑动距离
    private float mInitX;//按下x
    private float mInitY;//按下y
    private ValueAnimator mValueAnimator;//属性动画
    private final int mAnimDuring = 200;//动画时长
    private final int mDelLength = 76;//删除按钮的长度
    private ViewPager mViewPager;
    private RecyclerView mRecyclerView;
    private CardView mCardView;
    private boolean isReCompute = true;
    private OnDelViewStatusChangeLister mStatusChangeLister;//状态监听

    /************************************ View *********************************************/
    public LeftSlideView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }
    /**
     * 初始化
     */
    private void init() {
        mTouchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
        mRightCanSlide = DPIUtil.dip2px(mContext, mDelLength);
        setBackgroundColor(Color.TRANSPARENT);
        // 水平布局
        setOrientation(LinearLayout.HORIZONTAL);
        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
    }

    /**
     * 设置内容区域
     */
    public void addContentView(View contentView) {
        //内容区域View
        contentView.setTag("contentView");

        View cv = findViewWithTag("contentView");
        if (cv != null) {
            this.removeView(cv);
        }
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        this.addView(contentView, layoutParams);
    }

    /**
     * 设置右边菜单区域
     */
    public void addMenuView(View menuView) {
        //菜单区域View
        menuView.setTag("menuView");

        View mv = findViewWithTag("menuView");
        if (mv != null) {
            this.removeView(mv);
        }
        LayoutParams layoutParams = new LayoutParams(mRightCanSlide, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(menuView, layoutParams);
    }

    /**
     * 设置Viewpager
     */
    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
    }

    /**
     * 设置RecyclerView
     */
    public void setRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    /** 设置CardView */
    public void setCardView(CardView cardView) {
        mCardView = cardView;
    }

    /*
     ********************************** 方法 ********************************************
     */
    /**
     * 拦截触摸事件
     * 主要处理了左滑移动功能以及滑动冲突问题
     * 为了防止垂直方向的同向冲突，那么需要将外层的RecyclerView传入左滑容器，在这个容器中会处理滑动冲突
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int actionMasked = ev.getActionMasked();
        Log.e(TAG, "onInterceptTouchEvent: actionMasked = " + actionMasked);
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                mInitX = ev.getRawX() + getScrollX();
                mInitY = ev.getRawY();
                clearAnim();

                if (mViewPager != null) {
                    mViewPager.requestDisallowInterceptTouchEvent(true);
                }

                if (mCardView != null) {
                    mCardView.requestDisallowInterceptTouchEvent(true);
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (mInitX - ev.getRawX() < 0) {
                    // 让父级容器拦截
                    if (mRecyclerView != null && isReCompute) {
                        mRecyclerView.requestDisallowInterceptTouchEvent(false);
                        isReCompute = false;
                    }

                    // 阻止ViewPager拦截事件
                    if (mViewPager != null) {
                        mViewPager.requestDisallowInterceptTouchEvent(true);
                    }
                    return false;
                }

                // y轴方向上达到滑动最小距离, x 轴未达到
                if (Math.abs(ev.getRawY() - mInitY) >= mTouchSlop
                        && Math.abs(ev.getRawY() - mInitY) > Math.abs(mInitX - ev.getRawX() - getScrollX())) {

                    // 让父级容器拦截
                    if (mRecyclerView != null && isReCompute) {
                        mRecyclerView.requestDisallowInterceptTouchEvent(false);
                        isReCompute = false;
                    }
                    return false;
                }

                // x轴方向达到了最小滑动距离，y轴未达到
                if (Math.abs(mInitX - ev.getRawX() - getScrollX()) >= mTouchSlop
                        && Math.abs(ev.getRawY() - mInitY) <= Math.abs(mInitX - ev.getRawX() - getScrollX())) {

                    // 阻止父级容器拦截
                    if (mRecyclerView != null && isReCompute) {
                        mRecyclerView.requestDisallowInterceptTouchEvent(true);
                        isReCompute = false;
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mRecyclerView != null) {
                    mRecyclerView.requestDisallowInterceptTouchEvent(false);
                    isReCompute = true;
                }
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 处理触摸事件
     * 主要处理了左滑移动功能以及滑动冲突问题
     * 需要注意何时处理左滑，何时不处理
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int actionMasked = ev.getActionMasked();
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                mInitX = ev.getRawX() + getScrollX();
                mInitY = ev.getRawY();
                clearAnim();

                if (mViewPager != null) {
                    mViewPager.requestDisallowInterceptTouchEvent(true);
                }

                if (mCardView != null) {
                    mCardView.requestDisallowInterceptTouchEvent(true);
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (mInitX - ev.getRawX() < 0) {

                    // 让父级容器拦截
                    if (mRecyclerView != null && isReCompute) {
                        mRecyclerView.requestDisallowInterceptTouchEvent(false);
                        isReCompute = false;
                    }

                    // 阻止ViewPager拦截事件
                    if (mViewPager != null) {
                        mViewPager.requestDisallowInterceptTouchEvent(true);
                        isReCompute = false;
                    }
                }

                // y轴方向上达到滑动最小距离, x 轴未达到
                if (Math.abs(ev.getRawY() - mInitY) >= mTouchSlop
                        && Math.abs(ev.getRawY() - mInitY) > Math.abs(mInitX - ev.getRawX() - getScrollX())) {

                    // 让父级容器拦截
                    if (mRecyclerView != null && isReCompute) {
                        mRecyclerView.requestDisallowInterceptTouchEvent(false);
                        isReCompute = false;
                    }
                }

                // x轴方向达到了最小滑动距离，y轴未达到
                if (Math.abs(mInitX - ev.getRawX() - getScrollX()) >= mTouchSlop
                        && Math.abs(ev.getRawY() - mInitY) <= Math.abs(mInitX - ev.getRawX() - getScrollX())) {

                    // 阻止父级容器拦截
                    if (mRecyclerView != null && isReCompute) {
                        mRecyclerView.requestDisallowInterceptTouchEvent(true);
                        isReCompute = false;
                    }
                }

                // 如果手指移动距离超过最小距离
                float translationX = mInitX - ev.getRawX();

                // 如果滑动距离已经大于右边可伸缩的距离后, 应该重新设置initx
                if (translationX > mRightCanSlide) {
                    mInitX = ev.getRawX() + mRightCanSlide;

                }

                // 如果互动距离小于0，那么重新设置初始位置initx
                if (translationX < 0) {
                    mInitX = ev.getRawX();
                }

                translationX = translationX > mRightCanSlide ? mRightCanSlide : translationX;
                translationX = translationX < 0 ? 0 : translationX;

                // 向左滑动
                if (translationX <= mRightCanSlide && translationX >= 0) {

                    scrollTo((int) translationX, 0);

                    return true;
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                if (mRecyclerView != null) {
                    mRecyclerView.requestDisallowInterceptTouchEvent(false);
                    isReCompute = true;
                }

                upAnim();

                return true;

            default:
                break;
        }
        return true;
    }

    /**
     * 清除动画
     */
    private void clearAnim() {
        if (mValueAnimator == null) {
            return;
        }

        mValueAnimator.end();
        mValueAnimator.cancel();
        mValueAnimator = null;
    }

    /**
     * 手指抬起执行动画
     */
    private void upAnim() {
        int scrollX = getScrollX();
        if (scrollX == mRightCanSlide || scrollX == 0) {
            if (mStatusChangeLister != null) {
                mStatusChangeLister.onStatusChange(scrollX == mRightCanSlide);
            }
            return;
        }

        clearAnim();

        // 如果显出一半松开手指，那么自动完全显示。否则完全隐藏
        if (scrollX >= mRightCanSlide / 2) {
            mValueAnimator = ValueAnimator.ofInt(scrollX, mRightCanSlide);
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    scrollTo(value, 0);
                }
            });

            mValueAnimator.setDuration(mAnimDuring);
            mValueAnimator.start();

            if (mStatusChangeLister != null) {
                mStatusChangeLister.onStatusChange(true);
            }
        }
        else {
            mValueAnimator = ValueAnimator.ofInt(scrollX, 0);
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    scrollTo(value, 0);
                }
            });

            mValueAnimator.setDuration(mAnimDuring);
            mValueAnimator.start();

            if (mStatusChangeLister != null) {
                mStatusChangeLister.onStatusChange(false);
            }
        }
    }

    /**
     * 重置--菜单展开/菜单收起 状态
     * 菜单展开或者收起都会调用这个方法
     */
    public void resetDelStatus() {
        int scrollX = getScrollX();
        if (scrollX == 0) {
            return;
        }
        clearAnim();

        mValueAnimator = ValueAnimator.ofInt(scrollX, 0);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                scrollTo(value, 0);
            }
        });

        mValueAnimator.setDuration(mAnimDuring);
        mValueAnimator.start();
    }

    /*
     ********************************** 自定义接口 ********************************************
     */
    /**
     * 删除按钮状态变化监听
     */
    //设置状态监听
    public void setStatusChangeLister(OnDelViewStatusChangeLister statusChangeLister) {
        mStatusChangeLister = statusChangeLister;
    }

    /**
     * 自定义接口
     * 删除按钮状态变化监听
     * */
    public interface OnDelViewStatusChangeLister {
        /**
         * 状态变化监听
         * @param show  是否正在显示
         */
        void onStatusChange(boolean show);
    }
}
