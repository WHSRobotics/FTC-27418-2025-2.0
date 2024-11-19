package org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.function.Consumer;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Button implements GamepadHardware{

    private Consumer<GamepadInteractionEvent> interactionEventConsumer = defaultConsumer;
    private Consumer<GamepadInteractionEvent> pressEventConsumer = defaultConsumer;
    private Consumer<GamepadInteractionEvent> releaseEventConsumer = defaultConsumer;
    private Consumer<GamepadInteractionEvent> doublePressEventConsumer = defaultConsumer;
    private Consumer<GamepadInteractionEvent> holdEventConsumer = defaultConsumer;
    private Consumer<GamepadInteractionEvent> shortPressConsumer = defaultConsumer;

    private boolean lastState;
    private Long lastChanged;
    private int consecutivePresses = 0;
    private int timeoutInterval = 500;
    private int holdThreshold = 500;
    private long lastReleased = Long.MAX_VALUE;

    @Override
    public void onInteraction(@NonNull Consumer<GamepadInteractionEvent> callback) {
        this.interactionEventConsumer = callback;
    }

    public void onInteraction(VoidAction callback){
        this.interactionEventConsumer = e -> callback.action();
    }

    public void onPress(@NonNull Consumer<GamepadInteractionEvent> callback){
        this.pressEventConsumer = callback;
    }

    public void onPress(VoidAction callback){
        this.pressEventConsumer = e -> callback.action();
    }

    public void onRelease(@NonNull Consumer<GamepadInteractionEvent> callback){
        this.releaseEventConsumer = callback;
    }

    public void onRelease(VoidAction callback){
        this.releaseEventConsumer = e -> callback.action();
    }

    /* TODO: Rebuild double presses with FSM
    public void onDoublePress(@NonNull Consumer<GamepadInteractionEvent> callback){
        this.doublePressEventConsumer = callback;
    }
     */

    public void onButtonHold(@NonNull Consumer<GamepadInteractionEvent> callback){
        this.holdEventConsumer = callback;
    }

    /**
     * Unsupported as of 10/9/22
     * @param callback
     */
    public void onShortPress(@NonNull Consumer<GamepadInteractionEvent> callback){
        this.shortPressConsumer = callback;
    }

    public Button(){
        this(false);
    }

    public Button(boolean initialState){
        lastState = initialState;
        lastChanged = System.currentTimeMillis();
    }

    public Boolean value(){
        return lastState;
    }

    public Button setTimeoutInterval(int interval){
        timeoutInterval = interval;
        return this;
    }

    @Override
    public void update(Object newState) {
        GamepadInteractionEvent event = new GamepadInteractionEvent((boolean)newState, null, lastChanged, consecutivePresses);
        if(lastState != (boolean)newState){
            interactionEventConsumer.accept(event);
            if((boolean)newState) {
                pressEventConsumer.accept(event);
                if(consecutivePresses ==  2){
                    doublePressEventConsumer.accept(event);
                }
            } else {
                releaseEventConsumer.accept(event);
                lastReleased = System.currentTimeMillis();
            }
        } else {
            if((boolean) newState && (System.currentTimeMillis() - lastReleased >= holdThreshold)){
                holdEventConsumer.accept(event);
                lastReleased = Long.MAX_VALUE;
            }
        }

        if((boolean)newState && (System.currentTimeMillis() - lastReleased <= timeoutInterval)) {
            consecutivePresses++;
        } else {
            consecutivePresses = 0;
        }
        lastChanged = System.currentTimeMillis();
        lastState = (boolean)newState;
    }

    public void removeInteractionHandler(){
        this.interactionEventConsumer = defaultConsumer;
    }

    public void removePressHandler(){
        this.pressEventConsumer = defaultConsumer;
    }

    public void removeReleaseHandler(){
        this.releaseEventConsumer = defaultConsumer;
    }

    public void removeHoldHandler(){
        this.releaseEventConsumer = defaultConsumer;
    }

    public void removeShortPressHandler(){this.shortPressConsumer = defaultConsumer;}

    @Override
    public void disconnectAllHandlers(){
        interactionEventConsumer = defaultConsumer;
        pressEventConsumer = defaultConsumer;
        releaseEventConsumer = defaultConsumer;
        doublePressEventConsumer = defaultConsumer;
        holdEventConsumer = doublePressEventConsumer;
    }
}
