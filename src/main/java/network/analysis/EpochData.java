package network.analysis;
//--------------------------------------------------
//----- Imports ------------------------------------
//--------------------------------------------------
import importable.util.StringMethods;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//~~~~~

public class EpochData
{
	
	//--------------------------------------------------
	//----- _ ------------------------------------------
	//--------------------------------------------------
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//~~~~~
	
	//--------------------------------------------------
	//----- Constants ----------------------------------
	//--------------------------------------------------
	private final char corner = '+';
	private final char horizStrong = '-';
	private final char horizWeak = '-';
	private final char vertStrong = '|';
	private final char vertWeak = '|';
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//~~~~~
	//--------------------------------------------------
	//----- Variables ----------------------------------
	//--------------------------------------------------
	private ArrayList<DataEntry> entries;
	private StatList[] stats;
	
	private String name;
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//~~~~~
	
	public EpochData(final String name)
	{
		this.name = name;
		
		entries = new ArrayList<>();
	}
	
	public void add(final DataEntry entry)
	{
		entries.add(entry);
	}
	
	public String getName()
	{
		return name;
	}
	
	private void calculateStats()
	{
		stats = new StatList[entries.get(0).size()];
		for (int dataPoint = 0; dataPoint < stats.length; dataPoint++)
		{
			stats[dataPoint] = new StatList();
		}
		for (DataEntry entry : entries)
		{
			for (int dataPoint = 0; dataPoint < entry.size(); dataPoint++)
			{
				stats[dataPoint].add(entry.getError(dataPoint));
			}
		}
	}
	
	public VBox getEpochAsVBox(final Font font, final Color color, final int width, final int height)
	{
		return null;
	}
	
	public void print(final int dataWidth)
	{
		int numDataPoints = entries.get(0).size();
		String out = "";
		
		int maxWidth = Integer.toString(entries.size() + 1).length();
		int adjNumWidth = maxWidth + 2;
		
		calculateStats();
		
		// Prints the top row
		out += Character.toString(corner);
		for (int n = 0; n < adjNumWidth; n++)
		{
			out += Character.toString(horizStrong);
		}
		out += Character.toString(corner);
		for (int p = 0; p < numDataPoints * 3; p++)
		{
			for (int w = 0; w < dataWidth; w++)
			{
				out += Character.toString(horizStrong);
			}
			out += Character.toString(corner);
		}
		out += "\n";
		
		// Prints the data column labels
		out += Character.toString(vertStrong);
		out += StringMethods.getStringWithLength(" #", adjNumWidth);
		out += Character.toString(vertStrong);
		for (int p = 0; p < numDataPoints; p++)
		{
			out += StringMethods.getStringWithLength("Act ("+(p + 1)+")", dataWidth, StringMethods.Align.CENTER) + Character.toString(vertWeak);
			out += StringMethods.getStringWithLength("Exp ("+(p + 1)+")", dataWidth, StringMethods.Align.CENTER) + Character.toString(vertWeak);
			out += StringMethods.getStringWithLength("Err ("+(p + 1)+")", dataWidth, StringMethods.Align.CENTER) + Character.toString(vertStrong);
		}
		out += "\n";
		
		// Prints the row separating labels and data
		out += Character.toString(corner);
		for (int n = 0; n < adjNumWidth; n++)
		{
			out += Character.toString(horizWeak);
		}
		out += Character.toString(corner);
		for (int p = 0; p < numDataPoints * 3; p++)
		{
			for (int w = 0; w < dataWidth; w++)
			{
				out += Character.toString(horizWeak);
			}
			out += Character.toString(corner);
		}
		out += "\n";
		
		// Prints the actual data
		DataEntry entry;
		for (int i = 0; i < entries.size(); i++)
		{
			entry = entries.get(i);
			out += Character.toString(vertStrong);
			out += StringMethods.getStringWithLength(" " + Integer.toString(i + 1), adjNumWidth);
			out += Character.toString(vertStrong);
			for (int d = 0; d < numDataPoints; d++)
			{
				//System.out.println("["+entry.getActual(d)+", "+entry.getExpected(d)+", "+entry.getError(d)+"]");
				out += StringMethods.getStringWithLength(Double.toString(importable.util.Math.roundToLength(entry.getActual(d), dataWidth - 2)), ' ', dataWidth, StringMethods.Align.CENTER) + Character.toString(vertWeak);
				out += StringMethods.getStringWithLength(Double.toString(importable.util.Math.roundToLength(entry.getExpected(d), dataWidth - 2)), ' ', dataWidth, StringMethods.Align.CENTER) + Character.toString(vertWeak);
				out += StringMethods.getStringWithLength(Double.toString(importable.util.Math.roundToLength(entry.getError(d), dataWidth - 2)), ' ', dataWidth, StringMethods.Align.CENTER) + Character.toString(vertStrong);
			}
			out += "\n";
		}
		
		// Prints the row that separates the data and the stats
		out += Character.toString(corner);
		for (int n = 0; n < adjNumWidth; n++)
		{
			out += Character.toString(horizStrong);
		}
		out += Character.toString(corner);
		for (int p = 0; p < numDataPoints * 3; p++)
		{
			for (int w = 0; w < dataWidth; w++)
			{
				out += Character.toString(horizStrong);
			}
			out += Character.toString(corner);
		}
		out += "\n";
		
		// --------------- Printing error stats ---------------
		
		// Min errors
		out += Character.toString(vertStrong);
		out += StringMethods.getStringWithLength("", horizStrong, adjNumWidth);
		out += Character.toString(vertStrong);
		for (int p = 0; p < numDataPoints; p++)
		{
			out += StringMethods.getStringWithLength("   Min: "+stats[p].getMin(), dataWidth * 3 + 2, StringMethods.Align.LEFT);
			
			out += Character.toString(vertStrong);
		}
		out += "\n";
		// Max errors
		out += Character.toString(vertStrong);
		out += StringMethods.getStringWithLength("", horizStrong, adjNumWidth);
		out += Character.toString(vertStrong);
		for (int p = 0; p < numDataPoints; p++)
		{
			out += StringMethods.getStringWithLength("   Max: "+stats[p].getMax(), dataWidth * 3 + 2, StringMethods.Align.LEFT);
			
			out += Character.toString(vertStrong);
		}
		out += "\n";
		// Avg errors
		out += Character.toString(vertStrong);
		out += StringMethods.getStringWithLength("", horizStrong, adjNumWidth);
		out += Character.toString(vertStrong);
		for (int p = 0; p < numDataPoints; p++)
		{
			out += StringMethods.getStringWithLength("   Avg: "+stats[p].getAverage(), dataWidth * 3 + 2, StringMethods.Align.LEFT);
			
			out += Character.toString(vertStrong);
		}
		out += "\n";
		
		// Prints end line
		out += Character.toString(corner);
		for (int n = 0; n < adjNumWidth; n++)
		{
			out += Character.toString(horizStrong);
		}
		out += Character.toString(corner);
		for (int p = 0; p < numDataPoints * 3; p++)
		{
			for (int w = 0; w < dataWidth; w++)
			{
				out += Character.toString(horizStrong);
			}
			out += Character.toString(corner);
		}
		out += "\n";
		
		System.out.println(" ----- Epoch: "+name+" ----- ");
		System.out.print(out);
	}
	
}
