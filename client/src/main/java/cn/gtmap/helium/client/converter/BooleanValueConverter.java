package cn.gtmap.helium.client.converter;

/**
 *
 * Author: <a href="mailto:yingxiufeng@gtmap.cn">yingxiufeng</a>
 * Date:  2016/6/18 9:44
 */
public class BooleanValueConverter extends AbstractValueConverter<Boolean> {


    public static final String VALUE_TRUE = "true";
    public static final String VALUE_FALSE = "false";

    public static final String VALUE_ON = "on";
    public static final String VALUE_OFF = "off";

    public static final String VALUE_YES = "yes";
    public static final String VALUE_NO = "no";

    public static final String VALUE_1 = "1";
    public static final String VALUE_0 = "0";

    @Override
    protected Boolean doConvert(String value) {
        if (VALUE_TRUE.equalsIgnoreCase(value)
                || VALUE_ON.equalsIgnoreCase(value)
                || VALUE_YES.equalsIgnoreCase(value)
                || VALUE_1.equals(value)) {
            return true;
        } else if (VALUE_FALSE.equalsIgnoreCase(value)
                || VALUE_OFF.equalsIgnoreCase(value)
                || VALUE_NO.equalsIgnoreCase(value)
                || VALUE_0.equals(value)) {
            return false;
        } else {
            throw new IllegalArgumentException("非法的 boolean 属性值 [" + value + "]");
        }
    }
}
