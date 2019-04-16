package com.thmub.newbook.widget.page;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.gyf.barlibrary.ImmersionBar;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.manager.ReadSettingManager;
import com.thmub.newbook.utils.ScreenUtils;
import com.thmub.newbook.utils.SharedPreUtils;
import com.thmub.newbook.utils.SnackbarUtils;
import com.thmub.newbook.widget.animation.CoverPageAnim;
import com.thmub.newbook.widget.animation.HorizonPageAnim;
import com.thmub.newbook.widget.animation.NonePageAnim;
import com.thmub.newbook.widget.animation.PageAnimation;
import com.thmub.newbook.widget.animation.ScrollPageAnim;
import com.thmub.newbook.widget.animation.SimulationPageAnim;
import com.thmub.newbook.widget.animation.SlidePageAnim;

import static com.thmub.newbook.utils.ScreenUtils.getDisplayMetrics;


/**
 * Created by Administrator on 2016/8/29 0029.
 * 原作者的GitHub Project Path:(https://github.com/PeachBlossom/treader)
 * 绘制页面显示内容的类
 */
public class PageView extends View {

    private final static String TAG = PageView.class.getSimpleName();

    private Activity activity;

    private int mViewWidth = 0; // 当前View的宽
    private int mViewHeight = 0; // 当前View的高
    private int statusBarHeight = 0; //状态栏高度

    private int mStartX = 0;
    private int mStartY = 0;
    private boolean isMove = false;
    private boolean actionFromEdge = false;
    // 初始化参数
    private ReadSettingManager readSettingManager = ReadSettingManager.getInstance();
    // 是否允许点击
    private boolean canTouch = true;
    // 唤醒菜单的区域
    private RectF mCenterRect = null;
    //view是否准备
    private boolean isPrepare;
    // 动画类
    private PageAnimation mPageAnim;
    //点击监听
    private TouchListener mTouchListener;
    //内容加载器
    private PageLoader mPageLoader;
    // 动画监听类
    private PageAnimation.OnPageChangeListener mPageAnimListener = new PageAnimation.OnPageChangeListener() {
        @Override
        public void resetScroll() {
            mPageLoader.resetPageOffset();
        }

        @Override
        public boolean hasPrev() {
            return PageView.this.hasPrevPage();
        }

        @Override
        public boolean hasNext(int pageOnCur) {
            return PageView.this.hasNextPage(pageOnCur);
        }

        @Override
        public void drawContent(Canvas canvas, float offset) {
            PageView.this.drawContent(canvas, offset);
        }

        @Override
        public void drawBackground(Canvas canvas) {
            PageView.this.drawBackground(canvas);
        }

    };

    /*************************************Initialization****************************************/
    public PageView(Context context) {
        this(context, null);
    }

    public PageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 获取屏幕大小
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;

        isPrepare = true;

