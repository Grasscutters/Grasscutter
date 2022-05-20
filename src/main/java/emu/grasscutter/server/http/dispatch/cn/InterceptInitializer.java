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
 * https://github.com/litht/Grasscutter/tree/dev-cnServer
 */
public class InterceptInitializer extends HttpProxyInterceptInitializer {
    @Override
    public void init(HttpProxyInterceptPipeline pipeline) {
        /* cert download */
        pipeline.addLast(new CertDownIntercept());

        /* query_region_list */
        pipeline.addLast(new QueryRegionListIntercept());

        /* log */
        pipeline.addLast(new LogUploadIntercept());

        /*  captcha */
        pipeline.addLast(new AccountRiskyApiCheckIntercept());

        /* Username & Password login (from client). */
        pipeline.addLast(new MdkShieldApiLoginIntercept());

        /* Combo token login (from session key). */
        pipeline.addLast(new LoginV2Intercept());

        /* Cached token login (from registry) */
        pipeline.addLast(new MdkShieldApiVerifyIntercept());

        /* query_cur_region */
        pipeline.addLast(new QueryCurRegionIntercept());

        pipeline.addLast(new GetAnnListIntercept());
        pipeline.addLast(new GetAnnContentIntercept());
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
