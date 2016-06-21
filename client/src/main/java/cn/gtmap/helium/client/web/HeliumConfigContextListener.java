package cn.gtmap.helium.client.web;

import cn.gtmap.helium.client.core.HeliumBean;
import cn.gtmap.helium.client.core.HeliumConfig;
import cn.gtmap.helium.client.core.HeliumConfigContext;
import cn.gtmap.helium.client.config.PropertiesHeliumConfig;
import cn.gtmap.helium.client.exception.HeliumConfigException;
import cn.gtmap.helium.client.util.ObjectMapperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;

/**
 * Web 应用监听器初始化 {@link cn.gtmap.helium.client.core.HeliumConfig}.
 * Author: <a href="mailto:yingxiufeng@gtmap.cn">yingxiufeng</a>
 * Date:  2016/6/18 9:33
 */
public class HeliumConfigContextListener implements ServletContextListener {

    /**
     * 默认的配置文件路径
     */
    private static final String DEFAULT_HELIUM_CONFIG_LOCATION="classpath:helium.json";

    /**
     * {@link cn.gtmap.helium.client.core.HeliumConfig} 实现类
     */
    public static final String HELIUM_CONFIG_IMPLEMENTATION = "helium.config.implementation";

    /**
     * 自定义配置路径(可选)
     */
    public static final String HELIUM_CONFIG_LOCATION = "helium.config.location";

    private static final Logger logger= LoggerFactory.getLogger(HeliumConfigContextListener.class);

    private HeliumConfig heliumConfig;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        ServletContext sc = servletContextEvent.getServletContext();

        String configClass = sc.getInitParameter(HELIUM_CONFIG_IMPLEMENTATION);
        heliumConfig = createHeliumConfig(configClass, createHeliumBean(sc));

        // 初始化配置
        logger.debug("初始化heliumConfig...");
        heliumConfig.init();

        // 设置上下文变量
        HeliumConfigContext.setHeliumConfig(heliumConfig);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if(heliumConfig!=null)
        {
            logger.debug("销毁 heliumConfig...");
            heliumConfig.destroy();
        }
    }

    /***
     * 初始化需要watch的配置文件对象
     * @param sc
     * @return
     */
    private HeliumBean createHeliumBean(ServletContext sc){
        String location=sc.getInitParameter(HELIUM_CONFIG_LOCATION);
        if (location == null || location.isEmpty()) {
            location = DEFAULT_HELIUM_CONFIG_LOCATION;
        }
        File file = null;
        try {
            file = ResourceUtils.getFile(location);
            return ObjectMapperUtils.readValue(file, HeliumBean.class);
        } catch (FileNotFoundException e) {
            throw new HeliumConfigException("未找到文件 [" + location + "]", e);
        }
    }

    /***
     * 实例化 helium config
     * 如果未配置 则默认是 {@link cn.gtmap.helium.client.config.PropertiesHeliumConfig }
     * @param className
     * @param heliumBean
     * @return
     */
    private HeliumConfig createHeliumConfig(String className, HeliumBean heliumBean) {
        try {
            Class<?> clazz = (className == null||className.isEmpty()) ? PropertiesHeliumConfig.class : Class.forName(className);
            Constructor<?> constructor = clazz.getConstructor(new Class[]{HeliumBean.class});
            return (HeliumConfig) constructor.newInstance(heliumBean);
        } catch (Exception e) {
            throw new HeliumConfigException(e);
        }
    }
}
