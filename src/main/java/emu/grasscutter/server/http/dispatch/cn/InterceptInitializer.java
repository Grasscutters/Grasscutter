package emu.grasscutter.server.http.dispatch.cn;

import com.github.monkeywie.proxyee.intercept.HttpProxyIntercept;
import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptInitializer;
import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptPipeline;
import com.github.monkeywie.proxyee.intercept.common.CertDownIntercept;
import emu.grasscutter.server.http.dispatch.cn.intercept.*;
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
        pipeline.addLast(new CertDownIntercept());

        /* query_region_list */
        pipeline.addLast(new QueryRegionListIntercept());

        /* 日志上传 */
        pipeline.addLast(new LogUploadIntercept());

        /* 验证码 */
        pipeline.addLast(new AccountRiskyApiCheckIntercept());

        /* 用户名密码登录 */
        pipeline.addLast(new MdkShieldApiLoginIntercept());

        /* token登录(session) */
        pipeline.addLast(new LoginV2Intercept());

        /* token登录(registry) */
        pipeline.addLast(new MdkShieldApiVerifyIntercept());

        /* query_cur_region */
        pipeline.addLast(new QueryCurRegionIntercept());
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
