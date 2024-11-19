package org.whitneyrobotics.ftc.teamcode.Libraries.Utilities;

public abstract class FunctionGuardian {
    protected boolean shouldRun;
    protected Runnable function;
    public FunctionGuardian(Runnable function){
        this.function = function;
    }
    public void run(){
        if (shouldRun){
            function.run();
        }
    }

    public static FunctionGuardian callOnce(Runnable runnable){
        return new FunctionGuardian(runnable) {
            public void run() {
                if (this.shouldRun){
                    this.function.run();
                    shouldRun = false;
                }
            }

            public void reset(){
                shouldRun = true;
            }
        };
    }

}
