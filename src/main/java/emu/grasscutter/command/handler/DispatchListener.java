package emu.grasscutter.command.handler;

import emu.grasscutter.command.handler.exception.NoSuchArgumentException;
import emu.grasscutter.command.handler.exception.NoSuchHandlerException;
import lombok.SneakyThrows;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class DispatchListener {
    static final ConcurrentHashMap<String, Method> MethodMap = new ConcurrentHashMap<>();
    static final ConcurrentHashMap<String, Object> InstanceMap = new ConcurrentHashMap<>();
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    @SneakyThrows
    public void dispatch(HandlerEvent event) {
        Method method = MethodMap.get(event.key());
        Object instance = InstanceMap.get(event.key());
        if (method == null || instance == null) {
            throw new NoSuchHandlerException(event.key());
        }
        try {
            method.invoke(instance, event.context());
        } catch (InvocationTargetException e) {
            Throwable target = e.getTargetException();
            if (target instanceof HandlerSuccess s) { // check if it runs successfully
                Consumer<Object> resultConsumer = event.context().getResultConsumer();
                if (resultConsumer != null) {
                    resultConsumer.accept(s.getResult());
                }
            } else { // unwrap and throw so that ThrowListener can handle this as exceptions
                throw target;
            }
        }
    }
}
