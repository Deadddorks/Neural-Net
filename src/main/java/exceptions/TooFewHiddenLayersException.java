package exceptions;
//--------------------------------------------------
//----- Imports ------------------------------------
//--------------------------------------------------

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//~~~~~

public class TooFewHiddenLayersException extends RuntimeException
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

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //~~~~~

    public TooFewHiddenLayersException(final int numLayers)
    {
        super("Too few hidden layers: ["+numLayers+"] layer(s)");
    }

}
