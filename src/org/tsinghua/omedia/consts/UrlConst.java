package org.tsinghua.omedia.consts;

/**
 * URL常量
 * @author xuhongfeng
 *
 */
public interface UrlConst {

    public static final String BaseUrl = "http://"
            + OmediaConst.OmediaServerAddr+":"
            + OmediaConst.OmediaServerPort+"/omedia/";
    
    public static final String LoginUrl = BaseUrl+"login.do";
}
