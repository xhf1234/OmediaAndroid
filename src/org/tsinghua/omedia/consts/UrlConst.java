package org.tsinghua.omedia.consts;

/**
 * URL常量
 * @author xuhongfeng
 *
 */
public class UrlConst {

    public static final String BaseUrl = "http://"
            + OmediaConst.OmediaServerAddr+":"
            + OmediaConst.OmediaServerPort+"/";
    
    public static final String DownloadApkUrl = BaseUrl+"download-omedia.do";
    
    public static final String LoginUrl = BaseUrl+"login.do";
    public static final String RegisterUrl = BaseUrl+"register.do";
    public static final String GetAccountUrl = BaseUrl+"getAccount.do";
    public static final String CheckDataVersionUrl = BaseUrl+"checkDataVersion.do";
    public static final String GetFriendRequestUrl = BaseUrl+"getFriendRequest.do";
    public static final String SearchFriendsUrl = BaseUrl+"searchFriends.do";
    public static final String AddFriendUrl = BaseUrl+"addFriend.do";
    public static final String GetFriendsUrl = BaseUrl+"getFriends.do";
    public static final String SocialGraphUrl = BaseUrl+"socialGraph.do";
    public static final String GetConfigUrl = BaseUrl+"getConfig.do";
    public static final String ShowCcnFilesUrl = BaseUrl+"showCcnFiles.do";
    public static final String ShowFriendCcnFilesUrl = BaseUrl+"showFriendCcnFiles.do";
    public static final String ccnPutFileUrl = BaseUrl+"ccnPutFile.do";
    public static final String settingsUrl = BaseUrl+"setting.do";
    public static final String ccnDeleteFileUrl = BaseUrl+"deleteCcnFile.do";
    public static final String friendRequestReplyUrl = BaseUrl+"friendRequestReply.do";
    public static final String deleteFriendUrl = BaseUrl+"deleteFriends.do";
}
