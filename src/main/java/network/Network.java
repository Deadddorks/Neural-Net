package network;
//--------------------------------------------------
//----- Imports ------------------------------------
//--------------------------------------------------
import exceptions.DecodingException;
import exceptions.TestDataLengthException;
import exceptions.TooFewHiddenLayersException;
import importable.util.StringMethods;
import network.analysis.EpochData;
import network.layer.InputLayer;
import network.layer.Layer;
import network.layer.OutputLayer;
import network.util.Functions;

import java.io.*;
import java.util.ArrayList;
import java.util.function.Function;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//~~~~~

public class Network
{
    
    //--------------------------------------------------
    //----- _ ------------------------------------------
    //--------------------------------------------------
    
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //~~~~~
    
    //--------------------------------------------------
    //----- Constants ----------------------------------
    //--------------------------------------------------
    private enum NetworkType {FULLY_CONNECTED}
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //~~~~~
    //--------------------------------------------------
    //----- Variables ----------------------------------
    //--------------------------------------------------
    private final int numInputs;
    private final int numHiddenLayers;
    private final int numNodesPerHiddenLayer;
    private final int numOutputs;

    private InputLayer inputLayer;
    private Layer[] hiddenLayers;
    private OutputLayer outputLayer;

	private int colWidths = 10;
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //~~~~~
    
    public Network(final int numInputs, final int numHiddenLayers, final int numNodesPerHiddenLayer, final int numOutputs)
    {
        if (numHiddenLayers < 1)
        {
            throw new TooFewHiddenLayersException(numHiddenLayers);
        }

        this.numInputs = numInputs;
        this.numHiddenLayers = numHiddenLayers;
        this.numNodesPerHiddenLayer = numNodesPerHiddenLayer;
        this.numOutputs = numOutputs;

        initLayers();
    }

    protected void initLayers()
    {
        inputLayer = new InputLayer(numInputs, Functions.X, Functions.ONE);

        hiddenLayers = new Layer[numHiddenLayers];

        hiddenLayers[0] = new Layer(numNodesPerHiddenLayer, inputLayer, Functions.getSigmoid(1, 1), Functions.getSigmoidDeriv(1, 1));
        for (int i = 1; i < numHiddenLayers; i++)
        {
            hiddenLayers[i] = new Layer(numNodesPerHiddenLayer, hiddenLayers[i - 1], Functions.getSigmoid(1, 1), Functions.getSigmoidDeriv(1, 1));
        }

        outputLayer = new OutputLayer(numOutputs, hiddenLayers[numHiddenLayers - 1], Functions.getSigmoid(1, 1), Functions.getSigmoidDeriv(1, 1));
    }
    
	public void setHiddenLayerFunctions(Function<Double, Double> function, Function<Double, Double> functionDeriv)
	{
		for (Layer layer : hiddenLayers)
		{
			layer.setFunctions(function, functionDeriv);
		}
	}
	public void setOutputLayerFunctions(Function<Double, Double> function, Function<Double, Double> functionDeriv)
	{
		outputLayer.setFunctions(function, functionDeriv);
	}

    public void initRandomWeights()
    {
        outputLayer.randomizeWeights();
        for (Layer layer: hiddenLayers)
        {
            layer.randomizeWeights();
        }
    }

    public void train(final ArrayList<double[]> inputData, final ArrayList<double[]> outputData, final double learningRate, final int epochCount, final int printEveryNumEpochs)
    {
		boolean debug = false;
		
        if (inputData.size() != outputData.size())
        {
            throw new TestDataLengthException();
        }

		printOutputs(inputData, outputData, "Initial");

        for (int epoch = 0; epoch < epochCount; epoch++)
        {
            if (printEveryNumEpochs > 0)
            {
				if ((epoch + 1) % printEveryNumEpochs == 0)
				{
					if (debug)
					{
						printTree();
					}
					printOutputs(inputData, outputData, Integer.toString(epoch + 1));
				}
            }
            for (int dataPoint = 0; dataPoint < inputData.size(); dataPoint++)
            {
                // Passes the data into the network and calculates all the values to the end
                input(inputData.get(dataPoint));
                // Prints the final output values, expected, and Cost

                // Calculate the Delats for all the different layers
                outputLayer.setCurrentLayerDeltas(outputData.get(dataPoint));
                outputLayer.setPrevLayerDeltas();
                for (int hidLayerNum = numHiddenLayers-1; hidLayerNum > 0; hidLayerNum--)
                {
                    hiddenLayers[hidLayerNum].setPrevLayerDeltas();
                }

                // Adjust layer weights
                outputLayer.adjustWeights(learningRate);
                for (int hidLayerNum = numHiddenLayers-1; hidLayerNum >= 0; hidLayerNum--)
                {
                    hiddenLayers[hidLayerNum].adjustWeights(learningRate);
                }

            }
        }

		printOutputs(inputData, outputData, "Finished");
    }

	public void adjustDataScalingsToDataSet(final ArrayList<double[]> inputData, final ArrayList<double[]> outputData)
	{
		inputLayer.adjustInputsToDataSet(inputData);
		outputLayer.adjustOutputsToDataSet(outputData);
	}
	public void setInputRange(final double min, final double max)
	{
		inputLayer.setRange(min, max);
	}
	public void setOutputRange(final double min, final double max)
	{
		outputLayer.setRange(min, max);
	}

