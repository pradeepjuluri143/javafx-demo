package com.javafx.demo.fourth;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * EventHandlingDemo
 * -----------------
 * A single-file JavaFX 8-compatible demo showing many common node event handlers.
 * Copy-paste this file as EventHandlingDemo.java, then compile & run with Java 8.
 *
 * Notes for students (inline comments in code below explain each handler):
 *  - Action events (Button, MenuItem, etc.)
 *  - Mouse events (click, enter, exit, press, drag)
 *  - Key events (typed, pressed)
 *  - Focus events (focus gained/lost)
 *  - Change listeners (slider, text property, selection)
 *  - Drag-and-drop
 *  - Context menus and right-click handling
 *
 * Run with Java 8 (JavaFX bundled):
 *   javac EventHandlingDemo.java
 *   java EventHandlingDemo
 */
public class EventHandlingDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Event Handling Demo (Java 8)");

        // Top-level layout
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(12));

        // --- Left: Controls list with many nodes ---
        VBox controlsBox = new VBox(10);
        controlsBox.setPadding(new Insets(6));

        // 1) Button with ActionEvent
        Button btn = new Button("Click Me (Action)");
        // Action events are high level events for controls like Button, MenuItem, etc.
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Button Action: You clicked the button!");
                showTemporaryAlert("Button clicked", "You pressed the main button.");
            }
        });
        // Also demonstrate mouse click on same button (lower-level mouse event)
        btn.setOnMouseClicked(e -> System.out.println("Button MouseClicked: button at " + e.getScreenX() + "," + e.getScreenY()));

        // 2) Label that listens for mouse enter/exit
        Label lblHover = new Label("Hover over me (Mouse Enter/Exit)");
        lblHover.setPrefWidth(220);
        lblHover.setOnMouseEntered(e -> lblHover.setText("Thanks for hovering!"));
        lblHover.setOnMouseExited(e -> lblHover.setText("Hover over me (Mouse Enter/Exit)"));

        // 3) TextField with key events and focus
        TextField tf = new TextField();
        tf.setPromptText("Type here (Key events & focus)");
        tf.setOnKeyTyped(e -> System.out.println("KeyTyped in TextField: '" + e.getCharacter() + "'"));
        tf.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                System.out.println("Enter pressed: text = " + tf.getText());
            }
        });
        // Focus listener using focusedProperty() change listener
        tf.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            System.out.println("TextField focus changed: nowFocused=" + isNowFocused);
        });

        // 4) TextArea with context menu (right click)
        TextArea ta = new TextArea("Right-click to see ContextMenu. Try selecting text then right-click.");
        ta.setPrefRowCount(3);
        ContextMenu ctx = new ContextMenu();
        MenuItem cut = new MenuItem("Cut");
        MenuItem copy = new MenuItem("Copy");
        MenuItem paste = new MenuItem("Paste");
        ctx.getItems().addAll(cut, copy, paste);
        // Implement actions for context menu items
        cut.setOnAction(e -> ta.cut());
        copy.setOnAction(e -> ta.copy());
        paste.setOnAction(e -> ta.paste());
        ta.setContextMenu(ctx);

        // 5) CheckBox and RadioButtons with change listeners
        CheckBox cb = new CheckBox("I agree (ChangeListener)");
        cb.selectedProperty().addListener((obs, oldV, newV) -> System.out.println("CheckBox selected=" + newV));

        RadioButton r1 = new RadioButton("Option A");
        RadioButton r2 = new RadioButton("Option B");
        ToggleGroup tg = new ToggleGroup();
        r1.setToggleGroup(tg);
        r2.setToggleGroup(tg);
        // listen for selected toggle changes
        tg.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle == null) {
                System.out.println("Radio selection cleared");
            } else {
                RadioButton selected = (RadioButton) newToggle;
                System.out.println("Radio selected: " + selected.getText());
            }
        });

        // 6) Slider with continuous value change
        Slider slider = new Slider(0, 100, 30);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        // Add a listener to the valueProperty to react whenever the slider moves
        slider.valueProperty().addListener((obs, oldVal, newVal) -> System.out.println("Slider value=" + newVal.intValue()));

        // 7) ListView and ComboBox selection events
        ListView<String> listView = new ListView<>(FXCollections.observableArrayList("Apple", "Banana", "Cherry"));
        listView.setPrefHeight(80);
        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> System.out.println("ListView selected: " + newVal));

        ComboBox<String> combo = new ComboBox<>(FXCollections.observableArrayList("Red", "Green", "Blue"));
        combo.setOnAction(e -> System.out.println("Combo action: " + combo.getSelectionModel().getSelectedItem()));

        // 8) Hyperlink (action event)
        Hyperlink link = new Hyperlink("A sample hyperlink (Action)");
        link.setOnAction(e -> System.out.println("Hyperlink clicked: pretend to open browser"));

        // 9) ImageView with mouse events (click & drag)
        ImageView imgView;
        try {
            // Use a simple colored rectangle image via data URL would be complex; we'll use JavaFX shapes instead.
            imgView = new ImageView(new Image("https://via.placeholder.com/100x60.png?text=IMG"));
            imgView.setPreserveRatio(true);
            imgView.setFitWidth(100);
            imgView.setOnMouseClicked(e -> System.out.println("Image clicked at " + e.getX() + "," + e.getY()));
        } catch (Exception ex) {
            // If image fails to load (no internet), create a placeholder shape instead
            imgView = new ImageView();
            System.out.println("Could not load remote image; using blank ImageView fallback.");
        }

        // 10) Shapes: Rectangle with mouse press, drag and release handling
        Rectangle rect = new Rectangle(120, 60, Color.LIGHTBLUE);
        rect.setArcWidth(10);
        rect.setArcHeight(10);
        rect.setStroke(Color.DARKBLUE);
        // track dragging for the rectangle
        rect.setOnMousePressed(e -> {
            System.out.println("Rectangle pressed at: " + e.getX() + "," + e.getY());
        });
        rect.setOnMouseDragged(e -> rect.setTranslateX(rect.getTranslateX() + e.getX() - rect.getWidth()/2));
        rect.setOnMouseReleased(e -> System.out.println("Rectangle released."));

        // 11) Canvas with drawing using mouse drag
        Canvas canvas = new Canvas(250, 120);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(2);
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            gc.beginPath();
            gc.moveTo(e.getX(), e.getY());
            gc.stroke();
        });
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            gc.lineTo(e.getX(), e.getY());
            gc.stroke();
        });
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> gc.closePath());

        // 12) Drag-and-drop demo: drag from a Label and drop onto a target
        Label dragSource = new Label("Drag me ->");
        dragSource.setOnDragDetected(event -> {
            // Start drag-and-drop gesture
            Dragboard db = dragSource.startDragAndDrop(TransferMode.COPY);
            // Put a string on the dragboard
            ClipboardContent content = new ClipboardContent();
            content.putString(dragSource.getText());
            db.setContent(content);
            event.consume();
        });

        Label dropTarget = new Label("Drop here");
        dropTarget.setPrefWidth(120);
        dropTarget.setStyle("-fx-border-color: gray; -fx-padding: 6;");
        // When something is dragged over the target, accept it if it has a string
        dropTarget.setOnDragOver(event -> {
            if (event.getGestureSource() != dropTarget && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });
        // On drop, read the string
        dropTarget.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                dropTarget.setText("Dropped: " + db.getString());
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });

        // 13) Scroll event on a pane
        Label scrollLabel = new Label("Scroll your mouse wheel here");
        scrollLabel.setOnScroll(e -> System.out.println("Scroll deltaY=" + e.getDeltaY()));

        // 14) MenuBar with MenuItem action events
        MenuBar menuBar = new MenuBar();
        Menu file = new Menu("File");
        MenuItem miNew = new MenuItem("New");
        MenuItem miExit = new MenuItem("Exit");
        miNew.setOnAction(e -> System.out.println("New menu clicked"));
        miExit.setOnAction(e -> primaryStage.close());
        file.getItems().addAll(miNew, new SeparatorMenuItem(), miExit);
        menuBar.getMenus().add(file);

        // 15) ProgressBar bound to slider (showing property binding + change)
        ProgressBar pb = new ProgressBar();
        pb.setPrefWidth(180);
        // bind progress to slider value (normalized)
        pb.progressProperty().bind(slider.valueProperty().divide(100));

        // Layout composition: group related nodes so students can see structure
        controlsBox.getChildren().addAll(menuBar, btn, lblHover, tf, ta, cb, r1, r2, slider, pb, listView, combo, link, imgView, dragSource, dropTarget, scrollLabel);
        ScrollPane leftScroll = new ScrollPane(controlsBox);
        leftScroll.setFitToWidth(true);
        leftScroll.setPrefWidth(320);

        // --- Center: visual demo area ---
        VBox centerBox = new VBox(10);
        centerBox.setPadding(new Insets(6));
        Label centerTitle = new Label("Interactive Visual Area (mouse & keyboard events)");
        centerTitle.setFont(Font.font(14));

        HBox shapesRow = new HBox(10);
        shapesRow.getChildren().addAll(rect, canvas, new Separator());

        // Add ImageView fallback + controls
        centerBox.getChildren().addAll(centerTitle, shapesRow, tf, ta);

        // --- Bottom: status bar shows event details dynamically ---
        Label status = new Label("Status: Ready");
        status.setPrefHeight(24);
        status.setStyle("-fx-border-color: lightgray; -fx-padding: 4;");

        // Connect some controls to status for real-time feedback
        btn.setOnAction(e -> status.setText("Status: Button clicked at " + System.currentTimeMillis()));
        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> status.setText("Status: Selected " + newV));
        slider.valueProperty().addListener((obs, oldV, newV) -> status.setText("Status: Slider " + newValToPercent(newV.doubleValue())));

        // Demonstrate keyboard event at scene-level (captures keys regardless of node focus)
        Scene scene = new Scene(root, 980, 560);
        scene.setOnKeyPressed(e -> {
            // Example: press ESC to clear the status
            if (e.getCode() == KeyCode.ESCAPE) {
                status.setText("Status: Cleared (ESC pressed)");
            } else {
                status.setText("Status: Key pressed: " + e.getCode());
            }
        });

        // Also demonstrate mouse clicked on scene background
        scene.setOnMouseClicked(e -> {
            // Ignore clicks that hit controls by checking target
            if (e.getTarget() instanceof javafx.scene.layout.Region || e.getTarget() instanceof Canvas) {
                // don't override component-specific handlers
            } else {
                System.out.println("Scene clicked at " + e.getSceneX() + "," + e.getSceneY());
            }
        });

        // Put pieces together
        root.setLeft(leftScroll);
        root.setCenter(centerBox);
        root.setBottom(status);

        // a top-level instruction label
        Label instr = new Label("Tip: Try clicking, typing, dragging, right-clicking on nodes in the left pane.");
        instr.setPadding(new Insets(6));
        root.setTop(instr);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // small helper to format slider value into percent string for status
    private String newValToPercent(double val) {
        int p = (int) Math.round(val);
        return p + "%";
    }

    // show a small alert using the JavaFX thread (useful demo for Action events)
    private void showTemporaryAlert(String title, String content) {
        // Use a non-blocking Alert to avoid freezing the UI — practice for students
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(content);
        a.show(); // non-modal by default, good for demos
    }

    public static void main(String[] args) {
        launch(args);
    }
}
