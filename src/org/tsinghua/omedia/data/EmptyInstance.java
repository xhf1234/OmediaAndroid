package org.tsinghua.omedia.data;

/**
 * 定义一些Empty类型
 * 
 * @author xuhongfeng
 *
 */
public class EmptyInstance {
    public static FriendRequest[] EMPTY_FRIEND_REQUESTS = new FriendRequest[0];
    
    public static Account[] EMPTY_FRIENDS = new Account[0];
    
    public static String[] EMPTY_STRINGS = new String[0];
    
    public static class EmptyResultType implements Jsonable {
        
    }
    
    public static CcnFile[] EMPTY_CCN_FILES = new CcnFile[0];

    public static Group[] EMPTY_GROUPS = new Group[0];
}
