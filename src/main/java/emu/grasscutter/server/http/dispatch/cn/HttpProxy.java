package emu.grasscutter.server.http.dispatch.cn;

import com.github.monkeywie.proxyee.server.HttpProxyServer;
import com.github.monkeywie.proxyee.server.HttpProxyServerConfig;
import static emu.grasscutter.Configuration.*;

/**
 * @author litht
 */
public class HttpProxy {
    private HttpProxyServer server;
    private HttpProxyServerConfig config;


    public HttpProxy() {
        server = new HttpProxyServer();
        config = new HttpProxyServerConfig();
    }

    /**
     * 初始化
     */
    public void init() {
        /* https */
        config.setHandleSsl(true);
        /* 设置serverConfig */
        server.serverConfig(config);
        /* 设置拦截器 */
        server.proxyInterceptInitializer(new InterceptInitializer());
    }

    /**
     * 启动
     */
    public void start() {
        Thread thread=new Thread(()->{
            server.start(PROXY_INFO.bindPort);
        });
        thread.start();

    }
}
