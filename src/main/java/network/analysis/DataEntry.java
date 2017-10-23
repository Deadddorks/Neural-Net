package network.analysis;
//--------------------------------------------------
//----- Imports ------------------------------------
//--------------------------------------------------
import java.util.ArrayList;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//~~~~~

public class DataEntry
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
	private ArrayList<double[]> data;
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//~~~~~
	
	public DataEntry()
	{
		data = new ArrayList<>();
	}
	
	public void addData(final double actual, final double expected, final double error)
	{
		data.add(new double[] {actual, expected, error});
	}
	
	public double[][] getData()
	{
		double[][] dataArray = new double[data.size()][];
		
		for (int i = 0; i < data.size(); i++)
		{
			dataArray[i] = data.get(i);
		}
		
		return dataArray;
	}
	
	public double[] getErrors()
	{
		double[] dataArray = new double[data.size()];
		
		for (int i = 0; i < data.size(); i++)
		{
			dataArray[i] = data.get(i)[2];
		}
		
		return dataArray;
	}
	
	public double getActual(final int dataPointNum)
	{
		return data.get(dataPointNum)[0];
	}
	public double getExpected(final int dataPointNum)
	{
		return data.get(dataPointNum)[1];
	}
	public double getError(final int dataPointNum)
	{
		return data.get(dataPointNum)[2];
	}
	
	public int size()
	{
		return data.size();
	}
	
}
