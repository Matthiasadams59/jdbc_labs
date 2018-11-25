public class Foreign_Key {
    String foreign_key_name;

    String primary_key_table_name;
    String primary_key_column_name;

    String foreign_key_table_name;
    String foreign_key_column_name;

    String update_rule;
    String delete_rule;

    public Foreign_Key(String foreign_key_name) {
        this.foreign_key_name = foreign_key_name;
    }

    public String getForeign_key_name() {
        return foreign_key_name;
    }

    public void setForeign_key_name(String foreign_key_name) {
        this.foreign_key_name = foreign_key_name;
    }

    public String getPrimary_key_table_name() {
        return primary_key_table_name;
    }

    public void setPrimary_key_table_name(String primary_key_table_name) {
        this.primary_key_table_name = primary_key_table_name;
    }

    public String getPrimary_key_column_name() {
        return primary_key_column_name;
    }

    public void setPrimary_key_column_name(String primary_key_column_name) {
        this.primary_key_column_name = primary_key_column_name;
    }

    public String getForeign_key_table_name() {
        return foreign_key_table_name;
    }

    public void setForeign_key_table_name(String foreign_key_table_name) {
        this.foreign_key_table_name = foreign_key_table_name;
    }

    public String getForeign_key_column_name() {
        return foreign_key_column_name;
    }

    public void setForeign_key_column_name(String foreign_key_column_name) {
        this.foreign_key_column_name = foreign_key_column_name;
    }

    public String getUpdate_rule() {
        return update_rule;
    }

    public void setUpdate_rule(String update_rule) {
        this.update_rule = update_rule;
    }

    public String getDelete_rule() {
        return delete_rule;
    }

    public void setDelete_rule(String delete_rule) {
        this.delete_rule = delete_rule;
    }
}
