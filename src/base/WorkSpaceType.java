package base;

import matrixops.workspaces.*;

/**
 * WorkSpaceType.java: Enum containing all types of matrix operation workspaces.
 * Contains functionality for setting a new workspace in the GUI.
 */
public enum WorkSpaceType {

    ADDITION,
    SUBTRACTION,
    SCALARMULTIPLICATION,
    MATRIXMULTIPLICATION,
    GAUSSJORDAN,
    TRANSPOSITION,
    INVERSION,
    DETERMINANT,
    RANK;

    /**
     * Selects a type of MatrixWorkSpace to set in the GUI.
     *
     * @param calcGUI The CalcGUI in which to set the new workspace.
     * @return Subclass of MatrixWorkSpace corresponding to the type of this object.
     */
    public MatrixWorkSpace setWorkSpace(CalcGUI calcGUI)
    {
        MatrixWorkSpace workSpace;
        switch (this)
        {
            case ADDITION:
                workSpace = new AdditionWorkSpace(calcGUI);
                break;

            case SUBTRACTION:
                workSpace = new SubtractionWorkSpace(calcGUI);
                break;

            case SCALARMULTIPLICATION:
                workSpace = new ScalarMultWorkSpace(calcGUI);
                break;

            case MATRIXMULTIPLICATION:
                workSpace = new MatrixMultWorkSpace(calcGUI);
                break;

            case GAUSSJORDAN:
                workSpace = new GaussJordanWorkSpace(calcGUI);
                break;

            case TRANSPOSITION:
                workSpace = new TransposeWorkSpace(calcGUI);
                break;

            case INVERSION:
                workSpace = new InversionWorkSpace(calcGUI);
                break;

            case DETERMINANT:
                workSpace = new DeterminantWorkSpace(calcGUI);
                break;

            case RANK:
                workSpace = new RankWorkSpace(calcGUI);
                break;

                default:
                    throw new IllegalArgumentException("Workspace type not found!");
        }

        return workSpace;
    }
}
