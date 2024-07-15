public class MultiPerceptron {

    // multi perceptron
    private Perceptron[] multiPerceptron;

    // Creates a multi-perceptron object with m classes and n inputs.
    // It creates an array of m perceptrons, each with n inputs.
    public MultiPerceptron(int m, int n) {
        multiPerceptron = new Perceptron[m];
        for (int i = 0; i < m; i++) {
            multiPerceptron[i] = new Perceptron(n);
        }
    }

    // Returns the number of classes m.
    public int numberOfClasses() {
        return multiPerceptron.length;
    }

    // Returns the number of inputs n (length of the feature vector).
    public int numberOfInputs() {
        return multiPerceptron[0].numberOfInputs();
    }

    // Returns the predicted label (between 0 and m-1) for the given input.
    public int predictMulti(double[] x) {
        int m = numberOfClasses();
        int label = 0;
        double oscore = multiPerceptron[0].weightedSum(x);

        for (int i = 1; i < m; i++) {
            double sum = multiPerceptron[i].weightedSum(x);
            if (sum > oscore) {
                oscore = sum;
                label = i;
            }
        }

        return label;
    }


    // Trains this multi-perceptron on the labeled (between 0 and m-1) input.
    public void trainMulti(double[] x, int label) {
        // Train all the individual perceptrons within the multi-perceptron.
        // Train each individual perceptron within the multi-perceptron.
        for (int i = 0; i < numberOfClasses(); i++) {
            if (i == label) {
                multiPerceptron[i].train(x, 1);
            }
            else {
                multiPerceptron[i].train(x, -1);
            }
        }
    }

    // Returns a String representation of this MultiPerceptron, with
    // the string representations of the perceptrons separated by commas
    // and enclosed in parentheses.
    // Example with m = 2 and n = 3: ((2.0, 0.0, -2.0), (3.0, 4.0, 5.0))
    public String toString() {

        StringBuilder mRepresentation = new StringBuilder("(");
        for (int i = 0; i < numberOfClasses(); i++) {
            mRepresentation.append(multiPerceptron[i].toString());
            if (i < numberOfClasses() - 1) {
                mRepresentation.append(", ");
            }
        }
        mRepresentation.append(")");
        return mRepresentation.toString();
    }

    // Calling the functions to test them.
    public static void main(String[] args) {

        int n = 3;
        int m = 10;
        MultiPerceptron multiPerceptron = new MultiPerceptron(m, n);
        double[] x = { -11, 2, 1 };
        int label = 6;
        // Reading the number of classes
        StdOut.println(multiPerceptron.numberOfClasses());
        // Reading the number of Inputs
        StdOut.println(multiPerceptron.numberOfInputs());
        // Reading the predicted label
        StdOut.println(multiPerceptron.predictMulti(x));
        // Training our multiPerceptron
        multiPerceptron.trainMulti(x, label);
        // multiPerceptron in form of string.
        StdOut.println(multiPerceptron.toString());


    }
}

