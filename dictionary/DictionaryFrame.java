package maman14.dictionary;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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

        JPanel tablePanel = new JPanel();
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
        JScrollPane scrollPane = new JScrollPane(m_table);
        m_table.setFillsViewportHeight(true);

        //scrollPane.setLayout(new BorderLayout());
        //scrollPane.add(table.getTableHeader(), BorderLayout.PAGE_START);
        //scrollPane.add(table, BorderLayout.CENTER);

        //tablePanel.add(table);
        add(scrollPane, BorderLayout.CENTER);

        setSize(Constants.WINDOW_WIDTH_PIXELS,Constants.WINDOW_HEIGHT_PIXELS);
        setVisible(true);
        //setResizable(false);
    }

    public void loadTable(File file) {
        m_fileManager.loadTable(file, m_tableModel);
        //m_tableModel.setValueAt("ayal", 1, 1);
        m_tableModel.fireTableDataChanged();
        m_table.revalidate();
        //m_table = new JTable(m_tableModel);
        repaint();
    }

    public void saveTableToFile(File file) {
        m_fileManager.saveTable(m_tableModel, file);
    }
}
