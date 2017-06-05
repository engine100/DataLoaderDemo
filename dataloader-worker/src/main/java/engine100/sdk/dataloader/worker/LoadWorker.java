/*
 * Created by Engine100 on 2017-06-05 08:59:02.
 *
 *      https://github.com/engine100
 *
 */
package engine100.sdk.dataloader.worker;

import engine100.sdk.dataloader.core.DataType;
import engine100.sdk.dataloader.core.Progress;

public interface LoadWorker<T> {

    /**
     * first, down the data.then saveData .
     * This way can be used for most scene,
     * but when we need to down for many times,such as more than one page,it has no effect.
     * In such situation,We only need to call save data with our own methods to load data,
     * return null in getData and do all in saveData.
     *
     * @param type data type
     * @param pro  progress to show
     * @return the data you get , it can be from file or network etc...
     */
    T getData(DataType type, Progress pro);

    /**
     * two, save the data download
     *
     * @param data result from getData
     * @param type data type
     * @param pro  progress to show
     * @return when you own data, save it in database or file etc...
     */
    boolean saveData(T data, DataType type, Progress pro);
}
