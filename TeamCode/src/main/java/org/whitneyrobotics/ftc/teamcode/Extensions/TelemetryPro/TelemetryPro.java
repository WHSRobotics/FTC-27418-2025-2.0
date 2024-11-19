package org.whitneyrobotics.ftc.teamcode.Extensions.TelemetryPro;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Function;
import org.firstinspires.ftc.robotcore.external.Predicate;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx.Button;
import org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx.GamepadEx;
import org.whitneyrobotics.ftc.teamcode.Extensions.TelemetryPro.AutoSetupTesting.TestManager;
import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;
import org.whitneyrobotics.ftc.teamcode.Tests.Test;

import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.N)
public class TelemetryPro {
    private OpMode currentOpMode;
    private Telemetry opModeTelemetry;
    private TestManager testManager;

    private final ArrayList<LineItem> items;
    private final ArrayList<Interactable> interactables = new ArrayList<>();
    private boolean lineNumbers = false;

    private TelemetryPro(OpMode o){
        items = new ArrayList<>();
        currentOpMode = o;
        Autonomous auto = o.getClass().getDeclaredAnnotation(Autonomous.class);
        TeleOp tele = o.getClass().getDeclaredAnnotation(TeleOp.class);
        String name = (auto == null) ? (tele == null) ? "OpMode is missing annotation" : tele.name() : auto.name();
        this.addItem(new TextLine(String.format("OpMode %s (%s)",name, (auto == null) ? (tele == null) ? "Unknown" : "TeleOp" : "Autonomous"), true, LineItem.Color.AQUA)
                .setRichTextFormat(LineItem.RichTextFormat.BOLD)
        );
        this.addItem(new KeyValueLine("Runtime", true, (() ->
            String.format("%s:",(int)Math.floor(currentOpMode.getRuntime()/60)) + (currentOpMode.getRuntime()%60<10 ? "0" : "") + String.format("%s",(int)Math.floor(currentOpMode.getRuntime()%60))
        ), LineItem.Color.WHITE));

        Test testingAnnotation = o.getClass().getDeclaredAnnotation(Test.class);
        if(testingAnnotation != null){
            this.addItem(new TextLine(String.format("Running Test %s of [%s]\n%s%s",
                    testingAnnotation.name(),o.getClass().getPackage().getName(),
                    testingAnnotation.description(),
                    (testingAnnotation.autoTerminateAfterSeconds() > 0 && o instanceof OpModeEx) ?
                            "This test will terminate after " + testingAnnotation.autoTerminateAfterSeconds() + " seconds." :
                            "\n"),
                    true,LineItem.Color.LIME, LineItem.RichTextFormat.ITALICS));
        }
        opModeTelemetry = currentOpMode.telemetry;
        opModeTelemetry.setDisplayFormat(Telemetry.DisplayFormat.HTML);
    }

    public TestManager useTestManager(){
        testManager = new TestManager(this);
        return testManager;
    }

    public TestManager getTestManager(){
        return testManager;
    }

    private Telemetry dashbboardTelemetry;

    private static TelemetryPro instance;

    public static TelemetryPro setOpMode(OpMode o){
        instance = new TelemetryPro(o);
        return instance;
    }

    public void useDashboardTelemetry(Telemetry dashbboardTelemetry){
        this.dashbboardTelemetry = dashbboardTelemetry;
    }

    public static TelemetryPro getCurrentInstance(){
        return instance;
    }

    public void update(){
        //blink every second
        interact();
        boolean blink = currentOpMode.getRuntime() % 0.5 > 0.25;
        for (LineItem item : items) {
            opModeTelemetry.addLine(
                    (lineNumbers ? "   " + (items.size() < 10 ? "    " : "") + items.indexOf(item) + "| " : "") +
                            item.render(blink));
            if (dashbboardTelemetry != null && item instanceof KeyValueLine){
                KeyValueLine keyValueItem = (KeyValueLine) item;
                dashbboardTelemetry.addData(keyValueItem.caption, keyValueItem.value());
                dashbboardTelemetry.update();
            }
        }
        purge();
        //opModeTelemetry.clear();
    }

    public TelemetryPro toggleLineNumbers(){
        lineNumbers = !lineNumbers;
        return this;
    }

    public void purge(){
        items.removeIf(item -> {
            boolean persistent = item.isPersistent();
            if(!persistent){
                if (item instanceof Interactable){
                    ((Interactable) item).disconnect();
                    interactables.remove(item);
                }
            }
            return !persistent;
        });
    }

    public void clear(){
        items.clear();
        interactables.forEach(Interactable::disconnect);
        interactables.clear();
    }

    private Interactable focused = null;

