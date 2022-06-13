package emu.grasscutter.server.http.objects;

public class RPCResponse{
    public String jsonrpc = "2.0";
    public long id;
    public static class RPCResponseSuccess<T> extends RPCResponse{
        public RPCResponseSuccess(){}
        public T result;
    }
    public static class RPCResponseError<T> extends RPCResponse{
        public RPCResponseError(){}
        public RPCError<T> error;
    }
    public static class RPCError<T>{
        public RPCError(){}
        public RPCError(int code, String message, T data){
            this.code = code;
            this.message = message;
            this.data = data;
        }
        public int code;
        public String message;
        public T data;
    }
}
