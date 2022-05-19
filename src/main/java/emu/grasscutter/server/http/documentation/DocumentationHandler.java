package emu.grasscutter.server.http.documentation;

import express.http.Request;
import express.http.Response;

interface DocumentationHandler {

    void handle(Request request, Response response);
}