        if (mPageLoader != null) {
            mPageLoader.prepareDisplay(w, h);
        }
    }

    /**
     * 设置翻页的模式
     *
     * @param pageMode
     * @param marginTop
     * @param marginBottom
     */
    void setPageMode(PageAnimation.Mode pageMode, int marginTop, int marginBottom) {
        //视图未初始化的时候，禁止调用
        if (mViewWidth == 0 || mViewHeight == 0 || mPageLoader == null) return;
        if (!readSettingManager.getHideStatusBar()) {
            marginTop = marginTop + statusBarHeight;
        }
        switch (pageMode) {
            case COVER:
                mPageAnim = new CoverPageAnim(mViewWidth, mViewHeight, this, mPageAnimListener);
                break;
            case SLIDE:
                mPageAnim = new SlidePageAnim(mViewWidth, mViewHeight, this, mPageAnimListener);
                break;
            case NONE:
                mPageAnim = new NonePageAnim(mViewWidth, mViewHeight, this, mPageAnimListener);
                break;
            case SCROLL:
                mPageAnim = new ScrollPageAnim(mViewWidth, mViewHeight, 0, marginTop
                        , marginBottom, this, mPageAnimListener);
                break;
            default:
                mPageAnim = new SimulationPageAnim(mViewWidth, mViewHeight, this, mPageAnimListener);
        }
    }

    public Activity getActivity() {
        return activity;
    }

    public int getStatusBarHeight() {
        return statusBarHeight;
    }

    public Bitmap getBgBitmap(int pageOnCur) {
        if (mPageAnim == null) return null;
        return mPageAnim.getBgBitmap(pageOnCur);
    }

    public void autoPrevPage() {
        if (mPageAnim instanceof ScrollPageAnim) {
            ((ScrollPageAnim) mPageAnim).startAnim(PageAnimation.Direction.PREV);
        } else {
            startHorizonPageAnim(PageAnimation.Direction.PREV);
        }
    }

    public void autoNextPage() {
        if (mPageAnim instanceof ScrollPageAnim) {
            ((ScrollPageAnim) mPageAnim).startAnim(PageAnimation.Direction.NEXT);
        } else {
            startHorizonPageAnim(PageAnimation.Direction.NEXT);
        }
    }

    private void startHorizonPageAnim(PageAnimation.Direction direction) {
        if (mTouchListener == null) return;
        //是否正在执行动画
        abortAnimation();
        if (direction == PageAnimation.Direction.NEXT) {
            int x = mViewWidth;
            int y = mViewHeight;
            //初始化动画
            mPageAnim.setStartPoint(x, y);
            //设置点击点
            mPageAnim.setTouchPoint(x, y);
            //设置方向
            boolean hasNext = hasNextPage(0);

            mPageAnim.setDirection(direction);
            if (!hasNext) {
                ((HorizonPageAnim) mPageAnim).setNoNext(true);
                return;
            }
        } else {
            int x = 0;
            int y = mViewHeight;
            //初始化动画
            mPageAnim.setStartPoint(x, y);
            //设置点击点
            mPageAnim.setTouchPoint(x, y);
            mPageAnim.setDirection(direction);
            //设置方向方向
            boolean hashPrev = hasPrevPage();
            if (!hashPrev) {
                ((HorizonPageAnim) mPageAnim).setNoNext(true);
                return;
            }
        }
        ((HorizonPageAnim) mPageAnim).setNoNext(false);
        ((HorizonPageAnim) mPageAnim).setCancel(false);
        mPageAnim.startAnim();
    }

    public void drawPage(int pageOnCur) {
        if (!isPrepare) return;
        if (mPageLoader != null) {
            mPageLoader.drawPage(getBgBitmap(pageOnCur), pageOnCur);
            if (mPageAnim instanceof SimulationPageAnim) {
                ((SimulationPageAnim) mPageAnim).onPageDrawn(pageOnCur);
            }
        }
        invalidate();
    }

    /**
     * 绘制滚动背景
     */
    public void drawBackground(Canvas canvas) {
        if (!isPrepare) return;
        if (mPageLoader != null) {
            mPageLoader.drawBackground(canvas);
        }
    }

    /**
     * 绘制滚动内容
     */
    public void drawContent(Canvas canvas, float offset) {
        if (!isPrepare) return;
        if (mPageLoader != null) {
            mPageLoader.drawContent(canvas, offset);
        }
    }

    /**
     * 绘制横翻背景
     */
    public void drawBackground(int pageOnCur) {
        if (!isPrepare) return;
        if (mPageLoader != null) {
            mPageLoader.drawPage(getBgBitmap(pageOnCur), pageOnCur);
        }
        invalidate();
    }

    /**
     * 绘制横翻内容
     *
     * @param pageOnCur 相对当前页的位置
     */
    public void drawContent(int pageOnCur) {
        if (!isPrepare) return;
        if (mPageLoader != null) {
            mPageLoader.drawPage(getBgBitmap(pageOnCur), pageOnCur);
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mPageAnim instanceof ScrollPageAnim)
            super.onDraw(canvas);
        //绘制动画
        if (mPageAnim != null) {
            mPageAnim.draw(canvas);
        }
    }

    @Override
    public void computeScroll() {
        //进行滑动
        if (mPageAnim != null) {
            mPageAnim.scrollAnim();
            if (mPageAnim.isChangePage() && !mPageAnim.getScroller().computeScrollOffset()) {
                mPageAnim.changePageEnd();
                if (mPageAnim.getDirection() != PageAnimation.Direction.NONE) {
                    mPageLoader.finishPaging(mPageAnim.getDirection());
                    mPageAnim.setDirection(PageAnimation.Direction.NONE);
                }
            }
        }
        super.computeScroll();
    }

    /**
     * 监听触屏事件
     * 作用：检测动作
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        if (mPageAnim == null) {
            return true;
        }
        if (!canTouch && event.getAction() != MotionEvent.ACTION_DOWN) {
            return true;
        }

        if (actionFromEdge) {
            if (event.getAction() == MotionEvent.ACTION_UP)
                actionFromEdge = false;
            return true;
        }
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getEdgeFlags() != 0 || event.getRawY() < ScreenUtils.dpToPx(5)
                        || event.getRawY() > getDisplayMetrics().heightPixels - ScreenUtils.dpToPx(5)) {
                    actionFromEdge = true;
                    return true;
                }
                mStartX = x;
                mStartY = y;
                isMove = false;
                canTouch = mTouchListener.onTouch();
                mPageAnim.onTouchEvent(event);
                break;
            case MotionEvent.ACTION_MOVE:
                // 判断是否大于最小滑动值。
                int slop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
                if (!isMove) {
                    isMove = Math.abs(mStartX - event.getX()) > slop || Math.abs(mStartY - event.getY()) > slop;
                }

                // 如果滑动了，则进行翻页。
                if (isMove) {
                    mPageAnim.onTouchEvent(event);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isMove) {
                    //设置中间区域范围
                    if (mCenterRect == null) {
                        mCenterRect = new RectF(mViewWidth / 3f, mViewHeight / 3f,
                                mViewWidth * 2f / 3, mViewHeight * 2f / 3);
                    }

                    //是否点击了中间
                    if (mCenterRect.contains(x, y)) {
                        if (mTouchListener != null) {
                            mTouchListener.center();
                        }
                        return true;
                    }

                    if (!readSettingManager.getCanClickTurn() || isRunning()) {
                        return true;
                    }

                    if (mPageAnim instanceof ScrollPageAnim
                            && SharedPreUtils.getInstance()
                            .getBoolean("disableScrollClickTurn", false)) {
                        return true;
                    }
                }
                mPageAnim.onTouchEvent(event);
                break;
        }
        return true;
    }


    /**
     * 判断是否存在上一页
     */
    private boolean hasPrevPage() {
        if (mPageLoader.hasPrev()) {
            return true;
        } else {
            showSnackBar("没有上一页");
            return false;
        }
    }

    /**
     * 判断是否下一页存在
     */
    private boolean hasNextPage(int pageOnCur) {
        if (mPageLoader.hasNext(pageOnCur)) {
            return true;
        } else {
            showSnackBar("没有下一页");
            return false;
        }
    }

    //如果滑动状态没有停止就取消状态，重新设置Anim的触碰点
    public void abortAnimation() {
        mPageAnim.abortAnim();
    }

    public boolean isRunning() {
        return mPageAnim != null && mPageAnim.isRunning();
    }

    public boolean isPrepare() {
        return isPrepare;
    }

    public void setTouchListener(TouchListener mTouchListener) {
        this.mTouchListener = mTouchListener;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mPageAnim != null) {
            mPageAnim.abortAnim();
            mPageAnim.clear();
        }

        mPageLoader = null;
        mPageAnim = null;
    }

    /**
     * 获取 PageLoader
     */
    public PageLoader getPageLoader(Activity activity, ShelfBookBean bookShelfBean) {
        this.activity = activity;
        this.statusBarHeight = ImmersionBar.getStatusBarHeight(activity);
        // 判是否已经存在
        if (mPageLoader != null) {
            return mPageLoader;
        }
        // 根据书籍类型，获取具体的加载器
        mPageLoader = new NetPageLoader(this, bookShelfBean);
        // 判断是否 PageView 已经初始化完成
        if (mViewWidth != 0 || mViewHeight != 0) {
            // 初始化 PageLoader 的屏幕大小
            mPageLoader.prepareDisplay(mViewWidth, mViewHeight);
        }

        return mPageLoader;
    }

    /**
     * 显示tips
     *
     * @param msg
     */
    public void showSnackBar(String msg) {
        SnackbarUtils.show(this, msg);
    }

    /*****************************Interface******************************/
    public interface TouchListener {
        /**
         * 点击
         *
         * @return
         */
        boolean onTouch();

        /**
         * 中间区域
         */
        void center();
    }
}
