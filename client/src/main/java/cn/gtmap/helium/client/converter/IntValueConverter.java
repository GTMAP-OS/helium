package cn.gtmap.helium.client.converter;

/**
 * Author: <a href="mailto:yingxiufeng@gtmap.cn">yingxiufeng</a>
 * Date:  2016/6/18 9:48
 */
public class IntValueConverter extends AbstractValueConverter<Integer> {

    @Override
    protected Integer doConvert(String value) {
        return Integer.parseInt(value);
    }
}
