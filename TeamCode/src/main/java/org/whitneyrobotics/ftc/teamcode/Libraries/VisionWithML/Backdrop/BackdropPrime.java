package org.whitneyrobotics.ftc.teamcode.Libraries.VisionWithML.Backdrop;

import java.util.ArrayList;

public class BackdropPrime {
    public BackdropCellPrime[][] backdrop;

    private int highestPixel; // 2, 5, 8 for height points

    private boolean[][] checkingBackdrop = new boolean[][]{{true, true, true, true, true, true},
            {true, true, true, true, true, true, true},
            {true, true, true, true, true, true},
            {true, true, true, true, true, true, true},
            {true, true, true, true, true, true},
            {true, true, true, true, true, true, true},
            {true, true, true, true, true, true},
            {true, true, true, true, true, true, true},
            {true, true, true, true, true, true},
            {true, true, true, true, true, true, true},
            {true, true, true, true, true, true}};

    public ArrayList<Integer[]> possibleNextMoves;

    public BackdropPrime() {
        possibleNextMoves = new ArrayList<>();

        backdrop = new BackdropCellPrime[][]{{new BackdropCellPrime(0, 0), new BackdropCellPrime(0, 1), new BackdropCellPrime(0, 2), new BackdropCellPrime(0, 3), new BackdropCellPrime(0, 4), new BackdropCellPrime(0, 5)},
                {new BackdropCellPrime(1, 0), new BackdropCellPrime(1, 1), new BackdropCellPrime(1, 2), new BackdropCellPrime(1, 3), new BackdropCellPrime(1, 4), new BackdropCellPrime(1, 5), new BackdropCellPrime(1, 6)},
                {new BackdropCellPrime(2, 0), new BackdropCellPrime(2, 1), new BackdropCellPrime(2, 2), new BackdropCellPrime(2, 3), new BackdropCellPrime(2, 4), new BackdropCellPrime(2, 5)},
                {new BackdropCellPrime(3, 0), new BackdropCellPrime(3, 1), new BackdropCellPrime(3, 2), new BackdropCellPrime(3, 3), new BackdropCellPrime(3, 4), new BackdropCellPrime(3, 5), new BackdropCellPrime(3, 6)},
                {new BackdropCellPrime(4, 0), new BackdropCellPrime(4, 1), new BackdropCellPrime(4, 2), new BackdropCellPrime(4, 3), new BackdropCellPrime(4, 4), new BackdropCellPrime(4, 5)},
                {new BackdropCellPrime(5, 0), new BackdropCellPrime(5, 1), new BackdropCellPrime(5, 2), new BackdropCellPrime(5, 3), new BackdropCellPrime(5, 4), new BackdropCellPrime(5, 5), new BackdropCellPrime(5, 6)},
                {new BackdropCellPrime(6, 0), new BackdropCellPrime(6, 1), new BackdropCellPrime(6, 2), new BackdropCellPrime(6, 3), new BackdropCellPrime(6, 4), new BackdropCellPrime(6, 5)},
                {new BackdropCellPrime(7, 0), new BackdropCellPrime(7, 1), new BackdropCellPrime(7, 2), new BackdropCellPrime(7, 3), new BackdropCellPrime(7, 4), new BackdropCellPrime(7, 5), new BackdropCellPrime(7, 6)},
                {new BackdropCellPrime(8, 0), new BackdropCellPrime(8, 1), new BackdropCellPrime(8, 2), new BackdropCellPrime(8, 3), new BackdropCellPrime(8, 4), new BackdropCellPrime(8, 5)},
                {new BackdropCellPrime(9, 0), new BackdropCellPrime(9, 1), new BackdropCellPrime(9, 2), new BackdropCellPrime(9, 3), new BackdropCellPrime(9, 4), new BackdropCellPrime(9, 5), new BackdropCellPrime(9, 6)},
                {new BackdropCellPrime(10, 0), new BackdropCellPrime(10, 1), new BackdropCellPrime(10, 2), new BackdropCellPrime(10, 3), new BackdropCellPrime(10, 4), new BackdropCellPrime(10, 5)}};
    }

