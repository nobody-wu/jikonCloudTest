package cn.jikon.common;

/**
 * Created by lwj on 2017/3/28.
 */


public class BizException extends Exception {
    private static final long serialVersionUID = -6087039724397943604L;

    private int errno = -1;
    private Object data = null;

    public BizException() {
        super("unknown error");
        errno = -1;
        data = null;
    }

    public BizException(String errmsg) {
        super(errmsg);
        errno = -1;
        data = null;
    }

    public BizException(int errno, String errmsg) {
        super(errmsg);
        this.errno = errno;
        data = null;
    }

    public BizException(int errno, String errmsg, Throwable e) {
        super(errmsg, e);
        this.errno = errno;
        data = null;
    }

    public int getErrno() {
        return errno;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        String s = getClass().getName() + " - " + Integer.toString(errno);
        String message = getMessage();
        return (message != null) ? (s + ": " + message) : s;
    }

}
