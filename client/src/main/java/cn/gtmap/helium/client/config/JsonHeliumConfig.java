package cn.gtmap.helium.client.config;

import cn.gtmap.helium.client.config.AbstractWatchedHeliumConfig;
import cn.gtmap.helium.client.core.ConfigProvider;
import cn.gtmap.helium.client.core.DefaultConfigProvider;
import cn.gtmap.helium.client.core.HeliumBean;
import cn.gtmap.helium.client.exception.HeliumConfigException;
import cn.gtmap.helium.client.model.ConfigModel;
import cn.gtmap.helium.client.util.ObjectMapperUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * JSON配置文件实现类
 * Author: <a href="mailto:yingxiufeng@gtmap.cn">yingxiufeng</a>
 * Date:  2016/6/18 11:17
 */
public class JsonHeliumConfig extends AbstractWatchedHeliumConfig {

    private ConfigProvider configProvider;

    public JsonHeliumConfig(HeliumBean heliumBean) {
        super(heliumBean);
    }

    /***
     * model to java properties
     * @param ConfigModels
     * @return
     */
    protected Properties toProperties(Collection<ConfigModel> ConfigModels) {
        Properties properties = new Properties();
        for (ConfigModel ConfigModel : ConfigModels) {
            properties.setProperty(ConfigModel.getKey(), ConfigModel.getValue());
        }
        return properties;
    }

    @SuppressWarnings("Duplicates")
    @Override
    protected void doRefresh() {

        Set<ConfigModel> ConfigModelSet = new HashSet<ConfigModel>();

        List<HeliumBean.Resource> resources = getHeliumBean().getResources();

        for (int i = resources.size() - 1; i >= 0; i--) {
            HeliumBean.Resource resource = resources.get(i);
            try {
                File file = ResourceUtils.getFile(resource.getLocation());
                ConfigModel[] ConfigModelArray = ObjectMapperUtils.readValue(file, ConfigModel[].class);

                ConfigModelSet.addAll(Arrays.asList(ConfigModelArray));
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

        configProvider = new DefaultConfigProvider(toProperties(ConfigModelSet));
    }

    @Override
    protected void doDestroy() {

    }

    @Override
    public ConfigProvider getConfigProvider() {
        return configProvider;
    }
}
