package dev.mazurkiewicz.florystyka.utils;

public class TestTimer {
    private final int minutes;
    private final int seconds;

    public TestTimer(int minutes, int seconds) {
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }
}
