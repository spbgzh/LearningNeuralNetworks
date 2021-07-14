package MlPerceptron;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.learning.IterativeLearning;
import org.neuroph.core.learning.LearningRule;
import org.neuroph.core.learning.SupervisedLearning;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.TransferFunctionType;
import org.neuroph.util.random.NguyenWidrowRandomizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ParityCheck {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(32);
        list.add(10);
        list.add(4);
        NeuralNetwork myMlPerceptron = new MultiLayerPerceptron(list, TransferFunctionType.SIGMOID);

        DataSet trainingSet = new DataSet(32, 4);
        //对2000条数据进行训练
        for (int i = 0; i < 2000; i++) {
            int in = new Random().nextInt();
            trainingSet.add(new DataSetRow(DataSetMaker.int2double(in), DataSetMaker.int2prop(in)));
        }
        SupervisedLearning learningRule = (SupervisedLearning) myMlPerceptron.getLearningRule();
        learningRule.setMaxError(0.0001d);
        LearningEventListener listener = event -> {
            IterativeLearning flag = (IterativeLearning) event.getSource();
            BackPropagation bp = (BackPropagation) event.getSource();
            System.out.println("Iterate:" + flag.getCurrentIteration());
            System.out.println(bp.getTotalNetworkError());
        };
        learningRule.addListener(listener);
        myMlPerceptron.randomizeWeights();
        System.out.println("Training neural network...");
        myMlPerceptron.learn(trainingSet);

        myMlPerceptron.save("output/MlPerceptron/ParityCheck.nnet");
    }


}

class DataSetMaker{
    public static double[] int2double(int input) {
        double[] re = new double[32];
        for (int j = 0; j < 32; j++) {
            re[j] = (double) ((input >> j) & 1);
        }
        return re;
    }
    public static double[] int2prop(int input) {
        //正偶数
        double[] pe = {0d, 0d, 0d, 1d};
        //负偶数
        double[] ne = {0d, 0d, 1d, 0d};
        //正奇数
        double[] po = {0d, 1d, 0d, 0d};
        //负奇数
        double[] no = {1d, 0d, 0d, 0d};
        if (input > 0 && input % 2 == 0) {
            return pe;
        } else if (input < 0 && input % 2 == 0) {
            return ne;
        } else if (input > 0 && input % 2 != 0) {
            return po;
        } else if (input < 0 && input % 2 != 0) {
            return no;
        }
        return pe;
    }
}

