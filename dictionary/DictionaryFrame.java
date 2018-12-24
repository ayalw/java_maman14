package maman14.dictionary;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

public class DictionaryFrame extends JFrame {

    /**
     * FileManager object is responsible of loading\saving the dictionary.
     */
    private FileManager m_fileManager = new FileManager();

    /**
     * The UI representation of the dictionary, a table with 2 columns:
     * Column 0 is 'Word'
     * Column 1 is 'Meaning'
     * The JTable is backed by a TableModel with the logical data representation.
     */
    private JTable m_table;

    /**
     * The logical table
     */
    private DefaultTableModel m_tableModel;

    /**
     * Assumption: Zero or one file is open at every given moment.
     */
    private String m_currentFile = "No File Opened";

    /**
     * Search string
     */
    private JTextField m_textFieldSearch;

    /**
     * Constructor
     */
    public DictionaryFrame() {
        super("My Dictionary");

        JPanel buttonsPanel = new JPanel();
        JButton btnOpenFile = new JButton("Open...");
        btnOpenFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Open Dictionary From File");
                URL url = getClass().getResource("dictionary_sample.ayal");
                File file = new File(url.getPath());
                fileChooser.setCurrentDirectory(file.getParentFile());
                int userSelection = fileChooser.showOpenDialog(DictionaryFrame.this);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    loadTable(fileChooser.getSelectedFile());
                    System.out.println("Open file: " + fileToSave.getAbsolutePath());
                }
            }
        });
        JButton btnSaveAs = new JButton("Save As...");
        btnSaveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstDuplicateKey = checkForDuplicateKeys();
                if (firstDuplicateKey != null) {
                    JOptionPane.showMessageDialog(DictionaryFrame.this, "Cannot save file - duplicate entry exist: " + firstDuplicateKey, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                JFileChooser fileChooser = new JFileChooser();
                URL url = getClass().getResource("dictionary_sample.ayal");
                File file = new File(url.getPath());
                fileChooser.setCurrentDirectory(file.getParentFile());
                fileChooser.setDialogTitle("Save Dictionary To File");
                int userSelection = fileChooser.showSaveDialog(DictionaryFrame.this);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile().getAbsoluteFile();
                    String path = fileToSave.getAbsolutePath();
                    saveTableToFile(fileChooser.getSelectedFile());
                }
            }
        });
        JButton btnAddEntry = new JButton("Add Word");
        btnAddEntry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) m_table.getModel();
                model.addRow(new Object[]{"[Please Insert Word]", "[Please Insert Meaning]"});
            }
        });
        JButton btnRemoveEntry = new JButton("Remove Selected Words");
        btnRemoveEntry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeSelectedRows(m_table);
                //repaint();
            }
        });

        JButton btnSearch = new JButton("Search Word");
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });
        m_textFieldSearch = new JTextField("[Enter search word here]");
        m_textFieldSearch.setBackground(Color.YELLOW);

        buttonsPanel.add((btnOpenFile));
        buttonsPanel.add(btnSaveAs);
        buttonsPanel.add(btnAddEntry);
        buttonsPanel.add(btnRemoveEntry);
        buttonsPanel.add(m_textFieldSearch);
        buttonsPanel.add(btnSearch);
        add(buttonsPanel, BorderLayout.NORTH);

        m_tableModel = new DefaultTableModel() {

            @Override
            public int getColumnCount() {
                return 2;
            }

            @Override
            public String getColumnName(int columnIndex) {
                if (columnIndex == 0) return "Word";
                if (columnIndex == 1) return "Meaning";
                return "Unknown";
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return true;
            }
        };
        m_table = new JTable(m_tableModel);
        m_table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        m_table.setShowGrid(true);
        JScrollPane scrollPane = new JScrollPane(m_table);
        add(scrollPane, BorderLayout.CENTER);

        setSize(Constants.WINDOW_WIDTH_PIXELS,Constants.WINDOW_HEIGHT_PIXELS);
        setVisible(true);
        setResizable(false);

        URL url = getClass().getResource("dictionary_sample.ayal");
        File file = new File(url.getPath());
        loadTable(file);


        }

    /**
     * Repaint includes sorting the displayed table
     */
    @Override
    public void repaint() {
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(m_table.getModel());
        m_table.setRowSorter(sorter);

        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
    }

    /**
     * Select the row containing the word in the search bar
     */
    private void search() {
        String wordToSearch = m_textFieldSearch.getText();
        if (wordToSearch == null) return;
        for (int i=0; i< m_table.getRowCount(); i++) {
            if (m_table.getValueAt(i, 0).equals(wordToSearch)) {
                m_table.setRowSelectionInterval(i,i);
            }
        }
    }

    /**
     * Load file and repaint the displayed table
     * @param file
     */
    public void loadTable(File file) {
        m_fileManager.loadTable(file, m_tableModel);
        m_tableModel.fireTableDataChanged();
        m_table.revalidate();
        m_currentFile = file.getAbsolutePath();
        this.setTitle("Dictionary Editor - " + m_currentFile);
        repaint();
    }

    /**
     * Save the table into file
     * @param file
     */
    public void saveTableToFile(File file) {
        m_fileManager.saveTable(m_tableModel, file);
        m_currentFile = file.getAbsolutePath();
        this.setTitle("Dictionary Editor - " + m_currentFile);
        repaint();
    }

    /**
     * Duplicate words are allowed temporarily on screen, however they will not be allowed to be saved.
     * @return the first duplicate word, or null if there are no duplicates.
     */
    private String checkForDuplicateKeys() {
        for (int i=0; i<m_table.getRowCount(); i++) {
            for (int j=0; j<m_table.getRowCount(); j++) {
                if (m_table.getValueAt(i,0).equals(m_table.getValueAt(j,0)) && i!=j) {
                    return (String)m_table.getValueAt(i, 0);
                }
            }
        }
        return null;
    }

    /**
     * Remove the words selected by the user (supports multiple rows)
     * @param table
     */
    public void removeSelectedRows(JTable table){
        DefaultTableModel model = (DefaultTableModel) m_table.getModel();
        int[] rows = table.getSelectedRows();
        for(int i=0;i<rows.length;i++){
            int rowToRemove = rows[i] - i;
            String word = (String)table.getValueAt(rowToRemove, 0);
            int modelRowToRemove = -1;
            for (int j=0; j< model.getRowCount(); j++)
            {
                if (model.getValueAt(j, 0).equals(word))
                {
                    modelRowToRemove = j;
                }
            }
            if (modelRowToRemove != -1) {
                model.removeRow(modelRowToRemove);
            }
        }
        repaint();
    }

}
