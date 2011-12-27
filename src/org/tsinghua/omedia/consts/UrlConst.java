package org.tsinghua.omedia.consts;

/**
 * URL常量
 * @author xuhongfeng
 *
 */
public class UrlConst {

    public static final String BaseUrl = "http://"
            + OmediaConst.OmediaServerAddr+":"
            + OmediaConst.OmediaServerPort+"/omedia/";
    
    public static final String LoginUrl = BaseUrl+"login.do";
    public static final String GetAccountUrl = BaseUrl+"getAccount.do";
    public static final String CheckDataVersionUrl = BaseUrl+"checkDataVersion.do";
    public static final String GetFriendRequestUrl = BaseUrl+"getFriendRequest.do";
    public static final String SearchFriendsUrl = BaseUrl+"searchFriends.do";
    public static final String AddFriendUrl = BaseUrl+"addFriend.do";
    public static final String GetFriendsUrl = BaseUrl+"getFriends.do";
}
