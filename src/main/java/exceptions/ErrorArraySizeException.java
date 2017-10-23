package exceptions;
//--------------------------------------------------
//----- Imports ------------------------------------
//--------------------------------------------------

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//~~~~~

public class ErrorArraySizeException extends RuntimeException
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

    public ErrorArraySizeException(final int errorArraySize, final int currentDerivValueArraySize)
    {
        super("Error array size does not match CurrentDerivValue array size: ["+errorArraySize+"] != ["+currentDerivValueArraySize+"]");
    }

}
