package emu.grasscutter.game.systems;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.mail.Mail;
import emu.grasscutter.game.player.Player;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * System for handling daily check-in rewards for players.
 * Players receive rewards for each day they log in, with a monthly cycle.
 */
public class DailyCheckInSystem {
    // Mail content constants
    private static final String MAIL_TITLE = "Daily Check-In Reward";
    private static final String MAIL_CONTENT = "Thank you for logging in today, Traveler! Here's your daily check-in reward.";
    private static final String MAIL_SENDER = "Check-In System";
    
    // Player property for storing last check-in date
    private static final int LAST_CHECK_IN_DATE_PROP = 10002;
    // Player property for storing current check-in streak
    private static final int CHECK_IN_STREAK_PROP = 10003;
    
    // Map of day number to reward item data
    private static final Map<Integer, CheckInReward> CHECK_IN_REWARDS = new HashMap<>();
    
    static {
        // Initialize the rewards map
        CHECK_IN_REWARDS.put(1, new CheckInReward(104002, 3, "Adventurer's Experience"));
        CHECK_IN_REWARDS.put(2, new CheckInReward(104012, 3, "Fine Enhancement Ore"));
        CHECK_IN_REWARDS.put(3, new CheckInReward(202, 5000, "Mora"));
        CHECK_IN_REWARDS.put(4, new CheckInReward(201, 20, "Primogem"));
        CHECK_IN_REWARDS.put(5, new CheckInReward(108032, 3, "Sweet Madame"));
        CHECK_IN_REWARDS.put(6, new CheckInReward(104002, 2, "Adventurer's Experience"));
        CHECK_IN_REWARDS.put(7, new CheckInReward(202, 8000, "Mora"));
        CHECK_IN_REWARDS.put(8, new CheckInReward(104002, 3, "Adventurer's Experience"));
        CHECK_IN_REWARDS.put(9, new CheckInReward(104012, 3, "Fine Enhancement Ore"));
        CHECK_IN_REWARDS.put(10, new CheckInReward(202, 5000, "Mora"));
        CHECK_IN_REWARDS.put(11, new CheckInReward(201, 20, "Primogem"));
        CHECK_IN_REWARDS.put(12, new CheckInReward(108026, 2, "Fried Radish Balls"));
        CHECK_IN_REWARDS.put(13, new CheckInReward(104002, 3, "Adventurer's Experience"));
        CHECK_IN_REWARDS.put(14, new CheckInReward(202, 8000, "Mora"));
        CHECK_IN_REWARDS.put(15, new CheckInReward(104002, 5, "Adventurer's Experience"));
        CHECK_IN_REWARDS.put(16, new CheckInReward(104012, 5, "Fine Enhancement Ore"));
        CHECK_IN_REWARDS.put(17, new CheckInReward(202, 5000, "Mora"));
        CHECK_IN_REWARDS.put(18, new CheckInReward(201, 20, "Primogem"));
        CHECK_IN_REWARDS.put(19, new CheckInReward(108002, 3, "Fisherman's Toast"));
        CHECK_IN_REWARDS.put(20, new CheckInReward(104002, 3, "Adventurer's Experience"));
        CHECK_IN_REWARDS.put(21, new CheckInReward(202, 8000, "Mora"));
        CHECK_IN_REWARDS.put(22, new CheckInReward(104002, 5, "Adventurer's Experience"));
        CHECK_IN_REWARDS.put(23, new CheckInReward(104012, 5, "Fine Enhancement Ore"));
        CHECK_IN_REWARDS.put(24, new CheckInReward(202, 5000, "Mora"));
        CHECK_IN_REWARDS.put(25, new CheckInReward(104003, 3, "Hero's Wit"));
        CHECK_IN_REWARDS.put(26, new CheckInReward(108081, 3, "Almond Tofu"));
        CHECK_IN_REWARDS.put(27, new CheckInReward(104002, 3, "Adventurer's Experience"));
        CHECK_IN_REWARDS.put(28, new CheckInReward(104003, 3, "Hero's Wit"));
        CHECK_IN_REWARDS.put(29, new CheckInReward(202, 5000, "Mora"));
        CHECK_IN_REWARDS.put(30, new CheckInReward(202, 5000, "Mora"));
        CHECK_IN_REWARDS.put(31, new CheckInReward(202, 5000, "Mora"));
    }
    
