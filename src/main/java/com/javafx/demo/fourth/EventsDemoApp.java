package com.javafx.demo.fourth;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * EventsDemoApp
 *
 * Demonstrates:
 *  - Event handlers (setOnMouseClicked, setOnAction, setOnKeyPressed, etc.)
 *  - Event filters (addEventFilter)
 *  - addEventHandler with specific EventType
 *  - Mouse actions: clicked, pressed, released, entered, exited, moved, dragged, scroll
 *  - Consuming an event to stop further propagation
 *
 * Meant for freshers: lots of inline comments and a live event log.
 */
public class EventsDemoApp extends Application {

    // UI component to display event history for students to inspect
    private final ListView<String> eventLog = new ListView<>();

    // Helper to append message to event log (keeps newest at top)
    private void log(String message) {
        // show most recent at top
        eventLog.getItems().add(0, message);
        // limit log size so it doesn't grow forever
        if (eventLog.getItems().size() > 200) {
            eventLog.getItems().remove(200, eventLog.getItems().size());
        }
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Event Handling — Demo for Freshers");

        // ---------- Top: quick instructions ----------
        Label header = new Label("JavaFX Events Demo — click/hover/drag/scroll inside the area below");
        header.setStyle("-fx-font-size:16px; -fx-font-weight:bold;");

        // ---------- Center: an interactive pane where most mouse events will be captured ----------
        StackPane interactiveArea = new StackPane();
        interactiveArea.setMinSize(700, 360);
        interactiveArea.setStyle("-fx-background-color: linear-gradient(to bottom, #f6f9ff, #e6eefc); -fx-border-color: #2c3e50; -fx-border-width: 2px;");

        // Add a rectangle that students can drag around. This demonstrates drag detection + mouse dragged.
        Rectangle draggable = new Rectangle(120, 80, Color.CORNFLOWERBLUE);
        draggable.setArcWidth(12);
        draggable.setArcHeight(12);
        draggable.setStroke(Color.DARKSLATEBLUE);
        draggable.setStrokeWidth(2);

        // Initial tooltip to help students
        Tooltip.install(draggable, new Tooltip("Drag me: press, then move the mouse"));

        // place rectangle initially in center
        interactiveArea.getChildren().add(draggable);

        // A label inside interactive area to show coordinates while moving mouse
        Label coordsLabel = new Label("Mouse coords: - , -");
        StackPane.setAlignment(coordsLabel, Pos.TOP_LEFT);
        coordsLabel.setPadding(new Insets(8));
        interactiveArea.getChildren().add(coordsLabel);

        // ---------- Right: controls showing event handler examples ----------
        VBox controlsBox = new VBox(12);
        controlsBox.setPadding(new Insets(8));
        controlsBox.setPrefWidth(320);

        Label controlsTitle = new Label("Controls & Handlers (examples)");
        controlsTitle.setStyle("-fx-font-weight:bold;");

        // Button demonstrating setOnAction (ActionEvent)
        Button btnAction = new Button("Button: setOnAction (ActionEvent)");
        btnAction.setMaxWidth(Double.MAX_VALUE);
        btnAction.setOnAction(evt -> {
            // ActionEvent is typical for Buttons and MenuItems
            log("Button.setOnAction fired: ActionEvent (source=" + evt.getSource().getClass().getSimpleName() + ")");
        });

        // Same button also has mouse clicked handler to show difference between ActionEvent and MouseEvent
        btnAction.setOnMouseClicked(me -> {
            log("Button.setOnMouseClicked fired: MouseEvent (clickCount=" + me.getClickCount() + ", button=" + me.getButton() + ")");
        });

        // Checkbox demonstrating toggle events and using addEventHandler
        CheckBox cbConsumeClick = new CheckBox("Consume mouse clicks on rectangle");
        cbConsumeClick.setTooltip(new Tooltip("When selected, the rectangle's click handler will call consume() so parent handlers won't run"));

        // TextField to demonstrate key events (must be focused to catch keys)
        TextField txtInput = new TextField();
        txtInput.setPromptText("Type here to see key events (focus me)");
        txtInput.setMaxWidth(Double.MAX_VALUE);

        // Label to show last key pressed
        Label keyLabel = new Label("Last key: -");

        // Add key pressed handler on text field using setOnKeyPressed
        txtInput.setOnKeyPressed(keyEvent -> {
            keyLabel.setText("Last key: " + keyEvent.getCode());
            log("TextField.setOnKeyPressed: " + keyEvent.getCode());
            // example: consume the ENTER key so parent handlers don't see it
            if (keyEvent.getCode() == KeyCode.ENTER) {
                log("  -> ENTER consumed by TextField");
                keyEvent.consume();
            }
        });

        // RadioButtons to show use of addEventFilter (filters run during capture phase)
        ToggleGroup tg = new ToggleGroup();
        RadioButton rbFilter = new RadioButton("Enable scene-level MOUSE_CLICK filter");
        rbFilter.setToggleGroup(tg);

        // Button to clear the log
        Button btnClear = new Button("Clear Event Log");
        btnClear.setMaxWidth(Double.MAX_VALUE);
        btnClear.setOnAction(e -> eventLog.getItems().clear());

        controlsBox.getChildren().addAll(controlsTitle, btnAction, cbConsumeClick, new Label("Key events demo:"), txtInput, keyLabel, rbFilter, btnClear);

        // ---------- Bottom: live event log ----------
        VBox logBox = new VBox(6);
        logBox.setPadding(new Insets(6));
        Label logLabel = new Label("Event Log (most recent at top)");
        logLabel.setStyle("-fx-font-weight:bold;");
        eventLog.setPrefHeight(220);
        logBox.getChildren().addAll(logLabel, eventLog);

        // ---------- Assemble main layout ----------
        BorderPane root = new BorderPane();
        root.setTop(header);
        BorderPane.setAlignment(header, Pos.CENTER);
        BorderPane.setMargin(header, new Insets(10));
        root.setCenter(interactiveArea);
        root.setRight(controlsBox);
        root.setBottom(logBox);

        Scene scene = new Scene(root, 1100, 720);

        // -------------------- Scene-level filters/handlers --------------------
        // Example: addEventFilter on the scene for MOUSE_CLICKED (capture phase)
        // This runs before nodes' own handlers (capture phase). Good to show order of events.
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, ev -> {
            // Only log when the radio button is selected (so students can toggle)
            if (rbFilter.isSelected()) {
                log("[Scene FILTER] MOUSE_CLICKED at (" + (int) ev.getSceneX() + "," + (int) ev.getSceneY() + ") target=" + ev.getTarget().getClass().getSimpleName());
            }
            // Note: we intentionally don't consume here so event continues
        });

