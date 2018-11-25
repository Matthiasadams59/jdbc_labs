import javax.xml.crypto.Data;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// ajouter log4j logging

public class Reverse_Engineering {
    public static void main(String[] args) {
        if (args.length == 4) {
            try {
                // Récupérer les arguments
                String jdbcurl = args[0];
                String drivername = args[1];
                String username = args[2];
                String password = args[3];

                System.out.println("Chargement du driver");
                Class.forName(drivername);

                System.out.println("ouverture de la connexion à la bas de données");
                Connection con = DriverManager.getConnection(jdbcurl, username, password);

                System.out.println("récupération du DatabaseMtaData");
                DatabaseMetaData dma = con.getMetaData();

                String[] types = new String[1];
                types[0] = "TABLE"; //set table type mask

                System.out.println("Récupération de la liste des tables");
                ResultSet results = dma.getTables(null, null, null, types);
                // Liste des tables de notre base de données
                List<Database_Table> tables = new ArrayList<>();
                System.out.println("Traitement de la table ");
                while (results.next()) {
                    // Récupération du nom de la table et création de l'object Database_Table
                    Database_Table current_table = new Database_Table(results.getString(3));

                    // Récupération de la liste des colonnes de cette table
                    List<Database_Column> colonnes = new ArrayList<>();
                    ResultSet columns = dma.getColumns(null, null, current_table.getName(), null);
                    while (columns.next()) {
                        Database_Column current_column = new Database_Column(columns.getString("COLUMN_NAME"));
                        // smallint(5) -> smallint type et 5 size
                        // SMALLINT UNSIGNED par ex : type + attribut?
                        String data_type = columns.getString("TYPE_NAME");
                        String[] data_type_array = data_type.split(" ");
                        if (data_type_array.length > 1) {
                            current_column.setData_type(data_type_array[0]);
                            current_column.setAttribute(data_type_array[1]);
                        } else {
                            current_column.setData_type(columns.getString("TYPE_NAME"));
                        }
                        current_column.setData_type_size(columns.getString("COLUMN_SIZE"));
                        if (columns.getString("DECIMAL_DIGITS") != null) {
                            current_column.setData_type_size(columns.getString("COLUMN_SIZE")+","+columns.getString("DECIMAL_DIGITS"));
                        }
                        // 0 pour non, 1 pour oui
                        current_column.setIsNull(columns.getString("IS_NULLABLE"));
                        // String, peut être null
                        current_column.setDefault_value(columns.getString("COLUMN_DEF"));
                        // YES : la colonne peut inclure des null, NO pour non, "" si inconnu
                        current_column.setAuto_increment(columns.getString("IS_AUTOINCREMENT"));

                        // Ajout à la liste des colonnes
                        colonnes.add(current_column);
                    }

                    // Récupérer la clé primaire
                    ResultSet PK = dma.getPrimaryKeys(null, null, current_table.getName());
                    // La fonction getString retourne le nom du colonne
                    // Une primary key peut avoir plusieurs colonnes (ex : primary key (userid, userdataid)
                    // on fait donc un loop pour toutes les avoir
                    int primary_index = 0;
                    String primaryKey = "";
                    while (PK.next()) {
                        if (primary_index++ > 0) {
                            primaryKey += ",";
                        }
                        primaryKey += PK.getString("COLUMN_NAME");
                    }
                    current_table.setPrimary_key(primaryKey);

                    // Récupération de la liste des clés étrangères
                    List<Foreign_Key> clesetrangeres = new ArrayList<>();
                    ResultSet FK = dma.getImportedKeys(null, null, current_table.getName());
                    while (FK.next()) {
                        Foreign_Key current_foreign_key = new Foreign_Key(FK.getString("FK_NAME"));
                        current_foreign_key.setPrimary_key_table_name(FK.getString("PKTABLE_NAME"));
                        current_foreign_key.setPrimary_key_column_name(FK.getString("PKCOLUMN_NAME"));
                        current_foreign_key.setForeign_key_table_name(FK.getString("FKTABLE_NAME"));
                        current_foreign_key.setForeign_key_column_name(FK.getString("FKCOLUMN_NAME"));

                        if (Integer.parseInt(FK.getString("UPDATE_RULE")) == 0) {
                            current_foreign_key.setUpdate_rule("CASCADE");
                        } else if (Integer.parseInt(FK.getString("UPDATE_RULE")) == 1) {
                            current_foreign_key.setUpdate_rule("SET DEFAULT");
                        } else if (Integer.parseInt(FK.getString("UPDATE_RULE")) == 2) {
                            current_foreign_key.setUpdate_rule("SET NULL");
                        } else if (Integer.parseInt(FK.getString("UPDATE_RULE")) == 3) {
                            current_foreign_key.setUpdate_rule("RESTRICT");
                        } else if (Integer.parseInt(FK.getString("UPDATE_RULE")) == 4) {
                            current_foreign_key.setUpdate_rule("CASCADE");
                        }

                        if (Integer.parseInt(FK.getString("DELETE_RULE")) == 0) {
                            current_foreign_key.setDelete_rule("CASCADE");
                        } else if (Integer.parseInt(FK.getString("DELETE_RULE")) == 1) {
                            current_foreign_key.setDelete_rule("SET DEFAULT");
                        } else if (Integer.parseInt(FK.getString("DELETE_RULE")) == 2) {
                            current_foreign_key.setDelete_rule("SET NULL");
                        } else if (Integer.parseInt(FK.getString("DELETE_RULE")) == 3) {
                            current_foreign_key.setDelete_rule("RESTRICT");
                        } else if (Integer.parseInt(FK.getString("DELETE_RULE")) == 4) {
                            current_foreign_key.setDelete_rule("NO ACTION");
                        }

                        // ajout à la liste des clés étrangères
                        clesetrangeres.add(current_foreign_key);
                    }

                    // Récupération des clés uniques
                    ResultSet UK = dma.getIndexInfo(null, null, current_table.getName(), true, true);
                    int unique_index = 0;
                    String uniqueKey = "";
                    while (UK.next()) {
                        // Filtrer les clés primaires
                        if (!UK.getString("INDEX_NAME").equals("PRIMARY")) {
                            if (unique_index++ > 0) {
                                uniqueKey += ",";
                            }
                            uniqueKey += UK.getString("COLUMN_NAME");
                        }
                    }
                    current_table.setUnique_key(uniqueKey);

                    // Récupérer les autres indexes
                    ResultSet indexResults = dma.getIndexInfo(null, null, current_table.getName(), false, true);
                    List<Index> keys = new ArrayList<>();
                    while (indexResults.next()) {
                        if (!indexResults.getString("INDEX_NAME").equals("PRIMARY")) {
                            Index current_key = new Index(indexResults.getString("INDEX_NAME"));
                            current_key.setColumn_name(indexResults.getString("COLUMN_NAME"));
                            keys.add(current_key);
                        }
                    }
                    current_table.setIndexes(keys);

                    // Ajout à la liste des tables
                    current_table.setForeign_keys(clesetrangeres);
                    current_table.setColumns(colonnes);
                    tables.add(current_table);

                    indexResults.close();
                    UK.close();
                    FK.close();
                    PK.close();
                    columns.close();
                }
                String database_name = jdbcurl.substring(jdbcurl.lastIndexOf("/") + 1);
                System.out.println("NOM DE LA BASE DE DONNEES : " + database_name);
                Database database = new Database(database_name);
                database.setTables(tables);

                /*System.out.println();
                ResultSet columns = dma.getColumns(null,null, database.getTables().get(14).getName(), null);
                while(columns.next()) {
                    for (int i = 1 ; i < 24 ; i++) {
                        System.out.println(i + " : " + columns.getString(i));
                    }
                }*/

                //Get Foreign Keys
                /*ResultSet FK = dma.getImportedKeys(null, null, database.getTables().get(13).getName());
                System.out.println("------------FOREIGN KEYS-------------");
                while(FK.next())
                {
                    System.out.println(FK.getString("PKTABLE_NAME") + "---" + FK.getString("PKCOLUMN_NAME") + "===" + FK.getString("FKTABLE_NAME") + "---" + FK.getString("FKCOLUMN_NAME"));
                    for (int i = 1 ; i < 15 ; i++) {
                        System.out.println(i + " : " + FK.getString(i));
                    }
                }*/

                // Autres indexes
                /*ResultSet indexes = dma.getIndexInfo(null, null, sakila.getTables().get(13).getName(), false, true);
                System.out.println("--------INDEXES-------");
                while(indexes.next()) {
                    for (int i = 1 ; i < 14 ; i++) {
                        System.out.println(i + " : " + indexes.getString(i));
                    }
                }*/

                sql_dump(database);

                System.out.println("Fin du programme");
                results.close();


            } catch (ClassNotFoundException | SQLException nf) {
                nf.printStackTrace();
            }
        } else {
            System.out.println("Invalid argument. Your command should be written like 'java Reverse_Engineering \"JDBC:URL:FOR:DB\" myDb.Driver.Name DBUserName DBPassword'");
        }

    }

