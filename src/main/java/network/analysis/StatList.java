package network.analysis;
//--------------------------------------------------
//----- Imports ------------------------------------
//--------------------------------------------------
import exceptions.StatDataNotInitializedException;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//~~~~~

public class StatList
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
	private boolean firstPassed;
	private int dataSize;
	
	private double sum;
	private double min;
	private double max;
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//~~~~~
	
	public StatList()
	{
		firstPassed = false;
		dataSize = 0;
		sum = 0;
	}
	
	public void add(final double dataPoint)
	{
		dataSize++;
		sum += dataPoint;
		
		if (firstPassed)
		{
			min = Math.min(min, dataPoint);
			max = Math.max(max, dataPoint);
		}
		else
		{
			firstPassed = true;
			
			min = dataPoint;
			max = dataPoint;
		}
	}
	
	public double getMin()
	{
		if (firstPassed)
		{
			return min;
		}
		else
		{
			throw new StatDataNotInitializedException();
		}
	}
	public double getMax()
	{
		if (firstPassed)
		{
			return max;
		}
		else
		{
			throw new StatDataNotInitializedException();
		}
	}
	public double getAverage()
	{
		if (firstPassed)
		{
			return sum / dataSize;
		}
		else
		{
			throw new StatDataNotInitializedException();
		}
	}
	
}
