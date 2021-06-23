package Adaline;

import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.learning.IterativeLearning;
import org.neuroph.core.transfer.Linear;
import org.neuroph.nnet.comp.neuron.BiasNeuron;
import org.neuroph.nnet.comp.neuron.InputNeuron;
import org.neuroph.nnet.learning.LMS;
import org.neuroph.util.*;

import java.util.Arrays;

    //Input神经元属性必修设置成InputNeuron.class！！！！不然无法输入input数据！！

public class Adaline extends NeuralNetwork<LMS> {
    public static void main(String[] args) {
        //创建神经网络
        NeuralNetwork myAdaline = new NeuralNetwork<LMS>();
        //设置神经网络类型为Adaline
        myAdaline.setNetworkType(NeuralNetworkType.ADALINE);
        //创建输入层和输出层，并设置传递函数为Liner线性函数
        NeuronProperties inputNeuronProperties = new NeuronProperties(InputNeuron.class, Linear.class);
        myAdaline.addLayer(0,LayerFactory.createLayer(35, inputNeuronProperties));
        myAdaline.addLayer(1,LayerFactory.createLayer(10, TransferFunctionType.LINEAR));

        //设置输入输出神经元
        NeuralNetworkFactory.setDefaultIO(myAdaline);

        //在第一层加入一个贝叶斯神经元用于偏置
        myAdaline.getLayerAt(0).addNeuron(new BiasNeuron());
        //在输入层和输出层的神经元之间建立全连接
        ConnectionFactory.fullConnect(myAdaline.getLayerAt(0),myAdaline.getLayerAt(1));



        //监督训练
        LearningEventListener listener= event -> {
            IterativeLearning flag = (IterativeLearning)event.getSource();
            System.out.println("Iterate:"+flag.getCurrentIteration());
            System.out.println(Arrays.toString(flag.getNeuralNetwork().getWeights()));
        };

        //创建数据集
        DataSet data = AdalineDemo.createDataSet();

        //创建训练规则，LMS算法
        LMS lms = new LMS();
        lms.setLearningRate(0.05);
        lms.setMaxError(0.5);
        lms.setTrainingSet(data);
        lms.addListener(listener);;
        myAdaline.setLearningRule(lms);

        //保存输出文件
        data.save("output/Adaline/Adaline.tset");
        myAdaline.save("output/Adaline/Adaline.nnet");

        //训练
        myAdaline.learn(data);
    }

}
