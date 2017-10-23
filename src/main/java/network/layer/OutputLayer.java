package network.layer;
//--------------------------------------------------
//----- Imports ------------------------------------
//--------------------------------------------------
import exceptions.TestDataLengthException;
import network.analysis.DataEntry;

import java.util.ArrayList;
import java.util.function.Function;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//~~~~~

public class OutputLayer extends Layer
{

    //--------------------------------------------------
    //----- _ ------------------------------------------
    //--------------------------------------------------

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //~~~~~

    //--------------------------------------------------
    //----- Constants ----------------------------------
    //--------------------------------------------------

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //~~~~~
    //--------------------------------------------------
    //----- Variables ----------------------------------
    //--------------------------------------------------
    private ArrayList<Function<Double, Double>> outputToDataFuncts;
    private ArrayList<Function<Double, Double>> dataToOutputFuncts;
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //~~~~~

    public OutputLayer(final int size,  final Layer prevLayer, final Function<Double, Double> function, final Function<Double, Double> functionDeriv)
    {
        super(size, prevLayer, function, functionDeriv);
    }

    public void setCurrentLayerDeltas(double[] expectedValues)
    {
        if (expectedValues.length != size)
        {
            throw new TestDataLengthException();
        }
	
		double[] tempExp = new double[expectedValues.length];
		for (int i = 0; i < expectedValues.length; i++)
		{
			tempExp[i] = outputToDataFuncts.get(i).apply(expectedValues[i]);
		}
		
        double[] deltas = new double[size];
        for (int node = 0; node < size; node++)
        {
            deltas[node] = nodes[node].calculate(functionDeriv) * (tempExp[node] - nodes[node].calculate(function));
        }
        this.deltas = deltas;
    }
	
	public void passInData(final double[] data)
	{
		super.passInData(data);
		for (int node = 0; node < size; node++)
		{
			nodes[node].setValue(outputToDataFuncts.get(node).apply(nodes[node].getValue()));
		}
	}

    public DataEntry getDataEntry(final double[] expected)
	{
		DataEntry entry = new DataEntry();
        
        for (int node = 0; node < size; node++)
		{
		    entry.addData(dataToOutputFuncts.get(node).apply(nodes[node].calculate(function)),
                    expected[node],
                    getCost(dataToOutputFuncts.get(node).apply(nodes[node].calculate(function)),
                            expected[node]));
		}
		
		return entry;
	}

    private double getCost(final double expected, final double actual)
    {
        return Math.pow(expected - actual, 2) / 2;
    }
    
    public double[] getOutputs()
	{
		double[] outputs = new double[size];
		for (int n = 0; n < size; n++)
		{
			outputs[n] = dataToOutputFuncts.get(n).apply(nodes[n].calculate(function));
		}
		return outputs;
	}

    public void adjustOutputsToDataSet(final ArrayList<double[]> outputData)
    {
        if (outputData.size() < 1)
		{
			throw new TestDataLengthException();
		}
		
        int numArgs = outputData.get(0).length;
        double[][] maxAndMins = new double[numArgs][];
        for (int arg = 0; arg < numArgs; arg++)
        {
            maxAndMins[arg] = new double[] {0, 1};
        }
        for (double[] data : outputData)
        {
            for (int arg = 0; arg < numArgs; arg++)
            {
                maxAndMins[arg][0] = Math.min(maxAndMins[arg][0], data[arg]);
                maxAndMins[arg][1] = Math.max(maxAndMins[arg][1], data[arg]);
            }
        }

        outputToDataFuncts = new ArrayList<>(); // Not being used, should solve scaling data issues
        dataToOutputFuncts = new ArrayList<>();
        for (int arg = 0; arg < numArgs; arg++)
        {
            Integer argNum = arg;
            outputToDataFuncts.add(d -> (d - maxAndMins[argNum][0]) / (maxAndMins[argNum][1] - maxAndMins[argNum][0]));
            dataToOutputFuncts.add(d -> d * (maxAndMins[argNum][1] - maxAndMins[argNum][0]) + maxAndMins[argNum][0]);
        }
    }
	
	public void setRange(final double min, final double max)
	{
		outputToDataFuncts = new ArrayList<>();
		dataToOutputFuncts = new ArrayList<>();
		for (int n = 0; n < size; n++)
		{
			outputToDataFuncts.add(d -> (d - min) / (max - min));
			dataToOutputFuncts.add(d -> d * (max - min) + min);
		}
	}

}
