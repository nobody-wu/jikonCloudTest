package cn.jikon.common.rules;

import cn.jikon.common.BizException;

/**
 * Created by wangxin on 2017/4/21.
 */
public class BizResult {
    public static void ensureTrue(Object test, int errno, String errmsg) throws BizException {
        if (null != test && Boolean.class == test.getClass() && (Boolean) test == true) {
            return;
        }
        throw new BizException(errno, errmsg);
    }
}
