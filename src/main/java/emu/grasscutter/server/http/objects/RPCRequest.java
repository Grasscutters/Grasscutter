package emu.grasscutter.server.http.objects;

import java.util.HashMap;

public class RPCRequest{
    public RPCRequest(){}
    public String jsonrpc = "2.0";
    public String method;
    public HashMap<String,Object> params = new HashMap<>();
    public long id;
}
