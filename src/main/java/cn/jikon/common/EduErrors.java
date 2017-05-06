package cn.jikon.common;

/**
 * Created by lwj on 2017/3/28.
 */
public class EduErrors {

    /************公共平台状态码*************/
    public static final int CODE_100000 = 100000;//请求成功	正确请求以及正确返回的情况下
    public static final int CODE_100001 = 100001;//新增失败	适用于所有新增操作
    public static final int CODE_100002 = 100002;//更新失败       适用于所有更新操作
    public static final int CODE_100003 = 100003;//删除失败	适用于所有删除操作
    public static final int CODE_100004 = 100004;//查询列表失败	适用于所有查询操作
    public static final int CODE_100005 = 100005;//查看失败	适用于查看操作
	public static final int CODE_100006 = 100006;//绑定失败     用于所有绑定操作
    public static final int CODE_100007 = 100007;//参数为空     请求参数中任一参数为空的情况下
    public static final int CODE_100008 = 100008;//用户不存在    该用户（管理员或者用户）不存在
    public static final int CODE_100009 = 100009;//密码错误     用户存在情况下，密码输入错误
    public static final int CODE_100010 = 100010;//激活失败     激活用户失败情况下
    public static final int CODE_100011 = 100011;//长度错误     任意判断长度的地方提示 
    public static final int CODE_100012 = 100012;//请求内容异常
    public static final int CODE_100013 = 100013;//不可重复操作
    public static final int CODE_100014 = 100014;//重复    任意模块（组织/管理员/用户/模块等等）已存在，重复添加的情况下。
    public static final int CODE_100015 = 100015;//已存在   任意判断已存在的情况下。
    public static final int CODE_100018 = 100018;//用户未激活

    public static final int CODE_100020 = 100020;//命名不合法
    public static final int CODE_100022 = 100022;//密码生成失败
    public static final int CODE_100023 = 100023;//token生成失败
    public static final int CODE_100024 = 100024;//验证码错误

    /************实验室错误码*************/
    public static final int CODE_200000 = 200000;//请求成功	正确请求以及正确返回的情况下
    /************悬挂链错误码*************/
    public static final int CODE_300000 = 300000;//请求成功	正确请求以及正确返回的情况下

}
