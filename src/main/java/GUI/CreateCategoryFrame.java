package GUI;

import DB.Models.Category;
import Services.MainService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateCategoryFrame extends JFrame {

    private MainService mainService;

    public CreateCategoryFrame(MainService mainService) {
        this.mainService = mainService;

        setTitle("Создание категории");
        setSize(300, 150); // Устанавливаем размеры окна
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- Метка и поле ввода для названия категории ---
        JLabel categoryNameLabel = new JLabel("Название категории:");
        JTextField categoryNameField = new JTextField(20);

        // --- Кнопка "Создать" ---
        JButton createButton = new JButton("Создать");

        // --- Обработчик кнопки "Создать" ---
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String categoryName = categoryNameField.getText().trim();
                if (categoryName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Название категории не может быть пустым.");
                    return;
                }

                try {
                    // Создаем новую категорию
                    mainService.createCategory(categoryName);

                    // Уведомление об успешном создании
                    JOptionPane.showMessageDialog(null, "Категория успешно создана!");

                    // Закрытие окна
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ошибка при создании категории: " + ex.getMessage());
                }
            }
        });

        // --- Расположение компонентов на экране ---
        setLayout(new FlowLayout());
        add(categoryNameLabel);
        add(categoryNameField);
        add(createButton);

        setVisible(true);
    }
}
