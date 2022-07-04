package com.common.res.action;


import com.common.res.R;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2019/09/21
 *    desc   : 动画样式
 */
public interface AnimAction {

    /** 默认动画效果 */
    int ANIM_DEFAULT = -1;

    /** 没有动画效果 */
    int ANIM_EMPTY = 0;

    /** 缩放动画 */
    int ANIM_SCALE = R.style.res_ScaleAnimStyle;

    /** IOS 动画 */
    int ANIM_IOS = R.style.res_IOSAnimStyle;

    /** 吐司动画 */
    int ANIM_TOAST = android.R.style.Animation_Toast;

    /** 顶部弹出动画 */
    int ANIM_TOP = R.style.res_TopAnimStyle;

    /** 底部弹出动画 */
    int ANIM_BOTTOM = R.style.res_BottomAnimStyle;

    /** 左边弹出动画 */
    int ANIM_LEFT = R.style.res_LeftAnimStyle;

    /** 右边弹出动画 */
    int ANIM_RIGHT = R.style.res_RightAnimStyle;
}