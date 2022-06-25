package main.java.util;

/**
 * Class for format text - highlighting errors and informative messages
 */
public class Text {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String random = "\u001B[1m";

    public static String getRedText(String text) {
        return ANSI_RED + text + ANSI_RESET;
    }

    public static String getGreenText(String text) {
        return ANSI_GREEN + text + ANSI_RESET;
    }

    public static String getBlueText(String text) {
        return ANSI_BLUE + text + ANSI_RESET;
    }

    public static String getYellowText(String text) {
        return ANSI_YELLOW + text + ANSI_RESET;
    }

    public static String getNormalText(String text) {
        return random + text + ANSI_RESET;
    }


}
