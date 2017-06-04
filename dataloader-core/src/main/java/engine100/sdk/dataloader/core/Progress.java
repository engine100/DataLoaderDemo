/*
 * Created by Engine100 on 2017-06-03 18:47:55.
 *
 *      https://github.com/engine100
 *
 */
package engine100.sdk.dataloader.core;

/**
 * the progress show the status during downloading.
 */
public interface Progress {

    /**
     * @param type    which basicType to progress
     * @param message the progress contains
     */
    void onPublishProgress(DataType type, String message);

}
