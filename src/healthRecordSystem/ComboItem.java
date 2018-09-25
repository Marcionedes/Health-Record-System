package healthRecordSystem;

public class ComboItem {
    private String value;
    private String label;

    public ComboItem(String value, String label) {
        this.value = value;
        this.label = label;
    }
    public ComboItem(String value) {
        this.value = value;
    }
    public String getValue() {
        return this.value;
    }
    public String getLabel() {
        return this.label;
    }
    @Override
    public String toString() {
        return label;
    }

}
