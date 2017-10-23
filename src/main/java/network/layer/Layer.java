package network.layer;
//--------------------------------------------------
//----- Imports ------------------------------------
//--------------------------------------------------
import exceptions.DecodingException;
import exceptions.InvalidInputSizeException;
import exceptions.TooFewNodesInLayerException;
import importable.util.Math;
import importable.util.StringMethods;
import network.Node;
import network.Weights;

import java.util.function.Function;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//~~~~~

public class Layer
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
    protected final int size;
    protected final int nodesInPrevLayer;
    protected final Layer prevLayer;

	protected Function<Double, Double> function;
	protected Function<Double, Double> functionDeriv;

    protected Node[] nodes;
    protected Weights weights;

    protected double[] deltas;
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //~~~~~

    public Layer(final int size,  final Layer prevLayer, final Function<Double, Double> function, final Function<Double, Double> functionDeriv)
    {
        if (size < 1)
        {
            throw new TooFewNodesInLayerException(size);
        }

        this.size = size;
        this.function= function;
        this.functionDeriv = functionDeriv;
        this.prevLayer = prevLayer;
        if (prevLayer != null)
        {
            this.nodesInPrevLayer = prevLayer.size;
        }
        else
        {
            this.nodesInPrevLayer = 0;
        }

        nodes = new Node[size];

        for (int i = 0; i < nodes.length; i++)
        {
            nodes[i] = new Node();
        }

        weights = new Weights(size, nodesInPrevLayer);
    }

    public void passInData(final double[] data)
    {
        if (data.length != nodesInPrevLayer)
        {
            throw new InvalidInputSizeException(size, data.length);
        }

        double nodeSum;
        double[] weightsForNode;
        for (int node = 0; node < size; node++)
        {
            nodeSum = 0;
            weightsForNode = weights.getWeightsByNodeInCurrentLayer(node);
            for (int outNode = 0; outNode < nodesInPrevLayer; outNode++)
            {
                nodeSum += weightsForNode[outNode] * data[outNode];
            }
            nodes[node].setValue(nodeSum);
        }
    }

    public double[] getNodeValuesFromFunction()
    {
        double[] values = new double[size];
        for (int node = 0; node < size; node++)
        {
            values[node] = nodes[node].calculate(function);
        }
        return values;
    }
    public double[] getNodeValuesFromFunctionDeriv()
    {
        double[] values = new double[size];
        for (int node = 0; node < size; node++)
        {
            values[node] = nodes[node].calculate(functionDeriv);
        }
        return values;
    }

    public void setPrevLayerDeltas()
    {
        double[] prevLayerDerivs = prevLayer.getNodeValuesFromFunctionDeriv();
        double[] prevLayerDeltas = new double[prevLayer.size];
        double[] prevNodeWeights;

        for (int prevNode = 0; prevNode < prevLayer.size; prevNode++)
        {
            prevNodeWeights = weights.getWeightsByNodeInPrevLayer(prevNode);
            for (int node = 0; node < size; node++)
            {
                prevLayerDeltas[prevNode] += prevNodeWeights[node] * deltas[node];
            }
            prevLayerDeltas[prevNode] = prevLayerDeltas[prevNode] * prevLayerDerivs[prevNode];
        }

        prevLayer.setDeltas(prevLayerDeltas);
    }

    public void adjustWeights(final double learningRate)
    {
        weights.adjustWeights(deltas, prevLayer.getNodeValuesFromFunction(), learningRate);
    }

    protected void setDeltas(final double[] deltas)
    {
        this.deltas = deltas;
    }

    public void randomizeWeights()
    {
        weights.randomize();
    }
    public String getWeightArrayAsString()
    {
        return weights.encodeWeights();
    }
    public void decodeAndSetWeights(final String string) throws DecodingException
    {
        weights.decodeAndSetWeights(string);
    }

	public void setFunctions(Function<Double, Double> function, Function<Double, Double> functionDeriv)
	{
		this.function = function;
		this.functionDeriv = functionDeriv;
	}
	
	public String[] getNodeDataAsStringList(final int eachWidth)
    {
        String[] data = new String[size];
        Node tN;
        for (int n = 0; n < size; n++)
        {
            tN = nodes[n];
            data[n] = "["+ StringMethods.getStringWithLengthCrop(Double.toString(Math.round(tN.getValue(), eachWidth -2)), eachWidth, StringMethods.Align.CENTER)+"] : {"+ StringMethods.getStringWithLengthCrop(Double.toString(Math.round(tN.calculate(function), eachWidth - 2)), eachWidth, StringMethods.Align.CENTER)+"}";
        }
        return data;
    }

    @Override
    public String toString()
    {
        String out = " --- Printing Node Values and Activated Node Values --- \n";
    
        for (Node n : nodes)
        {
        	out += "Raw: ["+n.getValue()+"], Activated: ["+n.calculate(function)+"].\n";
        }
        
        return out;
    }

}
