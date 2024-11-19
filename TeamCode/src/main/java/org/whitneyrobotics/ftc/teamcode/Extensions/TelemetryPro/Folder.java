package org.whitneyrobotics.ftc.teamcode.Extensions.TelemetryPro;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx.GamepadEx;

import java.util.ArrayList;
import java.util.Arrays;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Folder extends Interactable {

    private ArrayList<LineItem> nested;

    private GamepadEx gamepad;

    private boolean focused = false;

    @Override
    public void connectGamepad(GamepadEx gamepad) {
        this.gamepad = gamepad;
        gamepad.A.onPress(e -> open = !open);
    }

    private boolean open = true;

    public Folder(String caption, LineItem... nestedLines) {
        super(caption);
        this.setPersistent(true);
        nested = new ArrayList<>(Arrays.asList(nestedLines));
    }

    public void purge(){nested.clear();}

    public void addChild(LineItem child){
        nested.add(child);
    }

    public void removeChildByItem(LineItem child){
        nested.remove(child);
    }

    public void removeNthChild(int index){
        nested.remove(index);
    }

    public void removeChildByCaption(String caption){
        nested.removeIf(item -> item.caption == caption);
    }


    @Override
    public void reset() {
        open = false;
    }

    @Override
    protected String format(boolean blink) {
        if(open){
            String result = (focused ? "<span style=\"background-color: #FFFFFF\"><strong><font color=\"#000000\" style=\"background-color: #FFFFFF\">" : "") +  "↓ " + this.caption + (focused ? "</font></strong></span>" : "");
            for(LineItem item : nested) {
                item.isVisible = true;
                result += String.format("<br> |  %s", item.render(blink));
            }
            return result;
        }
        for(LineItem item : nested) {
            item.isVisible = false;
        }
        return (focused ? "<span style=\"background-color: #FFFFFF\"><strong><font color=\"#000000\" style=\"background-color: #FFFFFF\">" : "") + "> " + this.caption  + (focused ? "</font></strong></span>" : "");
    }

    @Override
    public String render(boolean blink) {
        return this.format(blink);
    }

    @Override
    public void focus() {
        focused = true;
    }

    @Override
    public void disconnect() {
        focused = false;
        gamepad.A.removePressHandler();
    }
}
