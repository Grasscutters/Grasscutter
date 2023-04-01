package emu.grasscutter.scripts;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.scripts.data.controller.EntityController;
import lombok.val;

import javax.script.Bindings;
import javax.script.CompiledScript;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static emu.grasscutter.utils.FileUtils.getScriptPath;

public class EntityControllerScriptManager {
    private static final Map<String, EntityController> gadgetController = new ConcurrentHashMap<>();

    public static void load(){
        cacheGadgetControllers();
    }

    private static void cacheGadgetControllers(){
        try {
            Files.newDirectoryStream(getScriptPath("Gadget/"), "*.lua").forEach(path -> {
                val fileName = path.getFileName().toString();

                if(!fileName.endsWith(".lua")) return;

                val controllerName = fileName.substring(0, fileName.length()-4);
                CompiledScript cs = ScriptLoader.getScript("Gadget/"+fileName);
                Bindings bindings = ScriptLoader.getEngine().createBindings();
                if (cs == null) return;

                try{
                    cs.eval(bindings);
                    gadgetController.put(controllerName, new EntityController(cs, bindings));
                } catch (Throwable e){
                    Grasscutter.getLogger().error("Error while loading gadget controller: {}", fileName);
                }
            });

            Grasscutter.getLogger().info("Loaded {} gadget controllers", gadgetController.size());
        } catch (IOException e) {
            Grasscutter.getLogger().error("Error loading gadget controller luas");
        }
    }


    public static EntityController getGadgetController(String name) {
        return gadgetController.get(name);
    }
}
