package Sequential;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String SOURCE_FILE = "./03-ImageProcessing/resources/image.jpg";
    private static final String DESTINATION_FILE = "./out/result.jpg";

    public static void main(String[] args) throws IOException, InterruptedException {
        final File input = new File(SOURCE_FILE);
        BufferedImage originalImage = ImageIO.read(input);
        BufferedImage resultImage = new BufferedImage(originalImage.getWidth(),originalImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        File outputFile = new File(DESTINATION_FILE);
        Long startTime = System.currentTimeMillis();
        //recolorSingleThreaded(originalImage,resultImage);
        recolorMultiThreaded(originalImage,resultImage,6);
        ImageIO.write(resultImage,"jpg",outputFile);
        Long endTime = System.currentTimeMillis();
        System.out.println(String.valueOf(endTime-startTime));
    }

    public static void recolorMultiThreaded(BufferedImage image,BufferedImage result,int numberOfThreads) throws InterruptedException {
        List<Thread> threadArrayList = new ArrayList<>();
        int width = image.getWidth();
        int height = image.getHeight()/numberOfThreads;

        for (int i = 0; i < numberOfThreads; i++) {
            int heightMultiplier = i;
            Thread thread = new Thread(()->{
                int leftCorner = 0;
                int topCorner = height * heightMultiplier;

                recolorImage(image,result,leftCorner,topCorner,width,height);
            });
            threadArrayList.add(thread);
        }

        for (Thread t: threadArrayList) {
            t.start();
        }

        for (Thread t: threadArrayList) {
            t.join();
        }

    }


    public static void recolorSingleThreaded(BufferedImage image,BufferedImage result)
    {
        recolorImage(image,result,0,0,image.getWidth(),image.getHeight());
    }

    public static void recolorImage(BufferedImage image,BufferedImage resultImage,
                                    int leftCorner,int topCorner,
                                    int width, int height)
    {
        for(int i = leftCorner;i<leftCorner+width && i<image.getWidth(); i++)
        {
            for (int j = topCorner; j<topCorner+height && j< image.getHeight();j++)
            {
                recolorPixel(image,resultImage,i,j);
            }
        }
    }

    public static void recolorPixel(BufferedImage originalImage,BufferedImage resultImage,int x, int y)
    {
        int rgb = originalImage.getRGB(x,y);

        int red = getRed(rgb);
        int green = getGreen(rgb);
        int blue = getBlue(rgb);

        int newRed;
        int newGreen;
        int newBlue;

        if(isShadeOfGray(red,green,blue)){
            newRed = Math.min(255,red+10);
            newGreen = Math.max(0,green-80);
            newBlue = Math.max(0,blue-20);
        }else{
            newRed = red;
            newGreen = green;
            newBlue =blue;
        }

        int newRGB = createRGBFromColors(newRed,newGreen,newBlue);
        setRGB(resultImage,x,y,newRGB);
    }

    public static void setRGB(BufferedImage image,int x, int y, int rgb)
    {
        image.getRaster().setDataElements(x,y,image.getColorModel().getDataElements(rgb,null));
    }

    public static boolean isShadeOfGray(int red, int green, int blue){
        return Math.abs(red-green) < 30 && Math.abs(red-blue) <30 && Math.abs(green-blue) <30;
    }

    public static int createRGBFromColors(int red,int green,int blue){
        int rgb =0;
        rgb |= blue;
        rgb |= green <<8;
        rgb |= red << 16;
        rgb |= 0xFF000000;
        return rgb;
    }

    public static int getRed(int rgb)
    {
        int onlyRed = (rgb & 0x00FF0000) >> 16;
        return onlyRed;
    }

    public static int getGreen(int rgb){
        int onlyGreen = (rgb & 0x0000FF00) >> 8;
        return onlyGreen;
    }

    public static int getBlue(int rgb){
        int onlyBlue = rgb & 0x000000FF;
        return onlyBlue;
    }

}
