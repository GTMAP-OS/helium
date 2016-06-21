package cn.gtmap.helium.client.converter;

/**
 *
 * Author: <a href="mailto:yingxiufeng@gtmap.cn">yingxiufeng</a>
 * Date:  2016/6/18 9:49
 */
public class FloatValueConverter extends AbstractValueConverter<Float> {
    @Override
    protected Float doConvert(String value) {
        return Float.parseFloat(value);
    }
}
