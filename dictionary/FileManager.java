package maman14.dictionary;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.Vector;

public class FileManager {

    public void saveTable(DefaultTableModel tm, File file) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(file));
            Vector<String> dataVector = tm.getDataVector();
            out.writeObject(tm.getDataVector());
            Vector<String> columnNames = new Vector<>();
            columnNames.add("Word");
            columnNames.add("Meaning");
            //out.writeObject(tm.getDataVector());
            out.writeObject(columnNames);
            out.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DefaultTableModel loadTable(File file, DefaultTableModel tableModel) {
        try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                Vector rowData = (Vector)in.readObject();
                Vector columnNames = (Vector)in.readObject();
                //DefaultTableModel tm = new DefaultTableModel();
            tableModel.setDataVector(rowData, columnNames);
                in.close();
                return tableModel;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
