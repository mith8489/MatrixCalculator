package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class VisualMatrix extends GridPane {

    private int M;
    private int N;

    private boolean isEditable;

    public void setM(int m) {
        M = m;
    }

    public void setN(int n) {
        N = n;
    }

    public int getM() {
        return M;
    }

    public int getN() {
        return N;
    }

    public VisualMatrix(int i, int j, boolean isEditable)
    {
        getStyleClass().add("visual-matrix");
        M = i;
        N = j;
        this.isEditable = isEditable;
        makeFields();
    }

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
            textField.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    textField.setText("");
                }
            });
            add(textField, j, i);

        }
        }
    }

    public void setLargeFields()
    {
        for (Node field : getChildren())
        {
            field.setStyle("-fx-min-width: 100px");
        }
    }
}
