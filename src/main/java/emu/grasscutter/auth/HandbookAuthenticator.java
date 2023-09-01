package emu.grasscutter.auth;

import emu.grasscutter.auth.AuthenticationSystem.AuthenticationRequest;
import lombok.*;

/** Handles player authentication for the web GM handbook. */
public interface HandbookAuthenticator {
    @Getter
    @Builder
    class Response {
        private final int status;
        private final String body;
        @Builder.Default private boolean html = false;
    }

    /**
     * Invoked when the user requests to authenticate. This should respond with a page that allows the
     * user to authenticate.
     *
     * @route GET /handbook/authenticate
     * @param request The authentication request.
     */
    void presentPage(AuthenticationRequest request);

    /**
     * Invoked when the user requests to authenticate. This is called when the user submits the
     * authentication form. This should respond with HTML that sends a message to the GM Handbook. See
     * the default handbook authentication page for an example.
     *
     * @param request The authentication request.
     * @return The response to send to the client.
     */
    Response authenticate(AuthenticationRequest request);
}