    public BackdropPrime(String numerizedString) {
        possibleNextMoves = new ArrayList<>();

        backdrop = new BackdropCellPrime[][]{{new BackdropCellPrime(0, 0), new BackdropCellPrime(0, 1), new BackdropCellPrime(0, 2), new BackdropCellPrime(0, 3), new BackdropCellPrime(0, 4), new BackdropCellPrime(0, 5)},
                {new BackdropCellPrime(1, 0), new BackdropCellPrime(1, 1), new BackdropCellPrime(1, 2), new BackdropCellPrime(1, 3), new BackdropCellPrime(1, 4), new BackdropCellPrime(1, 5), new BackdropCellPrime(1, 6)},
                {new BackdropCellPrime(2, 0), new BackdropCellPrime(2, 1), new BackdropCellPrime(2, 2), new BackdropCellPrime(2, 3), new BackdropCellPrime(2, 4), new BackdropCellPrime(2, 5)},
                {new BackdropCellPrime(3, 0), new BackdropCellPrime(3, 1), new BackdropCellPrime(3, 2), new BackdropCellPrime(3, 3), new BackdropCellPrime(3, 4), new BackdropCellPrime(3, 5), new BackdropCellPrime(3, 6)},
                {new BackdropCellPrime(4, 0), new BackdropCellPrime(4, 1), new BackdropCellPrime(4, 2), new BackdropCellPrime(4, 3), new BackdropCellPrime(4, 4), new BackdropCellPrime(4, 5)},
                {new BackdropCellPrime(5, 0), new BackdropCellPrime(5, 1), new BackdropCellPrime(5, 2), new BackdropCellPrime(5, 3), new BackdropCellPrime(5, 4), new BackdropCellPrime(5, 5), new BackdropCellPrime(5, 6)},
                {new BackdropCellPrime(6, 0), new BackdropCellPrime(6, 1), new BackdropCellPrime(6, 2), new BackdropCellPrime(6, 3), new BackdropCellPrime(6, 4), new BackdropCellPrime(6, 5)},
                {new BackdropCellPrime(7, 0), new BackdropCellPrime(7, 1), new BackdropCellPrime(7, 2), new BackdropCellPrime(7, 3), new BackdropCellPrime(7, 4), new BackdropCellPrime(7, 5), new BackdropCellPrime(7, 6)},
                {new BackdropCellPrime(8, 0), new BackdropCellPrime(8, 1), new BackdropCellPrime(8, 2), new BackdropCellPrime(8, 3), new BackdropCellPrime(8, 4), new BackdropCellPrime(8, 5)},
                {new BackdropCellPrime(9, 0), new BackdropCellPrime(9, 1), new BackdropCellPrime(9, 2), new BackdropCellPrime(9, 3), new BackdropCellPrime(9, 4), new BackdropCellPrime(9, 5), new BackdropCellPrime(9, 6)},
                {new BackdropCellPrime(10, 0), new BackdropCellPrime(10, 1), new BackdropCellPrime(10, 2), new BackdropCellPrime(10, 3), new BackdropCellPrime(10, 4), new BackdropCellPrime(10, 5)}};

        for (int i = 0; i < backdrop.length; i++) {
            for (int j = 0; j < backdrop[i].length; j++) {
                backdrop[i][j].changeColor((int) (numerizedString.charAt(i + j)));
            }
        }
    }

    public String numerizeBackdrop() {
        String returnString = "";
        for (int i = 0; i < backdrop.length; i++) {
            for (int j = 0; j < backdrop[i].length; i++) {
                returnString += Integer.toString(backdrop[i][j].numerize());
            }
        }

        return returnString;
    }

    public void findHighestPixel() {
        for (int i = 0; i < backdrop.length; i++) {
            for (int j = 0; j < backdrop[i].length; j++) {
                if (backdrop[i][j].numerize() > 0) {
                    highestPixel = i;
                }
            }
        }
    }

    public void reset() {
        for (int i = 0; i < checkingBackdrop.length; i++) {
            for (int j = 0; j < checkingBackdrop[i].length; j++) {
                checkingBackdrop[i][j] = true;
            }
        }
    }

