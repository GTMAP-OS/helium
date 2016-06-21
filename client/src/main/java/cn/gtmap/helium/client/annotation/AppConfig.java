package cn.gtmap.helium.client.annotation;

import java.lang.annotation.*;

/**
 *
 * Author: <a href="mailto:yingxiufeng@gtmap.cn">yingxiufeng</a>
 * Date:  2016/6/17 22:16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Documented
public @interface AppConfig {
    /**
     * 配置属性名称及默认值表达式.
     * <p>样例</p>
     * <pre>
     *     &#064;AppConfig("${config.first}")
     *     private String first;
     *
     *     &#064;AppConfig("${config.second:Default Value}")
     *     private String second;
     *
     *     &#064;AppConfig("${config.third:Method Annotation}")
     *     public void setThird(String third) {
     *          // ----
     *     }
     * </pre>
     *
     * @return 属性名称
     */
    String value();
}
