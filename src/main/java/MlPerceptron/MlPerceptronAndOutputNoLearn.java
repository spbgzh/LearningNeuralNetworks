package MlPerceptron;

import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.input.And;
import org.neuroph.core.transfer.Linear;
import org.neuroph.nnet.comp.neuron.BiasNeuron;
import org.neuroph.nnet.comp.neuron.InputNeuron;
import org.neuroph.util.*;

import java.util.Arrays;

public class MlPerceptronAndOutputNoLearn {
    public static void main(String[] args) {
        NeuralNetwork myMlPerceptron = new NeuralNetwork();
        myMlPerceptron.setNetworkType(NeuralNetworkType.MULTI_LAYER_PERCEPTRON);
        NeuronProperties inputNeuronProperties = new NeuronProperties(InputNeuron.class, Linear.class);
        Layer input= LayerFactory.createLayer(2,inputNeuronProperties);

        Layer middle = LayerFactory.createLayer(2,TransferFunctionType.STEP);
        Layer output= LayerFactory.createLayer(1,TransferFunctionType.LINEAR);
        output.getNeuronAt(0).setInputFunction(new And());

        myMlPerceptron.addLayer(0,input);
        myMlPerceptron.addLayer(1,middle);
        myMlPerceptron.addLayer(2,output);

        myMlPerceptron.setInputNeurons(myMlPerceptron.getLayerAt(0).getNeurons());
        myMlPerceptron.setOutputNeurons(myMlPerceptron.getLayerAt(2).getNeurons());
        myMlPerceptron.getLayerAt(0).addNeuron(new BiasNeuron());

        ConnectionFactory.fullConnect(myMlPerceptron.getLayerAt(0), myMlPerceptron.getLayerAt(1));
        ConnectionFactory.fullConnect(myMlPerceptron.getLayerAt(1), myMlPerceptron.getLayerAt(2));

        DataSet data = new DataSet(2,1);
        data.add(new double[]{0, 0}, new double[]{Double.NaN});
        data.add(new double[]{0, 1}, new double[]{Double.NaN});
        data.add(new double[]{1, 0}, new double[]{Double.NaN});
        data.add(new double[]{1, 1}, new double[]{Double.NaN});
        myMlPerceptron.setWeights(new double[]{2,2,-1,-2,-2,3,1,1});
        myMlPerceptron.save("output/MlPerceptron/MlPerceptronAndOutputNoLearn.nnet");

        for (DataSetRow row : data.getRows()) {
            myMlPerceptron.setInput(row.getInput());
            myMlPerceptron.calculate();
            double[] networkOutput = myMlPerceptron.getOutput();
            System.out.println(Arrays.toString(row.getInput()) + "=" + Arrays.toString(networkOutput));
        }



    }
}
