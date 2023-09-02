package emu.grasscutter.game.entity;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.config.ConfigEntityGadget;
import emu.grasscutter.data.binout.config.fields.ConfigAbilityData;
import emu.grasscutter.data.excels.GadgetData;
import emu.grasscutter.game.props.EntityIdType;
import emu.grasscutter.game.world.*;
import emu.grasscutter.net.proto.SceneEntityInfoOuterClass.SceneEntityInfo;
import emu.grasscutter.scripts.EntityControllerScriptManager;
import it.unimi.dsi.fastutil.ints.*;
import javax.annotation.Nullable;
import lombok.*;

@ToString(callSuper = true)
public class EntityWeapon extends EntityBaseGadget {
    @Getter private final GadgetData gadgetData;

    @Getter(onMethod_ = @Override)
    @Setter
    private int gadgetId;

    @Nullable @Getter private ConfigEntityGadget configGadget;

    @Getter(onMethod_ = @Override, lazy = true)
    private final Int2FloatMap fightProperties = new Int2FloatOpenHashMap();

    @Getter private final Position bornPos;
    @Getter private final Position bornRot;

    public EntityWeapon(Scene scene, int gadgetId) {
        super(scene);

        this.gadgetData = GameData.getGadgetDataMap().get(gadgetId);
        if (gadgetData != null && gadgetData.getJsonName() != null) {
            this.configGadget = GameData.getGadgetConfigData().get(gadgetData.getJsonName());
        }

        this.gadgetId = gadgetId;
        this.bornPos = this.getPosition().clone();
        this.bornRot = this.getRotation().clone();

        this.fillFightProps(configGadget);
        if (GameData.getGadgetMappingMap().containsKey(gadgetId)) {
            var controllerName = GameData.getGadgetMappingMap().get(gadgetId).getServerController();
            this.setEntityController(EntityControllerScriptManager.getGadgetController(controllerName));
            if (getEntityController() == null) {
                Grasscutter.getLogger().warn("Gadget controller {} not found.", controllerName);
            }
        }

        this.id = scene.getWorld().getNextEntityId(EntityIdType.WEAPON);
        Grasscutter.getLogger()
                .trace("New weapon entity {} in scene {}.", this.id, this.getScene().getId());

        this.initAbilities();
    }

    private void addConfigAbility(ConfigAbilityData abilityData) {
        var data = GameData.getAbilityData(abilityData.getAbilityName());
        if (data != null) this.getWorld().getHost().getAbilityManager().addAbilityToEntity(this, data);
    }

    @Override
    public void initAbilities() {
        // TODO: handle pre-dynamic, static and dynamic here
        if (this.configGadget != null && this.configGadget.getAbilities() != null) {
            for (var ability : this.configGadget.getAbilities()) {
                this.addConfigAbility(ability);
            }
        }
    }

    @Override
    public SceneEntityInfo toProto() {
        return null;
    }
}
