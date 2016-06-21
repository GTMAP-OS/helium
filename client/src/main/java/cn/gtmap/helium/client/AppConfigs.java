package cn.gtmap.helium.client;

import cn.gtmap.helium.client.core.ConfigProvider;
import cn.gtmap.helium.client.core.HeliumConfigContext;
import cn.gtmap.helium.client.exception.ConfigNotFoundException;
import cn.gtmap.helium.client.exception.WrongAppConfigException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Properties;

/**
 * 区别于egov-common的AppConfig 并不支持获取类似 ${server.url} 的配置项
 * Author: <a href="mailto:yingxiufeng@gtmap.cn">yingxiufeng</a>
 * Date:  2016/6/18 10:44
 */
public final class AppConfigs {

    private AppConfigs() {
        throw new AssertionError("AppConfigs 不能创建实例");
    }

    /**
     * 判断是否有配置指定参数.
     *
     * @param key 属性名称
     * @return boolean
     */
    public static boolean containsKey(String key) {
        return getProvider().containsKey(key);
    }

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回{@code false}.
     *
     * @param key 属性名称
     * @return boolean
     */
    public static boolean getBooleanProperty(String key) throws ConfigNotFoundException, WrongAppConfigException {
        return getProvider().getBoolean(key);
    }

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回指定的默认值.
     *
     * @param key         属性名称
     * @param defaultValue 默认值
     * @return boolean
     */
    public static boolean getBooleanProperty(String key, boolean defaultValue) {
        return getProvider().getBoolean(key, defaultValue);
    }

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回{@code 0}.
     *
     * @param key 属性名称
     * @return int
     */
    public static int getIntProperty(String key) throws ConfigNotFoundException, WrongAppConfigException {
        return getProvider().getInt(key);
    }

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回指定的默认值.
     *
     * @param key         属性名称
     * @param defaultValue 默认值
     * @return int
     */
    public static int getIntProperty(String key, int defaultValue) {
        return getProvider().getInt(key, defaultValue);
    }

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回{@code 0}.
     *
     * @param key 属性名称
     * @return long
     */
    public static long getLongProperty(String key) throws ConfigNotFoundException, WrongAppConfigException {
        return getProvider().getLong(key);
    }

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回指定的默认值.
     *
     * @param key         属性名称
     * @param defaultValue 默认值
     * @return long
     */
    public static long getLongProperty(String key, long defaultValue) {
        return getProvider().getLong(key, defaultValue);
    }

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回{@code 0}.
     *
     * @param key 属性名称
     * @return float
     */
    public static float getFloatProperty(String key) throws ConfigNotFoundException, WrongAppConfigException {
        return getProvider().getFloat(key);
    }

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回指定的默认值.
     *
     * @param key         属性名称
     * @param defaultValue 默认值
     * @return float
     */
    public static float getFloatProperty(String key, float defaultValue) {
        return getProvider().getFloat(key, defaultValue);
    }

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回{@code 0}.
     *
     * @param key 属性名称
     * @return double
     */
    public static double getDoubleProperty(String key) throws ConfigNotFoundException, WrongAppConfigException {
        return getProvider().getDouble(key);
    }

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回指定的默认值.
     *
     * @param key         属性名称
     * @param defaultValue 默认值
     * @return double
     */
    public static double getDoubleProperty(String key, double defaultValue) {
        return getProvider().getDouble(key, defaultValue);
    }

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回{@code null}.
     *
     * @param key 属性名称
     * @return String
     */
    public static String getStringProperty(String key) {
        return getProvider().getString(key);
    }

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回指定的默认值.
     *
     * @param key         属性名称
     * @param defaultValue 默认值
     * @return String
     */
    public static String getStringProperty(String key, String defaultValue) {
        return getProvider().getString(key, defaultValue);
    }

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回{@code null}.
     *
     * @param key 属性名称
     * @return BigDecimal
     */
    public static BigDecimal getBigDecimalProperty(String key) throws WrongAppConfigException {
        return getProvider().getBigDecimal(key);
    }

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回指定的默认值.
     *
     * @param key         属性名称
     * @param defaultValue 默认值
     * @return BigDecimal
     */
    public static BigDecimal getBigDecimalProperty(String key, String defaultValue) {
        return getProvider().getBigDecimal(key, defaultValue);
    }

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回{@code null}.
     *
     * @param key 属性名称
     * @return BigInteger
     */
    public static BigInteger getBigIntegerProperty(String key) throws WrongAppConfigException {
        return getProvider().getBigInteger(key);
    }

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回指定的默认值.
     *
     * @param key         属性名称
     * @param defaultValue 默认值
     * @return BigInteger
     */
    public static BigInteger getBigIntegerProperty(String key, String defaultValue) {
        return getProvider().getBigInteger(key, defaultValue);
    }

    /***
     * 返回所有的配置属性
     * @return
     */
    public static Map getProperties(){
        return getProvider().getProperties();
    }

    private static ConfigProvider getProvider() {
        return HeliumConfigContext.getHeliumConfig().getConfigProvider();
    }
}
