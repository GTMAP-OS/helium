package cn.gtmap.helium.client.converter;

/**
 * Author: <a href="mailto:yingxiufeng@gtmap.cn">yingxiufeng</a>
 * Date:  2016/6/18 9:50
 */
public class LongValueConverter extends AbstractValueConverter<Long> {
    @Override
    protected Long doConvert(String value) {
        return Long.parseLong(value);
    }
}
