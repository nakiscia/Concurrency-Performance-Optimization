public class SyncInventory {

    private int item=0;
    Object lockObject = new Object();
    public void increaseItem(){
        synchronized (lockObject)
        {
            item++;
        }
    }

    public void decreaseItem(){
        synchronized (lockObject)
        {
            item--;
        }
    }

    public int getItem() {
            return item;
    }

}
