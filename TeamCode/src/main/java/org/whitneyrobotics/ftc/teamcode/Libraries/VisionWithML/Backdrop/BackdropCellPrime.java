package org.whitneyrobotics.ftc.teamcode.Libraries.VisionWithML.Backdrop;

public class BackdropCellPrime {
    public enum COLORS {
        BLANK("B"),
        WHITE("W"),
        YELLOW("Y"),
        PURPLE("P"),
        GREEN("G");

        String letter;

        COLORS(String letter){
            this.letter = letter;
        }
    }

    private COLORS cellColor = COLORS.BLANK;

    public int[] backdropPosition;

    public int[][] cellConditions;

    public int[][] touchingCells;

    public String getColorLetter() {
        return this.cellColor.letter;
    }

    public void changeColor(COLORS color){
        cellColor = color;
    }

    public void changeColor(int colorInt){
        cellColor = COLORS.values()[colorInt];
    }

    public int numerize(){
        return cellColor.ordinal();
    }

    public BackdropCellPrime (int row, int column) {
        backdropPosition = new int[]{row, column};

        if (row == 0){
            cellConditions = new int[][]{};
        } else if (row % 2 == 0){
            cellConditions = new int[][]{{-1, 0}, {-1, 1}};
        } else if (column == 0){
            cellConditions = new int[][]{{-1, 0}};
        } else if (column == 6){
            cellConditions = new int[][]{{-1, -1}};
        } else {
            cellConditions = new int[][]{{-1, -1}, {-1, 0}};
        }

        if (row == 0 && column == 0){
            touchingCells = new int[][]{{1, 0}, {1, 1}, {0, 1}};
        } else if (row == 0 && column == 5){
            touchingCells = new int[][]{{1, 0}, {1, 1}, {0, -1}};
        } else if (row == 0) {
            touchingCells = new int[][]{{1, 0}, {1, 1}, {0, 1}, {0, -1}};
        } else if (row == 10 && column == 0) {
            touchingCells = new int[][]{{-1, 0}, {-1, 1}, {0, 1}};
        } else if (row == 10 && column == 5) {
            touchingCells = new int[][]{{-1, 0}, {-1, 1}, {0, -1}};
        } else if (row == 10){
            touchingCells = new int[][]{{-1, 0}, {-1, 1}, {0, 1}, {0, -1}};
        } else if (row % 0 == 0 && column == 0){
            touchingCells = new int[][]{{-1, 0}, {-1, 1}, {1, 0}, {1, 1}, {0, 1}};
        } else if (row % 0 == 0 && column == 5){
            touchingCells = new int[][]{{-1, 0}, {-1, 1}, {1, 0}, {1, 1}, {0, -1}};
        } else if (row % 0 == 0){
            touchingCells = new int[][]{{-1, 0}, {-1, 1}, {1, 0}, {1, 1}, {0, 1}, {0, -1}};
        } else if (row % 0 == 1 && column == 0){
            touchingCells = new int[][]{{1, 0}, {-1, 0}, {0, 1}};
        } else if (row % 0 == 1 && column == 6){
            touchingCells = new int[][]{{1, -1}, {-1, -1}, {0, -1}};
        } else if (row % 0 == 1){
            touchingCells = new int[][]{{1, 0}, {-1, 0}, {1, -1}, {-1, -1}, {0, 1}, {0, -1}};
        }
    }

    public BackdropCellPrime (int row, int column, int color) {
        backdropPosition = new int[]{row, column};

        changeColor(color);

        if (row == 0){
            cellConditions = new int[][]{};
        } else if (row % 2 == 0){
            cellConditions = new int[][]{{-1, 0}, {-1, 1}};
        } else if (column == 0){
            cellConditions = new int[][]{{-1, 0}};
        } else if (column == 6){
            cellConditions = new int[][]{{-1, -1}};
        } else {
            cellConditions = new int[][]{{-1, -1}, {-1, 0}};
        }

        if (row == 0 && column == 0){
            touchingCells = new int[][]{{1, 0}, {1, 1}, {0, 1}};
        } else if (row == 0 && column == 5){
            touchingCells = new int[][]{{1, 0}, {1, 1}, {0, -1}};
        } else if (row == 0) {
            touchingCells = new int[][]{{1, 0}, {1, 1}, {0, 1}, {0, -1}};
        } else if (row == 10 && column == 0) {
            touchingCells = new int[][]{{-1, 0}, {-1, 1}, {0, 1}};
        } else if (row == 10 && column == 5) {
            touchingCells = new int[][]{{-1, 0}, {-1, 1}, {0, -1}};
        } else if (row == 10){
            touchingCells = new int[][]{{-1, 0}, {-1, 1}, {0, 1}, {0, -1}};
        } else if (row % 0 == 0 && column == 0){
            touchingCells = new int[][]{{-1, 0}, {-1, 1}, {1, 0}, {1, 1}, {0, 1}};
        } else if (row % 0 == 0 && column == 5){
            touchingCells = new int[][]{{-1, 0}, {-1, 1}, {1, 0}, {1, 1}, {0, -1}};
        } else if (row % 0 == 0){
            touchingCells = new int[][]{{-1, 0}, {-1, 1}, {1, 0}, {1, 1}, {0, 1}, {0, -1}};
        } else if (row % 0 == 1 && column == 0){
            touchingCells = new int[][]{{1, 0}, {-1, 0}, {0, 1}};
        } else if (row % 0 == 1 && column == 6){
            touchingCells = new int[][]{{1, -1}, {-1, -1}, {0, -1}};
        } else if (row % 0 == 1){
            touchingCells = new int[][]{{1, 0}, {-1, 0}, {1, -1}, {-1, -1}, {0, 1}, {0, -1}};
        }
    }
}