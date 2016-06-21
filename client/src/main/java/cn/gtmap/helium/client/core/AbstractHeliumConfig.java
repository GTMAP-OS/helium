package cn.gtmap.helium.client.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {@link cn.gtmap.helium.client.core.HeliumConfig} 抽象实现
 * Author: <a href="mailto:yingxiufeng@gtmap.cn">yingxiufeng</a>
 * Date:  2016/6/18 8:37
 */
public abstract class AbstractHeliumConfig implements HeliumConfig {

    private List<HeliumConfigListener> listeners=new ArrayList<HeliumConfigListener>();

    private Map<String, String> configParameters;

    protected AbstractHeliumConfig() {
    }

    protected AbstractHeliumConfig(Map<String, String> configParameters) {
        this.configParameters = configParameters;
    }

    @Override
    public final void init() {
        fireEvent(HeliumConfigListener.Event.BEFORE_INIT);
        doInit();
        fireEvent(HeliumConfigListener.Event.AFTER_INIT);
    }

    @Override
    public final void refresh() {
        fireEvent(HeliumConfigListener.Event.BEFORE_REFRESH);
        doRefresh();
        fireEvent(HeliumConfigListener.Event.AFTER_REFRESH);
    }

    @Override
    public final void destroy() {
        fireEvent(HeliumConfigListener.Event.BEFORE_DESTROY);
        doDestroy();
        fireEvent(HeliumConfigListener.Event.AFTER_DESTROY);
    }

    @Override
    public ConfigProvider getConfigProvider() {
        return null;
    }

    @Override
    public void addListener(HeliumConfigListener listener) {
        listeners.add(listener);
    }

    @Override
    public boolean removeListener(HeliumConfigListener listener) {
        return listeners.remove(listener);
    }

    /**
     * init.
     */
    protected abstract void doInit();

    /**
     * refresh.
     */
    protected abstract void doRefresh();

    /**
     * destroy.
     */
    protected abstract void doDestroy();

    /***
     * 通过名称获取初始化参数
     * @param name
     * @return
     * @throws IllegalArgumentException
     */
    public String getConfigParameter(String name) throws IllegalArgumentException {
        String value = configParameters.get(name);
        if (value == null) {
            throw new IllegalArgumentException("未发现配置参数 [" + name + "]");
        }
        return value;
    }
    /***
     * fire event
     * @param event
     */
    private void fireEvent(HeliumConfigListener.Event event){
        for (HeliumConfigListener listener : listeners) {
            listener.execute(event);
        }
    }
}
