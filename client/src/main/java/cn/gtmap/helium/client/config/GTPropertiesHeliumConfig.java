package cn.gtmap.helium.client.config;

import cn.gtmap.helium.client.core.ConfigProvider;
import cn.gtmap.helium.client.core.DefaultConfigProvider;
import cn.gtmap.helium.client.core.HeliumBean;
import cn.gtmap.helium.client.exception.HeliumConfigException;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * gtmap properties config
 * Author: <a href="mailto:yingxiufeng@gtmap.cn">yingxiufeng</a>
 * Date:  2016/6/18 14:45
 */
public class GTPropertiesHeliumConfig extends AbstractWatchedHeliumConfig {


    public static final String EGOV_HOME_FOLDER = "egov-home";
    public static final String DEFAULT_ACTIVE_CONF = "default";
    public static final String PROPERTY_FILE_NAME = "egov.properties";

    private ConfigProvider configProvider;

    public GTPropertiesHeliumConfig(HeliumBean heliumBean) {
        super(heliumBean);
    }

    @SuppressWarnings("Duplicates")
    @Override
    protected void doRefresh() {
        Properties properties = new Properties();
        for (HeliumBean.Resource resource : getHeliumBean().getResources()) {
            try {
                properties.putAll(loadProperties(getPropertyFile(resource)));
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
     * 解析成gtmap路径的配置文件
     * @param resource
     * @return
     */
    private File getPropertyFile(HeliumBean.Resource resource) throws FileNotFoundException {
        File conf = getEgovConfHome();
        if (conf != null) {
            File file = new File(conf.getAbsolutePath() + File.separator + resource.getLocation());
            addResourcePath(Paths.get(file.toURI()));
            return file;
        } else
            throw new FileNotFoundException(resource.getLocation());
    }

    /***
     * 加载配置
     * @param file
     * @return
     */
    private Properties loadProperties(File file){
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            Properties properties = new Properties();
            if (file.getPath().endsWith(".properties")) {
                properties.load(br);
            } else {
                throw new IllegalArgumentException("不支持的文件 [" + file.getPath()
                        + "], PropertiesHeliumConfig 只支持加载 properties 文件");
            }
            return properties;
        } catch (IOException e) {
            throw new IllegalStateException("读取配置文件 [" + file.getPath() + "]错误", e);
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

    /**
     * 获取egov的conf文件夹
     *
     * @param paths
     * @return
     */
    private File getEgovConfHome(final String... paths) {
        File root = determineRootDir(paths);
        File home;
        if (root != null) {
            logger.info("Use egov-home dir:[" + root + "]");
            File activeFile = new File(root, "active.conf");
            String active = DEFAULT_ACTIVE_CONF;
            try {
                active = FileUtils.readFileToString(activeFile);
            } catch (IOException e) {
                logger.info("Read active profile from [" + activeFile.getAbsolutePath() + "] fail,use default");
            }
            home = new File(root, active);
            if (!home.exists()) {
                home = new File(root, DEFAULT_ACTIVE_CONF);
                if (!home.exists()) {
                    logger.error("Egov active conf [" + home.getAbsolutePath() + "] not exsit");
                } else
                    home = new File(home, "conf");
            } else
                home = new File(home, "conf");
            logger.info("Use Custom config home dir:[" + home + "]");
        } else {
            home = new File("/opt/gtis/config/egov/default/conf");
            if (home.exists()) {
                logger.warn("Use compact config home dir:[" + home.getAbsolutePath() + "],please convert to new format");
                return home;
            }
            URL url = null;
            try {
                url = this.getClass().getResource("/META-INF/conf/" + PROPERTY_FILE_NAME);
                if (url != null) {
                    try {
                        home = new File(url.toURI()).getParentFile();
                        logger.info("Custom config home not found,Use classpath config home dir [" + home.getAbsolutePath() + "]");
                    } catch (URISyntaxException ignored) {
                        return null;
                    }
                } else {
                    logger.error("Load config error,config not found");
                    return null;
                }
            } catch (Exception e) {
                logger.error("Load config error,config not found");
                return null;
            }

        }
        return home;
    }

    /**
     * @param paths
     * @return
     */
    private File determineRootDir(final String... paths) {
        File root;
        if (paths != null) {
            for (String path : paths) {
                root = new File(path);
                if (root.exists())
                    return root;
            }
        }
        for (String path : new String[]{
                System.getProperty("EGOV_HOME"),
                System.getenv("EGOV_HOME")
        }) {
            if (path != null) {
                root = new File(path);
                if (root.exists())
                    return root;
            }
        }
        for (String path : new String[]{
                System.getProperty("catalina.base"),
                System.getProperty("catalina.home"),
                System.getProperty("user.home")
        }) {
            if (path != null) {
                root = new File(path, EGOV_HOME_FOLDER);
                if (root.exists())
                    return root;
            }
        }
        return null;
    }
}
