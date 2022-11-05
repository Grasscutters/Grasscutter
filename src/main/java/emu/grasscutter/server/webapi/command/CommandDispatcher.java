package emu.grasscutter.server.webapi.command;

import emu.grasscutter.server.webapi.RequestDispatcher;
import emu.grasscutter.server.webapi.annotations.RequestRoute;
import emu.grasscutter.server.webapi.arguments.ArgumentInfo;
import emu.grasscutter.server.webapi.arguments.ArgumentParser;
import emu.grasscutter.server.webapi.requestdata.CommandRequestData;
import emu.grasscutter.server.webapi.requestdata.RequestJson;
import emu.grasscutter.server.webapi.response.ResponseBuilder;
import emu.grasscutter.utils.Language;
import io.javalin.http.Context;


@RequestRoute(routes = "command,cmd")
public class CommandDispatcher implements RequestDispatcher {

    @Override
    public void dispatch(RequestJson requestJson, Context context) {
        var data = requestJson.getDataAs(CommandRequestData.class);

        if(data == null) {
            ResponseBuilder.buildArgumentMissing("Need data.", getArgumentParser()).send(context);
            return;
        }

        data.setRequestJson(requestJson);

        dispatchToHandler(data, context);
    }

    @Override
    public ArgumentParser getArgumentParser() {
        ArgumentInfo commandTypeArg = new ArgumentInfo("commandType", "string");
        commandTypeArg.setDescription(Language.translate("webapi.command.args.command_type"));

        ArgumentInfo executorIdArg = new ArgumentInfo("executorId", "int");
        executorIdArg.setDescription(Language.translate("webapi.command.args.executor_id"));

        ArgumentInfo executorNameArg = new ArgumentInfo("executorName", "string");
        executorNameArg.setDescription(Language.translate("webapi.command.args.executor_name"));

        ArgumentInfo targetIdArg = new ArgumentInfo("targetIdId", "int");
        targetIdArg.setDescription(Language.translate("webapi.command.args.target_id"));

        ArgumentInfo targetNameArg = new ArgumentInfo("targetName", "string");
        targetNameArg.setDescription(Language.translate("webapi.command.args.target_name"));

        ArgumentInfo dataArg = new ArgumentInfo("data", "object");
        dataArg.setDescription(Language.translate("webapi.command.args.data"));

        return new ArgumentParser(commandTypeArg, executorIdArg, executorNameArg, targetIdArg, targetNameArg, dataArg);
    }

    void dispatchToHandler(CommandRequestData data, Context context) {
        var handler = CommandRequestHandlerPool.getInstance().get(data.getCommandType());
        if(handler == null) {
            ResponseBuilder.buildNoCommandType().send(context);
            return;
        }
        handler.invoke(data, context);
    }
}
