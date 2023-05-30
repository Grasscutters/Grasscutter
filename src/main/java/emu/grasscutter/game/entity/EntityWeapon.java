package emu.grasscutter.game.entity;

import javax.annotation.Nullable;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.AbilityData;
import emu.grasscutter.data.binout.config.ConfigEntityGadget;
import emu.grasscutter.data.binout.config.fields.ConfigAbilityData;
import emu.grasscutter.data.excels.GadgetData;
import emu.grasscutter.game.entity.gadget.GadgetContent;
import emu.grasscutter.game.props.EntityIdType;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.proto.SceneEntityInfoOuterClass.SceneEntityInfo;
import emu.grasscutter.scripts.EntityControllerScriptManager;
import emu.grasscutter.utils.Position;
import it.unimi.dsi.fastutil.ints.Int2FloatMap;
import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
public class EntityWeapon extends EntityBaseGadget {
    @Getter private final GadgetData gadgetData;
    @Getter(onMethod = @__(@Override)) @Setter
    private int gadgetId;
    @Nullable @Getter
    private ConfigEntityGadget configGadget;

    @Getter(onMethod = @__(@Override), lazy = true)
    private final Int2FloatMap fightProperties = new Int2FloatOpenHashMap();

    @Getter private final Position bornPos;
    @Getter private final Position bornRot;

    public EntityWeapon(Scene scene, int gadgetId) {
        super(scene);

        this.gadgetData = GameData.getGadgetDataMap().get(gadgetId);
        if (gadgetData!=null && gadgetData.getJsonName()!=null) {
            this.configGadget = GameData.getGadgetConfigData().get(gadgetData.getJsonName());
        }

        this.gadgetId = gadgetId;
        this.gadgetId = gadgetId;
        this.bornPos = this.getPosition().clone();
        this.bornRot = this.getRotation().clone();
        fillFightProps(configGadget);
        if(GameData.getGadgetMappingMap().containsKey(gadgetId)) {
            String controllerName = GameData.getGadgetMappingMap().get(gadgetId).getServerController();
            setEntityController(EntityControllerScriptManager.getGadgetController(controllerName));
            if(getEntityController() == null) {
                Grasscutter.getLogger().warn("Gadget controller {} not found", controllerName);
            }
        }

        this.id = scene.getWorld().getNextEntityId(EntityIdType.WEAPON);
        Grasscutter.getLogger().warn("New weapon entity id {} at scene {}", this.id, this.getScene().getId());

        initAbilities();
    }

    private void addConfigAbility(ConfigAbilityData abilityData){
        AbilityData data =  GameData.getAbilityData(abilityData.getAbilityName());
        if(data != null)
            getScene().getWorld().getHost().getAbilityManager().addAbilityToEntity(
                this, data);
    }

    @Override
    public void initAbilities() {
        //TODO: handle predynamic, static and dynamic here
        if(this.configGadget != null && this.configGadget.getAbilities() != null) {
            for (var ability : this.configGadget.getAbilities()) {
                addConfigAbility(ability);
            }
        }
    }

    @Override
    public SceneEntityInfo toProto() {
        return null;
    }
}
