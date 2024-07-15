import java.awt.Color;

public class ImageClassifier {
    // Creates a feature vector (1D array) from the given picture.
    public static double[] extractFeatures(Picture picture) {

        // Reading the picture characteristics (width and height)
        int width = picture.width();
        int height = picture.height();
        double[] vector = new double[width * height];
        // Creating the 1D vector
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                Color color = picture.get(col, row);
                int r = color.getRed();
                vector[width * row + col] = r;
            }
        }
        return vector;
    }

    // See below.
    // Image classifier main
    public static void main(String[] args) {

        // Reading files with In.
        In file = new In(args[0]);

        int m = file.readInt();
        int width = file.readInt();
        int heigth = file.readInt();
        // Creating a multiPerceptron
        MultiPerceptron multiPerceptron = new MultiPerceptron(m, (width * heigth));

        while (!file.isEmpty()) {
            String image = file.readString();
            int label = file.readInt();
            Picture picture = new Picture(image);
            // extracting features of the picture
            double[] x = extractFeatures(picture);
            // Training the multiPerceptron
            multiPerceptron.trainMulti(x, label);
        }

        // Reading values from In
        In file2 = new In(args[1]);
        file2.readInt();
        file2.readInt();
        file2.readInt();

        double sum = 0.0;
        double total = 0.0;
        while (!file2.isEmpty()) {
            String image1 = file2.readString();
            int label1 = file2.readInt();
            Picture picture1 = new Picture(image1);
            // Extracting picture's features
            double[] x1 = extractFeatures(picture1);
            // Predicting the label
            int prediction = multiPerceptron.predictMulti(x1);
            total += 1;

            // Reading the incorrect predictions using StdOut
            if (prediction != label1) {
                sum += 1;
                StdOut.println(image1 + "," + " label = "
                                       + label1 + ","
                                       + " predict = "
                                       + prediction);

            }
        }
        // Calculating the test error.
        StdOut.println("test error rate = " + sum / total);

    }
}
