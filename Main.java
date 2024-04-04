package Baitap;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        View view = new View(model);
        Controller controller = new Controller(model, view);
        
        model.buildTree("D:\\Java pro\\Baitaptrenlop\\Baitap\\src\\main\\java");
        
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); 
        frame.setLocationRelativeTo(null);
      
        JButton saveButton = view.getSaveButton();
        JButton openButton = view.getOpenButton();
        JButton exitButton = view.getExitButton();
        
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        
       
        buttonPanel.add(saveButton);
        buttonPanel.add(openButton);
        buttonPanel.add(exitButton);
        
       
        frame.add(buttonPanel, BorderLayout.NORTH);

        JTree tree = new JTree(model.getRoot());
        JTextArea textArea = view.getTextArea();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(tree), new JScrollPane(textArea));
        splitPane.setResizeWeight(0.5);

        frame.add(splitPane, BorderLayout.CENTER);

        frame.setVisible(true);

        controller.capnhat();

        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> {
            try (Stream<Path> fileStream = Files.walk(Path.of("D:\\\\Java pro\\\\Baitaptrenlop\\\\Baitap\\\\src\\\\main\\\\java"))) {
                fileStream
                        .filter(Files::isRegularFile)
                        .forEach(file -> System.out.println(file.getFileName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });   
        executor.shutdown();
        
        tree.addTreeSelectionListener( e -> {
        	DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        	if ( selectedNode != null) {
        		String selectedNodeName = selectedNode.getUserObject().toString();
        		view.updateAdditionalInfo("Selected Node:" + selectedNodeName);
        	}
        });
    }
}
