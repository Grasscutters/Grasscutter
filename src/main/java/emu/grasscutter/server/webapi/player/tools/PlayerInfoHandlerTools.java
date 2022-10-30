package emu.grasscutter.server.webapi.player.tools;

import emu.grasscutter.server.webapi.player.handlers.interfaces.PlayerInfoRequestHandler;

import java.util.ArrayList;

public class PlayerInfoHandlerTools
{
    public static String[] getAvailableOperations(PlayerInfoRequestHandler handler)
    {
        ArrayList<String> availableOperations = new ArrayList<>();
        if(handler.canGet())
        {
            availableOperations.add("get");
        }

        if(handler.canSet())
        {
            availableOperations.add("set");
        }

        if(handler.canAdd())
        {
            availableOperations.add("add");
        }

        return availableOperations.toArray(new String[0]);
    }
}
