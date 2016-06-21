package cn.gtmap.helium.client.converter;

import cn.gtmap.helium.client.exception.WrongAppConfigException;

/**
 * Author: <a href="mailto:yingxiufeng@gtmap.cn">yingxiufeng</a>
 * Date:  2016/6/18 9:42
 */
public interface ValueConverter<T> {
    /**
     * 将属性值转换为目标数据类型.
     *
     * @param key   键
     * @param value 值
     * @return 值
     * @throws WrongAppConfigException 如果值类型与目标类型不匹配
     */
    T convert(String key, String value) throws WrongAppConfigException;

    /**
     * 将属性值转换为目标数据类型. 如果值类型不匹配或者未设置则返回默认值.
     *
     * @param key          键
     * @param value        值
     * @param defaultValue 默认值
     * @return 值
     */
    T convert(String key, String value, T defaultValue);

}
