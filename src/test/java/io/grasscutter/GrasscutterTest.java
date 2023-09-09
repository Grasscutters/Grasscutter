package io.grasscutter;

import io.grasscutter.virtual.*;
import lombok.*;
import okhttp3.OkHttpClient;

import java.util.concurrent.*;

public final class GrasscutterTest {
    @Getter
    public static final OkHttpClient httpClient = new OkHttpClient();
    @Getter public static final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Getter public static VirtualAccount account;
    @Setter
    @Getter public static VirtualPlayer player;
    @Getter public static VirtualGameSession gameSession;
}
