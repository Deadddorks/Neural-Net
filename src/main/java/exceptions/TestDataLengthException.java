package exceptions;
//--------------------------------------------------
//----- Imports ------------------------------------
//--------------------------------------------------

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//~~~~~

public class TestDataLengthException extends RuntimeException
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

    public TestDataLengthException()
    {
        super("Input value(s) List & Output value(s) List vary in length.");
    }

}
