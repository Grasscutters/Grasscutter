package emu.grasscutter.server.webapi.dispatcher;


import emu.grasscutter.server.webapi.RequestDispatcher;
import emu.grasscutter.server.webapi.genericobjectmgr.GenericObjectPool;

public class DispatcherPool extends GenericObjectPool<RequestDispatcher> {
    static volatile DispatcherPool mgr;
    public static DispatcherPool getInstance() {
        if(mgr == null) {
            synchronized (DispatcherPool.class) {
                if(mgr == null) {
                    mgr = new DispatcherPool();
                }
            }
        }

        return mgr;
    }
}
