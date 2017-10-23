package network;
//--------------------------------------------------
//----- Imports ------------------------------------
//--------------------------------------------------
import exceptions.DecodingException;
import exceptions.ErrorArraySizeException;
import exceptions.WeightsOutOfBoundsException;
import importable.util.Math;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//~~~~~

public class Weights
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
    protected final int nodesInLayer;
    protected final int nodesInPrevLayer;

    protected double[][] weights;
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //~~~~~

    public Weights(final int nodesInLayer, final int nodesInPrevLayer)
    {
        this.nodesInLayer = nodesInLayer;
        this.nodesInPrevLayer = nodesInPrevLayer;

        weights = new double[nodesInLayer][nodesInPrevLayer];
    }

    public void randomize()
    {
        for (int i = 0; i < nodesInLayer; i++)
        {
            for (int j = 0; j < nodesInPrevLayer; j++)
            {
                weights[i][j] = Math.randomDouble(-1, 1);
            }
        }
    }

    public void adjustWeights(final double[] currentLayerDeltas, final double[] prevLayerValues, final double learningRate)
    {
        double[][] newWeights = new double[nodesInLayer][nodesInPrevLayer];

        for (int node = 0; node < nodesInLayer; node++)
        {
            for (int prevNode = 0; prevNode < nodesInPrevLayer; prevNode++)
            {
                newWeights[node][prevNode] = weights[node][prevNode] + learningRate * prevLayerValues[prevNode] * currentLayerDeltas[node];
            }
        }

        weights = newWeights;
    }

    public double[] getWeightsByNodeInCurrentLayer(final int nodeNum)
    {
        if (nodeNum > nodesInLayer)
        {
            throw new WeightsOutOfBoundsException(nodesInLayer, nodeNum);
        }

        return weights[nodeNum];
    }

    public double[] getWeightsByNodeInPrevLayer(final int nodeNum)
    {
        if (nodeNum > nodesInPrevLayer)
        {
            throw new WeightsOutOfBoundsException(nodesInPrevLayer, nodeNum);
        }

        double[] weightsByPrev = new double[nodesInLayer];
		//System.out.println("NodesInLayer: ["+nodesInLayer+"], NodesInPrevLayer: ["+nodesInPrevLayer+"]");
        for (int node = 0; node < nodesInLayer; node++)
        {
			//System.out.println("weightsByPrev["+node+"] = weights["+node+"]["+nodeNum+"];");
            weightsByPrev[node] = weights[node][nodeNum];
        }
        return weightsByPrev;
    }
    
    public String encodeWeights()
	{
		String out = "";
		
		for (int i = 0; i < weights.length; i++)
		{
			for (int j = 0; j < weights[i].length; j++)
			{
				out += weights[i][j];
				if (j + 1 < weights[i].length)
				{
					out += ",";
				}
			}
			if (i + 1 < weights.length)
			{
				out += "/";
			}
		}
		
		return out;
	}
	public void decodeAndSetWeights(final String string) throws DecodingException
	{
		String[] split1 = string.split("/");
		String[][] split2 = new String[split1.length][];
		for (int i = 0; i < split1.length; i++)
		{
			split2[i] = split1[i].split(",");
		}
		double[][] newWeights = new double[nodesInLayer][nodesInPrevLayer];
		try
		{
			for (int i = 0; i < nodesInLayer; i++)
			{
				for (int j = 0; j < nodesInPrevLayer; j++)
				{
					newWeights[i][j] = Double.parseDouble(split2[i][j]);
				}
			}
			
			weights = newWeights;
		}
		catch (ArrayIndexOutOfBoundsException | NumberFormatException e)
		{
			throw new DecodingException();
		}
	}

    @Override
    public String toString()
    {
        String out = "{Weights} ["+nodesInLayer+"] set(s) of weights, with ["+nodesInPrevLayer+"] weight(s) in each set.";
        String w;
        for (int node = 0; node < nodesInLayer; node++)
        {
            out += "\n<"+node+"> : ";
            w = "[";
            for (int outNode = 0; outNode < nodesInPrevLayer; outNode++)
            {
                w += weights[node][outNode];
                if (outNode + 1 < nodesInPrevLayer)
                {
                    w += ", ";
                }
            }
            w += "]";
            out += w;
        }
        return out;
    }

}
