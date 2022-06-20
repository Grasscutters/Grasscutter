package emu.grasscutter.game.friends;

import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.DealAddFriendResultTypeOuterClass.DealAddFriendResultType;
import emu.grasscutter.server.packet.send.*;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.List;

public class FriendsList {
    private final Player player;

    private final Int2ObjectMap<Friendship> friends;
    private final Int2ObjectMap<Friendship> pendingFriends;

    private boolean loaded = false;

    public FriendsList(Player player) {
        this.player = player;
        this.friends = new Int2ObjectOpenHashMap<Friendship>();
        this.pendingFriends = new Int2ObjectOpenHashMap<Friendship>();
    }

    public Player getPlayer() {
        return this.player;
    }

    public boolean hasLoaded() {
        return this.loaded;
    }

    public synchronized Int2ObjectMap<Friendship> getFriends() {
        return this.friends;
    }

    public synchronized Int2ObjectMap<Friendship> getPendingFriends() {
        return this.pendingFriends;
    }

    public synchronized boolean isFriendsWith(int uid) {
        return this.getFriends().containsKey(uid);
    }

    private synchronized Friendship getFriendshipById(int id) {
        Friendship friendship = this.getFriends().get(id);
        if (friendship == null) {
            friendship = this.getPendingFriendById(id);
        }
        return friendship;
    }

    private synchronized Friendship getFriendById(int id) {
        return this.getFriends().get(id);
    }

    private synchronized Friendship getPendingFriendById(int id) {
        return this.getPendingFriends().get(id);
    }

    public void addFriend(Friendship friendship) {
        this.getFriends().put(friendship.getFriendId(), friendship);
    }

    public void addPendingFriend(Friendship friendship) {
        this.getPendingFriends().put(friendship.getFriendId(), friendship);
    }

    public synchronized void handleFriendRequest(int targetUid, DealAddFriendResultType result) {
        // Check if player has sent friend request
        Friendship myFriendship = this.getPendingFriendById(targetUid);
        if (myFriendship == null) {
            return;
        }

        // Make sure asker cant do anything
        if (myFriendship.getAskerId() == this.getPlayer().getUid()) {
            return;
        }

        Player target = this.getPlayer().getSession().getServer().getPlayerByUid(targetUid, true);
        if (target == null) {
            return; // Should never happen
        }

        // Get target's friendship
        Friendship theirFriendship = null;
        if (target.isOnline()) {
            theirFriendship = target.getFriendsList().getPendingFriendById(this.getPlayer().getUid());
        } else {
            theirFriendship = DatabaseHelper.getReverseFriendship(myFriendship);
        }

        if (theirFriendship == null) {
            // They dont have us on their friends list anymore, rip
            this.getPendingFriends().remove(myFriendship.getOwnerId());
            myFriendship.delete();
            return;
        }

        // Handle
        if (result == DealAddFriendResultType.DEAL_ADD_FRIEND_RESULT_TYPE_ACCEPT) { // Request accepted
            myFriendship.setIsFriend(true);
            theirFriendship.setIsFriend(true);

            this.getPendingFriends().remove(myFriendship.getOwnerId());
            this.addFriend(myFriendship);

            if (target.isOnline()) {
                target.getFriendsList().getPendingFriends().remove(this.getPlayer().getUid());
                target.getFriendsList().addFriend(theirFriendship);
            }

            myFriendship.save();
            theirFriendship.save();
        } else { // Request declined
            // Delete from my pending friends
            this.getPendingFriends().remove(myFriendship.getOwnerId());
            myFriendship.delete();
            // Delete from target uid
            if (target.isOnline()) {
                theirFriendship = target.getFriendsList().getPendingFriendById(this.getPlayer().getUid());
            }
            theirFriendship.delete();
        }

        // Packet
        this.getPlayer().sendPacket(new PacketDealAddFriendRsp(targetUid, result));
    }

