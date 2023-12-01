package com.common.splash.mvvm.activity;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.common.core.base.BaseActivity;
import com.common.home.export.arouter.HomeRouterHub;
import com.common.res.adapter.BaseAdapter;
import com.common.res.aop.SingleClick;
import com.common.res.utils.RouterUtilKt;
import com.common.splash.BR;
import com.common.splash.R;
import com.common.splash.export.arouter.SplashRouterHub;

import me.relex.circleindicator.CircleIndicator3;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/09/21
 * desc   : 应用引导页
 */
@Route(path = SplashRouterHub.PUBLIC_SPLASH_GUIDEACTIVITY)
public final class GuideActivity extends BaseActivity {

    private ViewPager2 mViewPager;
    private CircleIndicator3 mIndicatorView;
    private View mCompleteView;

    private BaseAdapter<Integer> mAdapter = new BaseAdapter<>(R.layout.splash_guide_item, BR.item);

    @Override
    public int getLayoutId() {
        return R.layout.splash_guide_activity;
    }

    @Override
    public void initView() {
        mViewPager = findViewById(R.id.vp_guide_pager);
        mIndicatorView = findViewById(R.id.cv_guide_indicator);
        mCompleteView = findViewById(R.id.btn_guide_complete);
        setOnClickListener(mCompleteView);
    }

    @Override
    public void initData() {
        mAdapter.addData(R.drawable.splash_guide_1_bg);
        mAdapter.addData(R.drawable.splash_guide_2_bg);
        mAdapter.addData(R.drawable.splash_guide_3_bg);
        mViewPager.setAdapter(mAdapter);
        mViewPager.registerOnPageChangeCallback(mCallback);
        mIndicatorView.setViewPager(mViewPager);
    }

    @SingleClick
    @Override
    public void onClick(View view) {
        if (view == mCompleteView) {
            RouterUtilKt.routerNavigation(HomeRouterHub.PUBLIC_HOME_MAINACTIVITY,null);
            finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewPager.unregisterOnPageChangeCallback(mCallback);
    }


    private final ViewPager2.OnPageChangeCallback mCallback = new ViewPager2.OnPageChangeCallback() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mViewPager.getCurrentItem() != mAdapter.getItemCount() - 1 || positionOffsetPixels <= 0) {
                return;
            }

            mIndicatorView.setVisibility(View.VISIBLE);
            mCompleteView.setVisibility(View.INVISIBLE);
            mCompleteView.clearAnimation();
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state != ViewPager2.SCROLL_STATE_IDLE) {
                return;
            }

            boolean lastItem = mViewPager.getCurrentItem() == mAdapter.getItemCount() - 1;
            mIndicatorView.setVisibility(lastItem ? View.INVISIBLE : View.VISIBLE);
            mCompleteView.setVisibility(lastItem ? View.VISIBLE : View.INVISIBLE);
            if (lastItem) {
                // 按钮呼吸动效
                ScaleAnimation animation = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(350);
                animation.setRepeatMode(Animation.REVERSE);
                animation.setRepeatCount(Animation.INFINITE);
                mCompleteView.startAnimation(animation);
            }
        }
    };
}