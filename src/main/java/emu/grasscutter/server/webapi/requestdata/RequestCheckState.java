package emu.grasscutter.server.webapi.requestdata;

public enum RequestCheckState
{
    INVALID_TOKEN,
    INVALID_ARGUMENT,
    REQUEST_TYPE_MISSING,
    REQUEST_TOKEN_MISSING,
    UNKNOWN_ERROR,
    ERROR,
    SUCCESS
}
