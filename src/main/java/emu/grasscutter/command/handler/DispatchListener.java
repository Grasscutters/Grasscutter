package emu.grasscutter.command.handler;

import lombok.SneakyThrows;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

class DispatchListener {
    static final ConcurrentHashMap<String, Method> MethodMap = new ConcurrentHashMap<>();

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    @SneakyThrows
    public void dispatch(HandlerEvent event) {
        Method method = MethodMap.get(event.key());
        if (method == null) {
            throw new NoSuchMethodException();
        }
        method.invoke(null, event.context());
    }
}
