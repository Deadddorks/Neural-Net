package threads;
//--------------------------------------------------
//----- Imports ------------------------------------
//--------------------------------------------------

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//~~~~~

public class ThreadListener
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
	private ThreadHandler handler;
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//~~~~~

	public ThreadListener()
	{

	}

	public void setHandler(ThreadHandler handler)
	{
		this.handler = handler;
	}

	public void trigger()
	{
		handler.onTrigger();
	}
	public void finished()
	{
		handler.onFinished();
	}

}
