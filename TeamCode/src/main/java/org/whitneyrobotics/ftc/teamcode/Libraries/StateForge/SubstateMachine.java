package org.whitneyrobotics.ftc.teamcode.Libraries.StateForge;

import org.whitneyrobotics.ftc.teamcode.Libraries.Utilities.Action;

import java.util.List;

/**
 * A special kind of state that embeds its own state machine
 * @param <I> Inherited set of enums
 * @param <R> Embedded state machine's set of enums
 */
public class SubstateMachine<I extends Enum<I>, R extends Enum<R>> extends State {
    public static enum TRANSITION_BEHAVIOR {
        RESET_INTERNAL_STATE,
        PERSIST_INTERNAL_STATE
    }
    private StateMachine<R> embeddedStateMachine;

    private TRANSITION_BEHAVIOR transitionBehavior = TRANSITION_BEHAVIOR.PERSIST_INTERNAL_STATE;

    public SubstateMachine(List<State<R>> embeddedStateList, I state, List transitions) {
        super(state, transitions);
        embeddedStateMachine = new StateMachine<>(embeddedStateList);
        setOnEntryAction(this::checkTransitionBehavior);
    }

    public SubstateMachine(List<State<R>> embeddedStateList, I state, Action onEntryAction, Action onExitAction, List transitions) {
        super(state, onEntryAction, onExitAction, transitions);
        embeddedStateMachine = new StateMachine<>(embeddedStateList);
        setOnEntryAction(() -> {
            checkTransitionBehavior();
            if(this.getOnEntryAction() != null) onEntryAction.call();
        });
    }

    public SubstateMachine(List<State<R>> embeddedStateList, I state, Action onEntryAction, Action onExitAction, List transitions, boolean nonLinear) {
        super(state, onEntryAction, onExitAction, transitions, nonLinear);
        embeddedStateMachine = new StateMachine<>(embeddedStateList);
        setOnEntryAction(() -> {
            checkTransitionBehavior();
            if(onEntryAction != null) onEntryAction.call();
        });
    }

    public SubstateMachine(StateMachine<R> embeddedStateMachine, I state, Action onEntryAction, Action onExitAction, List transitions, boolean nonLinear){
        super(state, onEntryAction, onExitAction, transitions, nonLinear);
        this.embeddedStateMachine = embeddedStateMachine;
        setOnEntryAction(() -> {
            checkTransitionBehavior();
            if(onEntryAction != null) onEntryAction.call();
        });
    }

    public void setTransitionBehavior(TRANSITION_BEHAVIOR transitionBehavior){
        this.transitionBehavior = transitionBehavior;
    }

    SubstateMachine<I,R> _setTransitionBehavior(TRANSITION_BEHAVIOR transitionBehavior){
        this.transitionBehavior = transitionBehavior;
        return this;
    }

    public void synchronize(boolean active){
        if (active){
            embeddedStateMachine.start();
        } else {
            embeddedStateMachine.reset();
        }
        embeddedStateMachine.synchronizeAllSubStateMachines();
    }

    public void checkTransitionBehavior() {
        if(transitionBehavior == TRANSITION_BEHAVIOR.RESET_INTERNAL_STATE) {
            embeddedStateMachine.reset();
        }
    }

    public void reset() {
        embeddedStateMachine.reset();
    }

    @Override
    public Enum<R> getSubstate(){
        return embeddedStateMachine.getMachineState();
    }

    @Override
    public void setOnEntryAction(Action onEntryAction) {
        super.setOnEntryAction(() -> {
            checkTransitionBehavior();
            if(onEntryAction != null) onEntryAction.call();
        });
    }

    public void update(){
        embeddedStateMachine.update();
    }
    public boolean isActive(){
        return embeddedStateMachine.isActive();
    }

    public StateMachine<R> getEmbeddedStateMachine(){
        return embeddedStateMachine;
    }
}
