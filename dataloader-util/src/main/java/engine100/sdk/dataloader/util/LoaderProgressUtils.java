/*
 * Created by Engine100 on 2017-06-04 14:39:10.
 *
 *      https://github.com/engine100
 *
 */
package engine100.sdk.dataloader.util;

import java.util.Map;

import engine100.sdk.dataloader.core.DataType;
import engine100.sdk.dataloader.core.LoadStatus;

/**
 * flat the progress map to show
 */
public class LoaderProgressUtils {

    /**
     * flat map to string
     *
     * @param progressMap load progress
     * @return the progress info
     */
    public static String flatProgress(Map<DataType, String> progressMap) {
        StringBuilder mProgress = new StringBuilder();
        try {

            for (Map.Entry<DataType, String> entry : progressMap.entrySet()) {
                DataType type = entry.getKey();
                String content = entry.getValue();

                if (LoadStatus.SUCCESS.getValue().equals(content)) continue;
                if (LoadStatus.FAILED.getValue().equals(content)) continue;
                if (LoadStatus.PREPARE.getValue().equals(content)) continue;

                mProgress.append(type.getName()).append(":").append(content).append("\n");
            }
            if (mProgress.length() > 0) {
                mProgress.deleteCharAt(mProgress.length() - 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
            //ignore
        }
        return mProgress.toString();
    }

    /**
     * flat result to string
     *
     * @param resultMap load result
     * @return the result info
     */
    public static String flatResult(Map<DataType, LoadStatus> resultMap) {
        StringBuilder mResult = new StringBuilder();

        for (Map.Entry<DataType, LoadStatus> entry : resultMap.entrySet()) {
            DataType type = entry.getKey();
            LoadStatus status = entry.getValue();
            mResult.append(type.getName()).append(": ").append(status.getValue()).append("\n");
        }
        if (mResult.length() > 0) {
            mResult.deleteCharAt(mResult.length() - 1);
        }

        return mResult.toString();
    }
}
