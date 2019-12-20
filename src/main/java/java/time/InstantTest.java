package java.time;

import org.junit.Test;

public class InstantTest {

    /**
     * Instant 表示时间线的某个点
     */
    @Test
    public void testInstant() {
        Instant now = Instant.now();
        System.out.println(now);
    }

    /**
     * Duration 表示两个时刻之间的时间量
     */
    @Test
    public void testDuration() throws InterruptedException {
        Instant start = Instant.now();
        Thread.sleep(1000);
        Instant end = Instant.now();
        Duration between = Duration.between(start, end);
        System.out.println(between.toMillis());
    }

    /**
     * LocalDate 本地时间
     */
    @Test
    public void testLocalDate() {
        LocalDate today = LocalDate.now();
        System.out.println("today:" + today);
    }

}