    public synchronized void deleteFriend(int targetUid) {
        Friendship myFriendship = this.getFriendById(targetUid);
        if (myFriendship == null) {
            return;
        }

        this.getFriends().remove(targetUid);
        myFriendship.delete();

        Friendship theirFriendship = null;
        Player friend = myFriendship.getFriendProfile().getPlayer();
        if (friend != null) {
            // Friend online
            theirFriendship = friend.getFriendsList().getFriendById(this.getPlayer().getUid());
            if (theirFriendship != null) {
                friend.getFriendsList().getFriends().remove(theirFriendship.getFriendId());
                theirFriendship.delete();
                friend.sendPacket(new PacketDeleteFriendNotify(theirFriendship.getFriendId()));
            }
        } else {
            // Friend offline
            theirFriendship = DatabaseHelper.getReverseFriendship(myFriendship);
            if (theirFriendship != null) {
                theirFriendship.delete();
            }
        }

        // Packet
        this.getPlayer().sendPacket(new PacketDeleteFriendRsp(targetUid));
    }

    public synchronized void sendFriendRequest(int targetUid) {
        Player target = this.getPlayer().getSession().getServer().getPlayerByUid(targetUid, true);

        if (target == null || target == this.getPlayer()) {
            return;
        }

        // Check if friend already exists
        if (this.getPendingFriends().containsKey(targetUid) || this.getFriends().containsKey(targetUid)) {
            return;
        }

        // Create friendships
        Friendship myFriendship = new Friendship(this.getPlayer(), target, this.getPlayer());
        Friendship theirFriendship = new Friendship(target, this.getPlayer(), this.getPlayer());

        // Add pending lists
        this.addPendingFriend(myFriendship);

        if (target.isOnline() && target.getFriendsList().hasLoaded()) {
            target.getFriendsList().addPendingFriend(theirFriendship);
            target.sendPacket(new PacketAskAddFriendNotify(theirFriendship));
        }

        // Save
        myFriendship.save();
        theirFriendship.save();

        // Packets
        this.getPlayer().sendPacket(new PacketAskAddFriendRsp(targetUid));
    }

    /**
     * Gets total amount of potential friends
     */
    public int getFullFriendCount() {
        return this.getPendingFriends().size() + this.getFriends().size();
    }

    public synchronized void loadFromDatabase() {
        if (this.hasLoaded()) {
            return;
        }

        // Get friendships from the db
        List<Friendship> friendships = DatabaseHelper.getFriends(this.player);
        friendships.forEach(this::loadFriendFromDatabase);

        // Set loaded flag
        this.loaded = true;
    }

    private void loadFriendFromDatabase(Friendship friendship) {
        // Set friendship owner
        friendship.setOwner(this.getPlayer());

        // Check if friend is online
        Player friend = this.getPlayer().getSession().getServer().getPlayerByUid(friendship.getFriendProfile().getUid());
        if (friend != null) {
            // Set friend to online mode
            friendship.setFriendProfile(friend);

            // Update our status on friend's client if theyre online
            if (friend.getFriendsList().hasLoaded()) {
                Friendship theirFriendship = friend.getFriendsList().getFriendshipById(this.getPlayer().getUid());
                if (theirFriendship != null) {
                    // Update friend profile
                    theirFriendship.setFriendProfile(this.getPlayer());
                } else {
                    // They dont have us on their friends list anymore, rip
                    friendship.delete();
                    return;
                }
            }
        }

        // Finally, load to our friends list
        if (friendship.isFriend()) {
            this.getFriends().put(friendship.getFriendId(), friendship);
        } else {
            this.getPendingFriends().put(friendship.getFriendId(), friendship);
            // TODO - Hacky fix to force client to see a notification for a friendship
            if (this.getPendingFriends().size() == 1) {
                this.getPlayer().getSession().send(new PacketAskAddFriendNotify(friendship));
            }
        }
    }

    public void save() {
        // Update all our friends
        List<Friendship> friendships = DatabaseHelper.getReverseFriends(this.getPlayer());
        for (Friendship friend : friendships) {
            friend.setFriendProfile(this.getPlayer());
            friend.save();
        }
    }
}
