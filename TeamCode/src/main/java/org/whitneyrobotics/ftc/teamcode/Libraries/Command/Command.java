package org.whitneyrobotics.ftc.teamcode.Libraries.Command;

import static org.whitneyrobotics.ftc.teamcode.Libraries.Utilities.Functions.requireNotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Perry Han
 * @version 1.0
 * Modeled off of WPILib
 */
public abstract class Command {
    protected  Set<Object> dependencies = new HashSet<>();
    /**
     * An enum describing the command behavior when another command with a conflicting requirement is scheduled
     */
    public static enum InterruptBehavior {
        CANCEL_INCOMING,
        CANCEL_SELF
    }

    protected Command() {
        String name = getClass().getName();
    }

    public void initialize() {}
    public void execute() {}
    public void end(boolean interrupted) {}
    public boolean isFinished(){
        return false;
    }

    public Set<Object> getDependencies() {
        return dependencies;
    }

    public void addRequirements(Object... requirements) {
        for (Object requirement : requirements) {
            dependencies.add(requireNotNull(requirement));
        }
    }

}
