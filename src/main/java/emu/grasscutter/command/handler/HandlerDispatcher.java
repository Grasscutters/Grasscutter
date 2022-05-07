package emu.grasscutter.command.handler;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.handler.annotation.HandlerCollection;
import emu.grasscutter.command.handler.builtin.ExceptionListener;
import emu.grasscutter.command.handler.builtin.EventLogListener;
import lombok.SneakyThrows;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class HandlerDispatcher {
    @SuppressWarnings("unchecked")
    private HandlerDispatcher() {
        Reflections reflections = Grasscutter.reflector;
        Set<Class<?>> handlerCollections = reflections.getTypesAnnotatedWith(HandlerCollection.class);
        handlerCollections.forEach(clazz -> {
            try {
                    registerCollection((Class<? extends BaseHandlerCollection>) clazz);
            } catch (Exception e) {
                Grasscutter.getLogger().error(
                        "An error occurred when constructing %s.".formatted(clazz.getSimpleName()), e
                );
            }
        });
        eventBus.register(new ExceptionListener());
        if (Grasscutter.getConfig().DebugMode == Grasscutter.ServerDebugMode.ALL) {
            eventBus.register(new EventLogListener());
        }
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
    private void register(Class<? extends BaseHandlerCollection> collectionClass) {
        HandlerCollection annotation = collectionClass.getAnnotation(HandlerCollection.class);
        if (annotation == null) {
            throw new RuntimeException("@HandlerCollection is missing on %s.".formatted(collectionClass.getSimpleName()));
        }
        Object handler = collectionClass.getDeclaredConstructor().newInstance();
        Reflections handlerRef = new Reflections(handler);
        Set<Method> handlerMethods = handlerRef.getMethodsAnnotatedWith(Subscribe.class);
        for (Method method: handlerMethods) {
            if (method.getAnnotation(Subscribe.class).threadMode() != ThreadMode.BACKGROUND) {
                throw new RuntimeException("You must use ThreadMode.BACKGROUND on %s.".formatted(method.getName()));
            }
        }
        Field codeField = collectionClass.getSuperclass().getDeclaredField("collectionCode");
        Field nameField = collectionClass.getSuperclass().getDeclaredField("collectionName");
        codeField.setAccessible(true);
        nameField.setAccessible(true);
        codeField.setInt(handler, annotation.collectionCode());
        nameField.set(handler, annotation.collectionName());
        eventBus.register(handler);
    }

    /**
     * Register a new handler collection to intervene how the game runs or to handle exceptions.
     */
    public static void registerCollection(Class<? extends BaseHandlerCollection> collectionClass) {
        getInstance().register(collectionClass);
    }

    public static void dispatch(HandlerEvent event) {
        getInstance().eventBus.post(event);
    }
    public static void dispatch(int collectionCode, int methodCode, HandlerContext context) {
        getInstance().eventBus.post(new HandlerEvent(collectionCode, methodCode, context));
    }
}
