package Adaline;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.learning.IterativeLearning;
import org.neuroph.nnet.comp.neuron.BiasNeuron;
import org.neuroph.nnet.learning.LMS;
import org.neuroph.util.*;

import java.util.Arrays;

    //还是有bug，不知道哪里错了！！

public class Adaline {
    public static void main(String[] args) {
        NeuralNetwork myAdaline = new NeuralNetwork<LMS>();
        myAdaline.setNetworkType(NeuralNetworkType.ADALINE);
        myAdaline.addLayer(0,LayerFactory.createLayer(35, TransferFunctionType.LINEAR));
        myAdaline.addLayer(1,LayerFactory.createLayer(10, TransferFunctionType.LINEAR));
        myAdaline.getLayerAt(0).addNeuron(35,new BiasNeuron());
        ConnectionFactory.fullConnect(myAdaline.getLayerAt(0),myAdaline.getLayerAt(1));
        NeuralNetworkFactory.setDefaultIO(myAdaline);
        LearningEventListener listener= event -> {
            IterativeLearning flag = (IterativeLearning)event.getSource();
            System.out.println("Iterate:"+flag.getCurrentIteration());
            System.out.println(Arrays.toString(flag.getNeuralNetwork().getWeights()));
        };
        DataSet data = AdalineDemo.createDataSet();
        LMS lms = new LMS();
        lms.setLearningRate(0.05);
        lms.setMaxError(0.5);
        lms.setTrainingSet(data);
        lms.addListener(listener);;
        myAdaline.setLearningRule(lms);
        data.save("output/Adaline/Adaline.tset");
        myAdaline.save("output/Adaline/Adaline.nnet");
        myAdaline.learn(data);
    }

}
