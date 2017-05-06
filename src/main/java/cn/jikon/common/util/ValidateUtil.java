package cn.jikon.common.util;

import cn.jikon.common.BizException;
import cn.jikon.common.EduErrors;

import java.util.regex.Pattern;

/******
 * 校验工具类
 * 用来校验用户名、密码长度以及其他校验方法
 * @author Administrator
 *
 */
public class ValidateUtil {

    private static final String ACCOUNT_REG = "^\\d{6,11}$";

    private static final String ORGANIZATION_NAME_REG = "^[\\u4e00-\\u9fa5_a-zA-Z0-9\\\\(\\\\)]{0,30}$";

    //用户名只能有数字0-9，不小于6位，不大于11位
    public static Boolean validateLength_account(String account) {
        boolean result = Pattern.compile(ACCOUNT_REG).matcher(account).find();
        return result;
    }

    //组织名规则  中文 数字 字母大小写 小括号 下划线 长度 0-30
    public static void validateLength_organization_name(String name) throws BizException {
        boolean result = Pattern.compile(ORGANIZATION_NAME_REG).matcher(name).find();
        if (false == result) {
            throw new BizException(EduErrors.CODE_100011, "组织名称长度为30个字以内");
        }
    }
}
