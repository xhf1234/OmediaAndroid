
package org.tsinghua.omedia.form;

import org.tsinghua.omedia.annotation.form.AlphaOrNumber;
import org.tsinghua.omedia.annotation.form.Email;
import org.tsinghua.omedia.annotation.form.HttpParam;
import org.tsinghua.omedia.annotation.form.NotEmpty;
import org.tsinghua.omedia.annotation.form.SameTo;
import org.tsinghua.omedia.annotation.form.Size;
import org.tsinghua.omedia.annotation.form.SkipValidate;

/**
 * @author czw
 */
public class SettingsForm extends BaseForm {

    @HttpParam(name = "oldPassword")
    @NotEmpty(msg = "旧密码不能为空")
    @Size(min = 4, minMsg = "密码不能少于4位", max = 32, maxMsg = "密码不能超过32位")
    @AlphaOrNumber(msg = "密码必须由字母或数字组成")
    private String oldPassword;

    @HttpParam(name = "newPassword")
    @NotEmpty(msg = "新密码不能为空")
    @Size(min = 4, minMsg = "密码不能少于4位", max = 32, maxMsg = "密码不能超过32位")
    @AlphaOrNumber(msg = "密码必须由字母或数字组成")
    @SkipValidate(tag="newPassword")
    private String newPassword;

    @SameTo(name = "newPassword", msg = "两次密码输入不一致")
    @SkipValidate(tag="confirmPassword")
    private String confirmPassword;

    @HttpParam(name = "email")
    @NotEmpty(msg = "邮箱不能为空")
    @Size(min = 4, minMsg = "邮箱长度不能少于4位", max = 128, maxMsg = "邮箱长度不能超过128位")
    @Email(msg = "不是正确的邮箱格式")
    private String email;

    @HttpParam(name = "realName")
    private String realName;

    @HttpParam(name = "phone")
    private String phone;

    @HttpParam(name = "address")
    private String address;

    public String getOldPsw() {
        return oldPassword;
    }

    public void setOldPsw(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPsw() {
        return newPassword;
    }

    public void setNewPsw(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return realName;
    }

    public void setName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean skipValidate(String tag) {
        if(tag.equals("newPassword")) {
            if(newPassword==null || newPassword.trim().isEmpty()) {
                return true;
            }
            return false;
        } else if(tag.equals("confirmPassword")) {
            if(newPassword==null || newPassword.trim().isEmpty()) {
                return true;
            }
            return false;
        } else {
            return super.skipValidate(tag);
        }
    }

    
}
