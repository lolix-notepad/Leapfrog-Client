package dev.leap.frog.Util;

public class Timer {

    private long time;

    public Timer() {
        this.time = -1L;
    }

    public boolean passed(long ms) {
        return this.getTime(System.nanoTime() - this.time) >= ms;
    }

    public void reset() {
        this.time = System.nanoTime();
    }

    public long getTime(final long time) {
        return time / 1000000L;
    }


}
