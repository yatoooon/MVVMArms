package com.common.media.mvvm.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.common.core.base.BaseActivity;
import com.common.media.BR;
import com.common.media.R;

import com.common.media.export.MediaExport;
import com.common.res.adapter.BaseAdapter;
import com.common.res.adapter.RecyclerPagerAdapter;
import com.common.res.aop.Log;
import com.common.res.immersionbar.BindImmersionBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;


/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/03/05
 * desc   : 查看大图
 */
@Route(path = MediaExport.PUBLIC_MEDIA_IMAGEPREVIEWACTIVITY)
public final class ImagePreviewActivity extends BaseActivity
        implements ViewPager.OnPageChangeListener,
        OnItemClickListener {

    private static final String INTENT_KEY_IN_IMAGE_LIST = "imageList";
    private static final String INTENT_KEY_IN_IMAGE_INDEX = "imageIndex";


    @Log
    public static void start(Context context, List<String> urls, int index) {
        if (urls == null || urls.isEmpty()) {
            return;
        }
        Intent intent = new Intent(context, ImagePreviewActivity.class);
        if (urls.size() > 2000) {
            // 请注意：如果传输的数据量过大，会抛出此异常，并且这种异常是不能被捕获的
            // 所以当图片数量过多的时候，我们应当只显示一张，这种一般是手机图片过多导致的
            // 经过测试，传入 3121 张图片集合的时候会抛出此异常，所以保险值应当是 2000
            // android.os.TransactionTooLargeException: data parcel size 521984 bytes
            urls = Collections.singletonList(urls.get(index));
        }

        if (urls instanceof ArrayList) {
            intent.putExtra(INTENT_KEY_IN_IMAGE_LIST, (ArrayList<String>) urls);
        } else {
            intent.putExtra(INTENT_KEY_IN_IMAGE_LIST, new ArrayList<>(urls));
        }
        intent.putExtra(INTENT_KEY_IN_IMAGE_INDEX, index);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    private ViewPager mViewPager;
    private BaseAdapter<String> mAdapter = new BaseAdapter<>(R.layout.media_image_preview_item, BR.item);

    /**
     * 圆圈指示器
     */
    private CircleIndicator mCircleIndicatorView;
    /**
     * 文本指示器
     */
    private TextView mTextIndicatorView;

    @Override
    public int getLayoutId() {
        return R.layout.media_image_preview_activity;
    }

    @Override
    public void initView() {
        mViewPager = findViewById(R.id.vp_image_preview_pager);
        mCircleIndicatorView = findViewById(R.id.ci_image_preview_indicator);
        mTextIndicatorView = findViewById(R.id.tv_image_preview_indicator);
    }

    @Override
    public void initData() {
        ArrayList<String> images = getStringArrayList(INTENT_KEY_IN_IMAGE_LIST);
        if (images == null || images.isEmpty()) {
            finish();
            return;
        }
        mAdapter.setList(images);
        mAdapter.setOnItemClickListener(this);
        mViewPager.setAdapter(new RecyclerPagerAdapter(mAdapter));
        if (images.size() != 1) {
            if (images.size() < 10) {
                // 如果是 10 张以内的图片，那么就显示圆圈指示器
                mCircleIndicatorView.setVisibility(View.VISIBLE);
                mCircleIndicatorView.setViewPager(mViewPager);
            } else {
                // 如果超过 10 张图片，那么就显示文字指示器
                mTextIndicatorView.setVisibility(View.VISIBLE);
                mViewPager.addOnPageChangeListener(this);
            }

            int index = getInt(INTENT_KEY_IN_IMAGE_INDEX);
            if (index < images.size()) {
                mViewPager.setCurrentItem(index);
                onPageSelected(index);
            }
        }
    }

    /**
     * {@link ViewPager.OnPageChangeListener}
     */

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onPageSelected(int position) {
        mTextIndicatorView.setText((position + 1) + "/" + mAdapter.getItemCount());
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewPager.removeOnPageChangeListener(this);
    }


    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        if (isFinishing() || isDestroyed()) {
            return;
        }
        // 单击图片退出当前的 Activity
        finish();
    }

    @Override
    public int getImmersionBarType() {
        return BindImmersionBar.FULLSCREEN;
    }
}