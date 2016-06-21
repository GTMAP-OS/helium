package cn.gtmap.helium.client.config;

import cn.gtmap.helium.client.core.AbstractHeliumConfig;
import cn.gtmap.helium.client.core.HeliumBean;
import cn.gtmap.helium.client.core.HeliumConfig;
import cn.gtmap.helium.client.exception.HeliumConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: <a href="mailto:yingxiufeng@gtmap.cn">yingxiufeng</a>
 * Date:  2016/6/18 8:52
 */
public abstract class AbstractWatchedHeliumConfig extends AbstractHeliumConfig {

    protected final Logger logger = LoggerFactory.getLogger(getClass());


    private HeliumBean heliumBean;

    private Thread fileWatchThread;

    private Set<Path> resourcePaths= new HashSet<Path>();


    protected AbstractWatchedHeliumConfig(HeliumBean heliumBean) {
        Assert.notNull(heliumBean);
        this.heliumBean = heliumBean;
    }

    @Override
    protected void doInit() {
        refresh();

        // watch file change
        fileWatchThread = new Thread(new ConfigFileWatch(this, heliumBean), "heliumconfig-file-watch");
        fileWatchThread.setDaemon(true);
        fileWatchThread.start();
    }

    /***
     *
     * @return
     */
    protected HeliumBean getHeliumBean() {
        return heliumBean;
    }

    protected Set<Path> getResourcePaths() {
        return resourcePaths;
    }

    protected void addResourcePath(Path path){
        resourcePaths.add(path);
    }

    /***
     * 配置文件watch进程
     */
    private class ConfigFileWatch implements Runnable {

        private HeliumConfig heliumConfig;
        private HeliumBean heliumBean;

        private ConfigFileWatch(HeliumConfig heliumConfig, HeliumBean heliumBean) {
            this.heliumConfig = heliumConfig;
            this.heliumBean = heliumBean;
        }

        @Override
        public void run() {
            WatchService watchService;
            try {
                watchService = FileSystems.getDefault().newWatchService();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
            List<String> fileNames = new ArrayList<String>();
            if (getResourcePaths() != null && getResourcePaths().size() > 0) {
                for (Path path : getResourcePaths()) {
                    File file = path.toFile();
                    String fileName = file.getName();
                    if (file.isFile() || (!file.isFile() && !file.isDirectory())) {
                        file = file.getParentFile();
                        path = path.getParent();
                    }
                    if (path == null || !file.isDirectory()) {
                        logger.warn("跳过对文件 [{}] 的监听, 不是一个有效文件夹路径", file.getAbsolutePath());
                        continue;
                    }
                    try {
                        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                                StandardWatchEventKinds.ENTRY_MODIFY);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    fileNames.add(fileName);
                }

            }else{
                for (HeliumBean.Resource resource : heliumBean.getResources()) {
                    try {
                        URL url= ResourceUtils.getURL(resource.getLocation());
                        if (ResourceUtils.isJarURL(url)) {
                            url = ResourceUtils.extractJarFileURL(url);
                        }
                        Path path = Paths.get(url.toURI());
                        File file = path.toFile();
                        String fileName = file.getName();
                        if (file.isFile() || (!file.isFile() && !file.isDirectory())) {
                            file = file.getParentFile();
                            path = path.getParent();
                        }
                        if (path == null || !file.isDirectory()) {
                            logger.warn("跳过对文件 [{}] 的监听, 不是一个有效文件夹路径", resource.getLocation());
                            continue;
                        }
                        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                                StandardWatchEventKinds.ENTRY_MODIFY);
                        fileNames.add(fileName);

                    } catch (FileNotFoundException e) {
                        if(!resource.isIgnoreNotFound())
                            throw new HeliumConfigException(e);
                    }  catch (Exception e) {
                        throw new HeliumConfigException(e);
                    }
                }
            }

            for (; ; ) {
                WatchKey watchKey = null;
                try {
                    watchKey = watchService.take();
                } catch (InterruptedException e) {
                    //
                }

                if (watchKey == null) {
                    continue;
                }

                List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
                for (WatchEvent<?> watchEvent : watchEvents) {
                    if (fileNames.contains(watchEvent.context().toString())) {
                        heliumConfig.refresh();
                        break;
                    }
                }
                watchKey.reset();
            }
        }
    }
}
