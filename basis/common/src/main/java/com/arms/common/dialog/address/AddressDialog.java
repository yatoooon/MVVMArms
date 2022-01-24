package com.arms.common.dialog.address;

import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_IDLE;
import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_SETTLING;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.arms.common.aop.SingleClick;
import com.arms.common.dialog.BaseDialog;
import com.arms.common.BR;
import com.arms.common.R;
import com.tencent.bugly.crashreport.CrashReport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/02/12
 * desc   : 省市区选择对话框
 * doc    : https://baijiahao.baidu.com/s?id=1615894776741007967
 */
public final class AddressDialog {

    public static final class Builder
            extends BaseDialog.Builder<Builder>
            implements TabAdapter.OnTabListener,
            Runnable, RecyclerViewAdapter.OnSelectListener,
            BaseDialog.OnShowListener, BaseDialog.OnDismissListener {

        private final TextView mTitleView;
        private final ImageView mCloseView;
        private final RecyclerView mTabView;

        private final ViewPager2 mViewPager;

        private final TabAdapter mTabAdapter = new TabAdapter(R.layout.res_tab_item_sliding, BR.item);
        private final RecyclerViewAdapter mAdapter;
        private final ViewPager2.OnPageChangeCallback mCallback;

        @Nullable
        private OnListener mListener;

        private String mProvince = null;
        private String mCity = null;
        private String mArea = null;

        private boolean mIgnoreArea;

        @SuppressWarnings("all")
        public Builder(Context context) {
            super(context);
            setContentView(R.layout.res_address_dialog);
            setHeight(getResources().getDisplayMetrics().heightPixels / 2);

            mViewPager = findViewById(R.id.vp_address_pager);
            mAdapter = new RecyclerViewAdapter(R.layout.res_address_dialog_item_rv, BR.item);
            mAdapter.setOnSelectListener(this);
            mViewPager.setAdapter(mAdapter);

            mTitleView = findViewById(R.id.tv_address_title);
            mCloseView = findViewById(R.id.iv_address_closer);
            mTabView = findViewById(R.id.rv_address_tab);
            setOnClickListener(mCloseView);

            mTabAdapter.addData(getString(R.string.res_address_hint));
            mTabView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
            mTabAdapter.setOnTabListener(this);
            mTabView.setAdapter(mTabAdapter);

            mCallback = new ViewPager2.OnPageChangeCallback() {

                private int mPreviousScrollState, mScrollState = SCROLL_STATE_IDLE;

                @Override
                public void onPageScrollStateChanged(int state) {
                    mPreviousScrollState = mScrollState;
                    mScrollState = state;
                    if (state == ViewPager2.SCROLL_STATE_IDLE && mTabAdapter.getSelectedPosition() != mViewPager.getCurrentItem()) {
                        final boolean updateIndicator = mScrollState == SCROLL_STATE_IDLE || (mScrollState == SCROLL_STATE_SETTLING && mPreviousScrollState == SCROLL_STATE_IDLE);
                        onTabSelected(mTabView, mViewPager.getCurrentItem());
                    }
                }

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }
            };

            // 显示省份列表
            mAdapter.addData(AddressManager.getProvinceList(getContext()));
            addOnShowListener(this);
            addOnDismissListener(this);
        }

        public Builder setTitle(@StringRes int id) {
            return setTitle(getString(id));
        }

        public Builder setTitle(CharSequence text) {
            mTitleView.setText(text);
            return this;
        }

        /**
         * 设置默认省份
         */
        public Builder setProvince(String province) {
            if (TextUtils.isEmpty(province)) {
                return this;
            }

            List<AddressBean> data = mAdapter.getItem(0);
            if (data == null || data.isEmpty()) {
                return this;
            }

            for (int i = 0; i < data.size(); i++) {
                if (!province.equals(data.get(i).getName())) {
                    continue;
                }
                selectedAddress(0, i, false);
                break;
            }
            return this;
        }

