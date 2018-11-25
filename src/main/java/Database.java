import java.util.ArrayList;
import java.util.List;

public class Database {
    String name;
    List<Database_Table> tables;
    String engine = "InnoDB";
    String charset = "utf8";

    public Database(String name) {
        this.name = name;
        this.tables = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Database_Table> getTables() {
        return tables;
    }

    public void setTables(List<Database_Table> tables) {
        this.tables = tables;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
