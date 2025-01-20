package GUI;

import DB.Models.Category;
import Services.MainService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditCategoryFrame extends JFrame {

    private MainService mainService;

    public EditCategoryFrame(MainService mainService, int categoryId) {
        this.mainService = mainService;
        Category category = mainService.getCategoryById(categoryId);
        setTitle("Редактирование категории");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel categoryNameLabel = new JLabel("Новое название категории:");
        JTextField categoryNameField = new JTextField(category.getName(), 20);

        JButton updateButton = new JButton("Обновить");

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newCategoryName = categoryNameField.getText().trim();
                if (newCategoryName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Название категории не может быть пустым.");
                    return;
                }

                try {
                    mainService.updateCategory(category.getId(), newCategoryName);
                    JOptionPane.showMessageDialog(null, "Категория успешно обновлена!");
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ошибка при обновлении категории: " + ex.getMessage());
                }
            }
        });

        setLayout(new FlowLayout());
        add(categoryNameLabel);
        add(categoryNameField);
        add(updateButton);

        setVisible(true);
    }
}
