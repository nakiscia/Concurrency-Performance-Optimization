import java.io.*;

public class MainApplication {

    private static final String OUTPUT_FILE = "./out/matrices_result";
    private static final String INPUT_FILE = "./out/matrices";

    public static void main(String[] args) throws IOException, InterruptedException {
        MatrisMultiplier matrisMultiplier = new MatrisMultiplier();
        File inputFile = new File(INPUT_FILE);
        File outputFile = new File(OUTPUT_FILE);

        MatrisReadProducer matrisReadProducer = new MatrisReadProducer(new FileReader(inputFile),matrisMultiplier);
        MatrisMultiplierConsumer matrisMultiplierConsumer = new MatrisMultiplierConsumer(new FileWriter(outputFile),matrisMultiplier);

        matrisReadProducer.start();
        matrisMultiplierConsumer.start();
    }


}
