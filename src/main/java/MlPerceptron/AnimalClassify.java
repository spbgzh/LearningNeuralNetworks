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

import java.util.Arrays;


public class AnimalClassify {
    public static void main(String[] args) {
        NeuralNetwork myMlPerceptron = new NeuralNetwork();
        myMlPerceptron.setNetworkType(NeuralNetworkType.MULTI_LAYER_PERCEPTRON);

        NeuronProperties inputNeuronProperties = new NeuronProperties(InputNeuron.class, Linear.class);
        Layer input = LayerFactory.createLayer(16, inputNeuronProperties);
        Layer middle = LayerFactory.createLayer(6, TransferFunctionType.SIGMOID);
        Layer output = LayerFactory.createLayer(7,TransferFunctionType.STEP);

        myMlPerceptron.addLayer(input);
        myMlPerceptron.addLayer(middle);
        myMlPerceptron.addLayer(output);

        NeuralNetworkFactory.setDefaultIO(myMlPerceptron);
        myMlPerceptron.getLayerAt(0).addNeuron(new BiasNeuron());

        ConnectionFactory.fullConnect(myMlPerceptron.getLayerAt(0), myMlPerceptron.getLayerAt(1));
        ConnectionFactory.fullConnect(myMlPerceptron.getLayerAt(1), myMlPerceptron.getLayerAt(2));
        LearningEventListener listener= event -> {
            IterativeLearning flag = (IterativeLearning)event.getSource();
            BackPropagation bp = (BackPropagation) event.getSource();
            System.out.println("Iterate:"+flag.getCurrentIteration());
            System.out.println(bp.getTotalNetworkError());
        };

        LearningRule rule = new BackPropagation();
        rule.addListener(listener);

        myMlPerceptron.setLearningRule(rule);


        DataSet trainingSet = DataSet.load("src/main/resources/DataSet/MlPerceptron/DataSetAnimalClassify.tset");
        myMlPerceptron.learn(trainingSet);

        DataSet testSet = DataSet.load("src/main/resources/DataSet/MlPerceptron/TestDataSetAnimalClassify.tset");
        for (DataSetRow row : testSet.getRows()) {
            myMlPerceptron.setInput(row.getInput());
            myMlPerceptron.calculate();
            double[] networkOutput = myMlPerceptron.getOutput();
            System.out.println(Arrays.toString(row.getInput()) + "=" + Arrays.toString(networkOutput));
        }

    }
}
