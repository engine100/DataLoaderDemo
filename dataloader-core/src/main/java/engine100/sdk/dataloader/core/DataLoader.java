/*
 * Created by Engine100 on 2017-06-04 00:17:08.
 *
 *      https://github.com/engine100
 *
 */
package engine100.sdk.dataloader.core;

/**
 * implement it to load your data.
 */
public interface DataLoader {

    /**
     * @param type data type
     * @param pro  progress to show
     * @return if load success ,return true, otherwise false.
     */
    boolean loadInBackground(DataType type, Progress pro);

}
