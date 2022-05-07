package emu.grasscutter.command.handler;

public abstract class BaseHandlerCollection {
    public final int collectionCode = Integer.parseInt("-1"); // to avoid getting inlined by JVM
    public final String collectionName = new String("Base" + "Handler"); // as above
    protected boolean notValid(HandlerEvent event, int expectedMethodCode) {
        return event.getCollectionCode() != collectionCode || event.getMethodCode() != expectedMethodCode;
    }
}
