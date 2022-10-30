package emu.grasscutter.server.webapi.command.handlers.player;

import emu.grasscutter.server.webapi.command.handlers.interfaces.CommandRequestHandler;
import emu.grasscutter.server.webapi.genericobjectmgr.GenericObjectPool;

public class PlayerCommandHandlerPool extends GenericObjectPool<CommandRequestHandler>
{
    private static volatile PlayerCommandHandlerPool mgr;
    public static PlayerCommandHandlerPool getInstance()
    {
        if(mgr == null)
        {
            synchronized (PlayerCommandHandlerPool.class)
            {
                if(mgr == null)
                {
                    mgr = new PlayerCommandHandlerPool();
                }
            }
        }

        return mgr;
    }
}
