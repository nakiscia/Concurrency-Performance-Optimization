
// This sample application is for
// calculate the factorial with threads

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        List<Long> inputs = Arrays.asList(0L,6L,10L,12L,1356L,2467L);
        List<FactorialCalculator> threads = new ArrayList<>();

        for (Long number:inputs) {
            threads.add(new FactorialCalculator(number));
        }

        for(Thread factorialThread : threads){
            factorialThread.start();
        }

        for(Thread factorialThread : threads){
            factorialThread.join(2000);
        }

        for (int i = 0; i < threads.size(); i++) {
            System.out.println(threads.get(i).getResult());
        }

    }

    private static class FactorialCalculator extends Thread{
        Long input;
        boolean isFinished = false;
        BigInteger result;

        public FactorialCalculator(Long input) {
            this.input = input;
            currentThread().setName(input.toString()+"Thread");
        }

        public BigInteger calculateFactorial(BigInteger number){
            if(number.compareTo(BigInteger.ZERO) == 0 || Thread.currentThread().isInterrupted())
                return BigInteger.ONE;
            return number.multiply(calculateFactorial(number.subtract(BigInteger.ONE)));
        }

        @Override
        public void run() {
            result = calculateFactorial(new BigInteger(input.toString()));
            isFinished = true;
        }

        private String getResult(){
            if(!isFinished)
                return "Calculation is not complete";
            return "The calculation result of factorial("+input+") is : "+result;
        }

    }

}
