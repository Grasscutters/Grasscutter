package emu.grasscutter.game.mail;

import static emu.grasscutter.net.proto.MailItemOuterClass.MailItem.newBuilder;

import dev.morphia.annotations.*;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.*;
import emu.grasscutter.net.proto.EquipParamOuterClass.EquipParam;
import emu.grasscutter.net.proto.MailCollectStateOuterClass.MailCollectState;
import emu.grasscutter.net.proto.MailTextContentOuterClass.MailTextContent;
import java.time.Instant;
import java.util.*;
import org.bson.types.ObjectId;

@Entity(value = "mail", useDiscriminator = false)
public final class Mail {
    @Id private ObjectId id;
    @Indexed private int ownerUid;
    public MailContent mailContent;
    public List<MailItem> itemList;
    public long sendTime;
    public long expireTime;
    public int importance;
    public boolean isRead;
    public boolean isAttachmentGot;
    public int stateValue;
    @Transient private boolean shouldDelete;

    public Mail() {
        this(
                new MailContent(),
                new ArrayList<MailItem>(),
                (int) Instant.now().getEpochSecond()
                        + 604800); // TODO: add expire time to send mail command
    }

    public Mail(MailContent mailContent, List<MailItem> itemList, long expireTime) {
        this(mailContent, itemList, expireTime, 0);
    }

    public Mail(MailContent mailContent, List<MailItem> itemList, long expireTime, int importance) {
        this(mailContent, itemList, expireTime, importance, 1);
    }

    public Mail(
            MailContent mailContent,
            List<MailItem> itemList,
            long expireTime,
            int importance,
            int state) {
        this.mailContent = mailContent;
        this.itemList = itemList;
        this.sendTime = (int) Instant.now().getEpochSecond();
        this.expireTime = expireTime;
        this.importance = importance; // Starred mail, 0 = No star, 1 = Star.
        this.isRead = false;
        this.isAttachmentGot = false;
        this.stateValue = state; // Different mailboxes, 1 = Default, 3 = Gift-box.
    }

    public ObjectId getId() {
        return id;
    }

    public int getOwnerUid() {
        return ownerUid;
    }

    public void setOwnerUid(int ownerUid) {
        this.ownerUid = ownerUid;
    }

    public MailDataOuterClass.MailData toProto(Player player) {
        return MailDataOuterClass.MailData.newBuilder()
                .setMailId(player.getMailId(this))
                .setMailTextContent(this.mailContent.toProto())
                .addAllItemList(this.itemList.stream().map(MailItem::toProto).toList())
                .setSendTime((int) this.sendTime)
                .setExpireTime((int) this.expireTime)
                .setImportance(this.importance)
                .setIsRead(this.isRead)
                .setIsAttachmentGot(this.isAttachmentGot)
                .setCollectState(MailCollectState.MAIL_COLLECT_STATE_NOT_COLLECTIBLE)
                .build();
    }

    @Entity
    public static class MailContent {
        public String title;
        public String content;
        public String sender;

        public MailContent() {
            this.title = "";
            this.content = "loading...";
            this.sender = "loading";
        }

        public MailContent(String title, String content) {
            this(title, content, "Server");
        }

        public MailContent(String title, String content, Player sender) {
            this(title, content, sender.getNickname());
        }

        public MailContent(String title, String content, String sender) {
            this.title = title;
            this.content = content;
            this.sender = sender;
        }

        public MailTextContent toProto() {
            return MailTextContent.newBuilder()
                    .setTitle(this.title)
                    .setContent(this.content)
                    .setSender(this.sender)
                    .build();
        }
    }

    @Entity
    public static class MailItem {
        public int itemId;
        public int itemCount;
        public int itemLevel;

        public MailItem() {
            this.itemId = 11101;
            this.itemCount = 1;
            this.itemLevel = 1;
        }

        public MailItem(int itemId) {
            this(itemId, 1);
        }

        public MailItem(int itemId, int itemCount) {
            this(itemId, itemCount, 1);
        }

        public MailItem(int itemId, int itemCount, int itemLevel) {
            this.itemId = itemId;
            this.itemCount = itemCount;
            this.itemLevel = itemLevel;
        }

        public MailItemOuterClass.MailItem toProto() {
            return newBuilder()
                    .setEquipParam(
                            EquipParam.newBuilder()
                                    .setItemId(this.itemId)
                                    .setItemNum(this.itemCount)
                                    .setItemLevel(this.itemLevel)
                                    .setPromoteLevel(0) // mock
                                    .build())
                    .build();
        }
    }

    public void save() {
        if (this.expireTime * 1000 < System.currentTimeMillis()) {
            DatabaseHelper.deleteMail(this);
        } else {
            DatabaseHelper.saveMail(this);
        }
    }
}
