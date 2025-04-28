package emu.grasscutter.game.managers.stamina;

public interface BeforeUpdateStaminaListener {
    /**
     * onBeforeUpdateStamina() will be called before StaminaManager attempt to update the player's
     * current stamina. This gives listeners a chance to intercept this update.
     *
     * @param reason Why updating stamina.
     * @param newStamina New ABSOLUTE stamina value.
     * @return current stamina value. If you want to cancel this update, return the newStamina value.
     */
    int onBeforeUpdateStamina(String reason, int newStamina, boolean isCharacterStamina);

    /**
     * onBeforeUpdateStamina() will be called before StaminaManager attempt to update the player's
     * current stamina. This gives listeners a chance to intercept this update.
     *
     * @param reason Why updating stamina.
     * @param consumption ConsumptionType and RELATIVE stamina change amount.
     * @return current stamina value. If you want to cancel this update, return the newStamina value.
     */
    Consumption onBeforeUpdateStamina(
            String reason, Consumption consumption, boolean isCharacterStamina);
}