    public void findPossibleMoves() {
        possibleNextMoves.clear();
        findHighestPixel();

        for (int i = highestPixel + 1; i >= 0; i--) {
            for (int j = 0; j < backdrop[i].length; j++) {
                boolean cellAdd = true;
                for (int k = 0; k < backdrop[i][j].cellConditions.length; k++) {
                    if (backdrop[backdrop[i][j].cellConditions[k][0]][backdrop[i][j].cellConditions[k][1]].numerize() == 0) {
                        cellAdd = false;
                        break;
                    }
                }

                if (cellAdd) {
                    possibleNextMoves.add(new Integer[]{i, j, 2});
                    possibleNextMoves.add(new Integer[]{i, j, 3});
                    possibleNextMoves.add(new Integer[]{i, j, 4});
                }
            }
        }
    }

    public int calculateScore() {
        int score = 0;
        int mosaic = 0;

        findHighestPixel();

        score += Math.floor((highestPixel + 1) / 3) * 10;

        for (int i = 0; i <= highestPixel; i++) {
            for (int j = 0; j < backdrop[i].length; j++) {
                if (backdrop[i][j].numerize() > 1) {
                    score += 3;
                }
                if (checkingBackdrop[i][j]) {
                    boolean cancel = false;

                    int yellow = 0;
                    int purple = 0;
                    int green = 0;

                    for (int k = 0; k < backdrop[i][j].touchingCells.length; k++) {
                        if (backdrop[backdrop[i][j].touchingCells[k][0]][backdrop[i][j].touchingCells[k][1]].numerize() > 1) {
                            int whiteBlank = 0;
                            int totalPixelsChecked = 0;
                            for (int m = 0; m < backdrop[backdrop[i][j].touchingCells[k][0]][backdrop[i][j].touchingCells[k][1]].touchingCells.length; m++) {
                                if (backdrop[backdrop[backdrop[i][j].touchingCells[k][0]][backdrop[i][j].touchingCells[k][1]].touchingCells[m][0]][backdrop[backdrop[i][j].touchingCells[k][0]][backdrop[i][j].touchingCells[k][1]].touchingCells[m][1]].numerize() <= 1) {
                                    whiteBlank += 1;
                                }
                                totalPixelsChecked += 1;
                                checkingBackdrop[backdrop[backdrop[i][j].touchingCells[k][0]][backdrop[i][j].touchingCells[k][1]].touchingCells[m][0]][backdrop[backdrop[i][j].touchingCells[k][0]][backdrop[i][j].touchingCells[k][1]].touchingCells[m][1]] = false;
                            }

                            if (whiteBlank != (totalPixelsChecked - 1)) {
                                cancel = true;
                                break;
                            }
                        }

                        if (backdrop[i][j].numerize() == 2) {
                            yellow += 1;
                        } else if (backdrop[i][j].numerize() == 3) {
                            purple += 1;
                        } else if (backdrop[i][j].numerize() == 4) {
                            green += 1;
                        }

                        checkingBackdrop[backdrop[i][j].touchingCells[k][0]][backdrop[i][j].touchingCells[k][1]] = false;
                    }

                    if (!cancel) {
                        if ((yellow == 1 && purple == 1 && green == 1) || (yellow == 3) || (purple == 3) || (green == 3)) {
                            mosaic += 1;
                        }
                    }
                }

                checkingBackdrop[i][j] = false;
            }
        }

        score += mosaic * 10;

        reset();
        return score;
    }

    public void addRandom() {
        findPossibleMoves();

        int randomPick = (int) Math.floor(Math.random() * possibleNextMoves.size());

        backdrop[possibleNextMoves.get(randomPick)[0]][possibleNextMoves.get(randomPick)[1]].changeColor(possibleNextMoves.get(randomPick)[2]);
    }

    public void addMultipleRandom(int howMany) {
        for (int i = 0; i < howMany; i++) {
            addRandom();
        }
    }

    public String displayCurrentBackdrop() {
        String returnString = "";

        for (int i = 10; i >= 0; i--) {
            for (int j = 0; j < backdrop[i].length; j++) {
                if (j == 0 && i % 0 == 0) {
                    returnString += "\n  " + backdrop[i][j].getColorLetter();
                } else if (j == 0) {
                    returnString += "\n" + backdrop[i][j].getColorLetter();
                } else {
                    returnString += " " + backdrop[i][j].getColorLetter();
                }
            }
        }

        return returnString;
    }
}