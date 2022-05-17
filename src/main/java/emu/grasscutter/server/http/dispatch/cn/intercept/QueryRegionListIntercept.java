package emu.grasscutter.server.http.dispatch.cn.intercept;

import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptPipeline;
import com.github.monkeywie.proxyee.intercept.common.FullResponseIntercept;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

import java.nio.charset.StandardCharsets;

import static emu.grasscutter.server.http.dispatch.cn.ProxyConstant.*;

/**
 * @author Admin
 * 处理query_region_list请求
 */
public class QueryRegionListIntercept extends FullResponseIntercept {
    @Override
    public boolean match(HttpRequest httpRequest, HttpResponse httpResponse, HttpProxyInterceptPipeline pipeline) {
        if (pipeline.getRequestProto().getHost().equals(DISPATCH_CN_GLOBAL_HOST) &&
                httpRequest.uri().indexOf(QUERY_REGION_LIST_PATH)!=-1) {
            return true;
        }
        return false;
    }

    @Override
    public void handleResponse(HttpRequest httpRequest, FullHttpResponse httpResponse, HttpProxyInterceptPipeline pipeline) {
        System.out.println(httpResponse.content().toString(StandardCharsets.UTF_8));
    }
}
