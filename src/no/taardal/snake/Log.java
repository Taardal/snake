package no.taardal.snake;

public class Log {

    public static void logBreak() {
        log("\n");
    }

    public static void logLine() {
        log("==============================================");
    }

    public static void log(String string) {
        System.out.println(string);
    }

}
