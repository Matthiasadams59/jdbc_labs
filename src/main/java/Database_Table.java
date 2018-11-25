import java.util.ArrayList;
import java.util.List;

public class Database_Table {
    String name;

    String type;
    String collation;
    List<Database_Column> columns;

    String primary_key;
    String unique_key;
    List<Foreign_Key> foreign_keys;
    List<Index> indexes;

    public Database_Table(String name) {
        this.name = name;
        this.columns = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Database_Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Database_Column> columns) {
        this.columns = columns;
    }

    public String getPrimary_key() {
        return primary_key;
    }

    public void setPrimary_key(String primary_key) {
        this.primary_key = primary_key;
    }

    public List<Foreign_Key> getForeign_keys() {
        return foreign_keys;
    }

    public void setForeign_keys(List<Foreign_Key> foreign_keys) {
        this.foreign_keys = foreign_keys;
    }

    public String getUnique_key() {
        return unique_key;
    }

    public void setUnique_key(String unique_key) {
        this.unique_key = unique_key;
    }

    public List<Index> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<Index> indexes) {
        this.indexes = indexes;
    }
}
