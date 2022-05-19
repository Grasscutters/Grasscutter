package emu.grasscutter.server.http.dispatch.cn;

import com.github.monkeywie.proxyee.intercept.HttpProxyIntercept;
import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptInitializer;
import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptPipeline;
import emu.grasscutter.server.http.dispatch.cn.intercept.LogUploadIntercept;
import emu.grasscutter.server.http.dispatch.cn.intercept.QueryRegionListIntercept;
import org.reflections.Reflections;

import java.util.Set;

/**
 * @author litht
 * 拦截器初始化
 */
public class InterceptInitializer extends HttpProxyInterceptInitializer {
    @Override
    public void init(HttpProxyInterceptPipeline pipeline) {
        /* 证书下载 */

        pipeline.addLast(new LogUploadIntercept());
        /* 加载包下所有的拦截器 */
        pipeline.addLast(new QueryRegionListIntercept());

//     registerIntercept(pipeline,"emu.grasscutter.server.http.dispatch.cn.intercept");

    }
    public void registerIntercept(HttpProxyInterceptPipeline pipeline,String packageName) {
        Reflections reflections = new Reflections(packageName);
        Set<?> InterceptClasses = reflections.getSubTypesOf(HttpProxyIntercept.class);
        for (Object obj : InterceptClasses) {
            add(pipeline,(Class<? extends HttpProxyIntercept>) obj);
        }
    }
    public void add(HttpProxyInterceptPipeline pipeline, Class<? extends HttpProxyIntercept> handlerClass){
        try {
            pipeline.addLast(handlerClass.getDeclaredConstructor().newInstance());
        } catch (Exception e) {

        }
    }
}
