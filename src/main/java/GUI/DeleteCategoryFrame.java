package GUI;

import DB.Models.Category;
import Services.MainService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteCategoryFrame extends JFrame {

    private MainService mainService;

    public DeleteCategoryFrame(MainService mainService, Category category) {
        this.mainService = mainService;

        setTitle("Удаление категории");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel confirmLabel = new JLabel("Вы уверены, что хотите удалить категорию: " + category.getName() + "?");

        JButton deleteButton = new JButton("Удалить");

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mainService.deleteCategory(category.getId());
                    JOptionPane.showMessageDialog(null, "Категория успешно удалена!");
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ошибка при удалении категории: " + ex.getMessage());
                }
            }
        });

        setLayout(new FlowLayout());
        add(confirmLabel);
        add(deleteButton);

        setVisible(true);
    }
}
