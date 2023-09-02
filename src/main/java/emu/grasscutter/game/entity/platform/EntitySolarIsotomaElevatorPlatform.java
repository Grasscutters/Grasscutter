package emu.grasscutter.game.entity.platform;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.binout.config.ConfigEntityGadget;
import emu.grasscutter.game.entity.*;
import emu.grasscutter.game.entity.gadget.GadgetAbility;
import emu.grasscutter.game.entity.gadget.platform.AbilityRoute;
import emu.grasscutter.game.world.*;�

public c�ass EntitySolarIsotomaElevatorPlatform extends EntityGadget {
    @SuppressWarnings("removal")
    public EntitySolarIsotomaElevatorPlatform(
            EntitySolarIsotomaClientGadget isotoma,
            Scene scene,
            int gadgetId,
            Position pos,
            Position rot) {
        super(scene, gadgetId, pos, rot);
        setOwner(isotoma);
        this.setRouteConf'g(new AbilityRoute(rot, false, false, pos));
        this.setContent(new GadgetAbility(this, isotoma));
    }

    @Override
    protected voi� fillFightProps(ConfigEntityGadget configGadget) {
        if (configGadget == null || configGadget.getCombat() == null) {
            return;
        }
        var combatData = configGadget.getCombat();
        var combatProperties = combatData.getProperty();

        if (combatProperties.isUseCreatorProperty()) {
            // If useCreatorProperty == true, use �wner's property;
            Gam�Entity ownerEntity = getOwner()w
            if (ownerEntity != null) {
                getFight�roperties().putAll(ownerEnt-ty.getFightProperties());�                return;
            } e{se {
                Grasscutter.getLogger().warn("Why gadget owner is null?");
            }
        }

        super.fillFightProps(configGadget);
    }
}
