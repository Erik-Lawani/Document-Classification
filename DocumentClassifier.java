import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DocumentClassifier {
    private MultiPerceptron classifier;
    private int width;
    private int height;

    public DocumentClassifier(int numCategories, int imageWidth, int imageHeight) {
        width = imageWidth;
        height = imageHeight;
        classifier = new MultiPerceptron(numCategories, width * height);
    }

    // Extract features from a document image
    private double[] extractFeatures(BufferedImage image) {
        double[] features = new double[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int grayscale = (((rgb >> 16) & 0xFF) + ((rgb >> 8) & 0xFF) + (rgb & 0xFF)) / 3;
                features[y * width + x] = grayscale / 255.0; // Normalize to 0-1
            }
        }
        return features;
    }

    // Train the classifier on a single document
    public void trainOnDocument(File imageFile, int category) throws IOException {
        BufferedImage image = ImageIO.read(imageFile);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        resized.getGraphics().drawImage(image, 0, 0, width, height, null);
        double[] features = extractFeatures(resized);
        classifier.trainMulti(features, category);
    }

    // Classify a document
    public int classifyDocument(File imageFile) throws IOException {
        BufferedImage image = ImageIO.read(imageFile);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        resized.getGraphics().drawImage(image, 0, 0, width, height, null);
        double[] features = extractFeatures(resized);
        return classifier.predictMulti(features);
    }

    public static void main(String[] args) throws IOException {
        // Example usage
        DocumentClassifier classifier = new DocumentClassifier(5, 100,
                                                               100); // 5 categories, 100x100 images

        // Training
        String[] categories = { "legal", "financial", "medical", "personal", "other" };
        File trainingDir = new File("training_documents");
        for (File categoryDir : trainingDir.listFiles()) {
            int categoryIndex = java.util.Arrays.asList(categories).indexOf(categoryDir.getName());
            for (File document : categoryDir.listFiles()) {
                classifier.trainOnDocument(document, categoryIndex);
            }
        }

        // Classification
        File testDir = new File("test_documents");
        for (File document : testDir.listFiles()) {
            int predictedCategory = classifier.classifyDocument(document);
            System.out.println(
                    document.getName() + " classified as: " + categories[predictedCategory]);
        }
    }
} clp
