package Isolet;

import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.learning.IterativeLearning;
import org.neuroph.core.learning.SupervisedLearning;
import org.neuroph.core.transfer.Linear;
import org.neuroph.nnet.comp.neuron.BiasNeuron;
import org.neuroph.nnet.comp.neuron.InputNeuron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.*;

//实现识别语言26个字母
public class Isolet {
    public static void main(String[] args) {

        NeuralNetwork myMlPerceptron = new NeuralNetwork();

        //设置神经网络类型为多层感知机
        myMlPerceptron.setNetworkType(NeuralNetworkType.MULTI_LAYER_PERCEPTRON);

        //分别设置616个神经元输入层，100个神经元的中间层，和26个神经元的输出层
        NeuronProperties inputNeuronProperties = new NeuronProperties(InputNeuron.class, Linear.class);
        Layer input = LayerFactory.createLayer(616, inputNeuronProperties);
        Layer middle = LayerFactory.createLayer(100, TransferFunctionType.SIGMOID);
        Layer output = LayerFactory.createLayer(26,TransferFunctionType.LINEAR);

        //将输入层，中间层，输出层添加到神经网络中
        myMlPerceptron.addLayer(input);
        myMlPerceptron.addLayer(middle);
        myMlPerceptron.addLayer(output);
        NeuralNetworkFactory.setDefaultIO(myMlPerceptron);
        //添加贝叶斯神经元用于偏置
        myMlPerceptron.getLayerAt(0).addNeuron(new BiasNeuron());

        //分别将层级之间的神经元连接
        ConnectionFactory.fullConnect(myMlPerceptron.getLayerAt(0), myMlPerceptron.getLayerAt(1));
        ConnectionFactory.fullConnect(myMlPerceptron.getLayerAt(1), myMlPerceptron.getLayerAt(2));

        LearningEventListener listener= event -> {
            IterativeLearning flag = (IterativeLearning)event.getSource();
            BackPropagation bp = (BackPropagation) event.getSource();
            System.out.println("Iterate:"+flag.getCurrentIteration());
            System.out.println(bp.getTotalNetworkError());
        };

        //设置学习规则为反向传播
        SupervisedLearning rule = new BackPropagation();
        rule.addListener(listener);
        //学习速率
        rule.setLearningRate(0.05);
        //最大允许的错误
        rule.setMaxError(0.05);
        //将学习规则添加到神经网络中
        myMlPerceptron.setLearningRule(rule);

        //调整数据集，数据集来源:UCI ML isolet(https://archive.ics.uci.edu/ml/datasets/isolet)
        DataSet trainData = DataSet.load("src/main/resources/DataSet/Isolet/DataSetIsolet.tset");

        myMlPerceptron.learn(trainData);
        myMlPerceptron.save("output/Isolet/Isolet.nnet");

    }
}
