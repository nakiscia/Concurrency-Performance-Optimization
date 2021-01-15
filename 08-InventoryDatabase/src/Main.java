import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private static final int HIGHEST_PRICE = 1000;

    public static void main(String[] args) throws InterruptedException {
        InventoryDatabase inventoryDatabase = new InventoryDatabase();
        Random randomPrice = new Random();
        for (int i = 0; i < 10000; i++) {
            inventoryDatabase.addItem(randomPrice.nextInt(1000));
        }

        // writer Thread
        Thread writerThread = new Thread(()->{
           inventoryDatabase.addItem(randomPrice.nextInt(HIGHEST_PRICE));
           inventoryDatabase.removeItem(randomPrice.nextInt(HIGHEST_PRICE));

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        writerThread.setDaemon(true);
        writerThread.start();

        int numberOfReaderThread = 7;
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < numberOfReaderThread; i++) {
            Thread thread = new Thread(()->{
                for (int j = 0; j < 100000; j++) {
                    int upper = randomPrice.nextInt(HIGHEST_PRICE);
                    int lower = upper>0 ? randomPrice.nextInt(upper):0;
                    inventoryDatabase.getNumberOfItemsInPriceRange(lower,upper);
                }
            });

            threads.add(thread);
        }

        long start = System.currentTimeMillis();

        for(Thread t : threads)
            t.start();

        for(Thread t: threads)
            t.join();

        long end = System.currentTimeMillis();
        System.out.println(String.format("The time passed %d : ",end-start));
    }

}
