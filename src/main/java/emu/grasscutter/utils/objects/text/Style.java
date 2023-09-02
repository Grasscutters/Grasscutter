package emu.grasscutter.utils.objects.text;

import java.awt.*;
import java.util.*;
import lombok.*;

/* Text style container. */
@Builder
@Data
public final class Style {
    private static final Map<Character, String> unity = new HashMap<>();
    private static final Map<Character, String> ansi = new HashMap<>();

    static {
        // Add the Minecraft color codes to the color map.
        unity.put('0', "#000000"); // Black
        unity.put('1', "#0000AA"); // Dark Blue
        unity.put('2', "#00AA00"); // Dark Green
        unity.put('3', "#00AAAA"); // Dark Aqua
        unity.put('4', "#AA0000"); // Dark Red
        unity.put('5', "#AA00AA"); // Dark Purple
        unity.put('6', "#FFAA00"); // Gold
        unity.put('7', "#AAAAAA"); // Gray
        unity.put('8', "#555555"); // Dark Gray
        unity.put('9', "#5555FF"); // Blue
        unity.put('a', "#55FF55"); // Green
        unity.put('b', "#55FFFF"); // Aqua
        unity.put('c', "#FF5555"); // Red
        unity.put('d', "#FF55FF"); // Light Purple
        unity.put('e', "#FFFF55"); // Yellow
        unity.put('f', "#FFFFFF"); // White

        ansi.put('0', "\u001B[30m"); // Black
        ansi.put('1', "\u001B[34m"); // Dark Blue
        ansi.put('2', "\u001B[32m"); // Dark Green
        ansi.put('3', "\u001B[36m"); // Dark Aqua
        ansi.put('4', "\u001B[31m"); // Dark Red
        ansi.put('5', "\u001B[35m"); // Dark Purple
        ansi.put('6', "\u001B[33m"); // Gold
        ansi.put('7', "\u001B[37m"); // Gray
        ansi.put('8', "\u001B[90m"); // Dark Gray
        ansi.put('9', "\u001B[94m"); // Blue
        ansi.put('a', "\u001B[92m"); // Green
        ansi.put('b', "\u001B[96m"); // Aqua
        ansi.put('c', "\u001B[91m"); // Red
        ansi.put('d', "\u001B[95m"); // Light Purple
        ansi.put('e', "\u001B[93m"); // Yellow
        ansi.put('f', "\u001B[97m"); // White
    }

    @Builder.Default private int size = -1; // Unity only.
    @Builder.Default private boolean bold = false; // Unity only.
    @Builder.Default private boolean italic = false; // Unity only.

    @Builder.Default private Color color = null;

    /**
     * Replaces detected sequences of &color with the specified text.
     *
     * @param input The input text.
     * @return The replaced text.
     */
    private String replaceUnity(String input) {
        // Thanks ChatGPT! (from ChatGPT)
        // Check if the input string is null or empty
        if (input == null || input.isEmpty()) {
            return "";
        }

        var output = new StringBuilder();
        var i = 0;
        while (i < input.length()) {
            var c = input.charAt(i);
            if (c == '&') {
                // Check if the Minecraft color code is valid
                if (i + 1 < input.length() && unity.containsKey(input.charAt(i + 1))) {
                    // Append the Unity color code
                    output.append("<color=").append(unity.get(input.charAt(i + 1))).append(">");

                    // Move the index past the Minecraft color code
                    i += 2;

                    // Find the end of the color code span
                    var end = input.indexOf('&', i);
                    if (end == -1) {
                        end = input.length();
                    }

                    // Append the text within the color code span
                    output.append(input, i, end);

                    // Append the closing tag for the Unity color code
                    output.append("</color>");

                    // Move the index to the end of the color code span
                    i = end;
                } else {
                    // Invalid Minecraft color code, treat it as regular text
                    output.append(c);
                    i++;
                }
            } else {
                // Append regular text
                output.append(c);
                i++;
            }
        }

        return output.toString();
    }

    /**
     * Replaces detected sequences of &color with the specified text.
     *
     * @param input The input text.
     * @return The replaced text.
     */
    private String replaceTerminal(String input) {
        // Check if the input string is null or empty
        if (input == null || input.isEmpty()) {
            return "";
        }

        var output = new StringBuilder();
        var i = 0;
        while (i < input.length()) {
            var c = input.charAt(i);
            if (c == '&') {
                // Check if the Minecraft color code is valid
                if (i + 1 < input.length() && ansi.containsKey(input.charAt(i + 1))) {
                    // Append the ANSI escape code
                    output.append(ansi.get(input.charAt(i + 1)));

                    // Move the index past the Minecraft color code
                    i += 2;

                    // Find the end of the color code span
                    var end = input.indexOf('&', i);
                    if (end == -1) {
                        end = input.length();
                    }

                    // Append the text within the color code span
                    output.append(input, i, end);

                    // Reset the color back to default
                    output.append("\u001B[0m");

                    // Move the index to the end of the color code span
                    i = end;
                } else {
                    // Invalid Minecraft color code, treat it as regular text
                    output.append(c);
                    i++;
                }
            } else {
                // Append regular text
                output.append(c);
                i++;
            }
        }

        return output.toString();
    }

    /**
     * Wraps the text in the style. Formatted for Unity clients.
     *
     * @param text The text to wrap.
     * @return The wrapped text.
     */
    public String toUnity(String text) {
        var builder = new StringBuilder();

        // Set the size.
        if (this.size != -1) {
            builder.append("<size=").append(this.size).append(">");
        }

        // Set the color.
        if (this.color != null) {
            builder
                    .append("<color=")
                    .append(
                            String.format(
                                    "#%02x%02x%02x",
                                    this.color.getRed(), this.color.getGreen(), this.color.getBlue()))
                    .append(">");
        }

        // Set the boldness.
        if (this.bold) builder.append("<b>");
        // Set the italicness.
        if (this.italic) builder.append("<i>");

        // Append the text.
        builder.append(this.replaceUnity(text));

        // Close the tags.
        if (this.italic) builder.append("</i>");
        if (this.bold) builder.append("</b>");
        if (this.color != null) builder.append("</color>");
        if (this.size != -1) builder.append("</size>");

        return builder.toString();
    }

    /**
     * Wraps the text in the style. Formatted for terminal clients.
     *
     * @param text The text to wrap.
     * @return The wrapped text.
     */
    public String toTerminal(String text) {
        // Check for color.
        if (this.color == null) return this.replaceTerminal(text);

        // Convert the color to an ANSI color.
        var ansiColor =
                this.color.getRed() > 127
                        ? this.color.getGreen() > 127
                                ? this.color.getBlue() > 127 ? 15 : 11
                                : this.color.getBlue() > 127 ? 13 : 9
                        : this.color.getGreen() > 127
                                ? this.color.getBlue() > 127 ? 14 : 10
                                : this.color.getBlue() > 127 ? 12 : 8;

        // Return the text with the ANSI color.
        // Reset the color at the end.
        return "\u001B[38;5;" + ansiColor + "m" + this.replaceTerminal(text) + "\u001B[0m";
    }
}
