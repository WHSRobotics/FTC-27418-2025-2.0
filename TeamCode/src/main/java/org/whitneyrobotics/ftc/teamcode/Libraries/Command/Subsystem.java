package org.whitneyrobotics.ftc.teamcode.Libraries.Command;

public abstract class Subsystem {
    public Subsystem(){
        String name = this.getClass().getSimpleName();
        name = name.substring(name.lastIndexOf('.')+1);

    }
}
