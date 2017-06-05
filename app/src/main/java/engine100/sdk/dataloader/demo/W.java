/*
 * Created by Engine100 on 2017-06-05 09:25:00.
 *
 *      https://github.com/engine100
 *
 */
package engine100.sdk.dataloader.demo;

import engine100.sdk.dataloader.core.DataType;
import engine100.sdk.dataloader.worker.LoadWorker;
import engine100.sdk.dataloader.worker.WorkerManager;


public class W extends WorkerManager {
    @Override
    protected LoadWorker getWorkerByType(DataType type) {
        if (type.getCode().equals("0")) {
            return new UserWorker();
        } else {
            return new OtherWorker();
        }
    }
}
