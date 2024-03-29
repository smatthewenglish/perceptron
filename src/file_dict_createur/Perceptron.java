package file_dict_createur;

/**
 The Perceptron Algorithm
 By Dr Noureddin Sadawi
 Please my youtube videos on perceptron for things to make sense!
 
 Code adapted from:
 https://github.com/RichardKnop/ansi-c-perceptron
*/  

import java.text.DecimalFormat;
import java.util.*;
import java.util.Map.Entry;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

class Perceptron 
{
    static final int MAX_ITER = 1000;
    static final double LEARNING_RATE = 0.1;
    static final double THETA = 0;
    
    static final String LABEL = "atheism";
    //static final String FILEPATH = "sports";
    
    //1 or 0 corresponding to the feature vector for aetheism or sports
    static final int TEST_CLASS = 0;
    

    
    static int test_output;

    public static void perceptron(Set<String> globoDict,
        Map<String, int[]> trainingPerceptronInput,
        Map<String, int[]> testPerceptronInput)
    {
        final int globoDictSize = globoDict.size(); // number of features (x, y, z)

        // weights total 32 (31 for input variables and one for bias)
        double[] weights = new double[globoDictSize + 1];
        for (int i = 0; i < weights.length; i++) 
        {
            //weights[i] = Math.floor(Math.random() * 10000) / 10000;
            weights[i] = randomNumber(0,1);
            //weights[i] = 0.0;
        }

        int inputSize = trainingPerceptronInput.size();
        double[] outputs = new double[inputSize];
        final double[][] a = initializeOutput(trainingPerceptronInput, globoDictSize, outputs);

        double globalError;
        int iteration = 0;
        do 
        {
            iteration++;
            globalError = 0;
            // loop through all instances (complete one epoch)
            for (int p = 0; p < inputSize; p++) 
            {
                // calculate predicted class
                double output = Prcptrn_CalcOutpt.calculateOutput(THETA, weights, a, p);
                // difference between predicted and actual class values
                //always either zero or one
                double localError = outputs[p] - output;
                
                int i;
                for (i = 0; i < a.length; i++) 
                {
                    weights[i] += LEARNING_RATE * localError * a[i][p];
                }
                weights[i] += LEARNING_RATE * localError;

                // summation of squared error (error value for all instances)
                globalError += localError * localError;
            }

            /* Root Mean Squared Error */
            System.out.println("Iteration " + iteration + " : RMSE = " + Math.sqrt(globalError / inputSize));
        } 
        while (globalError != 0 && iteration <= MAX_ITER);

        System.out.println("\n=======\nDecision boundary equation:");
        int i;
        for (i = 0; i < a.length; i++) 
        {
            System.out.print(" a");
            if (i < 10) System.out.print(0);
            System.out.println( i + " * " + weights[i] + " + " );
            
        	
        }
        System.out.println(" bias: " + weights[i]);
        
        
        //TEST
        //this works because, at this point the weights have already been learned. 
        inputSize = testPerceptronInput.size();
        outputs = new double[inputSize];
        double[][] z = initializeOutput(testPerceptronInput, globoDictSize, outputs); 

        test_output = Prcptrn_CalcOutpt.calculateOutput(THETA, weights, z, TEST_CLASS);       
        
        System.out.println("class = " + test_output);
        
        
    }

    static double[][] initializeOutput( Map<String, int[]> perceptronInput, 
    							        int size, 
    							        double[] outputs)
    {
        final int inputSize = perceptronInput.size();
        final double[][] a = new double[size][inputSize];

        // 2d array for features
        int[][] feature_matrix = new int[inputSize][size];
        String[] output_label = new String[inputSize];
        int x = 0;
        for (Entry<String, int[]> entry : perceptronInput.entrySet()) 
        {
            int[] container = entry.getValue();

            for (int j = 0; j < container.length; j++) 
            {
                feature_matrix[x][j] = container[j];
                output_label[x] = String.valueOf(entry.getKey());
            }
            x++;
        }

        for (x = 0; x < inputSize; x++) 
        {
            for (int i = 0; i < a.length; i++) 
            {
                a[i][x] = feature_matrix[x][i];
            }
            //outputs[x] = output_label[x].equals(LABEL) ? 1 : 0;
            outputs[x] = output_label[x].equals(LABEL) ? 1 : -1;
        }

        return a;
    }

    public static double randomNumber(int min , int max) 
    {
        DecimalFormat df = new DecimalFormat("#.####");
        double d = min + Math.random() * (max - min);
        String s = df.format(d);
        double x = Double.parseDouble(s);
        return x;
    }
    



}























