/*
 * Created by Engine100 on 2017-06-03 18:41:51.
 *
 *      https://github.com/engine100
 *
 */
package engine100.sdk.dataloader.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>A manager to down basic data.</p>
 */
public class DataManager {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE = 1;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "BasicDataManager #" + mCount.getAndIncrement());
        }
    };
    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<Runnable>(128);
    private static DataManager mInstance;

    /**
     * The current taskList
     */
    private List<FutureTask<?>> taskList = new ArrayList<FutureTask<?>>();

    private LoadMapListener mMapListener;

    /**
     * if the manager is down the data.
     */
    private boolean isLoading = false;

    /**
     * the downloading task number currently.
     */
    private int mCurrentTaskCount = 0;

    /**
     * if true ,can down with same time.
     */
    private boolean isConcurrently = true;

    /**
     * the dataTypes to be loaded
     */
    private DataType mDataTypes[];

    /**
     * downloading status map
     */
    private Map<DataType, String> mProgressMap = Collections.synchronizedMap(new HashMap<DataType, String>());
    private final Progress mPoxyProgress = new Progress() {
        @Override
        public void onPublishProgress(DataType type, String message) {
            onProgress(type, message);
        }
    };
    /**
     * download result map
     */
    private Map<DataType, LoadStatus> mResultMap = Collections.synchronizedMap(new HashMap<DataType, LoadStatus>());
    private Object mLock = new Object();
    /**
     * threadPool for download task
     */
    private ExecutorService sThreadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
            TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);

    private DataLoader mDataLoader;

    private DataManager() {

    }

    public static DataManager getInstance() {
        if (mInstance == null) {
            synchronized (DataManager.class) {
                if (mInstance == null) {
                    mInstance = new DataManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * prepare finish, begin downloading.
     */
    public void startLoading() {

        if (isLoading) {
            onLoading();
            return;
        }

        mCurrentTaskCount = 0;
        taskList.clear();
        isLoading = true;

        // if reset it in reSetManager,we would get empty result on UI.
        mResultMap.clear();
        // as same as mResultMap
        mProgressMap.clear();

        addTaskCount(mDataTypes.length);

        for (int i = 0; i < mDataTypes.length; i++) {
            DataType type = mDataTypes[i];
            FutureTask<Void> task = new FutureTask<Void>(new DownloadCall(type));
            taskList.add(task);
            onStatusChanged(type, LoadStatus.PREPARE);
            sThreadPool.submit(task);
        }

    }

    public DataManager setConcurrently(boolean concurrently) {
        this.isConcurrently = concurrently;
        return this;
    }

    public DataManager setDataTypes(DataType... types) {
        this.mDataTypes = types;
        return this;
    }

    public DataManager setDataLoader(DataLoader loader) {
        this.mDataLoader = loader;
        return this;
    }

    public DataManager setLoadMapListener(LoadMapListener listener) {
        this.mMapListener = listener;
        return this;
    }

    private void loadData(DataType type) {

        onStatusChanged(type, LoadStatus.LOADING);

        boolean loadResult = false;

        try {
            loadResult = mDataLoader.loadInBackground(type, mPoxyProgress);
        } catch (Exception e) {
            e.printStackTrace();
        }

        onStatusChanged(type, loadResult ? LoadStatus.SUCCESS : LoadStatus.FAILED);

        addTaskCount(-1);

        if (mCurrentTaskCount == 0) {
            onTaskFinish();
        }

    }

    private void onLoading() {
        if (mMapListener != null) {
            mMapListener.onLoading();
        }
    }

    private void onProgress(DataType type, String message) {
        synchronized (mProgressMap) {
            mProgressMap.put(type, message);
        }

        if (mMapListener != null) {
            mMapListener.onProgress(mProgressMap);
        }
    }

    private void onTaskFinish() {
        if (mMapListener != null) {
            mMapListener.onFinish(mResultMap);
        }

        isLoading = false;
        mCurrentTaskCount = 0;
        taskList.clear();
    }

    private void onStatusChanged(DataType type, LoadStatus status) {

        onProgress(type, status.getValue());

        if ((status == LoadStatus.SUCCESS || status == LoadStatus.FAILED)) {
            synchronized (mResultMap) {
                mResultMap.put(type, status);
            }
        }
    }

    private synchronized void addTaskCount(int num) {
        mCurrentTaskCount += num;
    }

    private class DownloadCall implements Callable<Void> {
        private DataType mType;

        public DownloadCall(DataType key) {
            mType = key;
        }

        public Void call() {
            if (isConcurrently) {
                loadData(mType);
            } else {
                synchronized (mLock) {
                    loadData(mType);
                }
            }
            return null;
        }
    }
}
