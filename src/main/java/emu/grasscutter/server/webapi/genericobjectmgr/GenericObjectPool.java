package emu.grasscutter.server.webapi.genericobjectmgr;

import emu.grasscutter.server.webapi.annotations.RequestRoute;

import java.util.HashMap;

public class GenericObjectPool<T> {
    protected GenericObjectPool() {
    }
    HashMap<String, T> handlerHashMap = new HashMap<>();

    public boolean register(Class<? extends T> clazz) {
        if(clazz.isInterface()) {
            return false;
        }

        var annotation = clazz.getAnnotation(RequestRoute.class);
        if(annotation == null) {
            return false;
        }

        T ins;
        try {
            ins = clazz.getConstructor().newInstance();
        } catch (Exception e) {
            return false;
        }

        String[] routes = annotation.routes().split(",");
        for (var r : routes) {
            handlerHashMap.put(r, ins);
        }
        return true;
    }

    public boolean registry(T handler) {
        var clazz = handler.getClass();
        var annotation = clazz.getAnnotation(RequestRoute.class);
        if(annotation == null) {
            return false;
        }

        String[] routes = annotation.routes().split(",");
        for (var r : routes) {
            handlerHashMap.put(r, handler);
        }
        return true;
    }

    public T get(String route) {
        return handlerHashMap.get(route);
    }

    public String[] getRoutes() {
        return handlerHashMap.keySet().toArray(new String[0]);
    }
}
