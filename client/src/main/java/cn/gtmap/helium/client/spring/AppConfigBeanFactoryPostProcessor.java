package cn.gtmap.helium.client.spring;

import cn.gtmap.helium.client.AppConfigs;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

/**
 * 注解处理器
 * Author: <a href="mailto:yingxiufeng@gtmap.cn">yingxiufeng</a>
 * Date:  2016/6/19 14:38
 */
public class AppConfigBeanFactoryPostProcessor implements BeanDefinitionRegistryPostProcessor, PriorityOrdered {

    /**
     * 是否已经注册 {@link cn.gtmap.helium.client.spring.AppConfigBeanPostProcessor} 处理器.
     */
    private boolean done;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
            throws BeansException {
        if (!done) {
            postProcess(beanFactory);
        }
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        postProcess((ConfigurableListableBeanFactory) registry);
        done = true;
    }

    public void postProcess(ConfigurableListableBeanFactory beanFactory) {
        // 注册 Spring 属性配置
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        MutablePropertySources mutablePropertySources = new MutablePropertySources();
        mutablePropertySources.addLast(new PropertySource<String>(AppConfigs.class.getName()) {
            @Override
            public String getProperty(String name) {
                return AppConfigs.getStringProperty(name);
            }
        });
        configurer.setPropertySources(mutablePropertySources);
        configurer.postProcessBeanFactory(beanFactory);

        /*
         * 注册 @AppConfig 处理器. AppConfiggValueBeanPostProcessor 实现了 ApplicationListener 接口, 不能使用
         * beanFactory.addBeanPostProcessor() 来注册实例.
         */
        beanFactory.registerSingleton(AppConfigBeanPostProcessor.class.getName(),
                new AppConfigBeanPostProcessor(beanFactory));
    }

    @Override
    public int getOrder() {
        return 0;
    }


}
