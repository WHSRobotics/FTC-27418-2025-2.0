package org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Consumer;

@RequiresApi(api = Build.VERSION_CODES.N)
public class GamepadScalarHardware implements GamepadHardware{
    public Consumer<GamepadInteractionEvent> interactionConsumer = defaultConsumer;
    private Float previousState = 0.0f;
    private boolean inverted = false;
    private boolean limitSensitivity;
    private float sensitivityThreshold;
    private Long lastChanged;

    @Documented
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface LimitSensitivity {
        float sensitivity() default 0.1f;
    }

    public GamepadScalarHardware(boolean inverted){
        this();
        this.inverted = inverted;
    }

    public GamepadScalarHardware(){
        LimitSensitivity sensitive = this.getClass().getDeclaredAnnotation(LimitSensitivity.class);
        if(sensitive != null){
            sensitivityThreshold = sensitive.sensitivity();
            limitSensitivity = true;
        }
        lastChanged = System.currentTimeMillis();
    }

    public float value() {
        return (inverted ? -1 : 1) * previousState;
    }

    @Override
    public void onInteraction(Consumer<GamepadInteractionEvent> callback) {
        this.interactionConsumer = callback;
    }

    public void onInteraction(VoidAction callback){
        this.interactionConsumer = e -> callback.action();
    }

    @Override
    public void update(Object newState) {
        Float input = (Float) newState;
        if(previousState != input) {
            if (limitSensitivity) {
                if ((Math.abs(previousState - input) < previousState * sensitivityThreshold)) {
                    GamepadInteractionEvent event = new GamepadInteractionEvent(null, inverted ? -1 : 1 * input, lastChanged, null);
                    interactionConsumer.accept(event);
                }
            } else {
                GamepadInteractionEvent event = new GamepadInteractionEvent(null, inverted ? -1 : 1 * input, lastChanged, null);
                interactionConsumer.accept(event);
            }
        }
        lastChanged = System.currentTimeMillis();
        previousState = input;
    }

    @Override
    public void disconnectAllHandlers() {
        this.interactionConsumer = defaultConsumer;
    }
}
