package Baitap;

import javax.swing.*;

public class Controller {
    private Model model;
    private View view;
    

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
       
        view.getButton().addActionListener(e -> buttonClicked());
    }

    public void capnhat() {
        view.updateTreeView(model.getRoot());
        view.updateAdditionalInfo("Bài tập java");
    }

    private void buttonClicked() {
        JOptionPane.showMessageDialog(null, "Button clicked");  	
    }
}
