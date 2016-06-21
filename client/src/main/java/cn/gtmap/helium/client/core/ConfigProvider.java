package cn.gtmap.helium.client.core;

import cn.gtmap.helium.client.exception.ConfigNotFoundException;
import cn.gtmap.helium.client.exception.WrongAppConfigException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Properties;

/**
 * 配置参数提供者接口定义.
 * Author: <a href="mailto:yingxiufeng@gtmap.cn">yingxiufeng</a>
 * Date:  2016/6/17 22:36
 */
public interface ConfigProvider {
    /**
     * 判断是否有配置指定参数.
     *
     * @param key 属性名称
     * @return boolean
     */
    boolean containsKey(String key);

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回{@code false}.
     *
     * @param key 属性名称
     * @return boolean
     */
    boolean getBoolean(String key) throws ConfigNotFoundException, WrongAppConfigException;

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回指定的默认值.
     *
     * @param key          属性名称
     * @param defaultValue 默认值
     * @return boolean
     */
    boolean getBoolean(String key, boolean defaultValue);

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回{@code 0}.
     *
     * @param key 属性名称
     * @return int
     */
    int getInt(String key) throws ConfigNotFoundException, WrongAppConfigException;

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回指定的默认值.
     *
     * @param key          属性名称
     * @param defaultValue 默认值
     * @return int
     */
    int getInt(String key, int defaultValue);

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回{@code 0}.
     *
     * @param key 属性名称
     * @return long
     */
    long getLong(String key) throws ConfigNotFoundException, WrongAppConfigException;

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回指定的默认值.
     *
     * @param key          属性名称
     * @param defaultValue 默认值
     * @return long
     */
    long getLong(String key, long defaultValue);

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回{@code 0}.
     *
     * @param key 属性名称
     * @return float
     */
    float getFloat(String key) throws ConfigNotFoundException, WrongAppConfigException;

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回指定的默认值.
     *
     * @param key          属性名称
     * @param defaultValue 默认值
     * @return float
     */
    float getFloat(String key, float defaultValue);

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回{@code 0}.
     *
     * @param key 属性名称
     * @return double
     */
    double getDouble(String key) throws ConfigNotFoundException, WrongAppConfigException;

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回指定的默认值.
     *
     * @param key          属性名称
     * @param defaultValue 默认值
     * @return double
     */
    double getDouble(String key, double defaultValue);

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回{@code null}.
     *
     * @param key 属性名称
     * @return String
     */
    String getString(String key);

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回指定的默认值.
     *
     * @param key          属性名称
     * @param defaultValue 默认值
     * @return String
     */
    String getString(String key, String defaultValue);

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回{@code null}.
     *
     * @param key 属性名称
     * @return BigDecimal
     */
    BigDecimal getBigDecimal(String key) throws WrongAppConfigException;

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回指定的默认值.
     *
     * @param key          属性名称
     * @param defaultValue 默认值
     * @return BigDecimal
     */
    BigDecimal getBigDecimal(String key, String defaultValue);

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回{@code null}.
     *
     * @param key 属性名称
     * @return BigInteger
     */
    BigInteger getBigInteger(String key) throws WrongAppConfigException;

    /**
     * 返回指定属性名称的参数值, 如果不存在则返回指定的默认值.
     *
     * @param key          属性名称
     * @param defaultValue 默认值
     * @return BigInteger
     */
    BigInteger getBigInteger(String key, String defaultValue);

    /**
     * @return
     */
    Map getProperties();

}
