package kr.hhplus.be.server.shared;

public class RandomUtil {
    public static int Range(int fromInclusive, int toExclusive) {
        return (int) ((Math.random() * (toExclusive - fromInclusive)) + fromInclusive);
    }
    
    public static boolean Bool() {
        return Math.random() < 0.5;
    }
}