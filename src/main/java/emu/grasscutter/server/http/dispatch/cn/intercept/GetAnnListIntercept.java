package emu.grasscutter.server.http.dispatch.cn.intercept;

import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptPipeline;
import com.github.monkeywie.proxyee.intercept.common.FullRequestIntercept;
import emu.grasscutter.server.http.dispatch.cn.ProxyConstant;
import emu.grasscutter.server.http.dispatch.cn.util.ProxyUtil;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * annlist
 *
 * @author litht
 * @date 2022/05/20
 * https://hk4e-api.mihoyo.com/common/hk4e_cn/announcement/api/getAnnList?game=hk4e&game_biz=hk4e_cn&lang=zh-cn&auth_appid=announcement&authkey_ver=1&bundle_id=hk4e_cn&channel_id=1&level=59&platform=pc&region=cn_gf01&sdk_presentation_style=fullscreen&sdk_screen_transparent=true&sign_type=2&uid=133837887
 */
public class GetAnnListIntercept extends FullRequestIntercept{

    @Override
    public boolean match(HttpRequest httpRequest, HttpProxyInterceptPipeline pipeline) {
        if(pipeline.getRequestProto().getHost().contains(ProxyConstant.HK4E_API_HOST)){
            if(httpRequest.uri().contains(ProxyConstant.COMMON_HK4E_CN_ANNOUNCEMENT_API_GETANNLIST_PATH)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void handleRequest(FullHttpRequest httpRequest, HttpProxyInterceptPipeline pipeline) {
        String uri = httpRequest.uri();
        String uid = ProxyUtil.getParamByUrl(uri, "uid");
        if(uid==null)
        {
             Map param = new HashMap<>();
             param.put("uid",12345679);
             uri= ProxyUtil.appendUrl(uri, param);
        }
        else {
            ProxyUtil.replaceUrlParam(uri,"uid","123456789");
        }
        httpRequest.setUri(uri);
    }
}
