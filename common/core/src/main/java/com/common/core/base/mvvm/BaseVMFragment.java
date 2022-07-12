package com.common.core.base.mvvm;

import android.os.Message;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.common.core.base.BaseFragment;
import com.common.res.livedata.MessageEvent;
import com.common.res.livedata.StatusEvent;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import timber.log.Timber;


/**
 * MVVMFrame 框架基于Google官方的 JetPack 构建，在使用MVVMFrame时，需遵循一些规范：
 * <p>
 * 如果您继承使用了BaseFragment或其子类，你需要参照如下方式添加@AndroidEntryPoint注解
 *
 * @example Fragment
 * //-------------------------
 * @AndroidEntryPoint public class YourFragment extends BaseFragment {
 * <p>
 * }
 * //-------------------------
 */
public abstract class BaseVMFragment<VDB extends ViewDataBinding, VM extends BaseViewModel> extends BaseFragment<VDB> {

    /**
     * 请通过 {@link #getViewModel()}获取，后续版本 {@link #mViewModel}可能会私有化
     */
    private VM mViewModel;


    /**
     * 初始化 {@link #mViewModel}
     */
    @Override
    public void initViewModel() {
        mViewModel = createViewModel();
        if (mViewModel != null) {
            try {
                getBinding().getClass().getMethod("setModel", mViewModel.getClass()).invoke(getBinding(), mViewModel);
            } catch (Exception e) {
                e.printStackTrace();
                Timber.d("BaseVMFragment #initViewModel() 对 .xml 文件设置model值失败,原因-->>%s", e.getMessage());
            }
            getLifecycle().addObserver(mViewModel);
            registerMessageEvent(message -> {
                Timber.d("message:%s", message);
                toast(message);
            });
        }
    }

    private Class<VM> getVMClass() {
        Class<?> cls = getClass();
        Class<VM> vmClass = null;
        while (vmClass == null && cls != null) {
            vmClass = getVMClass(cls);
            cls = cls.getSuperclass();
        }
        if (vmClass == null) {
            vmClass = (Class<VM>) BaseViewModel.class;
        }
        return vmClass;
    }

    private Class<VM> getVMClass(Class<?> cls) {
        Type type = cls.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            for (Type t : types) {
                if (t instanceof Class) {
                    Class<VM> vmClass = (Class<VM>) t;
                    if (BaseViewModel.class.isAssignableFrom(vmClass)) {
                        return vmClass;
                    }
                } else if (t instanceof ParameterizedType) {
                    Type rawType = ((ParameterizedType) t).getRawType();
                    if (rawType instanceof Class) {
                        Class<VM> vmClass = (Class) rawType;
                        if (BaseViewModel.class.isAssignableFrom(vmClass)) {
                            return vmClass;
                        }
                    }
                }
            }
        }

        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mViewModel != null) {
            getLifecycle().removeObserver(mViewModel);
            mViewModel = null;
        }


    }




    /**
     * 注册消息事件
     */
    protected void registerMessageEvent(@NonNull MessageEvent.MessageObserver observer) {
        mViewModel.getMessageEvent().observe(getViewLifecycleOwner(), observer);
    }

    /**
     * 注册单个消息事件，消息对象:{@link Message}
     *
     * @param observer
     */
    protected void registerSingleLiveEvent(@NonNull Observer<Message> observer) {
        mViewModel.getSingleLiveEvent().observe(getViewLifecycleOwner(), observer);
    }

    /**
     * 注册状态事件
     *
     * @param observer
     */
    protected void registerStatusEvent(@NonNull StatusEvent.StatusObserver observer) {
        mViewModel.getStatusEvent().observe(getViewLifecycleOwner(), observer);
    }


    /**
     * 创建ViewModel
     *
     * @return 默认为null，为null时，{@link #mViewModel}会默认根据当前Activity泛型 {@link VM}获得ViewModel
     */
    public VM createViewModel() {
        return obtainViewModel(getVMClass());
    }

    /**
     * 获取 ViewModel
     *
     * @return {@link #mViewModel}
     */
    public VM getViewModel() {
        return mViewModel;
    }

    /**
     * 通过 {@link #createViewModelProvider(ViewModelStoreOwner)}获得 ViewModel
     *
     * @param modelClass
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T obtainViewModel(@NonNull Class<T> modelClass) {
        return createViewModelProvider(this).get(modelClass);
    }

    /**
     * 创建 {@link ViewModelProvider}
     *
     * @param owner
     * @return
     */
    private ViewModelProvider createViewModelProvider(@NonNull ViewModelStoreOwner owner) {
        return new ViewModelProvider(owner);
    }

}
