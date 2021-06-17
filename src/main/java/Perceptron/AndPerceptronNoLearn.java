package Perceptron;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.transfer.Step;
import org.neuroph.nnet.Perceptron;
import org.neuroph.nnet.comp.neuron.BiasNeuron;
import org.neuroph.util.ConnectionFactory;


import java.util.Arrays;

public class AndPerceptronNoLearn {
    public static void main(String[] args) {
        DataSet data = new DataSet(2, 1);
        data.add(new DataSetRow(new double[]{1, 1}, new double[]{Double.NaN}));
        data.add(new DataSetRow(new double[]{1, 0}, new double[]{Double.NaN}));
        data.add(new DataSetRow(new double[]{0, 1}, new double[]{Double.NaN}));
        data.add(new DataSetRow(new double[]{0, 0}, new double[]{Double.NaN}));
        NeuralNetwork myPerceptron = new Perceptron(2, 1);
        Neuron biasNeuron = new BiasNeuron();
        myPerceptron.getLayerAt(0).addNeuron(biasNeuron);
        ConnectionFactory.createConnection(myPerceptron.getLayerAt(0).getNeuronAt(2),
                myPerceptron.getLayerAt(1).getNeuronAt(0));
        myPerceptron.setWeights(new double[]{0.25, 0.473, -0.485});
        myPerceptron.getLayerAt(1).getNeuronAt(0).setTransferFunction(new Step());
        myPerceptron.save("output/Perceptron/AndPerceptronNoLearn.nnet");
        for (DataSetRow row : data.getRows()) {
            myPerceptron.setInput(row.getInput());
            myPerceptron.calculate();
            double[] networkOutput = myPerceptron.getOutput();
            System.out.println(Arrays.toString(row.getInput()) + "=" + Arrays.toString(networkOutput));
        }
    }
}
