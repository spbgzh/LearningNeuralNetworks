package MlPerceptron;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.MultiLayerPerceptron;

import java.util.ArrayList;
import java.util.List;

public class ParityCheck {
    public static void main(String[] args) {
        List<Integer> list =new ArrayList<>();
        for(int i=0;i<10;i++)
            list.add(32);
        list.add(4);
        NeuralNetwork myMlPerceptron = new MultiLayerPerceptron(list);

        //myMlPerceptron.save("output/MlPerceptron/ParityCheck.nnet");
        //神经网络太大直接报错了
    }
}