    public static void sql_dump(Database basededonnees) {
        try {
            System.out.println("Écriture du fichier sql");
            String nom = basededonnees.getName();
            FileWriter fw = new FileWriter(nom + "-schema.sql");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("DROP SCHEMA IF EXISTS "+nom+";\n" +
                    "CREATE SCHEMA "+nom+";\n" +
                    "USE "+nom+";\n\n");
            // ECRITURE DES TABLES
            for (Database_Table unetable : basededonnees.getTables()) {
                bw.write("\nCREATE TABLE "+unetable.getName()+" (\n");

                // ECRITURE DES COLONNES
                int colonne_index = 1;
                for (Database_Column unecolonne : unetable.getColumns()) {
                    if (colonne_index++ > 1) {
                        bw.write(",\n");
                    }

                    bw.write(" " + unecolonne.getName());

                    if(unecolonne.getName().contains("CHAR") || unecolonne.getName().equals("DECIMAL")){

                        bw.write(" " + unecolonne.getData_type()+"("+unecolonne.getData_type_size()+")");

                    } else if (unecolonne.getAttribute() != null && !unecolonne.getAttribute().isEmpty()) {
                        bw.write(" " + unecolonne.getData_type() + " " + unecolonne.getAttribute());
                    } else if (unecolonne.getData_type().equals("BIT")) {
                        bw.write(" BOOLEAN");
                    } else {
                        bw.write(" " + unecolonne.getData_type());
                    }
                    if (unecolonne.getIsNull().equals("YES")) {
                        bw.write(" NULL");
                    } else if (unecolonne.getIsNull().equals("NO")) {
                        bw.write(" NOT NULL");
                    }
                    if (unecolonne.getDefault_value() != null) {
                        if (unecolonne.getData_type().equals("BIT") && Integer.parseInt(unecolonne.getDefault_value()) == 0) {
                            bw.write(" DEFAULT FALSE");
                        } else if (unecolonne.getData_type().equals("BIT") && Integer.parseInt(unecolonne.getDefault_value()) == 1) {
                            bw.write(" DEFAULT TRUE");
                        } else {
                            bw.write(" DEFAULT " + unecolonne.getDefault_value());
                        }
                    }
                    if (unecolonne.getDefault_value() != null && unecolonne.getDefault_value().equals("CURRENT_TIMESTAMP")) {

                        bw.write(" ON UPDATE CURRENT_TIMESTAMP");
                    }
                    if (unecolonne.getAuto_increment().equals("YES")) {
                        bw.write(" AUTO_INCREMENT");
                    }
                }

                if (unetable.getPrimary_key() != null && !unetable.getPrimary_key().isEmpty()) {
                    bw.write(",\n PRIMARY KEY ("+unetable.getPrimary_key()+")");
                }
                if (unetable.getUnique_key() != null && !unetable.getUnique_key().isEmpty()) {
                    bw.write(",\n UNIQUE KEY ("+unetable.getUnique_key()+")");
                }
                for (Index unindex : unetable.getIndexes()) {
                    bw.write(",\n KEY " + unindex.getIndex_name() + " ("+unindex.getColumn_name()+")");
                }
                for (Foreign_Key unetranger : unetable.getForeign_keys()) {
                    bw.write(",\n CONSTRAINT " + unetranger.getForeign_key_name() + " " +
                            "FOREIGN KEY (" + unetranger.getForeign_key_column_name() + ") " +
                            "REFERENCES " + unetranger.getForeign_key_table_name() + " (" + unetranger.getForeign_key_column_name() + ") " +
                            "ON DELETE " + unetranger.getDelete_rule() + " " +
                            "ON UPDATE " + unetranger.getUpdate_rule());
                }


                bw.write("\n)ENGINE="+basededonnees.getEngine()+" DEFAULT CHARSET="+basededonnees.getCharset()+";\n\n");
            }
            bw.close();
            fw.close();
            System.out.println("LE FICHIER " + basededonnees.getName()+"-schema.sql A BIEN ETE ENREGISTRE A LA RACINE DU DOSSIER");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