    private Interactable getNextInteractable(boolean dec){
        if(interactables.size() < 1) return null;
        if(focused == null){
            return interactables.get(0);
        } else {
            int focusIndex = interactables.indexOf(focused);
            if(dec && focusIndex == 0) return interactables.get(interactables.size()-1);//modulo wrapping fix
            return interactables.get((focusIndex + (dec ? -1 : 1) ) % interactables.size());
        }
    }

    public void speak(String text){
        opModeTelemetry.speak(text);
    }

    public void speak(String text, String languageCode, String countryCode){
        opModeTelemetry.speak(text, languageCode, countryCode);
    }

    /**
     * TODO: Make GamepadEx with button hold signal ignore methods
     * @param gamepad
     */
    private GamepadEx interactingGamepad;

    public void setInteractingGamepad(GamepadEx gamepadToCopy){
        setInteractingGamepad(gamepadToCopy, g->g.DPAD_DOWN, g->g.DPAD_UP);
    }

    /**
     * Creates a ghost {@link GamepadEx} instance in the telemetry to process gamepad interactions.
     * @param gamepadToCopy The gamepad instance to feed values off of. The gamepad will be copied so event listeners may still be attached
     * @param incrementButton A lambda function that takes in any gamepad, and returns a button. Necessary to attach the increment button to the new gamepad reference.
     * @param decrementButton A lambda function that takes in any gamepad, and returns a button. Necessary to attach the decrement button to the new gamepad reference.
     */
    public void setInteractingGamepad(GamepadEx gamepadToCopy, Function<GamepadEx, Button> incrementButton, Function<GamepadEx, Button> decrementButton){
        interactingGamepad = new GamepadEx(gamepadToCopy.getEncapsulatedGamepad());
        incrementButton.apply(interactingGamepad).onPress(e -> {
            if(focused != null) focused.disconnect();
            focused = getNextInteractable(true);
        });
        decrementButton.apply(interactingGamepad).onPress(e -> {
            if(focused != null) focused.disconnect();
            focused = getNextInteractable(false);
        });
    }

    public void interact(){
        if(interactingGamepad != null){
            interactingGamepad.update();
            if(focused != null) {
                focused.connectGamepad(interactingGamepad);
                focused.focus();
            } else if (interactables.size() > 0){
                focused = getNextInteractable(false);
            }
        }
    }

    public TelemetryPro removeLineByReference(LineItem item){
        removeLinePredicate(i -> i == item);
        return this;
    }

    public TelemetryPro removeLineByCaption(String caption){
        removeLinePredicate(item -> item.caption.equals(caption));
        return this;
    }

    public TelemetryPro removeLineById(int id){
        removeLinePredicate(item -> item.id == id);
        return this;
    }

    public TelemetryPro removeLinePredicate(Predicate<LineItem> predicate){
        for (LineItem item : items){
            if(predicate.test(item)) item.setPersistent(false);
        }
        interactables.removeIf(item -> predicate.test(item));
        if(!interactables.contains(focused) && focused != null){
            focused.disconnect();
            focused = getNextInteractable(false);
        }
        return this;
    }

    public TelemetryPro addItem(LineItem item){
        items.add(item);
        if(item instanceof Interactable) interactables.add((Interactable) item);
        return this;
    }

    public LineItem addLine(){
        SeparatorLine line = new SeparatorLine("blank line", SeparatorLine.LineStyle.NEWLINE);
        items.add(line);
        return line;
    }

    public LineItem addLine(String line, LineItem.RichTextFormat... richTextFormats){
        return addLine(line, LineItem.Color.WHITE, richTextFormats);
    }

    public LineItem addLine(String line, LineItem.Color color, LineItem.RichTextFormat... richTextFormats){
        TextLine lineItem = new TextLine(line, false, color, richTextFormats);
        items.add(lineItem);
        return lineItem;
    }

    public LineItem addData(String key, Object value, LineItem.RichTextFormat... richTextFormats){
        return addData(key, value, LineItem.Color.WHITE, richTextFormats);
    }

    public LineItem addData(String key, Object value, LineItem.Color color, LineItem.RichTextFormat... richTextFormats){
        KeyValueLine line = new KeyValueLine(key, false, value, color, richTextFormats);
        items.add(line);
        return line;
    }

    public LineItem addData(String key, Func<?> valueProducer, LineItem.RichTextFormat... richTextFormats){
        return addData(key, valueProducer, LineItem.Color.WHITE, richTextFormats);
    }

    public LineItem addData(String key, Func<?> valueProducer, LineItem.Color color, LineItem.RichTextFormat... richTextFormats){
        KeyValueLine line = new KeyValueLine(key, false, valueProducer, color, richTextFormats);
        items.add(line);
        return line;
    }

    public static String replaceNewLineWithLineBreaks(String s){
        return s.replaceAll("\n","<br>");
    }
}
