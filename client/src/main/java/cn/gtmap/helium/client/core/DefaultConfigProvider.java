package cn.gtmap.helium.client.core;

import cn.gtmap.helium.client.converter.*;
import cn.gtmap.helium.client.exception.ConfigNotFoundException;
import cn.gtmap.helium.client.exception.WrongAppConfigException;
import org.springframework.util.PropertyPlaceholderHelper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * {@link cn.gtmap.helium.client.core.ConfigProvider}的默认实现.
 * Author: <a href="mailto:yingxiufeng@gtmap.cn">yingxiufeng</a>
 * Date:  2016/6/18 10:59
 */
public class DefaultConfigProvider implements ConfigProvider {

    private final static BooleanValueConverter BOOLEAN_VALUE_CONVERTER = new BooleanValueConverter();
    private final static IntValueConverter INT_VALUE_CONVERTER = new IntValueConverter();
    private final static LongValueConverter LONG_VALUE_CONVERTER = new LongValueConverter();
    private final static FloatValueConverter FLOAT_VALUE_CONVERTER = new FloatValueConverter();
    private final static DoubleValueConverter DOUBLE_VALUE_CONVERTER = new DoubleValueConverter();

    private final Properties properties;
    /**
     * @param properties
     */
    public DefaultConfigProvider(Properties properties) {
        if (properties == null) {
            throw new IllegalArgumentException("properties 不能为null");
        }
        PropertyPlaceholderHelper placeholderHelper = new PropertyPlaceholderHelper("${", "}", ":", true);
        Properties replacedProperties = new Properties();
        for (Map.Entry<?, ?> entry : properties.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            replacedProperties.setProperty(key, placeholderHelper.replacePlaceholders(value, properties));
        }
        this.properties = replacedProperties;
    }
    @Override
    public boolean containsKey(String key) {
        return getProperties().containsKey(key);
    }

    @Override
    public boolean getBoolean(String key) throws ConfigNotFoundException, WrongAppConfigException {
        return BOOLEAN_VALUE_CONVERTER.convert(key, getRequiredProperty(key));
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return BOOLEAN_VALUE_CONVERTER.convert(key, getProperty(key), defaultValue);
    }

    @Override
    public int getInt(String key) throws ConfigNotFoundException, WrongAppConfigException {
        return INT_VALUE_CONVERTER.convert(key, getRequiredProperty(key));
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return INT_VALUE_CONVERTER.convert(key, getProperty(key), defaultValue);
    }

    @Override
    public long getLong(String key) throws ConfigNotFoundException, WrongAppConfigException {
        return LONG_VALUE_CONVERTER.convert(key, getRequiredProperty(key));
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return LONG_VALUE_CONVERTER.convert(key, getProperty(key), defaultValue);
    }

    @Override
    public float getFloat(String key) throws ConfigNotFoundException, WrongAppConfigException {
        return FLOAT_VALUE_CONVERTER.convert(key, getRequiredProperty(key));
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        return FLOAT_VALUE_CONVERTER.convert(key, getProperty(key), defaultValue);
    }

    @Override
    public double getDouble(String key) throws ConfigNotFoundException, WrongAppConfigException {
        return DOUBLE_VALUE_CONVERTER.convert(key, getRequiredProperty(key));
    }

    @Override
    public double getDouble(String key, double defaultValue) {
        return DOUBLE_VALUE_CONVERTER.convert(key, getProperty(key), defaultValue);
    }

    @Override
    public String getString(String key) {
        return getString(key, null);
    }

    @Override
    public String getString(String key, String defaultValue) {
        String v = getProperty(key);
        if (v == null) {
            v = defaultValue;
        }
        return v;
    }

    @Override
    public BigDecimal getBigDecimal(String key) throws WrongAppConfigException {
        return getBigDecimal(key, null);
    }

    @Override
    public BigDecimal getBigDecimal(String key, String defaultValue) {
        try {
            String value = getString(key, defaultValue);
            if (value == null) {
                return null;
            }

            return new BigDecimal(value);
        } catch (Exception e) {
            throw new WrongAppConfigException(key, defaultValue, e);
        }
    }

    @Override
    public BigInteger getBigInteger(String key) throws WrongAppConfigException {
        return getBigInteger(key, null);
    }

    @Override
    public BigInteger getBigInteger(String key, String defaultValue) {
        try {
            String value = getString(key, defaultValue);
            if (value == null) {
                return null;
            }
            return new BigInteger(value);
        } catch (Exception e) {
            throw new WrongAppConfigException(key, defaultValue, e);
        }
    }

    /***
     * get properties map
     * @return
     */
    @Override
    public Map getProperties() {
        if (properties == null) {
            return new Properties();
        }
        return new HashMap(properties);
    }

    /**
     * @param key
     * @return
     */
    protected String getRequiredProperty(String key) throws ConfigNotFoundException {
        String v = getProperty(key);
        if (v == null) {
            throw new ConfigNotFoundException(key);
        }
        return properties.getProperty(key);
    }

    /**
     * @param key
     * @return
     */
    protected String getProperty(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("属性名称 [propertyName] 不能为 null 或者空字符");
        }

        String v = String.valueOf(getProperties().get(key));
        if (v == null) {
            return v;
        }
        return properties.getProperty(key);
    }
}
