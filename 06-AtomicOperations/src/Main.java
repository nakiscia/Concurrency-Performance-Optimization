import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Metrics metrics = new Metrics();

        BusinessLogic businessLogic1 = new BusinessLogic(metrics);
        BusinessLogic businessLogic2 = new BusinessLogic(metrics);
        MetricPrinter metricPrinter = new MetricPrinter(metrics);

        businessLogic1.start();
        businessLogic2.start();
        metricPrinter.start();
    }

    public static class MetricPrinter extends Thread{
        Metrics metrics;

        public MetricPrinter(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                double currentAverage = metrics.getAverage();
                System.out.println("Current Average is " +currentAverage);
            }
        }
    }


    public static class BusinessLogic extends Thread{
        Metrics metrics;
        Random random = new Random();
        public BusinessLogic(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {

            long startTime = System.currentTimeMillis();
            try{
                int i = random.nextInt(10);
                Thread.sleep(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();

            metrics.addSample(endTime-startTime);
        }
    }


    public static class Metrics{
        private long count = 0;
        // double is not atomic variable type, because of that we add volatile keyword to make it atomic
        private volatile double average = 0.0;

        public synchronized void addSample(long sample)
        {
            double currentSum = average * count;
            count++;
            average = (currentSum+sample)/ count;
        }

        // Getters and setters are atomic no need to add synchronized.
        public double getAverage() {
            return average;
        }
    }
}
