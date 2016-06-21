package cn.gtmap.helium.client.converter;

import cn.gtmap.helium.client.exception.WrongAppConfigException;

/**
 * Author: <a href="mailto:yingxiufeng@gtmap.cn">yingxiufeng</a>
 * Date:  2016/6/18 9:41
 */
public abstract class AbstractValueConverter<T> implements ValueConverter<T> {


    /***
     *
     * @param key   键
     * @param value 值
     * @return
     * @throws WrongAppConfigException
     */
    @Override
    public T convert(String key, String value) throws WrongAppConfigException {
        try {
            return doConvert(value);
        } catch (Exception e) {
            throw new WrongAppConfigException(key, value, e);
        }
    }

    /***
     *
     * @param key          键
     * @param value        值
     * @param defaultValue 默认值
     * @return
     */
    @Override
    public T convert(String key, String value, T defaultValue) {
        if (value == null) {
            return defaultValue;
        }

        try {
            doConvert(value);
        } catch (Exception e) {
            return defaultValue;
        }
        return null;
    }

    /**
     * 将属性值转换为目标类型.
     *
     * @param value 值
     * @return 值
     */
    protected abstract T doConvert(String value);
}
