package emu.grasscutter.game.mail;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * System for handling birthday gifts for players.
 * Checks if today is a player's birthday and sends a gift if it is.
 */
public class BirthdayMailSystem {
    // Mail content constants
    private static final String MAIL_TITLE = "Best Wishes on Your Birthday";
    private static final String MAIL_CONTENT = "Happy Birthday, Traveler! Please find your gift attached to this message. Thank you for all your support. We wish you a brighter future, no matter what you are going through.";
    private static final String MAIL_SENDER = "Server";
    
    // Gift item ID - Cake for Traveler
    private static final int BIRTHDAY_GIFT_ID = 118008;
    private static final int BIRTHDAY_GIFT_COUNT = 1;
    
    /**
     * Checks if today is the player's birthday and sends a gift if it is.
     * Should be called when a player logs in.
     *
     * @param player The player to check
     */
    public static void checkBirthdayAndSendGift(Player player) {
        if (player == null || !player.hasBirthday()) {
            return;
        }
        
        // Get current date
        LocalDate currentDate = LocalDate.now(ZoneId.systemDefault());
        int currentDay = currentDate.getDayOfMonth();
        int currentMonth = currentDate.getMonthValue();
        
        // Get player's birthday
        int birthdayDay = player.getBirthday().getDay();
        int birthdayMonth = player.getBirthday().getMonth();
        
        // Check if today is the player's birthday
        if (currentDay == birthdayDay && currentMonth == birthdayMonth) {
            // Check if player already received birthday gift today
            if (!hasReceivedBirthdayGiftToday(player)) {
                sendBirthdayGift(player);
            }
        }
    }
    
    /**
     * Sends a birthday gift to the player.
     *
     * @param player The player to send the gift to
     */
    private static void sendBirthdayGift(Player player) {
        // Create mail content
        Mail.MailContent mailContent = new Mail.MailContent(MAIL_TITLE, MAIL_CONTENT, MAIL_SENDER);
        
        // Create mail item list with birthday cake
        List<Mail.MailItem> mailItems = new ArrayList<>();
        mailItems.add(new Mail.MailItem(BIRTHDAY_GIFT_ID, BIRTHDAY_GIFT_COUNT));
        
        // Calculate expiration time (7 days from now)
        long expireTime = System.currentTimeMillis() / 1000 + (7 * 24 * 60 * 60);
        
        // Create mail
        Mail birthdayMail = new Mail(mailContent, mailItems, expireTime);
        
        // Send mail to player
        player.getMailHandler().sendMail(birthdayMail);
        
        // Log the action
        Grasscutter.getLogger().info("Birthday gift sent to " + player.getNickname() + " (UID: " + player.getUid() + ")");
        
        // Mark that player received birthday gift today
        markBirthdayGiftReceived(player);
    }
    
    /**
     * Checks if the player has already received a birthday gift today.
     * Uses player properties to store this information.
     *
     * @param player The player to check
     * @return True if the player has received a birthday gift today, false otherwise
     */
    private static boolean hasReceivedBirthdayGiftToday(Player player) {
        // Get current date as a string in format YYYYMMDD
        LocalDate currentDate = LocalDate.now(ZoneId.systemDefault());
        String dateString = String.format("%04d%02d%02d", 
                currentDate.getYear(), 
                currentDate.getMonthValue(), 
                currentDate.getDayOfMonth());
        
        // Convert to integer for storage in player properties
        int dateValue = Integer.parseInt(dateString);
        
        // Check if player has a property for the last birthday gift date
        Integer lastGiftDate = player.getProperties().get(PlayerProperties.LAST_BIRTHDAY_GIFT_DATE);
        
        return lastGiftDate != null && lastGiftDate == dateValue;
    }
    
    /**
     * Marks that the player has received a birthday gift today.
     *
     * @param player The player to mark
     */
    private static void markBirthdayGiftReceived(Player player) {
        // Get current date as a string in format YYYYMMDD
        LocalDate currentDate = LocalDate.now(ZoneId.systemDefault());
        String dateString = String.format("%04d%02d%02d", 
                currentDate.getYear(), 
                currentDate.getMonthValue(), 
                currentDate.getDayOfMonth());
        
        // Convert to integer for storage in player properties
        int dateValue = Integer.parseInt(dateString);
        
        // Store the date in player properties
        player.getProperties().put(PlayerProperties.LAST_BIRTHDAY_GIFT_DATE, dateValue);
        
        // Save player data
        player.save();
    }
    
    /**
     * Constants for player properties related to birthday gifts.
     */
    private static class PlayerProperties {
        public static final int LAST_BIRTHDAY_GIFT_DATE = 10001; // Custom property ID
    }
}
