package emu.grasscutter.command.handler;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.handler.annotation.Handler;
import emu.grasscutter.command.handler.annotation.HandlerCollection;
import lombok.SneakyThrows;
import org.greenrobot.eventbus.EventBus;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.reflections.ReflectionUtils.Methods;
import static org.reflections.ReflectionUtils.get;
import static org.reflections.util.ReflectionUtilsPredicates.withAnnotation;

public final class HandlerDispatcher {
    private HandlerDispatcher() {
        Reflections reflections = Grasscutter.reflector;
        Set<Class<?>> handlerCollections = reflections.getTypesAnnotatedWith(HandlerCollection.class);
        handlerCollections.forEach(clazz -> {
            try {
                register(clazz);
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
            .logSubscriberExceptions(false)
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
        Set<Method> handlerMethods = get(Methods.of(collectionClass).filter(withAnnotation(Handler.class)));
        // abort registration if any
        for (Method method: handlerMethods) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 1
                    || !parameterTypes[0].equals(HandlerContext.class)) {
                throw new RuntimeException(
                        "The only allowed parameter of %s is \"HandlerContext\".".formatted(method.getName())
                );
            }
            if ((method.getModifiers() & (Modifier.STATIC | Modifier.PUBLIC)) != Modifier.PUBLIC) {
                throw new RuntimeException("%s must be public and non-static.".formatted(method.getName()));
            }
            if (DispatchListener.MethodMap.containsKey(method.getAnnotation(Handler.class).value())) {
                throw new RuntimeException("The key conflicts with other handlers.");
            }
        }
        // create a handler instance
        Object instance = collectionClass.getDeclaredConstructor().newInstance();
        for (Method method: handlerMethods) {
            String key = method.getAnnotation(Handler.class).value();
            DispatchListener.MethodMap.put(key, method);
            DispatchListener.InstanceMap.put(key, instance);
        }
    }

    /**
     * Register a new handler collection to interact with the game server.
     * @param collectionClass <p>A class annotated with @{@link HandlerCollection}.</p>
     *                        <p>Every public static method annotated with @{@link Handler} will be registered.</p>
     */
    public static void registerCollection(Class<?> collectionClass) {
        getInstance().register(collectionClass);
    }

    /**
     * <p>Register a subscriber to handle eventbus events.</p>
     * <p>The priority of all builtin subscribers is zero.
     * By subscribing to {@link HandlerEvent} and {@link org.greenrobot.eventbus.SubscriberExceptionEvent}
     * with other priorities you can hook the handlers. </p>
     * @param subscriber an eventbus subscriber
     */
    public static void addSubscriber(Object subscriber) {
        getInstance().eventBus.register(subscriber);
    }

    /**
     * Unregister a subscriber.
     * @param subscriber the subscriber instance
     */
    public static void removeSubscriber(Object subscriber) {
        getInstance().eventBus.unregister(subscriber);
    }

    /**
     * <p>Call the handler associated with <code>handlerKey</code> with the given context.</p>
     * <p>The context can be built by {@link HandlerContext#builder()}</p>
     * @param handlerKey a unique key to identify handlers
     * @param context handler runtime context, consists of arguments and callbacks
     */
    public static void dispatch(String handlerKey, HandlerContext context) {
        getInstance().eventBus.post(new HandlerEvent(handlerKey, context));
    }
}
