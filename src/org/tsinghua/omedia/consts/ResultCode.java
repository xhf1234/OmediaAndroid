package org.tsinghua.omedia.consts;

/**
 * 服务端接口的返回码 常量
 * @author xuhongfeng
 *
 */
public class ResultCode extends OmediaConst {
    public static final int SERVER_ERROR = -1;
    public static final int SUCCESS = 1;
    public static final int TOKEN_WRONG = 3;
    
    public static class Login extends ResultCode {
        public static final int FAILED = 2;
    }
}
