package cn.gtmap.helium.client.converter;

/**
 * double value converter
 * Author: <a href="mailto:yingxiufeng@gtmap.cn">yingxiufeng</a>
 * Date:  2016/6/18 9:47
 */
public class DoubleValueConverter extends AbstractValueConverter<Double> {
    @Override
    protected Double doConvert(String value) {
        return Double.parseDouble(value);
    }
}