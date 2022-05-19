package emu.grasscutter.server.http.dispatch.cn.intercept;

import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptPipeline;
import com.github.monkeywie.proxyee.intercept.common.FullResponseIntercept;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import emu.grasscutter.net.proto.QueryCurrRegionHttpRspOuterClass.QueryCurrRegionHttpRsp;
import emu.grasscutter.net.proto.RegionInfoOuterClass.RegionInfo;
import emu.grasscutter.server.http.dispatch.cn.ProxyConstant;
import emu.grasscutter.utils.Crypto;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static emu.grasscutter.Configuration.*;

/**
 * 获取当前区域的服务器
 * 测试例子（get）
 * https://cngfdispatch.yuanshen.com/query_cur_region?version=CNRELWin2.6.0&lang=2&platform=3&binary=1&time=182&channel_id=1&sub_channel_id=2&account_type=1&dispatchSeed=227fa47da8ce7dca
 *
 * @author litht
 * @date 2022/05/19
 */
public class QueryCurRegionIntercept extends FullResponseIntercept {
    @Override
    public boolean match(HttpRequest httpRequest, HttpResponse httpResponse, HttpProxyInterceptPipeline pipeline) {
        if (pipeline.getRequestProto().getHost().contains(ProxyConstant.CNGFDISPATCH_HOST) &&
                httpRequest.uri().contains(ProxyConstant.QUERY_CUR_REGION_PATH)) {
            return true;
        }
        return false;
    }

    @Override
    public void handleResponse(HttpRequest httpRequest, FullHttpResponse httpResponse, HttpProxyInterceptPipeline pipeline) {
        try {
            byte[] decode = Base64.getDecoder().decode(httpResponse.content().toString(StandardCharsets.UTF_8));
            QueryCurrRegionHttpRsp cr = QueryCurrRegionHttpRsp.parseFrom(decode);
            RegionInfo.Builder riBuilder = cr.getRegionInfo().toBuilder();
            Region cn = new Region("cn", DISPATCH_INFO.defaultName,
                    lr(GAME_INFO.accessAddress, GAME_INFO.bindAddress),
                    lr(GAME_INFO.accessPort, GAME_INFO.bindPort));
            var regionInfo = riBuilder
                    .setGateserverIp(cn.Ip).setGateserverPort(cn.Port)
                    .setSecretKey(ByteString.copyFrom(Crypto.DISPATCH_SEED))
                    .build();
            QueryCurrRegionHttpRsp result = cr.toBuilder().setRegionInfo(regionInfo).build();
            byte[] base64CurRegion = Base64.getEncoder().encode(result.toByteArray());
            httpResponse.content().clear();
            httpResponse.content().writeBytes(base64CurRegion);

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}
