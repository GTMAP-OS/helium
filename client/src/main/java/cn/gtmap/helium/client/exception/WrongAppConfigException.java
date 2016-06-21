package cn.gtmap.helium.client.exception;

/**
 * 错误的配置属性值异常
 * Author: <a href="mailto:yingxiufeng@gtmap.cn">yingxiufeng</a>
 * Date:  2016/6/17 22:29
 */
public class WrongAppConfigException extends RuntimeException {
    private String key;
    private String value;

    /**
     * 通过配置键, 值与目标异常构建异常.
     *
     * @param key   配置键
     * @param value 配置值
     * @param cause 目标异常
     */
    public WrongAppConfigException(String key, String value, Throwable cause) {
        super(formatMessage(key, value), cause);
        this.key = key;
        this.value = value;
    }

    /**
     * 返回属性值错误的键.
     *
     * @return 键
     */
    public String getKey() {
        return key;
    }

    /**
     * 返回错误的属性值.
     *
     * @return 值
     */
    public String getValue() {
        return value;
    }

    private static String formatMessage(String propertyName, String propertyValue) {
        return String.format("错误的属性配置 [%s: %s]", propertyName, propertyValue);
    }
}
