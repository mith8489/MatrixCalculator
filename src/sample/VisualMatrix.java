package sample;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class VisualMatrix extends GridPane {

    private int M;
    private int N;

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

    public VisualMatrix(int i, int j)
    {
        getStyleClass().add("visual-matrix");
        M = i;
        N = j;
        makeFields();
    }

    private void makeFields()
    {
        for (int j = 0; j < N; j++)
        { for (int i = 0; i < M; i++)
        {
          add(new TextField("0"), j, i);
        }
        }
    }
}
