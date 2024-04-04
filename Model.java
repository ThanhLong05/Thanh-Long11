package Baitap;
import javax.swing.tree.DefaultMutableTreeNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.stream.Collectors;

public class Model {
    private DefaultMutableTreeNode root;
    private String additionalInfo;

    public Model() {
        root = new DefaultMutableTreeNode("D://");
        additionalInfo = "";
    }

    public void buildTree(String path) {
        File rootDir = new File(path);
        buildTree(root, rootDir);
      }

    private void buildTree(DefaultMutableTreeNode node, File file) {
        if (file.isDirectory()) {
            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(file.getName());
            node.add(childNode);
            File[] fileList = file.listFiles();
            if (fileList != null) {
                for (File f : fileList) {
                    buildTree(childNode, f);
                }
            }
        } else {
            node.add(new DefaultMutableTreeNode(file.getName()));
        }
    }

    public String readFile(File file) throws IOException {
        if (file.getName().endsWith(".txt")) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        }
        return "";
    }

    public void saveFile(File file, String content) throws IOException {
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.write(content);
        }
    }

    public DefaultMutableTreeNode getRoot() {
        return root;	
	}
    public void traverseDirectory(File directory) throws IOException {
        Files.walk(directory.toPath())
                .filter(Files::isRegularFile)
                .forEach(file -> {
                    try {
                        readFile(file.toFile());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

}
