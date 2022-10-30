package emu.grasscutter.server.webapi.player.handlers.interfaces;

import emu.grasscutter.server.webapi.genericobjectmgr.GenericObjectPool;

public class PlayerInfoRequestHandlerPool extends GenericObjectPool<PlayerInfoRequestHandler>
{
    static volatile PlayerInfoRequestHandlerPool mgr;
    public static PlayerInfoRequestHandlerPool getInstance()
    {
        if(mgr == null)
        {
            synchronized (PlayerInfoRequestHandlerPool.class)
            {
                if(mgr == null)
                {
                    mgr = new PlayerInfoRequestHandlerPool();
                }
            }
        }

        return mgr;
    }
}
