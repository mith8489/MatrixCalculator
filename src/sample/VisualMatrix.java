package sample;

import javafx.scene.control.TextField;
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
            TextField textField = new TextField("0");
            if (!isEditable)
            {
                textField.setEditable(false);
                textField.setMouseTransparent(true);
            }
            add(textField, j, i);

        }
        }
    }
}
