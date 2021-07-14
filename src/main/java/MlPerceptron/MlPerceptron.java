package MlPerceptron;

import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.learning.IterativeLearning;
import org.neuroph.core.learning.LearningRule;
import org.neuroph.core.transfer.Linear;
import org.neuroph.nnet.comp.neuron.BiasNeuron;
import org.neuroph.nnet.comp.neuron.InputNeuron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.*;
import org.neuroph.util.random.NguyenWidrowRandomizer;

import java.util.Arrays;

public class MlPerceptron {
    public static void main(String[] args) {
        NeuralNetwork myMlPerceptron = new NeuralNetwork();
        myMlPerceptron.setNetworkType(NeuralNetworkType.MULTI_LAYER_PERCEPTRON);

        NeuronProperties inputNeuronProperties = new NeuronProperties(InputNeuron.class, Linear.class);
        Layer input = LayerFactory.createLayer(2, inputNeuronProperties);
        Layer middle = LayerFactory.createLayer(4, TransferFunctionType.SIGMOID);
        Layer output = LayerFactory.createLayer(1,TransferFunctionType.STEP);

        myMlPerceptron.addLayer(input);
        myMlPerceptron.addLayer(middle);
        myMlPerceptron.addLayer(output);
        NeuralNetworkFactory.setDefaultIO(myMlPerceptron);
        myMlPerceptron.getLayerAt(0).addNeuron(new BiasNeuron());

        ConnectionFactory.fullConnect(myMlPerceptron.getLayerAt(0), myMlPerceptron.getLayerAt(1));
        ConnectionFactory.fullConnect(myMlPerceptron.getLayerAt(1), myMlPerceptron.getLayerAt(2));

        LearningEventListener listener= event -> {
            IterativeLearning flag = (IterativeLearning)event.getSource();
            System.out.println("Iterate:"+flag.getCurrentIteration());
            System.out.println(Arrays.toString(flag.getNeuralNetwork().getWeights()));
        };
        myMlPerceptron.randomizeWeights(new NguyenWidrowRandomizer(-2,3));
        LearningRule rule = new BackPropagation();
        rule.addListener(listener);
        myMlPerceptron.setLearningRule(rule);

        DataSet learn = new DataSet(2,1);
        learn.add(new double[]{0, 0}, new double[]{0});
        learn.add(new double[]{0, 1}, new double[]{1});
        learn.add(new double[]{1, 0}, new double[]{1});
        learn.add(new double[]{1, 1}, new double[]{0});

        DataSet test = new DataSet(2,1);
        test.add(new double[]{0, 0}, new double[]{Double.NaN});
        test.add(new double[]{0, 1}, new double[]{Double.NaN});
        test.add(new double[]{1, 0}, new double[]{Double.NaN});
        test.add(new double[]{1, 1}, new double[]{Double.NaN});

        myMlPerceptron.save("output/MlPerceptron/MlPerceptron.nnet");

        myMlPerceptron.learn(learn);

        for (DataSetRow row : test.getRows()) {
            myMlPerceptron.setInput(row.getInput());
            myMlPerceptron.calculate();
            double[] networkOutput = myMlPerceptron.getOutput();
            System.out.println(Arrays.toString(row.getInput()) + "=" + Arrays.toString(networkOutput));
        }
    }
}
