package engine100.sdk.dataloader.demo;

import engine100.sdk.dataloader.core.DataType;
import engine100.sdk.dataloader.core.Progress;
import engine100.sdk.dataloader.worker.LoadWorker;

/*
 * Created by Engine100 on 2017-06-05 09:25:16.
 * 
 *      https://github.com/engine100
 *
 */
public class UserWorker implements LoadWorker<String> {
    @Override
    public String getData(DataType type, Progress pro) {
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean saveData(String data, Progress pro) {
        return true;
    }
}
