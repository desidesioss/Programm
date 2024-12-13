import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        String fileName = "data/test.meeting";
        String className = "Meeting";

        try {
            // Step 1: Deserialize object from file
            String serializedData = Files.readString(Path.of(fileName));
            Meeting meeting = (Meeting) ReflectionDeserializer.deserialize(serializedData, className);
            System.out.println("Deserialized Meeting:");
            System.out.println("Title: " + meeting.getTitle());
            System.out.println("Description: " + meeting.getDescription());

            // Step 2: Modify object using setters
            meeting.setTitle("Updated Meeting Title");
            meeting.setUsersLimit(100);
            System.out.println("Updated Meeting:");
            System.out.println("Title: " + meeting.getTitle());
            System.out.println("Users Limit: " + meeting.getUsersLimit());

            // Step 3: Serialize and save object back to file
            String updatedSerializedData = ReflectionSerializer.serialize(meeting);
            Files.writeString(Path.of(fileName), updatedSerializedData);
            System.out.println("Updated Meeting saved to file.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