    public void input(final double[] data)
    {
        inputLayer.passInData(data);
        hiddenLayers[0].passInData(inputLayer.getNodeValuesFromFunction());
        for (int layer = 1; layer < numHiddenLayers; layer++)
        {
            hiddenLayers[layer].passInData(hiddenLayers[layer-1].getNodeValuesFromFunction());
        }
        outputLayer.passInData(hiddenLayers[numHiddenLayers-1].getNodeValuesFromFunction());
    }
    public double[] getOutputs()
	{
		return outputLayer.getOutputs();
	}
    
    public void printOutputs(final ArrayList<double[]> inputData, final ArrayList<double[]> outputData, final String epochLabel)
	{
		EpochData epoch = getEpochData(inputData, outputData, epochLabel);
		epoch.print(colWidths);
	}
	
	public void printTree()
	{
		int dataWidth = 10;
		int maxLength = 0;
		
		// Get all the layers data
		Layer[] l = new Layer[2 + hiddenLayers.length];
		String[][] values = new String[l.length][];
		l[0] = inputLayer;
		l[l.length-1] = outputLayer;
		for (int l2 = 0; l2 < l.length - 2; l2++)
		{
			l[l2 + 1] = hiddenLayers[l2];
		}
		for (int n = 0; n < l.length; n++)
		{
			values[n] = l[n].getNodeDataAsStringList(dataWidth);
			maxLength = Math.max(maxLength, values[n].length);
		}
		
		// Print it all out
		String out = "";
		char separator = '|';
		int start;
		int end;
		for (int i = 0; i < maxLength; i++)
		{
			out += Character.toString(separator);
			
			for (int lay = 0; lay < l.length; lay++)
			{
				start = (maxLength - values[lay].length) / 2;
				end = start + values[lay].length;
				
				if (i >= start && i < end)
				{
					out += values[lay][i - start];
				}
				else
				{
					out += StringMethods.getStringWithLength("", dataWidth * 2 + 7);
				}
				out += Character.toString(separator);
			}
			out += "\n";
			
		}
		System.out.println(" --------------------------------------------------- Printing Network --------------------------------------------------- ");
		System.out.println(out);
	}

    public EpochData getEpochData(final ArrayList<double[]> inputData, final ArrayList<double[]> outputData, final String epochLabel)
	{
		EpochData epoch = new EpochData(epochLabel);
		
		for (int dataPoint = 0; dataPoint < inputData.size(); dataPoint++)
		{
			input(inputData.get(dataPoint));
			epoch.add(outputLayer.getDataEntry(outputData.get(dataPoint)));
		}
		
		return epoch;
	}

    @Override
    public String toString()
    {
        String out = " --- Input Layer ---\n";
        out += inputLayer.toString() + "\n";
        out += " --- Hidden Layer(s) ---\n";
        for (Layer layer : hiddenLayers)
        {
            out += layer.toString() + "\n";
        }
        out += " --- Output Layer ---\n";
        out += outputLayer.toString() + "\n";
        return out;
    }
    
    // File stuff
	public void printToFile(final String filePath)
	{
		File file = new File(filePath);
		BufferedWriter writer = null;
		try
		{
			writer = new BufferedWriter(new FileWriter(file));
			System.out.println("File ["+filePath+"] opened...");
		
			writer.write(numInputs+","+numHiddenLayers+","+numNodesPerHiddenLayer+","+numOutputs);
			writer.newLine();
			for (Layer l : hiddenLayers)
			{
				writer.write(l.getWeightArrayAsString());
				writer.newLine();
				//writer.write("&");
				//writer.newLine();
			}
			writer.write(outputLayer.getWeightArrayAsString());
			writer.newLine();
			//writer.write("&");
			
			System.out.println("File successfully saved.");
		}
		catch (IOException e)
		{
			System.out.println("Could not write to file: ["+filePath+"]");
		}
		finally
		{
			try
			{
				writer.close();
			}
			catch (IOException e)
			{
				System.out.println("Could not close file");
			}
		}
	}
	public static Network pullNetworkFromFile(final String filePath)
	{
		Network network;
		ArrayList<String> fileData = new ArrayList<>();
		File file = new File(filePath);
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			System.out.println("File ["+filePath+"] opened...");
			
			String line;
			while ((line = reader.readLine()) != null)
			{
				fileData.add(line);
			}
			String netData = fileData.get(0);
			fileData.remove(0);
			String[] netSpecsS = netData.split(",");
			int[] netSpecs = new int[4];
			for (int i = 0; i < 4; i++)
			{
				netSpecs[i] = Integer.parseInt(netSpecsS[i]);
			}
			network = new Network(netSpecs[0], netSpecs[1], netSpecs[2], netSpecs[3]);
			
			netData = fileData.get(fileData.size() - 1);
			fileData.remove((fileData.size() - 1));
			
			network.outputLayer.decodeAndSetWeights(netData);
			for (int i = 0; i < fileData.size(); i++)
			{
				network.hiddenLayers[i].decodeAndSetWeights(fileData.get(i));
			}
			
			System.out.println("Network successfully downloaded.");
			
			return network;
		}
		catch (DecodingException e)
		{
			System.out.println(e.getMessage());
			return null;
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Too many layers in data.");
			return null;
		}
		catch (IOException e)
		{
			System.out.println("Could not open file: ["+filePath+"]");
			return null;
		}
		catch (NumberFormatException e)
		{
			System.out.println("Error parsing network specs.");
			return null;
		}
		finally
		{
			try
			{
				reader.close();
			}
			catch (IOException e)
			{
				System.out.println("Could not close file");
			}
		}
	}
    
}
