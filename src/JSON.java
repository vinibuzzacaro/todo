import java.lang.reflect.Field;

public class JSON {
    private String content;

    private JSON() {}

    public static JSON fromObject(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        String content = "{";
        String fieldName;
        for (Field field : fields) {
            fieldName = field.getName();
            content = content.concat(String.format("\"%s\": ", fieldName));
        }
        return new JSON();
    }
}
