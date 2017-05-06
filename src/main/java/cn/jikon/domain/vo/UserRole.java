package cn.jikon.domain.vo;

/**
 * Created by zl on 2017/3/29. 用戶角色
 */
public class UserRole extends RoleVo {
    private String descripe;        //描述
    private String privilegeName;   //权限名称

    public String getDescripe() {
        return descripe;
    }

    public void setDescripe(String descripe) {
        this.descripe = descripe;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }
}
