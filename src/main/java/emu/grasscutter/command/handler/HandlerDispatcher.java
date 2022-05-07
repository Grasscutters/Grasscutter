package emu.grasscutter.command.handler;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.handler.annotation.Handler;
import emu.grasscutter.command.handler.annotation.HandlerCollection;
import lombok.SneakyThrows;
import org.greenrobot.eventbus.EventBus;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class HandlerDispatcher {
    private HandlerDispatcher() {
        Reflections reflections = Grasscutter.reflector;
        Set<Class<?>> handlerCollections = reflections.getTypesAnnotatedWith(HandlerCollection.class);
        handlerCollections.forEach(clazz -> {
            try {
                    registerCollection(clazz);
            } catch (Exception e) {
                Grasscutter.getLogger().error(
                        "An error occurred when constructing %s.".formatted(clazz.getSimpleName()), e
                );
            }
        });
        eventBus.register(new ThrowListener());
        eventBus.register(new DispatchListener());
    }
    private static final class SingletonHolder {
        private static final HandlerDispatcher instance = new HandlerDispatcher();
    }
    private final EventBus eventBus = EventBus.builder()
            .sendSubscriberExceptionEvent(true)
            .strictMethodVerification(true)
            .executorService(new ThreadPoolExecutor(
                    0,
                    10, // can be added to config.json
                    1, TimeUnit.MINUTES, // so is this
                    new LinkedBlockingQueue<>()
            ))
            .build();

    private static HandlerDispatcher getInstance() {
        return SingletonHolder.instance;
    }

    @SneakyThrows
    private void register(Class<?> collectionClass) {
        HandlerCollection annotation = collectionClass.getAnnotation(HandlerCollection.class);
        if (annotation == null) {
            throw new RuntimeException(
                    "%s must be annotated with @HandlerCollection.".formatted(collectionClass.getSimpleName())
            );
        }
        Reflections handlerRef = new Reflections(collectionClass);
        Set<Method> handlerMethods = handlerRef.getMethodsAnnotatedWith(Handler.class);
        // abort registration if any
        for (Method method: handlerMethods) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 1) {
                throw new RuntimeException("%s must have one parameter.".formatted(method.getName()));
            }
            if (!parameterTypes[0].equals(HandlerContext.class)) {
                throw new RuntimeException("%s must receive HandlerContext.".formatted(method.getName()));
            }
            if ((method.getModifiers() & Modifier.STATIC) == 0) {
                throw new RuntimeException("%s must be static.".formatted(method.getName()));
            }
            if ((method.getModifiers() & Modifier.PUBLIC) == 0) {
                throw new RuntimeException("%s must be public.".formatted(method.getName()));
            }
            if (DispatchListener.MethodMap.containsKey(method.getAnnotation(Handler.class).value())) {
                throw new RuntimeException("The key conflicts with other handlers.");
            }
        }
        for (Method method: handlerMethods) {
            String key = method.getAnnotation(Handler.class).value();
            DispatchListener.MethodMap.put(key, method);
        }
    }

    /**
     * Register a new handler collection to intervene how the game runs or to handle exceptions.
     */
    public static void registerCollection(Class<?> collectionClass) {
        getInstance().register(collectionClass);
    }

    public static void dispatch(String handlerKey, HandlerContext context) {
        getInstance().eventBus.post(new HandlerEvent(handlerKey, context));
    }
}
