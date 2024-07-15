public class Perceptron {

    // Perceptron calculated
    private double[] weights;

    // Creates a perceptron with n inputs. It should create an array
    // of n weights and initialize them all to 0.
    public Perceptron(int n) {
        weights = new double[n];
        for (int i = 0; i < n; i++) {
            weights[i] = 0.0;
        }
    }

    // Returns the number of inputs n.
    public int numberOfInputs() {
        int n = weights.length;
        return n;
    }

    // Returns the weighted sum of the weight vector and x.
    public double weightedSum(double[] x) {
        double wSum = 0;
        for (int i = 0; i < x.length; i++) {
            double product = x[i] * weights[i];
            wSum += product;
        }
        return wSum;
    }

    // Predicts the label (+1 or -1) of input x. It returns +1
    // if the weighted sum is positive and -1 if it is negative (or zero).
    public int predict(double[] x) {
        if (weightedSum(x) > 0) return +1;
        return -1;
    }

    // Trains this perceptron on the labeled (+1 or -1) input x.
    // The weights vector is updated accordingly.
    public void train(double[] x, int label) {

        if (predict(x) == 1 && label == -1)
            for (int i = 0; i < weights.length; i++) {
                weights[i] = this.weights[i] - x[i];
            }

        else if (predict(x) == -1 && label == 1)
            for (int i = 0; i < weights.length; i++) {
                weights[i] = this.weights[i] + x[i];
            }
    }


    // Returns a String representation of the weight vector, with the
    // individual weights separated by commas and enclosed in parentheses.
    // Example: (2.0, 1.0, -1.0, 5.0, 3.0)
    public String toString() {

        StringBuilder representation = new StringBuilder("(");
        for (int i = 0; i < weights.length; i++) {
            representation.append(weights[i]);
            if (i < weights.length - 1) {
                representation.append(", ");
            }
        }
        representation.append(")");
        return representation.toString();
    }

    // Perceptron main to test the functions we created above.
    public static void main(String[] args) {
        int n = 3;
        // creating a perceptron
        Perceptron perceptron = new Perceptron(n);
        double[] x = { -11, 2, 1 };
        int label = -1;
        perceptron.train(x, label);
        // reading number of Inputs
        StdOut.println(perceptron.numberOfInputs());
        // predicting the label
        StdOut.println(perceptron.predict(x));
        // Calculating the weighted sum
        StdOut.println(perceptron.weightedSum(x));

    }
}
