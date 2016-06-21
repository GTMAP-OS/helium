package cn.gtmap.helium.client.spring;

import cn.gtmap.helium.client.annotation.AppConfig;
import cn.gtmap.helium.client.core.HeliumConfig;
import cn.gtmap.helium.client.core.HeliumConfigContext;
import cn.gtmap.helium.client.core.HeliumConfigListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * {@link cn.gtmap.helium.client.annotation.AppConfig} 注解处理器.
 * Author: <a href="mailto:yingxiufeng@gtmap.cn">yingxiufeng</a>
 * Date:  2016/6/17 22:17
 */
public class AppConfigBeanPostProcessor implements BeanPostProcessor,PriorityOrdered,
        ApplicationListener<ApplicationContextEvent> {

    private HeliumConfigListener heliumConfigListener = new RefreshedHeliumConfigListener();
    private ConfigurableListableBeanFactory beanFactory;

    /**
     * 通过 {@link org.springframework.beans.factory.ListableBeanFactory} 创建处理器实例.
     *
     * @param beanFactory {@link ConfigurableListableBeanFactory}
     */
    AppConfigBeanPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(final Object bean, String beanName) throws BeansException {
        doPostProcessInitialization(bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private void doPostProcessInitialization(final Object bean) {
        Class<?> clazz = bean.getClass();
        ReflectionUtils.doWithMethods(clazz, new ReflectionUtils.MethodCallback() {

            @Override
            public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
                AppConfig configValue = AnnotationUtils.getAnnotation(method, AppConfig.class);
                if (configValue == null) {
                    return;
                }
                if (method.getParameterTypes().length != 1) {
                    throw new IllegalStateException("@AppConfig 注解只能用于 1 个参数的方法");
                }
                String str = beanFactory.resolveEmbeddedValue(configValue.value());
                Object newValue = beanFactory.getTypeConverter().convertIfNecessary(str, method.getParameterTypes()[0]);
                ReflectionUtils.invokeMethod(method, bean, newValue);
            }
        });

        ReflectionUtils.doWithFields(clazz, new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                AppConfig configValue = AnnotationUtils.getAnnotation(field, AppConfig.class);
                if (configValue == null) {
                    return;
                }
                String str = beanFactory.resolveEmbeddedValue(configValue.value());
                Object newValue = beanFactory.getTypeConverter().convertIfNecessary(str, field.getType());

                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, bean, newValue);
            }
        });
    }

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            HeliumConfig heliumConfig = HeliumConfigContext.getHeliumConfig();
            heliumConfig.addListener(heliumConfigListener);
        } else if (event instanceof ContextClosedEvent) {
            HeliumConfig heliumConfig = HeliumConfigContext.getHeliumConfig();
            heliumConfig.removeListener(heliumConfigListener);
        }
    }

    /**
     * {@link cn.gtmap.helium.client.core.HeliumConfig} 配置刷新监听器, 用于刷新 Spring 所管理对象的配置属性值.
     */
    private class RefreshedHeliumConfigListener implements HeliumConfigListener {
        @Override
        public void execute(Event event) {
            // 配置更新完成后自动刷新 Spring 所管理的 Bean 对象
            if (event == Event.AFTER_REFRESH) {
                for (String beanName : beanFactory.getBeanDefinitionNames()) {
                    doPostProcessInitialization(beanFactory.getBean(beanName));
                }
            }
        }
    }
}
