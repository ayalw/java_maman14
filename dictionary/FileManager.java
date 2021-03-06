package maman14.dictionary;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.Vector;

public class FileManager {

    /**
     * Save table model to file.
     * The file format is binary and not readable outside this application.
     * @param tm
     * @param file
     */
    public void saveTable(DefaultTableModel tm, File file) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(file));
            out.writeObject(tm.getDataVector());
            Vector<String> columnNames = new Vector<>();
            columnNames.add("Word");
            columnNames.add("Meaning");
            out.writeObject(columnNames);
            out.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Load table from file and convert to table model.
     * @param file
     * @param tableModel
     */
    public void loadTable(File file, DefaultTableModel tableModel) {
        try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                Vector rowData = (Vector)in.readObject();
                Vector columnNames = (Vector)in.readObject();
                tableModel.setDataVector(rowData, columnNames);
                in.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
