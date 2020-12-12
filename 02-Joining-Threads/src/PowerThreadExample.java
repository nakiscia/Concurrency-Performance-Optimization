import java.math.BigInteger;



public class PowerThreadExample {
    public static void main(String[] args) throws InterruptedException {

        ComplexCalculation calculation = new ComplexCalculation();
        System.out.println(calculation.calculateResult(
                new BigInteger("20"),
                new BigInteger("10"),
                new BigInteger("30"),
                new BigInteger("15")));

    }
}

// Calculate the result = base1^power1 + base2^power2

class ComplexCalculation {
    public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) throws InterruptedException {
        BigInteger result;

        PowerCalculatingThread thread1 = new PowerCalculatingThread(base1,power1);
        PowerCalculatingThread thread2 = new PowerCalculatingThread(base2,power2);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        System.out.println(thread1.getResult());
        System.out.println(thread2.getResult());

        result = thread1.getResult().add(thread2.getResult());

        return result;
    }

    private static class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private BigInteger base;
        private BigInteger power;

        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            pow();
        }

        private void pow(){
            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
                result = result.multiply(base);
            }
        }

        public BigInteger getResult() { return result; }
    }
}