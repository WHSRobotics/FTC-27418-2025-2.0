package org.whitneyrobotics.ftc.teamcode.Libraries.VisionWithML.Backdrop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;

@TeleOp (name = "2.12.2024 Backdrop ML Testing Prime")
public class BackdropPrimeTesting extends OpModeEx {
    BackdropPrime testingBackdrop = new BackdropPrime();
    int[] highestComboFirst = new int[]{};
    int[] highestComboSecond = new int[]{};

    public void findNextBestMove() {
        testingBackdrop.findPossibleMoves();
        int highestScore = 0;

        for (int i = 0; i < testingBackdrop.possibleNextMoves.size(); i++){
            BackdropPrime testBackdrop = new BackdropPrime(testingBackdrop.numerizeBackdrop());

            Integer[] currentCheck = testingBackdrop.possibleNextMoves.get(i);

            testBackdrop.backdrop[currentCheck[0].intValue()][currentCheck[1].intValue()].changeColor(currentCheck[2].intValue());

            if (testBackdrop.calculateScore() > highestScore){
                this.highestComboFirst = new int[]{currentCheck[0].intValue(), currentCheck[1].intValue(), currentCheck[2].intValue()};
            }
        }
    }

    public void findNextTwoBestMoves() {
        findNextBestMove();
        int highestScore = 0;

        BackdropPrime testBackdrop = new BackdropPrime(testingBackdrop.numerizeBackdrop());
        testBackdrop.backdrop[highestComboFirst[0]][highestComboFirst[1]].changeColor(highestComboFirst[2]);
        testBackdrop.findPossibleMoves();

        for (int i = 0; i < testBackdrop.possibleNextMoves.size(); i++){
            BackdropPrime testBackdropTwo = new BackdropPrime(testingBackdrop.numerizeBackdrop());

            Integer[] currentCheck = testingBackdrop.possibleNextMoves.get(i);

            testBackdropTwo.backdrop[currentCheck[0].intValue()][currentCheck[1].intValue()].changeColor(currentCheck[2].intValue());

            if (testBackdropTwo.calculateScore() > highestScore){
                this.highestComboFirst = new int[]{currentCheck[0].intValue(), currentCheck[1].intValue(), currentCheck[2].intValue()};
            }
        }
    }

    @Override
    public void initInternal() {
        gamepad1.SQUARE.onPress(() -> testingBackdrop.addMultipleRandom(5));
        gamepad1.CROSS.onPress(() -> {telemetryPro.clear();
        telemetryPro.addData("Backdrop Displayed", "\n" + testingBackdrop.displayCurrentBackdrop());});
        gamepad1.CIRCLE.onPress(() -> {findNextBestMove(); telemetryPro.clear(); telemetryPro.addData("Next Best Pixel Color", BackdropCellPrime.COLORS.values()[highestComboFirst[2]]);});
        gamepad1.TRIANGLE.onPress(() -> {findNextTwoBestMoves(); telemetryPro.clear(); telemetryPro.addData("Next Two Best Pixel Colors - Bottom, Top", BackdropCellPrime.COLORS.values()[highestComboFirst[2]].letter + " | " + BackdropCellPrime.COLORS.values()[highestComboSecond[2]].letter);});
    }

    @Override
    protected void loopInternal() {

    }
}