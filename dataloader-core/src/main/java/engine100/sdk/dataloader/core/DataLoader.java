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

    boolean loadInBackground(DataType type, Progress pro);

}
