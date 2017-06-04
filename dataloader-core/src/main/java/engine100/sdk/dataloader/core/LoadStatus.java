/*
 * Created by Engine100 on 2017-06-03 21:17:08.
 *
 *      https://github.com/engine100
 *
 */
package engine100.sdk.dataloader.core;

/**
 * the status during downloading.</br>
 */
public class LoadStatus {

    /**
     * before download
     */
    public static final LoadStatus PREPARE = new LoadStatus(1, "prepare");
    /**
     * during download
     */
    public static final LoadStatus LOADING = new LoadStatus(1 << 1, "loading");
    /**
     * download success
     */
    public static final LoadStatus SUCCESS = new LoadStatus(1 << 2, "success");
    /**
     * download failed
     */
    public static final LoadStatus FAILED = new LoadStatus(1 << 3, "failed");

    private int code;

    private String value;

    private LoadStatus(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

}
