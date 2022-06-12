package emu.grasscutter.game.managers.chat;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.game.GameServer;

public interface ChatManagerHandler {
    GameServer getServer();
    void sendPrivateMessage(Player player, int targetUid, String message);
    void sendPrivateMessage(Player player, int targetUid, int emote);
    void sendTeamMessage(Player player, int channel, String message);
    void sendTeamMessage(Player player, int channel, int icon);
    void sendPrivateMessageFromServer(int targetUid, String message);
    void sendPrivateMessageFromServer(int targetUid, int emote);
    void handlePullPrivateChatReq(Player player, int targetUid);
    void clearHistoryOnLogout(Player player);
    void sendServerWelcomeMessages(Player player);
    void handlePullRecentChatReq(Player player);
}
