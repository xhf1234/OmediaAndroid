package org.tsinghua.omedia.form;

import org.tsinghua.omedia.annotation.form.AlphaOrNumber;
import org.tsinghua.omedia.annotation.form.Email;
import org.tsinghua.omedia.annotation.form.HttpParam;
import org.tsinghua.omedia.annotation.form.NotEmpty;
import org.tsinghua.omedia.annotation.form.SameTo;
import org.tsinghua.omedia.annotation.form.Size;
import org.tsinghua.omedia.consts.OmediaConst;

/**
 * 
 * @author xuhongfeng
 *
 */
public class RegisterForm extends AbstractForm {
    @HttpParam(name="username")
    @NotEmpty(msg="用户名不能为空")
    @Size(min=4,minMsg="用户名不能少于4位",max=32,maxMsg="用户名不能超过32位")
    @AlphaOrNumber(msg="用户名必须由字母或数字组成")
    private String username;
    
    @HttpParam(name="password")
    @NotEmpty(msg="密码不能为空")
    @Size(min=4,minMsg="密码不能少于4位",max=32,maxMsg="密码不能超过32位")
    @AlphaOrNumber(msg="密码必须由字母或数字组成")
    private String password;
    
    @SameTo(name="password", msg="两次密码输入不一致")
    private String confirmPassword;
    
    @HttpParam(name="email")
    @NotEmpty(msg="邮箱不能为空")
    @Size(min=4,minMsg="邮箱长度不能少于4位",max=128,maxMsg="邮箱长度不能超过128位")
    @Email(msg="不是正确的邮箱格式")
    private String email;
    
    @HttpParam(name="omediaVersion")
    private String omediaVersion = OmediaConst.OmediaVersion;
    
  
	public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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
    public String getOmediaVersion() {
        return omediaVersion;
    }
    
}
