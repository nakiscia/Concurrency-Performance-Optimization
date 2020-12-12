public class Interruption1 {

    public static void main(String[] args) {
        Thread thread = new Thread(new BlockingThread());
        thread.start(); // Thread is started..
        thread.interrupt(); // and interrupted...
    }

    private static class BlockingThread implements Runnable{
        @Override
        public void run() {
            try {
                Thread.sleep(50000);
            } catch (InterruptedException e) {
                System.out.println("Exiting the current thread...");
                e.printStackTrace();
            }
        }
    }

}
