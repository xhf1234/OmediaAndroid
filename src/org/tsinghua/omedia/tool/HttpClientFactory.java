/**
 * @(#)HttpClientFactory.java, 2010-12-6. 
 * 
 * Copyright 2010 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.tsinghua.omedia.tool;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;

/**
 * The factory class to get the httpclient instance. DO NOT modify the default
 * parameter for the client.
 * 
 * @author Nicholas
 */
public class HttpClientFactory {
    protected static final int MAX_CONNECTION = 5;

    protected static final int TIMEOUT_CONNECTION = 30000;

    protected static final int TIMEOUT_SOCKET = 20000;

    protected static final int MAX_CONTENT_SIZE = 2 * 1024 * 1024;

    protected static final int MAX_CONNECTION_PER_IP = 5;

    public static HttpClient getNewInstance() {
        return getNewInstance(TIMEOUT_SOCKET, TIMEOUT_CONNECTION);
    }
    
    public static HttpClient getNewInstance(int soTimeout, int connTimeout) {
        return getNewInstance(soTimeout, connTimeout, MAX_CONNECTION,
                MAX_CONNECTION_PER_IP, MAX_CONTENT_SIZE);
    }
    
    public static HttpClient getNewInstance(int soTimeout, int connTimeout,
            int maxConn, int maxConnPerIp, int maxContentSize) {
        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.getParams().setMaxTotalConnections(maxConn);
        connectionManager.getParams().setConnectionTimeout(connTimeout);
        connectionManager.getParams().setSoTimeout(soTimeout);
        connectionManager.getParams().setSendBufferSize(maxContentSize);
        connectionManager.getParams().setReceiveBufferSize(maxContentSize);
        connectionManager.getParams().setDefaultMaxConnectionsPerHost(
                maxConnPerIp);
        return new HttpClient(connectionManager);
    }
}
