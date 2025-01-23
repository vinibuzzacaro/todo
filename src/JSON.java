import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;

public class JSON {
    private String content;

    private JSON(String str) {
        this.content = str;
    }

    public static JSON fromObject(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        StringBuilder content = new StringBuilder("{");
        String fieldName;
        String fieldValue;
        Field field;
        for (int i = 0; i < fields.length; i++) {
            field = fields[i];
            fieldName = field.getName();
            try {
                field.setAccessible(true);
                fieldValue = field.get(object).toString();
                content.append(String.format("\"%s\":\"%s\"", fieldName, fieldValue));
            } catch (Exception e) {
                System.err.println(e);
            }
            if (i < fields.length - 1) {
                content.append(",");
            }
        }
        content.append("}");
        return new JSON(content.toString());
    }

    public static <T> T objectFromJSON(JSON json, Class<T> _class) {
        Stream<String> fieldStream = Arrays.stream(_class.getDeclaredFields()).map(Field::getName);
        String content = json.getContent();
        int identationLevel = 0;
        boolean insideQuotes = false;
        StringBuilder tmpString = new StringBuilder(64);
        for (char ch : content.toCharArray()) {
            switch (ch) {
                case '{' -> identationLevel++;
                case '}' -> identationLevel--;
                case '"' -> insideQuotes = !insideQuotes;
                default -> {
                    if (insideQuotes) {
                        tmpString.append(ch);
                    } else {
                        if (tmpString.isEmpty()) { continue; }
                        Optional<String> optionalString =  fieldStream
                            .filter(field -> field.equalsIgnoreCase(tmpString.toString()))
                            .findFirst();
                        if (optionalString.isEmpty()) { continue; }
                        Optional<Field> optionalField = Arrays.stream(_class.getDeclaredFields())
                            .filter(field -> field.getName().equalsIgnoreCase(optionalString.get()))
                            .findFirst();
                        if (optionalField.isEmpty()) { continue; }
                        
                    }
                }
            }
        }
        return null;
    }

    public static JSON fromString(String content) {
        return new JSON(content);
    }

    public String getContent() {
        return this.content;
    }
}
