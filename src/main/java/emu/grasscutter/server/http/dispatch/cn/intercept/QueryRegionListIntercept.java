package emu.grasscutter.server.http.dispatch.cn.intercept;

import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptPipeline;
import com.github.monkeywie.proxyee.intercept.common.FullResponseIntercept;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import emu.grasscutter.net.proto.QueryRegionListHttpRspOuterClass.QueryRegionListHttpRsp;
import emu.grasscutter.server.http.dispatch.cn.ProxyConstant;
import emu.grasscutter.utils.Crypto;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

import java.nio.charset.StandardCharsets;
import java.util.Base64;



/**
 * @author litht
 * 处理query_region_list请求
 * @date 2022/05/18
 * 请求例子(get)
 * https://dispatchcnglobal.yuanshen.com/query_region_list?version=CNRELWin2.6.0&lang=2&platform=3&binary=1&time=302&channel_id=1&sub_channel_id=2
 */

public class QueryRegionListIntercept extends FullResponseIntercept {
    @Override
    public boolean match(HttpRequest httpRequest, HttpResponse httpResponse, HttpProxyInterceptPipeline pipeline) {
        if (pipeline.getRequestProto().getHost().equals(ProxyConstant.DISPATCH_CN_GLOBAL_HOST) &&
                httpRequest.uri().indexOf(ProxyConstant.QUERY_REGION_LIST_PATH) != -1) {
            return true;
        }
        return false;
    }

    @Override
    public void handleResponse(HttpRequest httpRequest, FullHttpResponse httpResponse, HttpProxyInterceptPipeline pipeline) {

        try {
            /* 解密数据 */
            byte[] decode = Base64.getDecoder().decode(httpResponse.content().toString(StandardCharsets.UTF_8));
            QueryRegionListHttpRsp rl = QueryRegionListHttpRsp.parseFrom(decode);

            /* 替换dispatch密钥，换成我们的密钥 */
            byte[] customConfig = "{\"sdkenv\":\"2\",\"checkdevice\":\"false\",\"loadPatch\":\"false\",\"showexception\":\"false\",\"regionConfig\":\"pm|fk|add\",\"downloadMode\":\"0\"}".getBytes();
            Crypto.xor(customConfig, Crypto.DISPATCH_KEY);
            QueryRegionListHttpRsp result = rl.toBuilder().setEnableLoginPc(true)
                    .setClientCustomConfigEncrypted(ByteString.copyFrom(customConfig))
                    .setClientSecretKey(ByteString.copyFrom(Crypto.DISPATCH_SEED)).build();
            httpResponse.content().clear();
            httpResponse.content().writeBytes(Base64.getEncoder().encode(result.toByteArray()));
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}
