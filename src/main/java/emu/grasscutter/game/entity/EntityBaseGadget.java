package emu.grasscutter.game.entity;

import emu.grasscutter.data.binout.ConfigGadget;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.utils.Position;
import lombok.Getter;

public abstract class EntityBaseGadget extends GameEntity {
    @Getter(onMethod = @__(@Override))
    protected final Position position;
    @Getter(onMethod = @__(@Override))
    protected final Position rotation;

    public EntityBaseGadget(Scene scene) {
        this(scene, null, null);
    }

    public EntityBaseGadget(Scene scene, Position position, Position rotation) {
        super(scene);
        this.position = position != null ? position.clone() : new Position();
        this.rotation = rotation != null ? rotation.clone() : new Position();
    }

    public abstract int getGadgetId();

    @Override
    public void onDeath(int killerId) {
        super.onDeath(killerId); // Invoke super class's onDeath() method.
    }

    protected void fillFightProps(ConfigGadget configGadget) {
        if (configGadget == null || configGadget.getCombat() == null) {
            return;
        }
        var combatData = configGadget.getCombat();
        var combatProperties = combatData.getProperty();

        var targetHp = combatProperties.getHP();
        setFightProperty(FightProperty.FIGHT_PROP_MAX_HP, targetHp);
        setFightProperty(FightProperty.FIGHT_PROP_BASE_HP, targetHp);
        if (combatProperties.isInvincible()) {
            targetHp = Float.POSITIVE_INFINITY;
        }
        setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, targetHp);

        var atk = combatProperties.getAttack();
        setFightProperty(FightProperty.FIGHT_PROP_BASE_ATTACK, atk);
        setFightProperty(FightProperty.FIGHT_PROP_CUR_ATTACK, atk);

        var def = combatProperties.getDefence();
        setFightProperty(FightProperty.FIGHT_PROP_BASE_DEFENSE, def);
        setFightProperty(FightProperty.FIGHT_PROP_CUR_DEFENSE, def);

        setLockHP(combatProperties.isLockHP());
    }
}
