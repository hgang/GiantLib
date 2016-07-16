package com.heg.baselib.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

/**
 * ***************** Toast工具类******************
 * text支持String和resourceId
 * 支持选择设置gravity
 * 支持选择设置显示时长
 * 支持取消显示
 *
 * @author by tanyixiu
 * @date 1.0 ,2016/2/17 9:08
 */
public class Toastor {
    private static volatile Toastor mInstance = new Toastor();
    private static Handler sMainHandler = new Handler(Looper.getMainLooper());
    private static Singleton<Toast, Context> mToast = new Singleton<Toast, Context>() {
        @Override
        protected Toast create(Context context) {
            if (context == null || context.getApplicationContext() == null) {
                return null;
            }
            return Toast.makeText(context.getApplicationContext(), "", Toast.LENGTH_SHORT);
        }
    };

    /**
     * Toast参数
     *
     * @author by tanyixiu
     * @version 1.0 ,2016/2/17 9:08
     */
    static class Params {
        public int mResId = 0;
        public CharSequence mText = "";
        public int mGravity = Gravity.BOTTOM;
        public int mDuration = Toast.LENGTH_SHORT;
    }

    private Toastor() {

    }

    public static Builder build(Context context) {
        return new Builder(context);
    }

    public void show(final Context context) {
        if (isMainThread()) {
            mToast.getInstance(context).show();
        } else {
            postToUiThread(new Runnable() {
                @Override
                public void run() {
                    mToast.getInstance(context).show();
                }
            });
        }
    }

    public void cancel(Context context) {
        mToast.getInstance(context).cancel();
    }

    private void apply(Params p, Context context) {
        Toast toast = mToast.getInstance(context);
        toast.setDuration(p.mDuration);
        toast.setGravity(p.mGravity, toast.getXOffset(), toast.getYOffset());
        if (!TextUtils.isEmpty(p.mText)) {
            toast.setText(p.mText);
        } else {
            toast.setText(p.mResId);
        }
    }

    private static Toastor getInstance() {
        return mInstance;
    }

    private boolean isMainThread() {
        Looper mainLooper = Looper.getMainLooper();
        return mainLooper != null && mainLooper.getThread() == Thread.currentThread();
    }

    private void postToUiThread(Runnable runnable) {
        sMainHandler.post(runnable);
    }

    /**
     * Toastor的构造者
     *
     * @author by tanyixiu
     * @version 1.0 ,2016/2/17 9:08
     */
    public static class Builder {

        private final Context mContext;
        private final Toastor.Params mParams;

        public Builder(Context context) {
            mContext = context;
            mParams = new Toastor.Params();
        }

        private Toastor create() {
            final Toastor toastor = Toastor.getInstance();
            toastor.apply(mParams, mContext);
            return toastor;
        }

        public Builder text(int resId) {
            mParams.mResId = resId;
            mParams.mText = "";
            return this;
        }

        public Builder text(CharSequence text) {
            mParams.mText = text;
            mParams.mResId = 0;
            return this;
        }

        public Builder gravity(int gravity) {
            mParams.mGravity = gravity;
            return this;
        }

        public Builder duration(int duration) {
            mParams.mDuration = duration;
            return this;
        }

        public Toastor show() {
            final Toastor toastor = create();
            toastor.show(mContext);
            return toastor;
        }
    }

    /**
     * 带参数的单例
     *
     * @author by tanyixiu
     * @version 1.0 ,2016/2/17 9:08
     */
    public static abstract class Singleton<T, P> {
        private volatile T mInstance;

        protected abstract T create(P p);

        public final T getInstance(P p) {
            if (mInstance == null) {
                synchronized (this) {
                    if (mInstance == null) {
                        mInstance = create(p);
                    }
                }
            }
            return mInstance;
        }
    }
}
