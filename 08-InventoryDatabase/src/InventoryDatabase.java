import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class InventoryDatabase {
    private TreeMap<Integer,Integer> priceToMap = new TreeMap<>();
    // slower solution : regardless of operation this will lock all methods
    private Lock lockObject = new ReentrantLock();
    private ReentrantReadWriteLock reetrantWRLock =  new ReentrantReadWriteLock(); // Increase performance 3 times
    private Lock readerLock = reetrantWRLock.readLock();
    private Lock writerLock = reetrantWRLock.writeLock();



    // Read opeartion
    public int getNumberOfItemsInPriceRange(int lower, int higher)
    {
        //lockObject.lock();
        readerLock.lock();
        try{
            Integer fromKey = priceToMap.ceilingKey(lower);
            Integer toKey = priceToMap.ceilingKey(higher);

            if(fromKey == null || toKey ==null)
                return 0;

            NavigableMap<Integer,Integer> rangeOfPrices = priceToMap.subMap(fromKey,true,toKey,true);

            int sum=0;
            for(Integer i : rangeOfPrices.values())
            {
                sum += i;
            }

            return sum;
        }finally {
            //lockObject.unlock();
            readerLock.unlock();
        }
    }

    // Write operation
    public void addItem(int itemPrice){
        //lockObject.lock();
        writerLock.lock();
        try{
            priceToMap.put(itemPrice,priceToMap.getOrDefault(itemPrice,0)+1);
        }finally {
            //lockObject.unlock();
            writerLock.unlock();
        }
    }

    // Write operation
    public void removeItem(int itemPrice){
        //lockObject.lock();
        writerLock.lock();
        try{
            Integer item = priceToMap.get(itemPrice);
            if(item == null || item ==1)
                priceToMap.remove(item);
            else
                priceToMap.put(itemPrice,item-1);
        }finally {
            //lockObject.unlock();
            writerLock.unlock();
        }
    }
}
