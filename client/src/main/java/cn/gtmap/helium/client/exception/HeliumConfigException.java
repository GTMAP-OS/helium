package cn.gtmap.helium.client.exception;

/**
 * 异常类
 * Author: <a href="mailto:yingxiufeng@gtmap.cn">yingxiufeng</a>
 * Date:  2016/6/17 22:27
 */
public class HeliumConfigException extends RuntimeException {
    /**
     * @param message
     */
    public HeliumConfigException(String message) {
        super(message);
    }

    /**
     * 通过错误详细信息与目标异常创建 {@code HeliumConfigException}.
     *
     * @param message 详细信息
     * @param cause   目标异常
     */
    public HeliumConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 通过目标异常创建 {@code HeliumConfigException}.
     *
     * @param cause 目标异常
     */
    public HeliumConfigException(Throwable cause) {
        super(cause);
    }
}
