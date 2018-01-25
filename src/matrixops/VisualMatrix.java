package matrixops;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * VisualMatrix.java: Graphics class representing a matrix with TextFields. Can be made uneditable.
 * @author Max Thurell
 * @version 1.0
 */
public class VisualMatrix extends GridPane {

    private final int M;
    private final int N;

    private boolean isEditable;

    public int getM() {
        return M;
    }

    public int getN() {
        return N;
    }

    /**
     * Creates a new VisualMatrix of the given dimensions and editability.
     *
     * @param i Row size of the new VisualMatrix.
     * @param j Column size of the new VisualMatrix.
     * @param isEditable Indicates whether the VisualMatrix is editable.
     */
    public VisualMatrix(int i, int j, boolean isEditable)
    {
        getStyleClass().add("visual-matrix");
        M = i;
        N = j;
        this.isEditable = isEditable;
        makeFields();
    }

    /**
     * Creates all the TextFields in the VisualMatrix. Makes the TextFields clear their text when clicked.
     */
    private void makeFields()
    {
        for (int j = 0; j < N; j++)
        { for (int i = 0; i < M; i++)
        {
            TextField textField = new TextField("");
            if (!isEditable)
            {
                textField.setEditable(false);
                textField.setMouseTransparent(true);
                textField.setFocusTraversable(false);
            }
            textField.setOnMouseClicked(event -> textField.setText(""));
            add(textField, j, i);

        }
        }
    }

    /**
     * Makes the TextFields larger, to accommodate large fractions.
     */
    public void setLargeFields()
    {
        for (Node field : getChildren())
        {
            field.setStyle("-fx-min-width: 90px");
        }
    }
}