        // Example: addEventHandler on scene for MOUSE_CLICKED (bubbling phase)
        // This demonstrates that handlers added with addEventHandler run during bubbling.
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, ev -> {
            // This runs after filters and after node handlers (unless consumed)
            log("[Scene HANDLER] MOUSE_CLICKED at (" + (int) ev.getSceneX() + "," + (int) ev.getSceneY() + ") target=" + ev.getTarget().getClass().getSimpleName());
        });

        // Example: addEventFilter for KEY_PRESSED to show key events anywhere on scene (if not consumed)
        scene.addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
            log("[Scene FILTER] KEY_PRESSED: " + ke.getCode());
        });

        // -------------------- Interactive area event handlers --------------------

        // Top-level handlers for the interactive area (StackPane)
        interactiveArea.setOnMouseMoved(me -> {
            coordsLabel.setText(String.format("Mouse coords: %.0f , %.0f", me.getX(), me.getY()));
            // tiny log to show move events (commented out to avoid spam; students can uncomment)
            // log("InteractiveArea: MOUSE_MOVED at " + me.getX() + "," + me.getY());
        });

        interactiveArea.setOnScroll(scrollEvent -> {
            log("InteractiveArea: SCROLL deltaY=" + scrollEvent.getDeltaY() + " at " + (int) scrollEvent.getX() + "," + (int) scrollEvent.getY());
        });

        // Add an event filter on the interactive area for mouse pressed to demonstrate capture vs bubbling
        interactiveArea.addEventFilter(MouseEvent.MOUSE_PRESSED, ev -> {
            log("[InteractiveArea FILTER] MOUSE_PRESSED target=" + ev.getTarget().getClass().getSimpleName());
            // don't consume — we want children handlers too
        });

        // Add an event handler on the area to catch clicks that bubble up from children
        interactiveArea.addEventHandler(MouseEvent.MOUSE_CLICKED, ev -> {
            log("[InteractiveArea HANDLER] MOUSE_CLICKED target=" + ev.getTarget().getClass().getSimpleName() + " (button=" + ev.getButton() + ")");
        });

        // ---------- Draggable rectangle: show mouse pressed, drag detection, mouse dragged, released ----------
        // We'll implement two ways to drag:
        // 1) A "manual" drag using press -> drag -> release that updates the shape's translateX/Y
        // 2) Show setOnDragDetected as an example of starting a full drag-and-drop gesture (we keep it simple)

        final Delta dragDelta = new Delta();

        // Mouse pressed on rectangle
        draggable.setOnMousePressed(me -> {
            // record distance between mouse and rectangle origin to allow smooth dragging
            dragDelta.x = me.getX();
            dragDelta.y = me.getY();
            // change cursor and visual feedback
            draggable.setCursor(Cursor.MOVE);
            draggable.setFill(Color.DODGERBLUE);
            log("Rectangle: MOUSE_PRESSED (localX=" + (int) me.getX() + ", localY=" + (int) me.getY() + ")");
            // If checkbox checked, demonstrate consuming the event so parent handlers won't see it
            if (cbConsumeClick.isSelected()) {
                log("Rectangle: consuming this MouseEvent -> parent handlers won't see this click");
                me.consume();
            }
        });

        // Mouse released on rectangle
        draggable.setOnMouseReleased(me -> {
            draggable.setCursor(Cursor.HAND);
            draggable.setFill(Color.CORNFLOWERBLUE);
            log("Rectangle: MOUSE_RELEASED (button=" + me.getButton() + ")");
        });

        // setOnMouseClicked: demonstrates clicked events and click count
        draggable.setOnMouseClicked(me -> {
            log("Rectangle: MOUSE_CLICKED (clickCount=" + me.getClickCount() + ")");
            // double click example
            if (me.getClickCount() == 2) {
                log("  -> Double click detected on rectangle");
            }
        });

        // setOnMouseDragged: run when mouse moves while a button is pressed (after press)
        draggable.setOnMouseDragged(me -> {
            // calculate new center based on mouse position in parent (StackPane)
            Point2D parentPoint = interactiveArea.sceneToLocal(me.getSceneX(), me.getSceneY());
            double newX = parentPoint.getX() - dragDelta.x;
            double newY = parentPoint.getY() - dragDelta.y;

            // reposition the rectangle by setting layoutX/layoutY or translate properties
            // Here we choose to update layoutX/layoutY relative to parent
            draggable.setTranslateX(newX - (interactiveArea.getWidth() - draggable.getWidth())/2);
            draggable.setTranslateY(newY - (interactiveArea.getHeight() - draggable.getHeight())/2);

            log(String.format("Rectangle: MOUSE_DRAGGED to scene(%.0f,%.0f)", me.getSceneX(), me.getSceneY()));
        });

        // setOnDragDetected: demonstrates starting a drag gesture (often used for drag-and-drop)
        draggable.setOnDragDetected(me -> {
            log("Rectangle: DRAG_DETECTED -> startFullDrag() (example)");
            // start full-drag mode (enables setOnMouseDragEntered/Exited on other nodes)
            draggable.startFullDrag();
            me.consume(); // consume to mark we've started drag gesture
        });

        // Example of nodes reacting to full drag events (mouse drag entered/exited)
        interactiveArea.setOnMouseDragEntered(me -> {
            // This fires when another node had startFullDrag and the mouse enters this node
            log("[InteractiveArea] MOUSE_DRAG_ENTERED (full drag)");
        });
        interactiveArea.setOnMouseDragReleased(me -> {
            log("[InteractiveArea] MOUSE_DRAG_RELEASED (full drag)");
        });

        // change cursor when hovering over rectangle
        draggable.setOnMouseEntered(me -> {
            draggable.setCursor(Cursor.HAND);
            log("Rectangle: MOUSE_ENTERED");
        });

        draggable.setOnMouseExited(me -> {
            draggable.setCursor(Cursor.DEFAULT);
            log("Rectangle: MOUSE_EXITED");
        });

        // ---------- Example of addEventHandler with specific event type (MouseEvent.MOUSE_CLICKED) ----------
        // This shows another way besides setOnMouseClicked
        draggable.addEventHandler(MouseEvent.MOUSE_CLICKED, me -> {
            log("[draggable.addEventHandler] MOUSE_CLICKED (button=" + me.getButton() + ")");
        });

        // ---------- Example of consuming an event at a filter level ----------
        // We'll add a temporary filter on right-click to consume it so other handlers don't see it.
        interactiveArea.addEventFilter(MouseEvent.MOUSE_PRESSED, ev -> {
            if (ev.getButton() == MouseButton.SECONDARY) { // right-click
                log("InteractiveArea FILTER: Right-click detected and consumed");
                ev.consume(); // prevents further handlers from running on this event
            }
        });

        // ---------- Keyboard demo: pressing keys at scene level vs text field ----------
        // Scene key pressed example using addEventHandler (runs in bubbling phase)
        scene.addEventHandler(KeyEvent.KEY_PRESSED, ke -> {
            log("[Scene HANDLER] KEY_PRESSED: " + ke.getCode());
        });

        // ---------- Finalize and show ----------
        primaryStage.setScene(scene);
        primaryStage.show();

        // helpful startup logs
        log("Application started. Click and interact with the rectangle and area.");
        log("Try: left click, double click, drag, right-click (will be consumed by filter), scroll, type in the TextField.");
    }

    // small helper class to store drag offsets
    private static class Delta {
        double x, y;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

