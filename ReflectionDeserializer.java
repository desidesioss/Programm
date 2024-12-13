import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReflectionDeserializer {
    public static Object deserialize(String serializedData, String className) throws Exception {
        Class<?> clazz = Class.forName(className);
        Object instance = clazz.getDeclaredConstructor().newInstance();

        String[] lines = serializedData.split("\\n");
        for (String line : lines) {
            if (line.isEmpty()) continue;

            String[] parts = line.split("='", 2);
            String fieldName = parts[0].replace("'", "");
            String fieldValue = parts[1].substring(0, parts[1].length() - 1);

            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);

            if (field.getType() == LocalDate.class) {
                field.set(instance, LocalDate.parse(fieldValue, DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            } else if (field.getType() == LocalTime.class) {
                field.set(instance, LocalTime.parse(fieldValue, DateTimeFormatter.ofPattern("HH:mm:ss")));
            } else if (field.getType() == LocalDateTime.class) {
                field.set(instance, LocalDateTime.parse(fieldValue, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
            } else if (field.getType() == int.class) {
                field.set(instance, Integer.parseInt(fieldValue));
            } else {
                field.set(instance, fieldValue);
            }
        }
        return instance;
    }
}
