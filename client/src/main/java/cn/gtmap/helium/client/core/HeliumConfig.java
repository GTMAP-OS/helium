package cn.gtmap.helium.client.core;

/**
 * Author: <a href="mailto:yingxiufeng@gtmap.cn">yingxiufeng</a>
 * Date:  2016/6/17 22:35
 */
public interface HeliumConfig {
    /**
     * 初始化配置信息.
     */
    void init();

    /**
     * 刷新配置信息.
     */
    void refresh();

    /**
     * 销毁配置信息.
     */
    void destroy();

    /**
     * 返回配置提供者接口.
     *
     * @return {@link ConfigProvider}
     */
    ConfigProvider getConfigProvider();

    /**
     * 添加配置监听器.
     *
     * @param listener 监听器
     */
    void addListener(HeliumConfigListener listener);

    /**
     * 删除配置监听器. 返回删除成功或失败.
     *
     * @param listener 监听器
     * @return true/false
     */
    boolean removeListener(HeliumConfigListener listener);
}
