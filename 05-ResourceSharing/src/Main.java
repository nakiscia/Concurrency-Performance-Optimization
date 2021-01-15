public class Main {

    public static void main(String[] args) throws InterruptedException {
        Inventory inventory = new Inventory();
        IncreaseItemThread increaseItemThread = new IncreaseItemThread(inventory);
        DecreaseItemThread decreaseItemThread = new DecreaseItemThread(inventory);

        increaseItemThread.start();
        decreaseItemThread.start();
        increaseItemThread.join();
        decreaseItemThread.join();
        System.out.println("Non-Sync result : "+inventory.getItem());


        SyncInventory syncInventory = new SyncInventory();
        SyncIncreaseItemThread syncIncreaseItemThread = new SyncIncreaseItemThread(syncInventory);
        SyncDecreaseItemThread syncDecreaseItemThread = new SyncDecreaseItemThread(syncInventory);
        syncIncreaseItemThread.start();
        syncDecreaseItemThread.start();
        syncIncreaseItemThread.join();
        syncDecreaseItemThread.join();

        System.out.println("Non-Sync result : "+syncInventory.getItem());

    }

    private static class IncreaseItemThread extends Thread{

        Inventory inventory;

        public IncreaseItemThread(Inventory inventory) {
            this.inventory = inventory;
        }


        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                inventory.increaseItem();
            }
        }
    }

    private static class DecreaseItemThread extends Thread{
        Inventory inventory;

        public DecreaseItemThread(Inventory inventory) {
            this.inventory = inventory;
        }

        @Override
        public void run(){
            for (int i = 0; i < 1000; i++) {
                inventory.decreaseItem();
            }
        }
    }

    private static class SyncIncreaseItemThread extends Thread{

        SyncInventory inventory;

        public SyncIncreaseItemThread(SyncInventory inventory) {
            this.inventory = inventory;
        }


        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                inventory.increaseItem();
            }
        }
    }

    private static class SyncDecreaseItemThread extends Thread{
        SyncInventory inventory;

        public SyncDecreaseItemThread(SyncInventory inventory) {
            this.inventory = inventory;
        }

        @Override
        public void run(){
            for (int i = 0; i < 1000; i++) {
                inventory.decreaseItem();
            }
        }
    }
}
