package org.whitneyrobotics.ftc.teamcode.Libraries.StateForge;

import org.whitneyrobotics.ftc.teamcode.Libraries.Utilities.Action;
import org.whitneyrobotics.ftc.teamcode.Libraries.Utilities.Triple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * StateForge is a modified version of StateMachine for FIRST Tech Challenge
 * Learn more here: <a href="https://github.com/StateFactory-Dev/StateFactory">StateFactory Repository</a>
 */
public class StateMachine<E extends Enum<E>> {
    public List<State<E>> linearStates = new ArrayList<>();
    public List<State<E>> nonLinearStates = new ArrayList<>();

    /**
     * Hashmaps to improve lookup time for states
     */
    public HashMap<E, Integer> linearStateMap = new HashMap<>();
    public HashMap<E, Integer> nonLinearStateMap = new HashMap<>();

    private State<E> currentState;

    private boolean active = false;
    public boolean ranEnterCallback = false;

    public StateMachine(List<State<E>> states){
        for (State s: states){
            if (s.nonLinear){
                nonLinearStates.add(s);
                nonLinearStateMap.put((E)s.getState(), nonLinearStates.indexOf(s));
            } else {
                linearStates.add(s);
                linearStateMap.put((E)s.getState(), linearStates.indexOf(s));
            }
        }
        currentState = linearStates.get(0);
    }

    public void transitionNextLinear(){
        this.currentState.forceTransition();
    }


    //Assumption: No transition  triples are added to the list after this method is called, since the temporary transition will not be properly removed
    public void transitionTo(E state){
        State<E> current = currentState;
        currentState.getTransitions().add(new Triple<>(
                () -> true,
                state,
                () -> {
                    current.getTransitions().remove(current.getTransitions().size() - 1); //Should self destruct the transition after running once
                }
        ));
    }

    public void synchronizeAllSubStateMachines(){
        for(State s : linearStates){
            if (s instanceof SubstateMachine) ((SubstateMachine)s).synchronize(active);
        }
    }

    public void start(){
        if(currentState == null){
            active = false;
        } else active = true;
        if(currentState.getOnEntryAction() != null){
            currentState.getOnEntryAction().call();
            ranEnterCallback = true;
        }
    }

    public boolean isActive(){
        return active;
    }

    public void stop(){
        active = false;
        synchronizeAllSubStateMachines();
    }

    public Enum<E> getMachineState(){
        return currentState.getState();
    }

    public State<E> getState(){
        return currentState;
    }

    public void reset(){
        currentState = linearStates.get(0);
        active = true;
        for(State s : linearStates){
            if (s instanceof SubstateMachine) ((SubstateMachine)s).reset();
        }
        synchronizeAllSubStateMachines();
    }

    public void update(){
        if(!active) return;
        if(currentState instanceof SubstateMachine){
            if(!((SubstateMachine) currentState).isActive()) ((SubstateMachine) currentState).synchronize(active);
            ((SubstateMachine) currentState).update();
        }
        Action exitAction = null;
        State<E> nextState = null;
        boolean willTransition = false;
        for (Triple<TransitionCondition, E, Action> transitionInfo : currentState.getTransitions()){
            if(transitionInfo.a instanceof TimedTransition && !((TimedTransition) transitionInfo.a).timerStarted()){
                ((TimedTransition) transitionInfo.a).startTimer();
            }
            int index = linearStateMap.get(currentState.getState());
            if(transitionInfo.a.shouldTransition()){
                exitAction = transitionInfo.c;

                if(transitionInfo.b != null){
                    try {
                        nextState = linearStates.get(linearStateMap.get(transitionInfo.b));
                    } catch (NullPointerException e){ //if no linear state is specified, it must be a nonlinear transition
                        nextState = nonLinearStates.get(nonLinearStateMap.get(transitionInfo.b));
                    }
                } else {
                    nextState = linearStates.get((index+1) % linearStates.size());
                }
                willTransition = true;
                break;
            }
        }

        if(!ranEnterCallback && currentState.getOnEntryAction() != null){
            currentState.getOnEntryAction().call();
            ranEnterCallback = true;
        }

        if(willTransition && nextState != null){
            if(exitAction != null){ //an exit action paired with a transition OVERRIDES the default exit action of the current state
                exitAction.call();
            } else if (currentState.getOnExitAction() != null) {
                currentState.getOnExitAction().call();
            }
            State<E> pastState = currentState;
            currentState = nextState;
            ranEnterCallback = false;
            //reset all timers of the past state
            for (Triple<TransitionCondition, E, Action> transitionInfo : pastState.getTransitions()) {
                if (transitionInfo.a instanceof TimedTransition && ((TimedTransition) transitionInfo.a).timerStarted()) {
                    ((TimedTransition) transitionInfo.a).reset();
                }
            }
        } else {
            if(currentState.getPeriodic() != null){
                currentState.getPeriodic().call();
            }
        }
    }
}
