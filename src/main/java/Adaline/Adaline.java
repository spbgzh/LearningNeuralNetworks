package Adaline;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.learning.IterativeLearning;
import org.neuroph.core.learning.LearningRule;
import org.neuroph.nnet.learning.LMS;

import java.util.Arrays;

public class Adaline {
    public static void main(String[] args) {
        NeuralNetwork myAdaline= new org.neuroph.nnet.Adaline(35);
        LMS lms = new LMS();
        lms.setLearningRate(0.005);
        lms.setMaxError(0.05);
        LearningRule rule = lms;
        LearningEventListener listener= event -> {
            IterativeLearning flag = (IterativeLearning)event.getSource();
            System.out.println("Iterate:"+flag.getCurrentIteration());
            System.out.println(Arrays.toString(flag.getNeuralNetwork().getWeights()));
        };
        rule.addListener(listener);
        myAdaline.setLearningRule(rule);

    }

}
