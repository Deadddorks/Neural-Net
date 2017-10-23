package network.layer;
//--------------------------------------------------
//----- Imports ------------------------------------
//--------------------------------------------------
import exceptions.InvalidInputSizeException;
import exceptions.TestDataLengthException;

import java.util.ArrayList;
import java.util.function.Function;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//~~~~~

public class InputLayer extends Layer
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
	private ArrayList<Function<Double, Double>> inputAdjusters;
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //~~~~~

    public InputLayer(final int size, final Function<Double, Double> function, final Function<Double, Double> functionDeriv)
    {
        super(size, null, function, functionDeriv);
        weights = null;
    }

	public void adjustInputsToDataSet(final ArrayList<double[]> inputData)
	{
		if (inputData.size() < 1)
		{
			throw new TestDataLengthException();
		}

		int numArgs = inputData.get(0).length;
		double[][] maxAndMins = new double[numArgs][];
		for (int arg = 0; arg < numArgs; arg++)
		{
			maxAndMins[arg] = new double[] {0, 1};
		}
		for (double[] data : inputData)
		{
			for (int arg = 0; arg < numArgs; arg++)
			{
				maxAndMins[arg][0] = Math.min(maxAndMins[arg][0], data[arg]);
				maxAndMins[arg][1] = Math.max(maxAndMins[arg][1], data[arg]);
			}
		}

		inputAdjusters = new ArrayList<>();
		for (int arg = 0; arg < numArgs; arg++)
		{
			Integer argNum = arg;
			inputAdjusters.add(d -> (d - maxAndMins[argNum][0]) / (maxAndMins[argNum][1] - maxAndMins[argNum][0]));
		}
	}
	
	public void setRange(final double min, final double max)
	{
		inputAdjusters = new ArrayList<>();
		for (int n = 0; n < size; n++)
		{
			inputAdjusters.add(d -> (d - min) / (max - min));
		}
	}

    @Override
    public void passInData(final double[] data)
    {
		if (data.length != size)
		{
			throw new InvalidInputSizeException(size, data.length);
		}
		if (data.length != inputAdjusters.size())
		{
			throw new InvalidInputSizeException(inputAdjusters.size(), data.length);
		}

		double[] scaledData = new double[data.length];
		for (int arg = 0; arg < size; arg++)
		{
			scaledData[arg] = inputAdjusters.get(arg).apply(data[arg]);
		}

        for (int node = 0; node < size; node++)
        {
            nodes[node].setValue(scaledData[node]);
        }
    }

}
