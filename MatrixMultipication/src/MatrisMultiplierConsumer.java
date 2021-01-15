import java.io.FileWriter;
import java.io.IOException;
import java.util.StringJoiner;

public class MatrisMultiplierConsumer extends Thread{
    private final int N =10;
    MatrisMultiplier matrisMultiplier;
    FileWriter fileWriter;

    public MatrisMultiplierConsumer(FileWriter fileWriter, MatrisMultiplier matrisMultiplier) {
        this.matrisMultiplier = matrisMultiplier;
        this.fileWriter = fileWriter;
    }

    @Override
    public void run() {
        while(true)
        {
            try {
                MatricesPair matricesPair = matrisMultiplier.remove();
                if (matricesPair == null)
                {
                    System.out.println("No more matrices to multiply..");
                    break;
                }

                float result[][] = multiplyMatrices(matricesPair.getMatrix1(),matricesPair.getMatrix2());

                saveToFile(fileWriter,result);

            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void saveToFile(FileWriter fileWriter, float[][] result) throws IOException {
        for (int r = 0; r < N; r++) {
            StringJoiner stringJoiner = new StringJoiner(", ");
            for (int c = 0; c < N; c++) {
                stringJoiner.add(String.format("%.2f", result[r][c]));
            }
            fileWriter.write(stringJoiner.toString());
            fileWriter.write('\n');
        }
        fileWriter.write('\n');
    }

    private float[][] multiplyMatrices(float[][] matris1, float[][] matris2){
        float[][] result = new float[N][N];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                for (int k = 0; k < N; k++) {
                    result[r][c] += matris1[r][k] * matris2[k][c];
                }
            }
        }

        return result;
    }
}
