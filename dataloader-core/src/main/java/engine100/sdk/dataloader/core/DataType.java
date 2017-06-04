/*
 * Created by Engine100 on 2017-06-03 18:56:27.
 *
 *      https://github.com/engine100
 *
 */
package engine100.sdk.dataloader.core;

/**
 * The BasicData type to download.
 * e.g. User,Department ...
 */
public class DataType {

    private String code;

    private String name;

    /**
     * @param code the type code,as usual,we define it in English
     * @param name the type name,which show in different languages
     */
    public DataType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DataType)) {
            return false;
        }
        return code.equals(((DataType) o).code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

}
