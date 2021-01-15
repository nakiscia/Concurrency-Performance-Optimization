import java.util.Random;

public class PriceUpdater extends Thread {

    private PriceContainer priceContainer;
    private Random random= new Random();// Simulate the price
    public PriceUpdater(PriceContainer priceContainer) {
        this.priceContainer = priceContainer;
    }

    @Override
    public void run() {
        while(true){
            priceContainer.getLockObject().lock();
            try{
                try {
                    Thread.sleep(1000); // For simulate the network connection..
                } catch (InterruptedException e) {}

                priceContainer.setBitcoinCashPrice(random.nextInt(20000));
                priceContainer.setEtherPrice(random.nextInt(20000));
                priceContainer.setBitcoinCashPrice(random.nextInt(20000));
                priceContainer.setRipplePrice(random.nextInt(20000));
                priceContainer.setLitecoinPrice(random.nextInt(20000));

            } finally {
                priceContainer.getLockObject().unlock();
            }
            // Again for simulation..
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
