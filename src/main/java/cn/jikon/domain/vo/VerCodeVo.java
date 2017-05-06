package cn.jikon.domain.vo;

/**
 * Created by wangxin on 2017/4/23.
 */
public class VerCodeVo {
    private String verCodeId;
    private String verCodeImage;

    public VerCodeVo() {
    }

    public VerCodeVo(String verCodeId, String verCodeImage) {
        this.verCodeId = verCodeId;
        this.verCodeImage = verCodeImage;
    }

    public String getVerCodeId() {
        return verCodeId;
    }

    public void setVerCodeId(String verCodeId) {
        this.verCodeId = verCodeId;
    }

    public String getVerCodeImage() {
        return verCodeImage;
    }

    public void setVerCodeImage(String verCodeImage) {
        this.verCodeImage = verCodeImage;
    }
}
