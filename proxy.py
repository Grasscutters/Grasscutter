##
#
#   Copyright (C) 2002-2022 MlgmXyysd All Rights Reserved.
#
##

##
#
#   Genshin Impact script for mitmproxy
#
#   https://github.com/MlgmXyysd/
#
#   *Original fiddler script from https://github.lunatic.moe/fiddlerscript
#
#   Environment requirement:
#     - mitmdump from mitmproxy
#
#   @author MlgmXyysd
#   @version 1.1
#
##

from mitmproxy import http
from proxy_config import USE_SSL
from proxy_config import REMOTE_HOST
from proxy_config import REMOTE_PORT

class MlgmXyysd_Genshin_Impact_Proxy:

    LIST_DOMAINS = [
        "api-os-takumi.mihoyo.com",
        "hk4e-api-os-static.mihoyo.com",
        "hk4e-sdk-os.mihoyo.com",
        "dispatchosglobal.yuanshen.com",
        "osusadispatch.yuanshen.com",
        "account.mihoyo.com",
        "log-upload-os.mihoyo.com",
        "dispatchcntest.yuanshen.com",
        "devlog-upload.mihoyo.com",
        "webstatic.mihoyo.com",
        "log-upload.mihoyo.com",
        "hk4e-sdk.mihoyo.com",
        "api-beta-sdk.mihoyo.com",
        "api-beta-sdk-os.mihoyo.com",
        "cnbeta01dispatch.yuanshen.com",
        "dispatchcnglobal.yuanshen.com",
        "cnbeta02dispatch.yuanshen.com",
        "sdk-os-static.mihoyo.com",
        "webstatic-sea.mihoyo.com",
        "webstatic-sea.hoyoverse.com",
        "hk4e-sdk-os-static.hoyoverse.com",
        "sdk-os-static.hoyoverse.com",
        "api-account-os.hoyoverse.com",
        "hk4e-sdk-os.hoyoverse.com",
        "overseauspider.yuanshen.com",
        "gameapi-account.mihoyo.com",
        "minor-api.mihoyo.com",
        "public-data-api.mihoyo.com",
        "uspider.yuanshen.com",
        "sdk-static.mihoyo.com",
        "abtest-api-data-sg.hoyoverse.com",
        "log-upload-os.hoyoverse.com"
    ]

    def request(self, flow: http.HTTPFlow) -> None:
        if flow.request.host in self.LIST_DOMAINS:
            if USE_SSL:
                flow.request.scheme = "https"
            else:
                flow.request.scheme = "http"
            flow.request.host = REMOTE_HOST
            flow.request.port = REMOTE_PORT

addons = [
	MlgmXyysd_Genshin_Impact_Proxy()
]