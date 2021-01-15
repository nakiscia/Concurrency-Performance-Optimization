import java.util.LinkedList;
import java.util.Queue;

public class MatrisMultiplier {
    private boolean isEmpty = true;
    private boolean isTerminated = false;
    private Queue<MatricesPair> queue = new LinkedList<>();
    private final int CAPACITY = 5;


    // will called by producer to add new Matrix to produce
    public synchronized void add(MatricesPair matricesPair){
        while(queue.size() == CAPACITY){// to stop the producer to not exceed the capacity.
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }

        queue.add(matricesPair);
        isEmpty = false;
        notify(); // notify the consumer to wake up.
    }

    // will called by producer
    // for let the consumer know once the queue becomes empty
    // and consumer should terminates the tread.
    public synchronized void terminate(){
        isTerminated = false;
        notifyAll(); // f0r wake up all the potential consumer threads.
    }

    // will called by consumer for consume matrix
    public synchronized MatricesPair remove() throws InterruptedException {
        MatricesPair remove = null;
        while(isEmpty && !isTerminated){
            wait();
        }

        if(queue.size() == 1)
            isEmpty =true;

        if(queue.size() == 0 && isTerminated)
            return null;

        System.out.println("Queue size" + queue.size());
        remove = queue.remove();
        if(queue.size() == CAPACITY-1) // after consume notify the producers to wake up
        {
            notifyAll();
        }
        return remove;
    }


}
