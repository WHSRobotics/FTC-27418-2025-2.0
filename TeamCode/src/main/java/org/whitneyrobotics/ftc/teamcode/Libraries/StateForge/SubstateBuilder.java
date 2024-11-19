package org.whitneyrobotics.ftc.teamcode.Libraries.StateForge;

@FunctionalInterface
public interface SubstateBuilder<R extends Enum<R>> {
    StateForge.StateMachineBuilder<R> useCommands(StateForge.StateMachineBuilder<R> builder);
}
