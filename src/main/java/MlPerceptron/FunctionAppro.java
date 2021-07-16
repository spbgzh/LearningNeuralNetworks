package MlPerceptron;

import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.learning.IterativeLearning;
import org.neuroph.core.learning.SupervisedLearning;
import org.neuroph.core.transfer.Linear;
import org.neuroph.nnet.comp.neuron.BiasNeuron;
import org.neuroph.nnet.comp.neuron.InputNeuron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.*;
import org.neuroph.util.io.*;


import java.io.File;
import java.io.IOException;
import java.util.Random;

public class FunctionAppro {
    public static void main(String[] args) {
        NeuralNetwork myMlPerceptron = new NeuralNetwork();
        myMlPerceptron.setNetworkType(NeuralNetworkType.MULTI_LAYER_PERCEPTRON);

        NeuronProperties inputNeuronProperties = new NeuronProperties(InputNeuron.class, Linear.class);
        Layer input = LayerFactory.createLayer(1, inputNeuronProperties);
        Layer middle = LayerFactory.createLayer(8, TransferFunctionType.SIGMOID);
        Layer output = LayerFactory.createLayer(1,TransferFunctionType.LINEAR);

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

        SupervisedLearning rule = new BackPropagation();
        rule.addListener(listener);
        rule.setMaxError(0.0001d);
        myMlPerceptron.setLearningRule(rule);

        DataSet trainingSet = new DataSet(1,1);
        for(int i=0;i<2000;i++){
            double in=new Random().nextDouble()*4-2;
            double out=1+Math.sin(Math.PI*2/4*in);
            trainingSet.add(new DataSetRow(new double[]{in}, new double[]{out}));
        }
        myMlPerceptron.save("output/MlPerceptron/FunctionAppro.nnet");
        System.out.println("Testing training set");
        myMlPerceptron.learn(trainingSet);
        try {
            testNeuralNetwork(myMlPerceptron);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testNeuralNetwork(NeuralNetwork neuralNet) throws IOException {
        File xfile = new File("output/MlPerceptron/scilab/x.txt");
        File yfile = new File("output/MlPerceptron/scilab/y.txt");
        FileOutputAdapter x = new FileOutputAdapter(xfile);
        FileOutputAdapter y = new FileOutputAdapter(yfile);
        for(int i=0;i<500;i++){
            double in=new Random().nextDouble()*4-2;
            neuralNet.setInput(in);
            neuralNet.calculate();
            double[] xOutput = new double[]{in};
            x.writeOutput(xOutput);
            y.writeOutput(neuralNet.getOutput());
        }
    }
}
