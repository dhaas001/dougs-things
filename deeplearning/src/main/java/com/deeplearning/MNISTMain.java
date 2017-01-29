package com.deeplearning;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.DropoutLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;

public class MNISTMain {

	public static void main(String[] args) {
		PropertyConfigurator.configure("C:/projects/workspace/dougs-things/deeplearning/src/main/resources/log4j.properties");
		Logger log = Logger.getLogger(MNISTMain.class); 
		log.debug("Running MNISTMain");
		final int numRows = 28;
        final int numColumns = 28;
        int outputNum = 10; // number of output classes
        int batchSize = 64; // batch size for each epoch
        int rngSeed = 123; // random number seed for reproducibility
        int numEpochs = 15; // number of epochs to perform
double rate = 0.0015; // learning rate
	    
	    try{
	    	DataSetIterator mnistTrain = new MnistDataSetIterator(batchSize, true, rngSeed);
	    	DataSetIterator mnistTest = new MnistDataSetIterator(batchSize, false, rngSeed);

	    	log.info("Build model....");
	        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
	            .seed(rngSeed) //include a random seed for reproducibility
	            .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT) // use stochastic gradient descent as an optimization algorithm
	            .iterations(1)
	            .activation(Activation.RELU)
	            .weightInit(WeightInit.XAVIER)
	            .learningRate(rate) //specify the learning rate
	            .updater(Updater.NESTEROVS).momentum(0.98) //specify the rate of change of the learning rate.
	            .regularization(true).l2(rate * 0.005) // regularize learning model
	            .list()
	            .layer(0, new DenseLayer.Builder() //create the first input layer.
	                    .nIn(numRows * numColumns)
	                    .nOut(500)
	                    .build())
	            .layer(1, new DenseLayer.Builder() //create the second input layer
	                    .nIn(500)
	                    .nOut(100)
	                    .build())
	            .layer(2, new OutputLayer.Builder(LossFunction.NEGATIVELOGLIKELIHOOD) //create hidden layer
	                    .activation(Activation.SOFTMAX)
	                    .nIn(100)
	                    .nOut(outputNum)
	                    .build())
	            .pretrain(false).backprop(true) //use backpropagation to adjust weights
	.build();
	        
	    	 MultiLayerNetwork model = new MultiLayerNetwork(conf);
	         model.init();
	         model.setListeners(new ScoreIterationListener(5));  //print the score with every iteration

	         log.info("Train model....");
	         for( int i=0; i<numEpochs; i++ ){
	         	log.info("Epoch " + i);
	             model.fit(mnistTrain);
	         }


	         log.info("Evaluate model....");
	         Evaluation eval = new Evaluation(outputNum); //create an evaluation object with 10 possible classes
	         while(mnistTest.hasNext()){
	             DataSet next = mnistTest.next();
	             INDArray output = model.output(next.getFeatureMatrix()); //get the networks prediction
	             eval.eval(next.getLabels(), output); //check the prediction against the true class
	         }

	         log.info(eval.stats());
	 log.info("****************Example finished********************");
	    }
	    catch(Throwable e){
	    	e.printStackTrace(); 
	    }
	}

}
