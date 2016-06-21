package cn.gtmap.helium.client.core;

/**
 * {@link cn.gtmap.helium.client.core.HeliumConfig} 上下文对象.
 * Author: <a href="mailto:yingxiufeng@gtmap.cn">yingxiufeng</a>
 * Date:  2016/6/18 8:32
 */
public final class HeliumConfigContext {


    private static HeliumConfig heliumConfig;

    private HeliumConfigContext() {
    }

    /**
     * {@link HeliumConfig} 上下文对象.
     *
     * @return {@link HeliumConfig}
     * @throws IllegalStateException 如果上下文中不存在 {@link HeliumConfig}
     */
    public static HeliumConfig getHeliumConfig() throws IllegalStateException {
        if (heliumConfig == null) {
            throw new IllegalStateException("未找到可用的 HeliumConfig 对象, 请确保 HeliumConfig 已初始化并通过" +
                    " HeliumConfigContext.setSetariaConfig(obj) 更新至上下文中");
        }
        return heliumConfig;
    }

    /**
     * 设置 {@link HeliumConfig} 上下文对象, 一般在应用启动中执行该方法.
     *
     * @param heliumConfig {@link HeliumConfig} 上下文对象
     */
    public static void setHeliumConfig(HeliumConfig heliumConfig) {
        HeliumConfigContext.heliumConfig = heliumConfig;
    }
}
