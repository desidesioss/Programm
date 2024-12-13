import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReflectionSerializer {
    public static String serialize(Object object) throws IllegalAccessException {
        StringBuilder serializedData = new StringBuilder();
        Class<?> clazz = object.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(object);

            if (value instanceof LocalDate) {
                value = ((LocalDate) value).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            } else if (value instanceof LocalTime) {
                value = ((LocalTime) value).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            } else if (value instanceof LocalDateTime) {
                value = ((LocalDateTime) value).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
            }

            serializedData.append("'").append(field.getName()).append("'='").append(value).append("'\n");
        }
        return serializedData.toString();
    }
}
