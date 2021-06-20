package Perceptron;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.learning.IterativeLearning;
import org.neuroph.core.learning.LearningRule;
import org.neuroph.nnet.Perceptron;
import org.neuroph.nnet.learning.PerceptronLearning;

import java.util.Arrays;
import java.util.Random;

public class PerceptronClassify {

    public static void main(String[] args) {

        DataSet data =new DataSet(2,2);
        data.add(new DataSetRow(new double[]{123,123},new double[]{1,1}));
        data.add(new DataSetRow(new double[]{323,-321},new double[]{1,0}));
        data.add(new DataSetRow(new double[]{-67,-34},new double[]{0,0}));
        data.add(new DataSetRow(new double[]{-33,43},new double[]{0,1}));
        NeuralNetwork myPerceptron = new Perceptron(2,2);
        LearningEventListener listener= event -> {
            IterativeLearning flag = (IterativeLearning)event.getSource();
            System.out.println("Iterate:"+flag.getCurrentIteration());
            System.out.println(Arrays.toString(flag.getNeuralNetwork().getWeights()));
        };
        LearningRule rule = new PerceptronLearning();
        rule.addListener(listener);
        myPerceptron.setLearningRule(rule);
        myPerceptron.learn(RandomLocation.randomLocation());
        myPerceptron.save("output/Perceptron/PerceptronClassify.nnet");
        PerceptronClassifyNoLearn.testNeuralNetwork(myPerceptron,data);

    }

}

class RandomLocation{
    static Random r = new Random();

    public static double nextDouble() {
        double re = 0;
        while ((re = r.nextDouble()) != 0) {
            return re;
        }
        return r.nextDouble();
    }

    public static DataSet randomLocation() {
        DataSet data = new DataSet(2, 2);
        for (int i = 0; i < 10000; i++) {
            data.add(new DataSetRow(new double[]{1 * nextDouble(), 1 * nextDouble()}, new double[]{1, 1}));
            data.add(new DataSetRow(new double[]{-1 * nextDouble(), 1 * nextDouble()}, new double[]{0, 1}));
            data.add(new DataSetRow(new double[]{-1 * nextDouble(), -1 * nextDouble()}, new double[]{0, 0}));
            data.add(new DataSetRow(new double[]{1 * nextDouble(), -1 * nextDouble()}, new double[]{1, 0}));
        }
        return data;
    }
}

