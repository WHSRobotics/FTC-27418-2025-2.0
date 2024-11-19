package org.whitneyrobotics.ftc.teamcode.Extensions.TelemetryPro.AutoSetupTesting;

import org.whitneyrobotics.ftc.teamcode.Extensions.TelemetryPro.TelemetryPro;
import org.whitneyrobotics.ftc.teamcode.Extensions.TelemetryPro.Folder;
import org.whitneyrobotics.ftc.teamcode.Extensions.TelemetryPro.LineItem;
import org.whitneyrobotics.ftc.teamcode.Extensions.TelemetryPro.TextLine;

import java.util.LinkedList;
import java.util.List;

public class TestManager {

    TelemetryPro telemetry;

    public static class Test {
        String label;
        Runnable func;

        boolean warning = false;
        boolean failed = false;
        String reason = "";

        public boolean getWarning(){
            return warning;
        }

        public boolean getFailed(){
            return failed;
        }

        public Test(String label, Runnable func){
            this.label = label;
            this.func = func;
        }

        public void evaluate() {
            try {
                func.run();
                failed = false;
                warning = false;
                reason = "✓ Test passed";
            } catch(AssertionError e) {
                if(e instanceof Tests.Warning){
                    failed = false;
                    warning = true;
                } else {
                    warning = false;
                    failed = true;
                }
                reason = e.getMessage();
            }
        }
    }

    LinkedList<Test> tests = new LinkedList<>();
    Folder folder;
    public TestManager(TelemetryPro bt){
        folder = new Folder("Test Manager");
        this.telemetry = bt;
        telemetry.addItem(folder);
    }

    public TestManager addTest(String label, Runnable func){
        tests.add(0,new Test(label, func));
        return this;
    }

    /**
     * Call in a looping context.
     */
    public List<Test> run() {
        folder.purge();
        for (Test t: tests) {
            t.evaluate();
            folder.addChild(new TextLine(
                    t.label + " " + (t.failed ? " FAILED" : t.warning ? " Passed with warning" : "Passed") + "\n" + t.reason,
                    true,
                    t.failed ? LineItem.Color.RED : t.warning ? LineItem.Color.ROBOTICS : LineItem.Color.LIME,
                    LineItem.RichTextFormat.BOLD)
            );
        }
        return tests;
    }
}
