package ES;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class TestLeak {

    class Big {
        double[] data = new double[100000];
    }

    @Test
    public void testLeak() throws InterruptedException {

        BoundedBuffer<Big> bb = new BoundedBuffer<Big>(10);
        System.gc();
        // Get current size of heap in bytes
        long heapSize1 = Runtime.getRuntime().freeMemory();
        for (int i = 0; i < 10; i++)
            bb.put(new Big());
        long heapSize3 = Runtime.getRuntime().freeMemory();
        long threshHold = Math.abs(heapSize1 - heapSize3);
        // assertNull(Math.abs(heapSize1 - heapSize3));
        assertTrue(Math.abs(heapSize1 - heapSize3) > 0);
        for (int i = 0; i < 10; i++)
            bb.take();
        System.gc();
        long heapSize2 = Runtime.getRuntime().freeMemory();
        // assertNull(Math.abs(heapSize1 - heapSize2));
        assertTrue(Math.abs(heapSize1 - heapSize2) <= threshHold);
    }
}