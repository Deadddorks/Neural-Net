package exceptions;//--------------------------------------------------
//----- Imports ------------------------------------
//--------------------------------------------------

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//~~~~~

public class InvalidOutputSizeException extends RuntimeException
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

    public InvalidOutputSizeException(final int expected, final int actual)
    {
        super("Invalid Input Size: Expected: ["+expected+"], Actual: ["+actual+"]");
    }

}
