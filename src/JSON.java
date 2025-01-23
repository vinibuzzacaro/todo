import java.lang.reflect.Field;

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

    public String getContent() {
        return this.content;
    }
}
