package emu.grasscutter.server.webapi.command;

import emu.grasscutter.server.webapi.command.handlers.interfaces.CommandRequestHandler;
import emu.grasscutter.server.webapi.genericobjectmgr.GenericObjectPool;

public class CommandRequestHandlerPool extends GenericObjectPool<CommandRequestHandler>
{
    private static volatile CommandRequestHandlerPool mgr;
    public static CommandRequestHandlerPool getInstance()
    {
        if(mgr == null)
        {
            synchronized (CommandRequestHandlerPool.class)
            {
                if(mgr == null)
                {
                    mgr = new CommandRequestHandlerPool();
                }
            }
        }

        return mgr;
    }
}
