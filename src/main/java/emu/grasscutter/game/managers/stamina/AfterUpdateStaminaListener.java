package emu.grasscutter.game.managers.stamina;

public interface AfterUpdateStaminaListener {
    /**
     * onBeforeUpdateStamina() will be called before StaminaManager attempt to update the player's
     * current stamina. This gives listeners a chance to intercept this update.
     *
     * @param reason Why updating stamina.
     * @param newStamina New Stamina value.
     */
    void onAfterUpdateStamina(String reason, int newStamina, boolean isCharacterStamina);
}
