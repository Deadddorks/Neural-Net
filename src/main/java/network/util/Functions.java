package network.util;
//--------------------------------------------------
//----- Imports ------------------------------------
//--------------------------------------------------

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//~~~~~

import java.util.function.Function;

public class Functions
{

    //--------------------------------------------------
    //----- _ ------------------------------------------
    //--------------------------------------------------

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //~~~~~

    //--------------------------------------------------
    //----- Constants ----------------------------------
    //--------------------------------------------------
    public static final Function<Double, Double> SIGMOID = (Double d) -> 1 / (1 + Math.pow(Math.E, -d));
    public static final Function<Double, Double> REVERSE_SIGMOID = (Double d) -> -Math.log((1 / d) - 1);

    public static final Function<Double, Double> X = (Double d) -> d;
    public static final Function<Double, Double> ONE = (Double d) -> 1.0;
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //~~~~~
    //--------------------------------------------------
    //----- Variables ----------------------------------
    //--------------------------------------------------

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //~~~~~

    private Functions()
    {

    }

    public static Function<Double, Double> getSigmoid(final double vGrow, final double hStretch)
    {
        return (Double d) -> vGrow / (1 + Math.pow(Math.E, -(d / hStretch)));
    }

    public static Function<Double, Double> getSigmoidDeriv(final double vGrow, final double hStretch)
    {
        return (Double d) -> vGrow * Math.pow(Math.E, -d / hStretch) / (hStretch * Math.pow(1 + Math.pow(Math.E, -d / hStretch), 2));
    }

}
