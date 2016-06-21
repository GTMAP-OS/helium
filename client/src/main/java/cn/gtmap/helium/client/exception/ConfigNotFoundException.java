package cn.gtmap.helium.client.exception;

/**
 * 未找到指定的配置项异常类型.
 * Author: <a href="mailto:yingxiufeng@gtmap.cn">yingxiufeng</a>
 * Date:  2016/6/17 22:31
 */
public class ConfigNotFoundException extends RuntimeException {

    private String key;

    /**
     * 根据配置键创建异常对象.
     *
     * @param key 配置键
     */
    public ConfigNotFoundException(String key) {
        super(formatMessage(key));
        this.key = key;
    }

    /**
     * 根据配置键与目标异常创建异常对象.
     *
     * @param key   配置键
     * @param cause 目标异常
     */
    public ConfigNotFoundException(String key, Throwable cause) {
        super(formatMessage(key), cause);
        this.key = key;
    }

    /**
     * 返回未找到的配置键.
     *
     * @return 配置键
     */
    public String getKey() {
        return key;
    }

    private static String formatMessage(String propertyName) {
        return String.format("未找到键为 [%s] 的配置", propertyName);
    }
}
