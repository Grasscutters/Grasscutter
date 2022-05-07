package emu.grasscutter.server.event;

public enum HandlerPriority {
    /**
     * The handler will be called before every other handler.
     */
    HIGH,

    /**
     * The handler will be called the same time as other handlers.
     */
    NORMAL,

    /**
     * The handler will be called after every other handler.
     */
    LOW
}
