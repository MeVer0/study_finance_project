package GUI;

import Services.MainService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCategoryDialog extends JDialog {
    private JTextField nameField;
    private JButton submitButton;
    private JButton cancelButton;

    public AddCategoryDialog(JFrame parent, MainService mainService) {
        super(parent, "Добавить категорию", true);
        setLayout(new GridLayout(2, 2));
        setSize(300, 150);
        setLocationRelativeTo(parent);

        // Поле для имени категории
        add(new JLabel("Название категории:"));
        nameField = new JTextField();
        add(nameField);

        // Кнопки подтверждения и отмены
        submitButton = new JButton("Добавить");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String categoryName = nameField.getText();
                if (!categoryName.isEmpty()) {
                    mainService.new CategoryCRUD().createCategory(categoryName);
                    JOptionPane.showMessageDialog(parent, "Категория добавлена!");
                    dispose(); // Закрыть диалог
                } else {
                    JOptionPane.showMessageDialog(parent, "Введите название категории!");
                }
            }
        });

        cancelButton = new JButton("Отмена");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Закрыть диалог
            }
        });

        add(submitButton);
        add(cancelButton);
    }
}
