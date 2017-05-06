package cn.jikon.domain.vo;

/**
 * Created by zl on 2017/4/27.
 */
public class SubSystemModuleVo {

    private String subsystemModuleId;
    private String modeuleName;
    private boolean checked =false;

    public String getSubsystemModuleId() {
        return subsystemModuleId;
    }

    public void setSubsystemModuleId(String subsystemModuleId) {
        this.subsystemModuleId = subsystemModuleId;
    }

    public String getModeuleName() {
        return modeuleName;
    }

    public void setModeuleName(String modeuleName) {
        this.modeuleName = modeuleName;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
