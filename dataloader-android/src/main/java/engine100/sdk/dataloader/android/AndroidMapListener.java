/*
 * Created by Engine100 on 2017-06-04 15:56:51.
 *
 *      https://github.com/engine100
 *
 */
package engine100.sdk.dataloader.android;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.Map;

import engine100.sdk.dataloader.core.DataType;
import engine100.sdk.dataloader.core.LoadMapListener;
import engine100.sdk.dataloader.core.LoadStatus;

/**
 * it implements the LoadMapListener,and dispatch the params on android UI.
 * if you use data loader in android ,you can use it ,or you can control ui thread in your own methods.
 */
public abstract class AndroidMapListener implements LoadMapListener {

    private static final int NOTIFY_REFRESH_PROGRESS_MAP = 1;
    private static final int NOTIFY_LOADING_FINISH = 2;
    private static final int NOTIFY_ALREADY_LOADING = 3;

    private Map<DataType, String> mProgressMap;
    private Map<DataType, LoadStatus> mResultMap;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NOTIFY_REFRESH_PROGRESS_MAP:
                    onProgressUI(mProgressMap);
                    break;
                case NOTIFY_LOADING_FINISH:
                    onFinishUI(mResultMap);
                    break;
                case NOTIFY_ALREADY_LOADING:
                    onLoadingUI();
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    public void onLoading() {
        mHandler.sendEmptyMessage(NOTIFY_ALREADY_LOADING);
    }

    @Override
    public void onProgress(Map<DataType, String> progressMap) {
        mProgressMap = progressMap;
        mHandler.sendEmptyMessage(NOTIFY_REFRESH_PROGRESS_MAP);
    }

    @Override
    public void onFinish(Map<DataType, LoadStatus> resultMap) {
        mResultMap = resultMap;
        mHandler.sendEmptyMessage(NOTIFY_LOADING_FINISH);
    }

    public abstract void onLoadingUI();

    public abstract void onProgressUI(Map<DataType, String> progressMap);

    public abstract void onFinishUI(Map<DataType, LoadStatus> resultMap);

}
