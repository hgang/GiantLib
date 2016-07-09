package com.android.volley.manager;

/**
 * @author hegang
 * @version 1.0, 2016/2/22 10:34
 */
public class RequestConfig {

    /** 提示-解析JSON错误 */
    public static final String MSG_PARSE_ERROR = "解析错误";

    /** 提示-未知错误 */
    public static final String MSG_UNKNOWN_ERROR = "未知错误";

    /** 提示-服务器错误 */
    public static final String MSG_SERVER_ERROR = "服务器错误";

    /** 提示-网络异常 */
    public static final String MSG_NETWORK_ERROR = "网络异常";

    /** 默认编码：uft-8 */
    public static final String CHARSET_UTF_8 = "UTF-8";

    /** 全局默认是否缓存 */
    static boolean SHOULD_CACHE = true;

    /** 单次请求默认超时时间 */
    static int TIMEOUT_COUNT = 10 * 1000;

    /** 默认重试次数 */
    static int RETRY_TIMES = 1;
}
