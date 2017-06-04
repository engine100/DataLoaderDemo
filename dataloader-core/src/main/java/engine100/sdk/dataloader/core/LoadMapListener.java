/*
 * Created by Engine100 on 2017-06-03 23:19:43.
 *
 *      https://github.com/engine100
 *
 */
package engine100.sdk.dataloader.core;

import java.util.Map;

/**
 * The interface show the status or result during downloading</br>
 * the method callback on UI thread.
 */
public interface LoadMapListener {

    /**
     * if we repeat call startLoading method ,</br>
     * and the manager is loading data already,callback the method
     */
    void onLoading();

    /**
     * when loading data,callback this method to show the progress
     *
     * @param progressMap the loading detail message
     */
    void onProgress(Map<DataType, String> progressMap);

    /**
     * when loading finish ,callback it
     *
     * @param resultMap show the load result
     */
    void onFinish(Map<DataType, LoadStatus> resultMap);

//    /**
//     * when cancel the loading by user
//     */
//    @Deprecated
//    void onCancel();
}