        /**
         * 设置默认城市
         */
        public Builder setCity(String city) {
            if (mIgnoreArea) {
                // 已经忽略了县级区域的选择，不能选定指定的城市
                throw new IllegalStateException("The selection of county-level regions has been ignored. The designated city cannot be selected");
            }
            if (TextUtils.isEmpty(city)) {
                return this;
            }

            List<AddressBean> data = mAdapter.getItem(1);
            if (data == null || data.isEmpty()) {
                return this;
            }

            for (int i = 0; i < data.size(); i++) {
                if (!city.equals(data.get(i).getName())) {
                    continue;
                }
                // 避开直辖市，因为选择省的时候已经自动跳过市区了
                if (mAdapter.getItem(1).size() > 1) {
                    selectedAddress(1, i, false);
                }
                break;
            }
            return this;
        }

        /**
         * 不选择县级区域
         */
        public Builder setIgnoreArea() {
            if (mAdapter.getItemCount() == 3) {
                // 已经指定了城市，则不能忽略县级区域
                throw new IllegalStateException("Cities have been designated and county-level areas can no longer be ignored");
            }
            mIgnoreArea = true;
            return this;
        }

        public Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }

        @Override
        public void onSelected(int recyclerViewPosition, int clickItemPosition) {
            selectedAddress(recyclerViewPosition, clickItemPosition, true);
        }

        /**
         * 选择地区
         *
         * @param type         类型（省、市、区）
         * @param position     点击的位置
         * @param smoothScroll 是否需要平滑滚动
         */
        @SuppressWarnings("all")
        private void selectedAddress(int type, int position, boolean smoothScroll) {
            switch (type) {
                case 0:
                    // 记录当前选择的省份
                    mProvince = mAdapter.getItem(type).get(position).getName();
                    mTabAdapter.setData(type, mProvince);

                    mTabAdapter.addData(getString(R.string.res_address_hint));
                    mTabAdapter.setSelectedPosition(type + 1);
                    mAdapter.addData(AddressManager.getCityList(mAdapter.getItem(type).get(position).getNext()));
                    mViewPager.setCurrentItem(type + 1, smoothScroll);

                    // 如果当前选择的是直辖市，就直接跳过选择城市，直接选择区域
                    if (mAdapter.getItem(type + 1).size() == 1) {
                        selectedAddress(type + 1, 0, false);
                    }
                    break;
                case 1:
                    // 记录当前选择的城市
                    mCity = mAdapter.getItem(type).get(position).getName();
                    mTabAdapter.setData(type, mCity);

                    if (mIgnoreArea) {

                        if (mListener != null) {
                            mListener.onSelected(getDialog(), mProvince, mCity, mArea);
                        }

                        // 延迟关闭
                        postDelayed(this::dismiss, 300);

                    } else {
                        mTabAdapter.addData(getString(R.string.res_address_hint));
                        mTabAdapter.setSelectedPosition(type + 1);
                        mAdapter.addData(AddressManager.getAreaList(mAdapter.getItem(type).get(position).getNext()));
                        mViewPager.setCurrentItem(type + 1, smoothScroll);
                    }

                    break;
                case 2:
                    // 记录当前选择的区域
                    mArea = mAdapter.getItem(type).get(position).getName();
                    mTabAdapter.setData(type, mArea);

                    if (mListener != null) {
                        mListener.onSelected(getDialog(), mProvince, mCity, mArea);
                    }

                    // 延迟关闭
                    postDelayed(this::dismiss, 300);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void run() {
            if (isShowing()) {
                dismiss();
            }
        }

        @SingleClick
        @Override
        public void onClick(View view) {
            if (view == mCloseView) {
                dismiss();
                if (mListener == null) {
                    return;
                }
                mListener.onCancel(getDialog());
            }
        }

        /**
         * {@link TabAdapter.OnTabListener}
         */

        @Override
        public boolean onTabSelected(RecyclerView recyclerView, int position) {
            synchronized (this) {
                if (mViewPager.getCurrentItem() != position) {
                    mViewPager.setCurrentItem(position);
                }

                mTabAdapter.setData(position, getString(R.string.res_address_hint));
                switch (position) {
                    case 0:
                        mProvince = mCity = mArea = null;
                        if (mTabAdapter.getItemCount() > 2) {
                            mTabAdapter.removeAt(2);
                            mAdapter.removeAt(2);
                        }

                        if (mTabAdapter.getItemCount() > 1) {
                            mTabAdapter.removeAt(1);
                            mAdapter.removeAt(1);
                        }
                        break;
                    case 1:
                        mCity = mArea = null;
                        if (mTabAdapter.getItemCount() > 2) {
                            mTabAdapter.removeAt(2);
                            mAdapter.removeAt(2);
                        }
                        break;
                    case 2:
                        mArea = null;
                        break;
                    default:
                        break;
                }
            }
            return true;
        }

        /**
         * {@link BaseDialog.OnShowListener}
         */

        @Override
        public void onShow(BaseDialog dialog) {
            // 注册 ViewPager 滑动监听
            mViewPager.registerOnPageChangeCallback(mCallback);
        }

        /**
         * {@link BaseDialog.OnDismissListener}
         */

        @Override
        public void onDismiss(BaseDialog dialog) {
            // 反注册 ViewPager 滑动监听
            mViewPager.unregisterOnPageChangeCallback(mCallback);
        }
    }


    public static final class AddressBean {

        /**
         * （省\市\区）的名称
         */
        public final String name;
        /**
         * 下一级的 Json
         */
        public final JSONObject next;

        public AddressBean(String name, JSONObject next) {
            this.name = name;
            this.next = next;
        }

        public String getName() {
            return name;
        }

        public JSONObject getNext() {
            return next;
        }
    }

    /**
     * 省市区数据管理类
     */
    private static final class AddressManager {

        /**
         * 获取省列表
         */
        private static List<AddressBean> getProvinceList(Context context) {
            try {
                // 省市区Json数据文件来源：https://github.com/getActivity/ProvinceJson
                JSONArray jsonArray = getProvinceJson(context);

                if (jsonArray == null) {
                    return null;
                }

                int length = jsonArray.length();
                ArrayList<AddressBean> list = new ArrayList<>(length);
                for (int i = 0; i < length; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    list.add(new AddressBean(jsonObject.getString("name"), jsonObject));
                }

                return list;

            } catch (JSONException e) {
                CrashReport.postCatchedException(e);
            }
            return null;
        }

        /**
         * 获取城市列表
         *
         * @param jsonObject 城市Json
         */
        private static List<AddressBean> getCityList(JSONObject jsonObject) {
            try {
                JSONArray listCity = jsonObject.getJSONArray("city");
                int length = listCity.length();

                ArrayList<AddressBean> list = new ArrayList<>(length);

                for (int i = 0; i < length; i++) {
                    list.add(new AddressBean(listCity.getJSONObject(i).getString("name"), listCity.getJSONObject(i)));
                }

                return list;
            } catch (JSONException e) {
                CrashReport.postCatchedException(e);
                return null;
            }
        }

        /**
         * 获取区域列表
         *
         * @param jsonObject 区域 Json
         */
        private static List<AddressBean> getAreaList(JSONObject jsonObject) {
            try {
                JSONArray listArea = jsonObject.getJSONArray("area");
                int length = listArea.length();

                ArrayList<AddressBean> list = new ArrayList<>(length);

                for (int i = 0; i < length; i++) {
                    String string = listArea.getString(i);
                    list.add(new AddressBean(string, null));
                }
                return list;
            } catch (JSONException e) {
                CrashReport.postCatchedException(e);
                return null;
            }
        }

        /**
         * 获取资产目录下面文件的字符串
         */
        private static JSONArray getProvinceJson(Context context) {
            try {
                InputStream inputStream = context.getResources().openRawResource(R.raw.province);
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[512];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, length);
                }
                outStream.close();
                inputStream.close();
                return new JSONArray(outStream.toString());
            } catch (IOException | JSONException e) {
                CrashReport.postCatchedException(e);
            }
            return null;
        }
    }

    public interface OnListener {

        /**
         * 选择完成后回调
         *
         * @param province 省
         * @param city     市
         * @param area     区
         */
        void onSelected(BaseDialog dialog, String province, String city, String area);

        /**
         * 点击取消时回调
         */
        default void onCancel(BaseDialog dialog) {
        }
    }
}