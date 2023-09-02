package emu.grasscutter.scripts;

import static emu.grasscutter.utils.FileUtils.getScriptPath;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.scripts.data.controller.EntityController;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.val;

public class EntityControllerScriptManager {
    private static final Map<String, EntityController> gadgetController = new ConcurrentHashMap<>();

    public static void load() {
        cacheGadgetControllers();
    }

    private static void cacheGadgetControllers() {
        try (var stream = Files.newDirectoryStream(getScriptPath("Gadget/"), "*.lua")) {
            stream.forEach(
                    path -> {
                        val fileName = path.getFileName().toString();
                        if (!fileName.endsWith(".lua")) return;

                        val controllerName = fileName.substring(0, fileName.length() - 4);
                        var cs = ScriptLoader.getScript("Gadget/" + fileName);
                        var bindings = ScriptLoader.getEngine().createBindings();
                        if (cs == null) return;

                        try {
                            ScriptLoader.eval(cs, bindings);
                            gadgetController.put(controllerName, new EntityController(cs, bindings));
                        } catch (Throwable e) {
                            Grasscutter.getLogger().error("Error while loading gadget controller: {}.", fileName);
                        }
                    });
            Grasscutter.getLogger().debug("Loaded {} gadget controllers", gadgetController.size());
        } catch (IOException e) {
            Grasscutter.getLogger().error("Error loading gadget controller Lua scripts.");
        }
    }

    public static EntityController getGadgetController(String name) {
        return gadgetController.get(name);
    }
}
