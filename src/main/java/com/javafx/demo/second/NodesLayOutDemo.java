package com.javafx.demo.second;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * NodesLayOutDemo.java
 *
 * Console-driven launcher that asks the user to pick a layout, then opens
 * a JavaFX window demonstrating that layout with a variety of nodes.
 *
 * Supported layouts (select by number):
 *   1 - HBox
 *   2 - VBox
 *   3 - FlowPane
 *   4 - GridPane
 *   5 - StackPane
 *   6 - BorderPane
 *   7 - AnchorPane
 *
 * Java 8 compatible.
 *
 * Usage: run this program; it will prompt on the console for the layout number.
 *
 * Author: Pradeep (adapted)
 */
public class NodesLayOutDemo extends Application {

    /**
     * Console-driven main: prompt the user, then call launch with the selected layout
     * as the first unnamed argument.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("==============================================");
        System.out.println(" Welcome to Layout Demonstration (JavaFX) ");
        System.out.println("==============================================");
        System.out.println("Select which layout you want to view (enter the number):");
        System.out.println(" 1 - HBox");
        System.out.println(" 2 - VBox");
        System.out.println(" 3 - FlowPane");
        System.out.println(" 4 - GridPane");
        System.out.println(" 5 - StackPane");
        System.out.println(" 6 - BorderPane");
        System.out.println(" 7 - AnchorPane");
        System.out.print("Your choice [1..7] (default 3 - FlowPane): ");

        String choice = "FlowPane";
        try {
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                int n = Integer.parseInt(line);
                switch (n) {
                    case 1: choice = "HBox"; break;
                    case 2: choice = "VBox"; break;
                    case 3: choice = "FlowPane"; break;
                    case 4: choice = "GridPane"; break;
                    case 5: choice = "StackPane"; break;
                    case 6: choice = "BorderPane"; break;
                    case 7: choice = "AnchorPane"; break;
                    default:
                        System.out.println("Invalid number. Defaulting to FlowPane.");
                        choice = "FlowPane";
                }
            } else {
                System.out.println("No input. Defaulting to FlowPane.");
            }
        } catch (Exception ex) {
            System.out.println("Input error. Defaulting to FlowPane.");
            choice = "FlowPane";
        } finally {
            scanner.close();
        }

        // Pass the chosen layout as the first argument to the JavaFX application
        launch(new String[]{ choice });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Read layout choice passed from main as first unnamed arg
        List<String> params = getParameters().getUnnamed();
        String layoutChoice = (params.isEmpty() ? "FlowPane" : params.get(0));
        if (layoutChoice == null) layoutChoice = "FlowPane";
        layoutChoice = layoutChoice.trim().toLowerCase();

        // Create demo nodes (no event handling)
        List<Node> demoNodes = createDemoNodes();

        javafx.scene.Parent root = null;

        // Select root layout based on user choice
        if ("hbox".equals(layoutChoice)) {
            HBox hbox = new HBox(10); // spacing between children = 10
            // Adds 12px padding inside HBox on all sides
            hbox.setPadding(new Insets(12));
            hbox.getChildren().addAll(demoNodes);
            hbox.setAlignment(Pos.CENTER_LEFT);
            root = hbox;

        } else if ("vbox".equals(layoutChoice)) {
            VBox vbox = new VBox(10);
            vbox.setPadding(new Insets(12));
            vbox.getChildren().addAll(demoNodes);
            vbox.setAlignment(Pos.TOP_CENTER);
            root = vbox;

        } else if ("flowpane".equals(layoutChoice) || "flow".equals(layoutChoice)) {
            FlowPane flow = new FlowPane();
            flow.setHgap(10);
            flow.setVgap(10);
            flow.setPadding(new Insets(12));
            flow.getChildren().addAll(demoNodes);
            root = flow;

        } else if ("gridpane".equals(layoutChoice) || "grid".equals(layoutChoice)) {
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(12));

            // place demo nodes into a 3-column grid for demonstration
            int col = 0;
            int row = 0;
            for (Node n : demoNodes) {
                grid.add(n, col, row);
                col++;
                if (col > 2) {
                    col = 0;
                    row++;
                }
            }
            root = grid;

        } else if ("stackpane".equals(layoutChoice) || "stack".equals(layoutChoice)) {
            StackPane stack = new StackPane();
            stack.setPadding(new Insets(12));
            // show first few nodes stacked so overlap is visible
            if (demoNodes.size() >= 3) {
                stack.getChildren().addAll(demoNodes.get(0), demoNodes.get(1), demoNodes.get(2));
            } else {
                stack.getChildren().addAll(demoNodes);
            }
            root = stack;

        } else if ("borderpane".equals(layoutChoice) || "border".equals(layoutChoice)) {
            BorderPane border = new BorderPane();
            border.setPadding(new Insets(12));

            // Top: HBox with label + textfield
            HBox top = new HBox(8);
            top.setPadding(new Insets(6));
            top.getChildren().addAll(new Label("Top Title:"), new TextField("Type here..."));
            top.setAlignment(Pos.CENTER_LEFT);
            border.setTop(top);

            // Left: vertical menu
            VBox left = new VBox(8);
            left.setPadding(new Insets(6));
            left.getChildren().addAll(new Label("Left Menu"), new Button("Btn 1"), new Button("Btn 2"));
            border.setLeft(left);

            // Right: small list
            ListView<String> listView = new ListView<>(FXCollections.observableArrayList("Item A", "Item B", "Item C"));
            listView.setPrefWidth(120);
            border.setRight(listView);

            // Bottom: HBox with ProgressBar and Slider
            HBox bottom = new HBox(10);
            bottom.setPadding(new Insets(6));
            bottom.getChildren().addAll(new ProgressBar(0.6), new Slider());
            bottom.setAlignment(Pos.CENTER);
            border.setBottom(bottom);

            // Center: TextArea
            TextArea centerArea = new TextArea("Center content area\n(Resizable)");
            border.setCenter(centerArea);

            root = border;

        } else if ("anchorpane".equals(layoutChoice) || "anchor".equals(layoutChoice)) {
            AnchorPane anchor = new AnchorPane();
            anchor.setPadding(new Insets(12));

            Label anchoredLabel = new Label("Anchored Top-Left");
            Button anchoredBtn = new Button("Bottom-Right");

            AnchorPane.setTopAnchor(anchoredLabel, 8.0);
            AnchorPane.setLeftAnchor(anchoredLabel, 8.0);

            AnchorPane.setBottomAnchor(anchoredBtn, 8.0);
            AnchorPane.setRightAnchor(anchoredBtn, 8.0);

            anchor.getChildren().addAll(anchoredLabel, anchoredBtn);

            TextField stretched = new TextField("Stretched TextField (left & right anchors)");
            AnchorPane.setTopAnchor(stretched, 40.0);
            AnchorPane.setLeftAnchor(stretched, 8.0);
            AnchorPane.setRightAnchor(stretched, 8.0);
            anchor.getChildren().add(stretched);

            root = anchor;

        } else {
            // fallback to FlowPane
            FlowPane flow = new FlowPane();
            flow.setHgap(10);
            flow.setVgap(10);
            flow.setPadding(new Insets(12));
            flow.getChildren().addAll(demoNodes);
            root = flow;
            layoutChoice = "flowpane";
        }

        // Create Scene and show
        Scene scene = new Scene(root, 900, 520);
        primaryStage.setScene(scene);
        primaryStage.setTitle("NodesLayOutDemo - Layout: " + layoutChoice.toUpperCase());
        primaryStage.show();
    }

    /**
     * Create a list of demo nodes (controls) to add to different layouts.
     * No event handling - purely visual.
     */
    private List<Node> createDemoNodes() {
        List<Node> nodes = new ArrayList<Node>();

        Label lbl = new Label("Label: Hello World");
        TextField tf = new TextField("TextField - sample text");
        TextArea ta = new TextArea("TextArea - multi-line\n(try resizing window)");
        ta.setPrefRowCount(3);
        Button btn = new Button("Button");
        CheckBox cb = new CheckBox("CheckBox");
        RadioButton rb1 = new RadioButton("Option 1");
        RadioButton rb2 = new RadioButton("Option 2");
        ToggleGroup tg = new ToggleGroup();
        rb1.setToggleGroup(tg);
        rb2.setToggleGroup(tg);

        ComboBox<String> combo = new ComboBox<String>(FXCollections.observableArrayList("Choice A", "Choice B", "Choice C"));
        combo.getSelectionModel().selectFirst();

        ListView<String> listView = new ListView<String>(FXCollections.observableArrayList("One", "Two", "Three", "Four"));
        listView.setPrefHeight(80);
        listView.setPrefWidth(120);

        Slider slider = new Slider(0, 100, 30);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        ProgressBar progress = new ProgressBar(0.4);

        // For HBox/VBox demo: show a growable text field
        HBox growBox = new HBox();
        TextField growField = new TextField("Growable TextField - HBox/HGrow demo");
        HBox.setHgrow(growField, Priority.ALWAYS);
        growField.setMaxWidth(Double.MAX_VALUE);
        growBox.getChildren().add(growField);
        growBox.setPrefWidth(240);

        // Add nodes in a useful order
        nodes.add(lbl);
        nodes.add(btn);
        nodes.add(tf);
        nodes.add(ta);
        nodes.add(cb);
        nodes.add(rb1);
        nodes.add(rb2);
        nodes.add(combo);
        nodes.add(listView);
        nodes.add(slider);
        nodes.add(progress);
        nodes.add(growBox);

        return nodes;
    }
}
