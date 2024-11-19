package org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx;

import android.os.Build;
import android.util.Pair;

import androidx.annotation.RequiresApi;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamServer;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource;
import org.whitneyrobotics.ftc.teamcode.Extensions.TelemetryPro.TelemetryPro;
import org.whitneyrobotics.ftc.teamcode.Extensions.TelemetryPro.LineItem;
import org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx.GamepadEx;
import org.whitneyrobotics.ftc.teamcode.Libraries.JSON.Alias;
import org.whitneyrobotics.ftc.teamcode.Extensions.TelemetryPro.TelemetryData;
import org.whitneyrobotics.ftc.teamcode.Tests.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.Consumer;

@RequiresApi(api = Build.VERSION_CODES.N)
public abstract class OpModeEx extends OpMode {

    public enum BULK_READ_BEHAVIOR {
        NEW_FRAME_EVERY_READ,
        ONE_FRAME_PER_LOOP
    }

    private BULK_READ_BEHAVIOR bulkReadBehavior = BULK_READ_BEHAVIOR.NEW_FRAME_EVERY_READ;

    protected GamepadEx gamepad1;
    protected GamepadEx gamepad2;

    protected TelemetryPro telemetryPro = TelemetryPro.setOpMode(this);

    private List<LynxModule> hubs;

    private FtcDashboard dashboard = FtcDashboard.getInstance();
    protected Telemetry dashboardTelemetry = dashboard.getTelemetry();
    protected Telemetry telemetry;
    protected TelemetryPacket packet = new TelemetryPacket();

    protected boolean sendOpModeExTelemetryPacket = false;

    Map<Object, Pair<Field, String>> annotatedFields = new Hashtable();

    private void pairHubBehavior() {
        switch (bulkReadBehavior) {
            case ONE_FRAME_PER_LOOP:
                hubs.forEach(h -> h.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL));
                break;
            default:
                hubs.forEach(h -> h.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO));
                break;
        }
    }

    public void setBulkReadBehavior(BULK_READ_BEHAVIOR behavior){
        bulkReadBehavior = behavior;
        pairHubBehavior();
    }

    protected void initializeDashboardTelemetry(int msTransmissionInterval) {
        telemetry = dashboard.getTelemetry();
        telemetry = new MultipleTelemetry(telemetry,telemetry);
        telemetry.setMsTransmissionInterval(msTransmissionInterval);
    }

    protected void startDriverStationWebcamStream(CameraStreamSource source){
        CameraStreamServer.getInstance().setSource(source);
        dashboard.startCameraStream(source,30);
    }

    public static class Timeable {

        @FunctionalInterface
        public interface TemporalCallback {
            void call(Consumer<Boolean> resolve);
        }
        public TemporalCallback callback;
        public boolean persistent = false;
        public double timeoutMs;
        Timeable(TemporalCallback callback, double timeoutMs){
            this.callback = callback;
            this.timeoutMs = timeoutMs;
        }
        Timeable(TemporalCallback callback, double timeoutMs, boolean persistent){
            this.callback = callback;
            this.timeoutMs = timeoutMs;
            this.persistent = persistent;
        }
    }

    private PriorityQueue<Timeable> queue = new PriorityQueue<>(1, (o1, o2) -> {
        if(o1.timeoutMs == o2.timeoutMs) return 0;
        if(o1.timeoutMs < o2.timeoutMs) return -1;
        return 1;
    });
    protected Timeable current;

    @Override
    public final void init(){
        Test testingAnnotation = this.getClass().getDeclaredAnnotation(Test.class);
        if(testingAnnotation != null){
            if(testingAnnotation.autoTerminateAfterSeconds() > 0){
                addTemporalCallback(resolve -> requestOpModeStop(), testingAnnotation.autoTerminateAfterSeconds()*1000);
            }
        }
        for (Field f : this.getClass().getDeclaredFields()) {
            RobotLog.vv("OpModeField ",f.getName());
            boolean hasTelemetryAnnotation = f.getAnnotation(TelemetryData.class) != null;
            Alias alias = f.getAnnotation(Alias.class);
            if(hasTelemetryAnnotation && f.isAccessible()) annotatedFields.put(this,new Pair(f, alias != null ? alias.value() : null));
        }
        gamepad1 = new GamepadEx(super.gamepad1);
        gamepad2 = new GamepadEx(super.gamepad2);

        hubs = hardwareMap.getAll(LynxModule.class);
        initInternal();
        pairHubBehavior();
    }

    //TODO: Convert annotatedFields to a map containing the parent object and the fields to track

    public final void addTelemetryFields(Object o, String... fields){
        List<String> fieldsList = Arrays.asList(fields);
        for (Field f : o.getClass().getDeclaredFields()){
            RobotLog.vv("Field",f.getName());
            fieldsList.stream().forEach(field -> {
                if(field.equals(f.getName()) && f.isAccessible()) {
                    Alias alias = f.getAnnotation(Alias.class);
                    RobotLog.vv(String.format("Class field <%s> ", o.getClass().getSimpleName()), f.getName());
                    annotatedFields.put(o, new Pair(f, alias != null ? alias.value() : null));
                }
            });
        }
    }


    public abstract void initInternal();

    @Override
    public void init_loop(){
        gamepad1.update();
        gamepad2.update();
        telemetryPro.update();
        initInternalLoop();
    }

    public void initInternalLoop(){};

    @Override
    public void start() {
        resetRuntime();
        startInternal();
    }

    public void startInternal(){

    }

    @Override
    public final void loop(){
        if (bulkReadBehavior == BULK_READ_BEHAVIOR.ONE_FRAME_PER_LOOP) hubs.forEach(LynxModule::clearBulkCache);

        packet = new TelemetryPacket();
        annotatedFields.forEach((o,fieldPair)-> {
            try {
                telemetryPro.addData(fieldPair.second != null ? fieldPair.second : fieldPair.first.getName(), fieldPair.first.get(o));
            } catch (IllegalAccessException e) {
                RobotLog.e("L Bozo + Ratio");
            }
        });
        loopInternal();
        processQueue();
        gamepad1.update();
        gamepad2.update();
        telemetryPro.update();
        dashboard.sendTelemetryPacket(packet);
    }

    protected abstract void loopInternal();

    private void advanceQueue(boolean shouldAdvance){
        if(shouldAdvance) current = null;
    }

    private void processQueue(){
        if(current == null) {
            if(queue.size() < 1) return;
            if(getRuntime() <= (queue.peek().timeoutMs)/1000) return;
            current = queue.remove();
            current.callback.call(this::advanceQueue);
            if(current != null && !current.persistent) current = null;
        }
    }

    public OpModeEx addTemporalCallback(Timeable t){
        queue.add(t);
        return this;
    }

    public OpModeEx addTemporalCallback(Timeable.TemporalCallback callback, double timeoutMs){
        queue.add(new Timeable(callback, timeoutMs));
        return this;
    }

    protected void playSound(String identifier){ playSound(identifier,"raw",50.0f); }

    protected void playSound(String identifier, float volume){
        playSound(identifier, "raw", volume);
    }

    protected void playSound(String identifier, String folder, float volume){
        int id = hardwareMap.appContext.getResources().getIdentifier(identifier, folder, hardwareMap.appContext.getPackageName());
        if(id == 0){
            telemetryPro.addLine(String.format("Sound '%s' not found in '%s'", identifier,folder), LineItem.Color.MAROON);
        } else {
            SoundPlayer.getInstance().setMasterVolume(volume);
            SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, id);
        }
    }
}
