package leiproject;

import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Jesus
 */
public class LeiProject extends Application {

    public int requirement[][];
    public int allocation[][];
    public int maximum[][];
    public int availablr[][];
    public int numOfP;
    public int numOfR;
    int allocMatrix;
    int maxMatrix;
    int availMatrix;

    GridPane root = new GridPane();
    Text text = new Text("Enter no. of processes");
    TextField noOfProcesses = new TextField();

    Text text2 = new Text("Enter no. of Resources");
    TextField noOfResources = new TextField();

    Text text3 = new Text("Enter Allocation Matrix");
    TextField allocationMatrix = new TextField();

    Text text4 = new Text("Enter Max Matrix");
    TextField MaxMatrix = new TextField();

    Text text5 = new Text("Enter available Matrix");
    TextField availableMatrix = new TextField();

    Button btn = new Button("Begin Simulation");

    Text result = new Text();
    Text result2 = new Text();

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.add(text, 0, 0);
        grid.add(noOfProcesses, 1, 0);

        grid.add(text2, 0, 1);
        grid.add(noOfResources, 1, 1);

        grid.add(text3, 0, 2);
        grid.add(allocationMatrix, 1, 2);

        grid.add(text4, 0, 3);
        grid.add(MaxMatrix, 1, 3);

        grid.add(text5, 0, 4);
        grid.add(availableMatrix, 1, 4);

        grid.add(btn, 0, 5);
        grid.add(result, 0, 6);
        grid.add(result2, 0, 7);

        btn.setOnAction((ActionEvent event) -> {
            numOfP = Integer.parseInt(noOfProcesses.getText());
            numOfR = Integer.parseInt(noOfResources.getText());
            allocMatrix = Integer.parseInt(allocationMatrix.getText());
            maxMatrix = Integer.parseInt(MaxMatrix.getText());
            availMatrix = Integer.parseInt(availableMatrix.getText());

            simulate(primaryStage);
        });

        Scene scene = new Scene(grid, 500, 500);

        primaryStage.setTitle("SimulationSimulator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
        //isSafe();

    }

    private void calculations() {
        requirement = new int[numOfP][numOfR];
        maximum = new int[numOfP][numOfR];
        allocation = new int[numOfP][numOfR];
        availablr = new int[1][numOfR];

        for (int i = 0; i < numOfP; i++) {
            for (int j = 0; j < numOfR; j++) {
                allocation[i][j] = allocMatrix;
            }
        }
        for (int i = 0; i < numOfP; i++) {
            for (int j = 0; j < numOfR; j++) {
                maximum[i][j] = maxMatrix;
            }
        }
        for (int j = 0; j < numOfR; j++) {
            availablr[0][j] = availMatrix;
        }
    }

    private int[][] calculate() {
        for (int i = 0; i < numOfP; i++) {
            for (int j = 0; j < numOfR; j++) {
                requirement[i][j] = maximum[i][j] - allocation[i][j];
            }
        }
        return requirement;
    }

    private boolean check(int i) {
        for (int j = 0; j < numOfR; j++) {
            if (availablr[0][j] < requirement[i][j]) {
                return false;
            }
        }
        return true;
    }

    public void simulate(Stage stage) {
        StringBuilder msg = new StringBuilder();
        calculations();
        calculate();
        boolean done[] = new boolean[numOfP];
        int j = 0;
        while (j < numOfP) {
            boolean allocated = false;
            for (int i = 0; i < numOfP; i++) {
                if (!done[i] && check(i)) {
                    for (int k = 0; k < numOfR; k++) {
                        availablr[0][k] = availablr[0][k] - requirement[i][k] + maximum[i][k];
                    }
                    msg.append("Allocated process : " + i);
                    msg.append("\n");
                    result2.setText(msg.toString());

                    allocated = done[i] = true;
                    j++;
                }
            }
            if (!allocated) {
                break;
            }
        }
        if (j == numOfP) {
            result.setText("All processes can be allocated");
        } else {
            result.setText("None of the processes be allocated safely");
        }
    }
}
