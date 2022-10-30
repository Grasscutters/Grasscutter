package emu.grasscutter.server.webapi;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.server.webapi.command.CommandDispatcher;
import emu.grasscutter.server.webapi.command.CommandRequestHandlerPool;
import emu.grasscutter.server.webapi.command.handlers.GrasscutterCommandHandler;
import emu.grasscutter.server.webapi.command.handlers.MultipleCommandsHandler;
import emu.grasscutter.server.webapi.command.handlers.player.PlayerCommandHandler;
import emu.grasscutter.server.webapi.command.handlers.player.PlayerCommandHandlerPool;
import emu.grasscutter.server.webapi.command.handlers.player.RechargeMoonCardCommand;
import emu.grasscutter.server.webapi.dispatcher.DispatcherPool;
import emu.grasscutter.server.webapi.player.PlayerInfoRequestDispatcher;
import emu.grasscutter.server.webapi.player.handlers.PlayerGodModeInfoHandler;
import emu.grasscutter.server.webapi.player.handlers.PlayerListRequestHandler;
import emu.grasscutter.server.webapi.player.handlers.PositionInfoRequestHandler;
import emu.grasscutter.server.webapi.player.handlers.interfaces.PlayerInfoRequestHandlerPool;

public class HsManWebApi {
    private HsManWebApi()
    {
    }

    static void initApiDispatcher()
    {
        var mgr = DispatcherPool.getInstance();
        mgr.register(PlayerInfoRequestDispatcher.class);
        mgr.register(CommandDispatcher.class);
    }

    static void initPlayerInfoRequestHandler()
    {
        var mgr = PlayerInfoRequestHandlerPool.getInstance();
        mgr.register(PositionInfoRequestHandler.class);
        mgr.register(PlayerGodModeInfoHandler.class);
        mgr.register(PlayerListRequestHandler.class);
    }

    static void initCommandsHandlers()
    {
        var mgr = CommandRequestHandlerPool.getInstance();
        mgr.register(PlayerCommandHandler.class);
        mgr.register(GrasscutterCommandHandler.class);
        mgr.register(MultipleCommandsHandler.class);
    }

    static void initPlayerCommand()
    {
        var mgr = PlayerCommandHandlerPool.getInstance();
        mgr.register(RechargeMoonCardCommand.class);
    }

    static void initRouter()
    {
        Grasscutter.getHttpServer().addRouter(WebApiRouter.class);
    }

    public static void Init()
    {
        initRouter();
        initApiDispatcher();
        initCommandsHandlers();
        initPlayerInfoRequestHandler();
        initPlayerCommand();

    }
}
