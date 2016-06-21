package cn.gtmap.helium.client.core;

import java.util.EventListener;

/**
 * 配置监听器接口定义.
 * Author: <a href="mailto:yingxiufeng@gtmap.cn">yingxiufeng</a>
 * Date:  2016/6/18 8:30
 */
public interface HeliumConfigListener extends EventListener {
    /**
     * 监听事件.
     */
    enum Event {
        /**
         * {@link HeliumConfig#init()} 开始事件.
         */
        BEFORE_INIT,
        /**
         * {@link HeliumConfig#init()} 结束事件.
         */
        AFTER_INIT,
        /**
         * {@link HeliumConfig#refresh()} 开始事件.
         */
        BEFORE_REFRESH,
        /**
         * {@link HeliumConfig#refresh()} 结束事件.
         */
        AFTER_REFRESH,
        /**
         * {@link HeliumConfig#destroy()} 开始事件.
         */
        BEFORE_DESTROY,
        /**
         * {@link HeliumConfig#destroy()} 结束事件.
         */
        AFTER_DESTROY
    }

    /**
     * 执行.
     *
     * @param event 事件
     */
    void execute(Event event);
}
