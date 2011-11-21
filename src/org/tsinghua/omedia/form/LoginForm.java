package org.tsinghua.omedia.form;

import org.tsinghua.omedia.annotation.validate.AlphaOrNumber;
import org.tsinghua.omedia.annotation.validate.NotEmpty;
import org.tsinghua.omedia.annotation.validate.Size;


/**
 * 
 * @author xuhongfeng
 *
 */
public class LoginForm extends AbstractForm {
    @NotEmpty(msg="用户名不能为空")
    @Size(min=4,minMsg="用户名不能少于4位",max=32,maxMsg="用户名不能超过32位")
    @AlphaOrNumber(msg="用户名必须由字母或数字组成")
    private String username;
    
    @NotEmpty(msg="密码不能为空")
    @Size(min=6,minMsg="密码不能少于6位",max=32,maxMsg="密码不能超过32位")
    @AlphaOrNumber(msg="密码必须由字母或数字组成")
    private String password;
    
    private boolean rememberPassword;

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

    public boolean isRememberPassword() {
        return rememberPassword;
    }

    public void setRememberPassword(boolean rememberPassword) {
        this.rememberPassword = rememberPassword;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((password == null) ? 0 : password.hashCode());
        result = prime * result + (rememberPassword ? 1231 : 1237);
        result = prime * result
                + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LoginForm other = (LoginForm) obj;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (rememberPassword != other.rememberPassword)
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "LoginForm [username=" + username + ", password=" + password
                + ", rememberPassword=" + rememberPassword + "]";
    }

}
