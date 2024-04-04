package Baitap;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class View {
    private JTextArea textArea;
    private JTree tree;
    private JButton button;
    private JButton saveButton;
    private JButton openButton;
    private JButton exitButton;
    private JFileChooser fileChooser;

    public View(Model model) {
        textArea = new JTextArea(10, 30);
        textArea.setEditable(true);
        tree = new JTree();
        button = new JButton("Click");

        saveButton = new JButton("Save");
        openButton = new JButton("Open");
        exitButton = new JButton("Exit");

        fileChooser = new JFileChooser();

        // Add action listeners for buttons
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    openFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JButton getButton() {
        return button;
    }

    public void setText(String text) {
        textArea.setText(text);
    }

    public void updateTreeView(DefaultMutableTreeNode root) {
        tree.setModel(new DefaultTreeModel(root));
    }

    public void updateAdditionalInfo(String info) {
        textArea.setText(info);
    }

    private void openFile() throws IOException {
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile.isDirectory()) {
                new Model().traverseDirectory(selectedFile);
            } else {
                textArea.setText(new Model().readFile(selectedFile));
            }
        }
    }

    private void saveFile() {
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                new Model().saveFile(selectedFile, textArea.getText());
                JOptionPane.showMessageDialog(null, "Lưu file thành công", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Xảy ra lỗi trong quá trình lưu file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getOpenButton() {
        return openButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }
}
