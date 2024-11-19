// Refactored by: Christopher Gholmieh
// Package:
package org.firstinspires.ftc.teamcode.Libraries.JSON;

// Imports:
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import com.qualcomm.robotcore.util.ReadWriteFile;
import com.qualcomm.robotcore.util.RobotLog;
import androidx.annotation.RequiresApi;
import java.lang.reflect.Modifier;
import java.lang.reflect.Field;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Hashtable;
import android.os.Build;
import java.util.Arrays;
import java.util.Map;
import java.io.File;

// Class:
@RequiresApi(api = Build.VERSION_CODES.N)
public final class RobotDataUtil {
    // Variables (Assignment):
    @JsonIgnored
    private static final AppUtil appUtility = AppUtil.getInstance();

    // Methods:
    @JsonIgnored
    public synchronized static Field[] getClassWritableFields(Class<?> dataClass) {
        return Arrays.stream(dataClass.getFields())
                .filter(field -> field.getAnnotation(JsonIgnored.class) == null
                        && Modifier.isStatic(field.getModifiers())
                        && field.getType().isPrimitive())
                .toArray(Field[]::new);
    }

    public synchronized static void save(Class<?> dataClass) {
        save(dataClass, false);
    }

    @JsonIgnored
    public synchronized static void save(Class<?> dataClass, boolean mergeExistingData) {
        save(dataClass, dataClass.getName(), mergeExistingData);
    }

    @JsonIgnored
    public synchronized static void save(final Class<?> dataClass, String fileName, boolean mergeExistingData) {
        // Variables (Assignment):
        Map<String, Object> valueMap = new Hashtable<>();

        // Logic:
        if (mergeExistingData) {
            // Variables (Assignment):
            File file = loadFile(fileName, ".json");

            // Logic:
            try {
                // Variables (Assignment):
                JSONObject jsonObject = new JSONObject(ReadWriteFile.readFile(file));

                // Logic:
                jsonObject.keys().forEachRemaining(key -> {
                    try {
                        valueMap.put(key, jsonObject.get(key));
                    } catch (JSONException exception) {
                        RobotLog.ee(String.format("Unable to fetch a value for JSON file %s", fileName), exception.getLocalizedMessage());
                    }
                });
            } catch (Exception exception) {
                RobotLog.ee(String.format("Unable to merge JSON variables for class %s", fileName), exception.getLocalizedMessage());
            }
        }

        // Variables (Assignment):
        Field[] writableFields = getClassWritableFields(dataClass);

        // Logic:
        for (Field field : writableFields) {
            // Logic:
            try {
                // Logic:
                if (field.getName() != null) {
                    valueMap.put(field.getName(), field.get(null));
                }
            } catch (IllegalAccessException exception) {
                RobotLog.ee(String.format("Failed to capture internal class field %s", field.getName()), exception.getMessage());
            }
        }

        // Variables (Assignment):
        JSONObject jsonObject = new JSONObject(valueMap);
        File file = loadFile(fileName, ".json");

        // Logic:
        ReadWriteFile.writeFile(file, jsonObject.toString());
    }

    @JsonIgnored
    public synchronized static void load(Class<?> dataClass) {
        load(dataClass, dataClass.getName());
    }

    @JsonIgnored
    public synchronized static void load(Class<?> dataClass, String fileName) {
        // Variables (Assignment):
        File file = loadFile(fileName, ".json");

        // Logic:
        try {
            // Variables (Assignment):
            JSONObject jsonObject = new JSONObject(ReadWriteFile.readFile(file));

            // Logic:
            for (Field field : getClassWritableFields(dataClass)) {
                // Logic:
                try {
                    // Logic:
                    if (jsonObject.has(field.getName())) {
                        field.set(dataClass, jsonObject.get(field.getName()));
                    }
                } catch (Exception exception) {
                    RobotLog.ee(String.format("Could not set field %s", field.getName()), exception.getLocalizedMessage());
                }
            }
        } catch (JSONException exception) {
            RobotLog.ee(String.format("Unable to parse JSON file %s.json", fileName), exception.getLocalizedMessage());
        }
    }

    @JsonIgnored
    public static synchronized File loadFile(String fileName, String extension) {
        // Variables (Assignment):
        File file = new File(fileName + extension);

        // Logic:
        if (!file.isAbsolute()) {
            // Logic:
            appUtility.ensureDirectoryExists(file, true);

            // Variables (Assignment):
            file = new File(AppUtil.ROBOT_DATA_DIR, fileName + extension);
        }

        return file;
    }
}