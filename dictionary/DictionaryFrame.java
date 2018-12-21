package maman14.dictionary;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

public class DictionaryFrame extends JFrame {

    private FileManager m_fileManager = new FileManager();
    private JTable m_table;
    private DefaultTableModel m_tableModel;

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
        buttonsPanel.add((btnOpenFile));
        buttonsPanel.add(btnSaveAs);
        add(buttonsPanel, BorderLayout.NORTH);

        m_tableModel = new DefaultTableModel() {
            @Override
            public int getRowCount() {
                return 10;
            }

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
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(m_table.getModel());
        sorter.setSortable(0, true);
        m_table.setRowSorter(sorter);
        JScrollPane scrollPane = new JScrollPane(m_table);
        DefaultTableModel model = (DefaultTableModel) m_table.getModel();
        //model.addRow(new Object[]{"Column 1", "Column 2"});        //m_table.setFillsViewportHeight(true);
        //m_table.getColumnModel().getColumn(0).setPreferredWidth(50);
        //m_table.getColumnModel().getColumn(1).setPreferredWidth(300);


        add(scrollPane, BorderLayout.CENTER);

        setSize(Constants.WINDOW_WIDTH_PIXELS,Constants.WINDOW_HEIGHT_PIXELS);
        setVisible(true);
        setResizable(false);

        URL url = getClass().getResource("dictionary_sample.ayal");
        File file = new File(url.getPath());
        loadTable(file);
    }

    public void loadTable(File file) {
        m_fileManager.loadTable(file, m_tableModel);
        m_tableModel.fireTableDataChanged();
        m_table.revalidate();
        repaint();
    }

    public void saveTableToFile(File file) {
        m_fileManager.saveTable(m_tableModel, file);
    }
}
