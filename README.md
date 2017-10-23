# Neural-Network  
A feed-forward Neural Network which has `I` imputs, `H` hidden layers, `N` Nodes per hidden layer, and `O` ouputs.  

Data is passed in and received via a `double[]`.
Training is done via passing in an `ArrayList<double[]>` for the inputs and the coresponding outputs.
Eventually I would like to set up a better system for this.  

## Proper procedure for network setup:  
- new Network(`I`, `H`, `N`, `O`);  
- setHiddenLayerFunctions(`activation function via Function<Double, Double>`, `derivitive of that function via Function<Double, Double>`);  
- setOutputLayerFunction(`activation function via Function<Double, Double>`, `derivitive of that function via Function<Double, Double>`);  
- initRandomWeights();  
- adjustDataScalingsToDataSet(`input data as ArrayList<double[]>`, `expected outputs as ArrayList<double[]>`);  

## Once the network is set up, you can then do 3 things:  
- pass in data via input(`input data as double[]`);  
- pass in data then check the output via getOutputs();  
- train the network via train(`list of input datas as ArrayList<double[]>`, `list of output datas as ArrayList<double[]>`,
`learning rate as double`, `number of epochs to run through as int`, `show outputs every this many epochs as int`)

# Example Successes
## 13 logical comparisons of 3 booleans
![13booleans_1](https://user-images.githubusercontent.com/32821726/31862470-e678fff2-b703-11e7-8a2c-575cc19b7c48.png)
![13booleans_2](https://user-images.githubusercontent.com/32821726/31862473-e96159b2-b703-11e7-9116-71a96ffdf610.png)
## Sorting the X and Y coordinates of a tic-tac toe board into 9 bins
![x y-9bins](https://user-images.githubusercontent.com/32821726/31862474-f616946a-b703-11e7-8153-ae577e250fb5.png)
## Turning the X and Y coordinates of a tic-tac-toe board into a linear Z
![x y-z](https://user-images.githubusercontent.com/32821726/31862476-ff0ecfba-b703-11e7-9fc1-a988f484a146.png)
