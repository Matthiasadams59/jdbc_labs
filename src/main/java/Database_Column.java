public class Database_Column {
    String name;
    String data_type;
    String data_type_size;
    String isNull;
    String default_value;

    String attribute;

    String auto_increment;

    public Database_Column(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public String getData_type_size() {
        return data_type_size;
    }

    public void setData_type_size(String data_type_size) {
        this.data_type_size = data_type_size;
    }

    public String getIsNull() {
        return isNull;
    }

    public void setIsNull(String isNull) {
        this.isNull = isNull;
    }

    public String getDefault_value() {
        return default_value;
    }

    public void setDefault_value(String default_value) {
        this.default_value = default_value;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAuto_increment() {
        return auto_increment;
    }

    public void setAuto_increment(String auto_increment) {
        this.auto_increment = auto_increment;
    }
}
