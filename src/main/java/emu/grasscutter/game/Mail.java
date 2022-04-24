package emu.grasscutter.game;

import dev.morphia.annotations.Entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Mail {

    public int _id;
    public MailContent mailContent;
    public List<MailItem> itemList;
    public long sendTime;
    public long expireTime;
    public int importance;
    public boolean isRead;
    public boolean isAttachmentGot;
    public int stateValue;

    public Mail() {
        _id = 1;
        mailContent = new MailContent("No title set...", "No content set...");
        itemList = new ArrayList<>();
        sendTime = 0;
        expireTime = 0;
        importance = 1;
        isRead = true;
        isAttachmentGot = true;
        stateValue = 1;
    }

    public Mail(MailContent mailContent, List<MailItem> itemList, long expireTime) {
        this(mailContent, itemList, expireTime, 1);
    }

    public Mail(MailContent mailContent, List<MailItem> itemList, long expireTime, int importance) {
        this(mailContent, itemList, expireTime, importance, 1);
    }

    public Mail(MailContent mailContent, List<MailItem> itemList, long expireTime, int importance, int state) {
        this(0, mailContent, itemList, expireTime, importance, state);
    }

    public Mail(int _id, MailContent mailContent, List<MailItem> itemList, long expireTime, int importance, int state) {
        this._id = _id;
        this.mailContent = mailContent;
        this.itemList = itemList;
        this.sendTime = (int) Instant.now().getEpochSecond();
        this.expireTime = expireTime;
        this.importance = importance;
        this.isRead = false;
        this.isAttachmentGot = false;
        this.stateValue = state;
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

        public MailContent(String title, String content, GenshinPlayer sender) {
            this(title, content, sender.getNickname());
        }

        public MailContent(String title, String content, String sender) {
            this.title = title;
            this.content = content;
            this.sender = sender;
        }
    }

    @Entity
    public static class MailItem {
        public int itemId;
        public int itemCount;

        public MailItem() {
            this.itemId = 11101;
            this.itemCount = 1;
        }

        public MailItem(int itemId) {
            this(itemId, 1);
        }

        public MailItem(int itemId, int itemCount) {
            this.itemId = itemId;
            this.itemCount = itemCount;
        }
    }
}
