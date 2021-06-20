package Perceptron;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.Perceptron;

import java.util.Arrays;

public class PerceptronClassifyNoLearn {
    public static void main(String[] args) {
        DataSet data =new DataSet(2,2);
        data.add(new DataSetRow(new double[]{123,123},new double[]{1,1}));
        data.add(new DataSetRow(new double[]{323,-321},new double[]{1,0}));
        data.add(new DataSetRow(new double[]{-67,-34},new double[]{0,0}));
        data.add(new DataSetRow(new double[]{-33,43},new double[]{0,1}));
        NeuralNetwork myPerceptron= new Perceptron(2,2);
        myPerceptron.setWeights(new double[]{1,0,0,1});
        System.out.println(Arrays.toString(myPerceptron.getWeights()));
        myPerceptron.save("output/Perceptron/PerceptronClassifyNoLearn.nnet");
        testNeuralNetwork(myPerceptron,data);

    }

    public static void testNeuralNetwork(NeuralNetwork nnet, DataSet tset) {

        for (DataSetRow dataRow : tset.getRows()){
            nnet.setInput(dataRow.getInput());
            nnet.calculate();
            double[] networkOutput = nnet.getOutput();
            System.out.print("Input: " + Arrays.toString(dataRow.getInput()));
            System.out.println(" Output: " + Arrays.toString(networkOutput));
            System.out.println(Arrays.toString(dataRow.getInput()) + "=" + posToString(networkOutput));
        }
    }

    public static String posToString(double[] networkOutput ){
        if((networkOutput[0]+networkOutput[1])==2){
            return "第一象限";
        }else if((networkOutput[0]+networkOutput[1])==0){
            return "第三象限";
        }else if((networkOutput[0]-networkOutput[1])==1){
            return "第四象限";
        }
        return "第二象限";
    }
}
