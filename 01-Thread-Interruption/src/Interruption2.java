import java.math.BigInteger;

public class Interruption2 {

    public static void main(String[] args) {

        Thread thread = new Thread(new LongComputation(new BigInteger("200000"),new BigInteger("100000000000")));
        thread.setDaemon(true); // Application will end main thread and mill not wait that thread to complete.
        thread.start();
        thread.interrupt();
    }

    private static class LongComputation implements Runnable{

        BigInteger base;
        BigInteger power;

        public LongComputation(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            BigInteger bigInteger = calculatePow(base, power);
            System.out.println("Result is : "+ bigInteger);
        }

        public BigInteger calculatePow(BigInteger base, BigInteger power)
        {
            BigInteger result = BigInteger.ONE;

            for(BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE))
            {
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("Calculation thread is interrupted..");
                    return BigInteger.ZERO;
                }
                result = result.multiply(base);
            }

            return result;
        }

    }

}
