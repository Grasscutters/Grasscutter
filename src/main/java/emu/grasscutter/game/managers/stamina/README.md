# Stamina Manager

---

## UpdateStamina

```java
// will use consumption.consumptionType as reason
public int updateStaminaRelative(GameSession session, Consumption consumption);
```

```java
public int updateStaminaAbsolute(GameSession session, String reason, int newStamina)
```

---

## Pause and Resume

```java
public void startSustainedStaminaHandler()
```

```java
public void stopSustainedStaminaHandler()
```

---

## Stamina change listeners and intercepting

### BeforeUpdateStaminaListener

```java

import emu.grasscutter.game.managers.StaminaManager.BeforeUpdateStaminaListener;

// Listener sample: plugin disable CLIMB_JUMP stamina cost.
private class MyClass implements BeforeUpdateStaminaListener {
    // Make your class implement the listener, and pass in your class as a listener.

    public MyClass() {
        getStaminaManager().registerBeforeUpdateStaminaListener("myClass", this);
    }

    @Override
    public boolean onBeforeUpdateStamina(String reason, int newStamina) {
        // do not intercept this update
        return false;
    }

    @Override
    public boolean onBeforeUpdateStamina(String reason, Consumption consumption) {
        // Try to intercept if this update is CLIMB_JUMP
        return consumption.consumptionType == ConsumptionType.CLIMB_JUMP;
        // If it is not CLIMB_JUMP, do not intercept.
    }
}
```

### AfterUpdateStaminaListener

```java

import emu.grasscutter.game.managers.StaminaManager.AfterUpdateStaminaListener;

// Listener sample: plugin listens for changes already made.
private class MyClass implements AfterUpdateStaminaListener {
    // Make your class implement the listener, and pass in your class as a listener.

    public MyClass() {
        registerAfterUpdateStaminaListener("myClass", this);
    }

    @Override
    public void onAfterUpdateStamina(String reason, int newStamina) {
        // ...
    }
}
```