package gui;
//--------------------------------------------------
//----- Imports ------------------------------------
//--------------------------------------------------
import importable.javafx.GridHandler;
import importable.javafx.NodeGenerator;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//~~~~~

public class NeuralNetGUI extends Application
{
	
	//--------------------------------------------------
	//----- _ ------------------------------------------
	//--------------------------------------------------
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//~~~~~
	
	//--------------------------------------------------
	//----- Constants ----------------------------------
	//--------------------------------------------------
	private final int WIDTH = 1200;
	private final int HEIGHT = 900;
	
	private enum Window {DATA, CONSOLE, OUTPUT}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//~~~~~
	//--------------------------------------------------
	//----- Variables ----------------------------------
	//--------------------------------------------------
	private NodeGenerator nodeGenerator = new NodeGenerator(new Font("Times New Roman", 15), Color.BLACK);
	
	private Scene scene;
	private GridHandler grid;
	private ScrollPane topScrollPane1, topScrollPane2, bottomScrollPane;
	private VBox topBox1, topBox2, bottomBox;
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//~~~~~
	
	@Override
	public void start(Stage window) throws Exception
	{
		
		nodeGenerator.addFont(new Font("Segoe UI", 30));
		
		// Init Grid
		grid = new GridHandler();
		grid.addColumn(-1, 0);
		grid.addColumn(-1, 0);
		grid.addRow(200, 0);
		grid.addRow(-1, 0);
		grid.addRow(-2, 0);
		grid.initGridPaneConstraints();
		
		// Init Scrolls
		topScrollPane1 = new ScrollPane();
		topScrollPane1.setPadding(new Insets(5));
		topBox1 = new VBox();
		topScrollPane1.setContent(topBox1);
		
		topScrollPane2 = new ScrollPane();
		topScrollPane2.setPadding(new Insets(5));
		topBox2 = new VBox();
		topScrollPane2.setContent(topBox2);
		
		bottomScrollPane = new ScrollPane();
		bottomBox = new VBox();
		bottomScrollPane.setContent(bottomBox);
		
		// Add contents to boxes
		int j = 25;
		for (int i = 0; i < j; i++)
		{
			CheckBox check = nodeGenerator.createCheckBox("["+(i+1)+"]");
			topBox1.getChildren().add(check);
			topBox1.setMargin(check, new Insets(0, 0, 5, 10));
		}
		
		// Text editor
		TextField textField = nodeGenerator.createTextField("Enter commands here", 1);
		textField.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
			{
				runCommand(textField.getText());
				textField.setText("");
			}
		});
		
		// Add to grid
		grid.add(topScrollPane1, 0, 0);
		grid.add(topScrollPane2, 1, 0);
		grid.add(bottomScrollPane, 0, 1, 2, 1);
		grid.add(textField, 0, 2, 2, 1);
		
		// Set up window
		scene = new Scene(grid, WIDTH, HEIGHT);
		window.setScene(scene);
		window.setTitle("Neural Network GUI");
		window.sizeToScene();
		window.show();
	}
	
	public void runCommand(final String command)
	{
		topBox2.getChildren().add(nodeGenerator.createLabel(command));
		
		switch(command)
		{
			case "select-all":
				for (Node n : topBox1.getChildren())
				{
					((CheckBox) n).setSelected(true);
				}
				break;
			case "select-none":
				for (Node n : topBox1.getChildren())
				{
					((CheckBox) n).setSelected(false);
				}
				break;
		}
	}
	
}
