package cn.gtmap.helium.client.model;

import java.io.Serializable;

/**
 * client info
 * Author: <a href="mailto:yingxiufeng@gtmap.cn">yingxiufeng</a>
 * Date:  2016/6/17 22:23
 */
public class ClientInfo implements Serializable {

    private static final long serialVersionUID = -6611915052441363579L;
    /**
     * 主机地址.
     */
    private String host;
    /**
     * 最后拉取配置时间 (unix timestamp).
     */
    private long lastPullTime;

    public String getHost() {
        return host;
    }

    public ClientInfo setHost(String host) {
        this.host = host;
        return this;
    }

    public long getLastPullTime() {
        return lastPullTime;
    }

    public ClientInfo setLastPullTime(long lastPullTime) {
        this.lastPullTime = lastPullTime;
        return this;
    }

    @Override
    public String toString() {
        return "ClientInfo{" +
                "host='" + host + '\'' +
                ", lastPullTime=" + lastPullTime +
                '}';
    }
}
