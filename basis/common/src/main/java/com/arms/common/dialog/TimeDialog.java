package com.arms.common.dialog;

import android.content.Context;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.arms.common.aop.SingleClick;
import com.arms.common.adapter.BaseAdapter;
import com.arms.common.manager.PickerLayoutManager;
import com.arms.common.BR;
import com.arms.common.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2019/08/17
 *    desc   : 时间选择对话框
 */
public final class TimeDialog {

    public static final class Builder
            extends CommonDialog.Builder<Builder> {

        private final RecyclerView mHourView;
        private final RecyclerView mMinuteView;
        private final RecyclerView mSecondView;

        private final PickerLayoutManager mHourManager;
        private final PickerLayoutManager mMinuteManager;
        private final PickerLayoutManager mSecondManager;

        private final BaseAdapter<String> mHourAdapter=new BaseAdapter<String>(R.layout.res_picker_item, BR.item);
        private final BaseAdapter<String> mMinuteAdapter=new BaseAdapter<String>(R.layout.res_picker_item, BR.item);
        private final BaseAdapter<String> mSecondAdapter=new BaseAdapter<String>(R.layout.res_picker_item, BR.item);

        @Nullable
        private OnListener mListener;

        @SuppressWarnings("all")
        public Builder(Context context) {
            super(context);
            setCustomView(R.layout.res_time_dialog);
            setTitle(R.string.res_time_title);

            mHourView = findViewById(R.id.rv_time_hour);
            mMinuteView = findViewById(R.id.rv_time_minute);
            mSecondView = findViewById(R.id.rv_time_second);


            // 生产小时
            ArrayList<String> hourData = new ArrayList<>(24);
            for (int i = 0; i <= 23; i++) {
                hourData.add((i < 10 ? "0" : "") + i + " " + getString(R.string.res_common_hour));
            }

            // 生产分钟
            ArrayList<String> minuteData = new ArrayList<>(60);
            for (int i = 0; i <= 59; i++) {
                minuteData.add((i < 10 ? "0" : "") + i + " " + getString(R.string.res_common_minute));
            }

            // 生产秒钟
            ArrayList<String> secondData = new ArrayList<>(60);
            for (int i = 0; i <= 59; i++) {
                secondData.add((i < 10 ? "0" : "") + i + " " + getString(R.string.res_common_second));
            }

            mHourAdapter.setList(hourData);
            mMinuteAdapter.setList(minuteData);
            mSecondAdapter.setList(secondData);

            mHourManager = new PickerLayoutManager.Builder(context)
                    .build();
            mMinuteManager = new PickerLayoutManager.Builder(context)
                    .build();
            mSecondManager = new PickerLayoutManager.Builder(context)
                    .build();

            mHourView.setLayoutManager(mHourManager);
            mMinuteView.setLayoutManager(mMinuteManager);
            mSecondView.setLayoutManager(mSecondManager);

            mHourView.setAdapter(mHourAdapter);
            mMinuteView.setAdapter(mMinuteAdapter);
            mSecondView.setAdapter(mSecondAdapter);

            Calendar calendar = Calendar.getInstance();
            setHour(calendar.get(Calendar.HOUR_OF_DAY));
            setMinute(calendar.get(Calendar.MINUTE));
            setSecond(calendar.get(Calendar.SECOND));
        }

        public Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }

        /**
         * 不选择秒数
         */
        public Builder setIgnoreSecond() {
            mSecondView.setVisibility(View.GONE);
            return this;
        }

        public Builder setTime(String time) {
            // 102030
            if (time.matches("\\d{6}")) {
                setHour(time.substring(0, 2));
                setMinute(time.substring(2, 4));
                setSecond(time.substring(4, 6));
            // 10:20:30
            } else if (time.matches("\\d{2}:\\d{2}:\\d{2}")) {
                setHour(time.substring(0, 2));
                setMinute(time.substring(3, 5));
                setSecond(time.substring(6, 8));
            }
            return this;
        }

        public Builder setHour(String hour) {
            return setHour(Integer.parseInt(hour));
        }

        public Builder setHour(int hour) {
            int index = hour;
            if (index < 0 || hour == 24) {
                index = 0;
            } else if (index > mHourAdapter.getItemCount() - 1) {
                index = mHourAdapter.getItemCount() - 1;
            }
            mHourView.scrollToPosition(index);
            return this;
        }

        public Builder setMinute(String minute) {
            return setMinute(Integer.parseInt(minute));
        }

        public Builder setMinute(int minute) {
            int index = minute;
            if (index < 0) {
                index = 0;
            } else if (index > mMinuteAdapter.getItemCount() - 1) {
                index = mMinuteAdapter.getItemCount() - 1;
            }
            mMinuteView.scrollToPosition(index);
            return this;
        }

        public Builder setSecond(String second) {
            return setSecond(Integer.parseInt(second));
        }

        public Builder setSecond(int second) {
            int index = second;
            if (index < 0) {
                index = 0;
            } else if (index > mSecondAdapter.getItemCount() - 1) {
                index = mSecondAdapter.getItemCount() - 1;
            }
            mSecondView.scrollToPosition(index);
            return this;
        }

        @SingleClick
        @Override
        public void onClick(View view) {
            int viewId = view.getId();
            if (viewId == R.id.tv_ui_confirm) {
                autoDismiss();
                if (mListener == null) {
                    return;
                }
                mListener.onSelected(getDialog(), mHourManager.getPickedPosition(), mMinuteManager.getPickedPosition(), mSecondManager.getPickedPosition());
            } else if (viewId == R.id.tv_ui_cancel) {
                autoDismiss();
                if (mListener == null) {
                    return;
                }
                mListener.onCancel(getDialog());
            }
        }
    }

    public interface OnListener {

        /**
         * 选择完时间后回调
         *
         * @param hour              时钟
         * @param minute            分钟
         * @param second            秒钟
         */
        void onSelected(BaseDialog dialog, int hour, int minute, int second);

        /**
         * 点击取消时回调
         */
        default void onCancel(BaseDialog dialog) {}
    }
}