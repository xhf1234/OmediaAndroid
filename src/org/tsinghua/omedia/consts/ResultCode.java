package org.tsinghua.omedia.consts;

/**
 * 服务端接口的返回码 常量
 * @author xuhongfeng
 *
 */
public class ResultCode extends OmediaConst {
    //服务端错误
    public static final int SERVER_ERROR = -1;
    //请求处理成功
    public static final int SUCCESS = 1;
    //token过期
    public static final int TOKEN_WRONG = 3;
    
    public static class Register extends ResultCode {
        //用户名已存在
        public static final int USERNAME_EXIST = 4;
    }
    
    public static class Login extends ResultCode {
        //验证失败
        public static final int FAILED = 2;
        //客户端版本需要升级
        public static final int SOFTWARE_NEED_UPDATE = 4;
    }
    
    public static class AddFriend extends ResultCode {
        //不能加自己为好友
        public static final int ADD_YOUR_SELF = 4;
        //已经在您的好友列表中
        public static final int ALREADY_FRIEND = 5;
    }
}
