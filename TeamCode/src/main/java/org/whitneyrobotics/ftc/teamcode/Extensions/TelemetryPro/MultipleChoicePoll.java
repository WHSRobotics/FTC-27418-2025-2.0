package org.whitneyrobotics.ftc.teamcode.Extensions.TelemetryPro;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx.GamepadEx;

import java.util.ArrayList;
import java.util.function.Consumer;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MultipleChoicePoll extends Interactable {
    private GamepadEx gp;
    private int pointer = 0;

    @Override
    public void connectGamepad(GamepadEx gamepad) {
        gp = gamepad;
        gp.DPAD_LEFT.onPress(e -> pointer = (((pointer - 1) % options.length) + options.length) % options.length);
        gp.DPAD_RIGHT.onPress(e -> pointer = (pointer + 1) % options.length);
        gp.A.onPress(e-> select(options[pointer]));
    }

    @Override
    public void focus() {
        focused = true;
    }

    @Override
    public void disconnect() {
        pointer = 0;
        focused = false;
        gp.DPAD_LEFT.removePressHandler();
        gp.DPAD_RIGHT.removePressHandler();
        gp.A.removePressHandler();
    }

    public static class MultipleChoiceOption<T> {
        private String caption;
        private T value;
        protected boolean enabled;

        @Override
        public String toString() {
            return "MultipleChoiceOption{" +
                    "caption='" + caption + '\'' +
                    ", value=" + value +
                    ", enabled=" + enabled +
                    '}';
        }

        public String render(boolean hover, boolean blink){
            if(enabled){
                return "\t" + "<span style=\"background-color: " + (blink && hover ? "#FFFFFF" : "#BBBBBB") + "\"><strong><font color=\"#000000\" style=\"background-color: #FFFFFF\">" + caption + "</font></strong></span>";
            }
            if (hover && blink){
                return "\t" + "<span style=\"background-color: #FFFFFF\"><strong><font color=\"#000000\">" + caption + "</font></strong></span>";
            }
            return "\t" + caption;
        }

        public T getValue(){
            return value;
        }

        public MultipleChoiceOption(String caption, T value){
            this.caption = caption;
            this.value = value;
        }
    }

    private String prompt;
    private MultipleChoiceOption[] options;
    private ArrayList<MultipleChoiceOption> selected = new ArrayList<>();
    private Consumer<MultipleChoiceOption[]> onChangeCallback = e -> {};
    private Consumer<MultipleChoiceOption> onChangeCallbackSingle = e -> {};

    private boolean multiSelectAllowed = false;
    private boolean focused = false;

    public MultipleChoicePoll(String prompt, boolean multiSelectAllowed, Color color, MultipleChoiceOption option1, MultipleChoiceOption... options){
        super(prompt, color);
        MultipleChoiceOption[] newOptions = new MultipleChoiceOption[options.length + 1];
        newOptions[0] = option1;
        for(int i = 1; i<=options.length; i++){newOptions[i] = options[i-1];}
        this.prompt = prompt;
        this.options = newOptions;
        this.multiSelectAllowed = multiSelectAllowed;
        if(!multiSelectAllowed){
            option1.enabled = true;
            selected.add(option1);
        }
    }

    public MultipleChoicePoll(String prompt, boolean multiSelectAllowed, MultipleChoiceOption option1, MultipleChoiceOption... options){
        this(prompt, multiSelectAllowed, Color.WHITE, option1, options);
    }

    public Consumer<MultipleChoiceOption> useMultipleChoiceAdder(){
        return this::addOption;
    }

    public static Consumer<MultipleChoiceOption> useMultipleChoiceAdder(MultipleChoicePoll poll){
        return poll::addOption;
    }

    public MultipleChoicePoll addOption(MultipleChoiceOption option){
        MultipleChoiceOption[] newOptions = new MultipleChoiceOption[options.length + 1];
        System.arraycopy(options,0,newOptions,0,options.length);
        newOptions[options.length] = option;
        options = newOptions;
        return this;
    }

    public <T> MultipleChoicePoll addOption(String caption, T value){
        return this.addOption(new MultipleChoiceOption(caption, value));
    }

    @Override
    public void reset() {
        selected.clear();
        if(!multiSelectAllowed) selected.add(options[0]);
    }

    @Override
    protected String format(boolean blink) {
        String formattedString = renderPartial(prompt);
        for (MultipleChoiceOption option : options){
            formattedString += option.render(focused && options[pointer] == option, blink);
        }
        return  formattedString;
    }

    @Override
    public String render(boolean blink) {
        return this.format(blink);
    }

    private void select(MultipleChoiceOption option){
        if(multiSelectAllowed){
            option.enabled = !option.enabled;
            selected = new ArrayList<>();
            for (MultipleChoiceOption _option : options){
                if(_option.enabled){
                    selected.add(_option);
                }
            }
            if(selected.size() == 0) {
                onChangeCallback.accept(new MultipleChoiceOption[0]);
            } else {
                onChangeCallback.accept(selected.toArray(new MultipleChoiceOption[selected.size() - 1]));
            }
        } else {
            MultipleChoiceOption currentlyEnabled = selected.get(0);
            if(option == currentlyEnabled) return;
            currentlyEnabled.enabled = false;
            option.enabled = true;
            selected = new ArrayList<>();
            selected.add(option);
            onChangeCallbackSingle.accept(option);
        }
    }

    public MultipleChoiceOption[] getSelected(){
        if(selected.size() == 0) return new MultipleChoiceOption[0];
        return selected.toArray(new MultipleChoiceOption[selected.size()-1]);
    }

    public void onChange(Consumer<MultipleChoiceOption[]> callback) {this.onChangeCallback = callback;}
    public void onChangeSingle(Consumer<MultipleChoiceOption> callback){this.onChangeCallbackSingle = callback;}
}
