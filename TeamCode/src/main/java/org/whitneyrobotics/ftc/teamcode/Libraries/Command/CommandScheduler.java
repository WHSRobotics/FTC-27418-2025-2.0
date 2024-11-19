package org.whitneyrobotics.ftc.teamcode.Libraries.Command;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class CommandScheduler {

    private static CommandScheduler singletonInstance;

    public static synchronized CommandScheduler getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new CommandScheduler();
        }
        return singletonInstance;
    }

    //ALlows unused commands to be Garbage Collected
    private final Set<Command> composedCommands = Collections.newSetFromMap(new java.util.WeakHashMap<>());
    private final Set<Command> scheduledCommands = new LinkedHashSet<>();
    private final Map<Object, Command> dependencies = new LinkedHashMap<>();



}
