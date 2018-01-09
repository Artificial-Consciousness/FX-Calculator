package ca.personalprojects;

import java.util.ArrayList;

import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class BasicView extends View {
    /**
     * State of arithmetic calculation.
     */
    private boolean finished;
    
    /**
     * A MathReader.
     */
    private MathReader math;
    /**
     * An array of strings for the button text.
     */
    private String[] listOfButtons = {"SQRT", "X^2", "X^Y", "PI", "CE", "C", 
            "Del", "/", "7", "8", "9", "*", "4", "5", "6", "-", "1", 
            "2", "3", "+", "(-)", "0", ".", "="};
    
    /**
     * The display.
     */
    private Label display;
    
    
    public BasicView(String name) {
        super(name);
        
        final int three = 3;
        final int four = 4;
        final int buttonWidth = 75;
        final int buttonHeight = 65;
        final int fontSize = 30;
        
        finished = false;
        GridPane pane = new GridPane();
        display = new Label();
        display.setFont(new Font("Arial", fontSize));
        pane.add(display, 0, 1, four, 2);
        int row = three;
        int col = 0;
        
        // Generate all the buttons.
        ArrayList<Button> numberPad = new ArrayList<Button>();
        for (int count = 0; count < listOfButtons.length; count++) {
            if (col > three) {
                col = 0;
                row++;
            }
            numberPad.add(new Button(listOfButtons[count]));
            numberPad.get(count).setPrefWidth(buttonWidth);
            numberPad.get(count).setPrefHeight(buttonHeight);
            numberPad.get(count).setOnAction(this::process);
            pane.add(numberPad.get(count), col, row);
            col++;
        }

        // Meta data.
        pane.setAlignment(Pos.CENTER);

        VBox controls = new VBox(15.0, pane);
        controls.setAlignment(Pos.CENTER);
        
        setCenter(controls);
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> System.out.println("Menu")));
        appBar.setTitleText("FX Calculator");
        appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e -> System.out.println("Search")));
    }
    
    /**
     * Processes the button press events.
     * 
     * @param event button press
     */
    private void process(ActionEvent event) {
        // Clears display after with any button press after a calculation.
        if (finished) {
            display.setText("");
            finished = false;
        }
        
        // The text from the button that was pressed.
        String temp = ((Button) event.getSource()).getText();
        
        switch (temp) {
        case "=":
            math = new MathReader(display.getText());
            display.setText(math.result());
            finished = true;
            break;
        case "CE":
            display.setText("");
            break;
        case "C":
            display.setText("");
            break;
        case "Del":
            if (display.getText().length() != 0) {
                display.setText(display.getText().substring(0,
                        display.getText().length() - 1));
            }
            break;
        case "SQRT":
            math = new MathReader(display.getText());
            display.setText("" + Math.sqrt(Double.parseDouble(math.result())));
            break;
        case "X^2":
            math = new MathReader(display.getText());
            display.setText("" + Math.pow(Double.parseDouble(
                    math.result()), 2));
            break;
        case "X^Y":
            // Unimplemented ^ sign.
            // display.setText(display.getText() + "^");
            break;
        case "(-)":
            // Unimplemented - sign.
            // display.setText(display.getText() + "-");
            break;
        case "PI":
            display.setText("" + display.getText() + Math.PI);
            break;
        default:
            display.setText("" + display.getText() + temp);
        }
    }
}
