package cn.jikon.common.util;

import cn.jikon.common.BizException;

/**
 * Created by wujiapeng on 17/4/6.
 */
public class UKID {

    /**
     * 获取生成的ID
     * @return
     * @throws BizException
     */
    public final static String getGeneratorID() {
        IdGenerator idGenerator = new IdGenerator(1);
        return String.valueOf(idGenerator.generate());
    }

}
