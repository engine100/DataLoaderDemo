/*
 * Created by Engine100 on 2017-06-05 09:11:27.
 *
 *      https://github.com/engine100
 *
 */
package engine100.sdk.dataloader.worker;

import engine100.sdk.dataloader.core.DataType;
import engine100.sdk.dataloader.core.Progress;

public abstract class WorkerManager {

    protected abstract LoadWorker<Object> getWorkerByType(DataType type);

    /**
     * call this method in loadInBackground on DataLoader
     * it split the method by two steps.
     *
     * @param type the data to load
     * @param pro  show the progress
     * @return true if success
     */
    public boolean loadInBackground(DataType type, Progress pro) {
        LoadWorker loadWorker = getWorkerByType(type);

        Object data = loadWorker.getData(type, pro);
        boolean saveResult = loadWorker.saveData(data, type, pro);
        return saveResult;

    }

}