    /**
     * Checks if the player can claim a daily check-in reward and sends it if eligible.
     * Should be called when a player logs in.
     *
     * @param player The player to check
     */
    public static void checkDailyRewardAndSend(Player player) {
        if (player == null) {
            return;
        }
        
        // Get current date
        LocalDate currentDate = LocalDate.now(ZoneId.systemDefault());
        String currentDateString = currentDate.format(DateTimeFormatter.BASIC_ISO_DATE);
        int currentDateValue = Integer.parseInt(currentDateString);
        
        // Get last check-in date from player properties
        Integer lastCheckInDate = player.getProperties().get(LAST_CHECK_IN_DATE_PROP);
        
        // If player has never checked in or it's a different day
        if (lastCheckInDate == null || lastCheckInDate != currentDateValue) {
            // Get current streak
            int currentStreak = player.getProperties().getOrDefault(CHECK_IN_STREAK_PROP, 0);
            
            // Check if this is a consecutive day
            boolean isConsecutiveDay = isConsecutiveDay(lastCheckInDate, currentDateValue);
            
            // Update streak
            if (isConsecutiveDay) {
                currentStreak++;
                // If streak is greater than the number of days in a month, reset to 1
                if (currentStreak > 31) {
                    currentStreak = 1;
                }
            } else {
                // Reset streak if not consecutive
                currentStreak = 1;
            }
            
            // Send reward based on current streak
            sendDailyReward(player, currentStreak);
            
            // Update player properties
            player.getProperties().put(LAST_CHECK_IN_DATE_PROP, currentDateValue);
            player.getProperties().put(CHECK_IN_STREAK_PROP, currentStreak);
            
            // Save player data
            player.save();
            
            // Log the check-in
            Grasscutter.getLogger().info("Player {} (UID: {}) claimed day {} check-in reward", 
                    player.getNickname(), player.getUid(), currentStreak);
        }
    }
    
    /**
     * Checks if two dates are consecutive days.
     *
     * @param lastDateValue The previous date as YYYYMMDD
     * @param currentDateValue The current date as YYYYMMDD
     * @return True if the dates are consecutive days, false otherwise
     */
    private static boolean isConsecutiveDay(Integer lastDateValue, int currentDateValue) {
        if (lastDateValue == null) {
            return false;
        }
        
        try {
            // Parse the date values
            String lastDateStr = String.valueOf(lastDateValue);
            String currentDateStr = String.valueOf(currentDateValue);
            
            LocalDate lastDate = LocalDate.parse(lastDateStr, DateTimeFormatter.BASIC_ISO_DATE);
            LocalDate currentDate = LocalDate.parse(currentDateStr, DateTimeFormatter.BASIC_ISO_DATE);
            
            // Check if the dates are consecutive
            return lastDate.plusDays(1).equals(currentDate);
        } catch (Exception e) {
            Grasscutter.getLogger().error("Error checking consecutive days", e);
            return false;
        }
    }
    
    /**
     * Sends a daily check-in reward to the player.
     *
     * @param player The player to send the reward to
     * @param day The day number in the check-in cycle
     */
    private static void sendDailyReward(Player player, int day) {
        // Get the reward for the current day
        CheckInReward reward = CHECK_IN_REWARDS.get(day);
        if (reward == null) {
            Grasscutter.getLogger().error("No reward found for day {}", day);
            return;
        }
        
        // Create mail content
        String content = MAIL_CONTENT + "\n\nDay " + day + ": " + reward.getCount() + "x " + reward.getName();
        Mail.MailContent mailContent = new Mail.MailContent(MAIL_TITLE, content, MAIL_SENDER);
        
        // Create mail item list with the reward
        List<Mail.MailItem> mailItems = new ArrayList<>();
        mailItems.add(new Mail.MailItem(reward.getItemId(), reward.getCount()));
        
        // Calculate expiration time (7 days from now)
        long expireTime = System.currentTimeMillis() / 1000 + (7 * 24 * 60 * 60);
        
        // Create mail
        Mail checkInMail = new Mail(mailContent, mailItems, expireTime);
        
        // Send mail to player
        player.getMailHandler().sendMail(checkInMail);
    }
    
    /**
     * Class representing a daily check-in reward.
     */
    private static class CheckInReward {
        private final int itemId;
        private final int count;
        private final String name;
        
        public CheckInReward(int itemId, int count, String name) {
            this.itemId = itemId;
            this.count = count;
            this.name = name;
        }
        
        public int getItemId() {
            return itemId;
        }
        
        public int getCount() {
            return count;
        }
        
        public String getName() {
            return name;
        }
    }
}
