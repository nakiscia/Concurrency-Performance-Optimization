import java.io.FileReader;
import java.util.Scanner;

public class MatrisReadProducer extends Thread{
    private final int N = 10;
    private Scanner scanner;
    private MatrisMultiplier matrisMultiplier;

    public MatrisReadProducer(FileReader reader, MatrisMultiplier matrisMultiplier) {
        scanner = new Scanner(reader);
        this.matrisMultiplier = matrisMultiplier;
    }

    private float[][] readMatrix(){
        float[][] matrix = new float[N][N];
        for (int r = 0; r < N; r++) {
            if(!scanner.hasNext())
                return null;
            String[] line = scanner.nextLine().split(", ");
            for (int c = 0; c < N; c++) {
                matrix[r][c] = Float.valueOf(line[c]);
            }
        }

        if(!scanner.hasNextLine())
            return null;

        scanner.nextLine();
        return matrix;
    }

    @Override
    public void run() {
        while(true){
            float[][] matrix1 = readMatrix();
            float[][] matrix2 = readMatrix();

            if(matrix1 == null || matrix2 == null)
            {
                matrisMultiplier.terminate();
                System.out.println("No more matrix to read");
                return;
            }

            MatricesPair matricesPair = new MatricesPair();
            matricesPair.setMatrix1(matrix1);
            matricesPair.setMatrix2(matrix2);

            matrisMultiplier.add(matricesPair);
        }
    }



}
