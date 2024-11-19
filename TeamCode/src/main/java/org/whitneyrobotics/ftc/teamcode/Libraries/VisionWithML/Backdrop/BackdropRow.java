package org.whitneyrobotics.ftc.teamcode.Libraries.VisionWithML.Backdrop;

import static org.whitneyrobotics.ftc.teamcode.Libraries.VisionWithML.Backdrop.BackdropRow.COLOR.BLANK;
import static org.whitneyrobotics.ftc.teamcode.Libraries.VisionWithML.Backdrop.BackdropRow.COLOR.FILLED;

public class BackdropRow {
    public Object[] rowComponents;
    public PLACING rowPlacing;
    public int specificPlacing;

    public enum PLACING {
        EVEN(0),
        ODD(1);

        private final int evenOdd;

        PLACING (int evenOdd){
            this.evenOdd = evenOdd;
        }
    }

    public enum COLOR {
        BLANK(false),
        WHITE(true),
        YELLOW(true),
        PURPLE(true),
        FILLED(true);

        private final boolean isFilled;
        COLOR(boolean isFilled){
            this.isFilled = isFilled;
        }
    }

    public BackdropRow (int specificPlacing){
        this.specificPlacing = specificPlacing;
        rowPlacing = specificPlacing % 2 == 0 ? PLACING.EVEN : PLACING.ODD;

        if (rowPlacing == PLACING.EVEN) {
            this.rowComponents = new Object[7];
            //this.rowComponents = [FILLED, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, FILLED];
        }

    }
}

