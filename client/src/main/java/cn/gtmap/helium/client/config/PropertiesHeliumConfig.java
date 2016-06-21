package cn.gtmap.helium.client.config;

import cn.gtmap.helium.client.config.AbstractWatchedHeliumConfig;
import cn.gtmap.helium.client.core.ConfigProvider;
import cn.gtmap.helium.client.core.DefaultConfigProvider;
import cn.gtmap.helium.client.core.HeliumBean;
import cn.gtmap.helium.client.exception.HeliumConfigException;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Author: <a href="mailto:yingxiufeng@gtmap.cn">yingxiufeng</a>
 * Date:  2016/6/18 10:07
 */
public class PropertiesHeliumConfig extends AbstractWatchedHeliumConfig {

    private ConfigProvider configProvider;

    public PropertiesHeliumConfig(HeliumBean heliumBean) {
        super(heliumBean);
    }

    @SuppressWarnings("Duplicates")
    @Override
    protected void doRefresh() {
        Properties properties = new Properties();
        for (HeliumBean.Resource resource : getHeliumBean().getResources()) {
            try {
                URL url = ResourceUtils.getURL(resource.getLocation());
                properties.putAll(loadProperties(url));
            } catch (Exception e) {
                if (resource.isIgnoreNotFound()) {
                    logger.info("文件 [{}] 不存在, 已忽略", resource.getLocation());
                } else {
                    if (e instanceof FileNotFoundException) {
                        throw new HeliumConfigException("未发现配置文件 [" + resource.getLocation() + "]", e);
                    }
                    throw new HeliumConfigException("加载配置文件 [" + resource.getLocation() + "] 错误", e);
                }
            }
        }
        configProvider = new DefaultConfigProvider(properties);
    }

    @Override
    protected void doDestroy() {

    }

    @Override
    public ConfigProvider getConfigProvider() {
        return configProvider;
    }
    /***
     * 加载配置
     * @param url
     * @return
     */
    private Properties loadProperties(URL url) {
        InputStream inputStream = null;
        try {
            inputStream = url.openStream();
            Properties properties = new Properties();

            if (url.getPath().endsWith(".properties")) {
                properties.load(inputStream);
            } else if (url.getPath().endsWith(".xml")) {
                properties.loadFromXML(inputStream);
            } else {
                throw new IllegalArgumentException("不支持的文件 [" + url
                        + "], PropertiesHeliumConfig 只支持加载 [*.properties, *.xml] 的 properties 文件");
            }
            return properties;
        } catch (IOException e) {
            throw new IllegalStateException("读取配置文件 [" + url + "]错误", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }
}
