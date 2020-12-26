import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) {
        PriceContainer container = new PriceContainer();
        PriceUpdater updater = new PriceUpdater(container);
        PricePrinter printer = new PricePrinter(container);
        updater.start();
        printer.start();
    }
}
