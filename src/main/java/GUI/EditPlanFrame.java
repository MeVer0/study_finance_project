package GUI;

import DB.Models.Category;
import DB.Models.Plan;
import Services.MainService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EditPlanFrame extends JFrame {

    private MainService mainService;
    private Plan selectedPlan;

    private JTextField amountField;
    private JComboBox<String> categoryComboBox;

    public EditPlanFrame(MainService mainService, int planIdToEdit) {
        this.mainService = mainService;
        this.selectedPlan = mainService.getPlanById(planIdToEdit);

        setTitle("Редактировать план");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Создание формы для редактирования
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        // Категория
        JLabel categoryLabel = new JLabel("Выберите категорию:");
        categoryComboBox = new JComboBox<>();
        List<Category> categories = mainService.getAllCategoryByUserId(mainService.getCurrentUser().getId());
        for (Category category : categories) {
            categoryComboBox.addItem(category.getName());
        }

        // Устанавливаем текущую категорию в comboBox
        categoryComboBox.setSelectedItem(findCategoryById(selectedPlan.getCategoryId()));

        // Сумма
        JLabel amountLabel = new JLabel("Сумма:");
        amountField = new JTextField(selectedPlan.getSum().toString());

        // Кнопка для сохранения изменений
        JButton saveButton = new JButton("Сохранить");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveChanges();
            }
        });

        // Кнопка для закрытия окна
        JButton cancelButton = new JButton("Отмена");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Закрыть окно
            }
        });

        // Добавляем компоненты в форму
        panel.add(categoryLabel);
        panel.add(categoryComboBox);
        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(saveButton);
        panel.add(cancelButton);

        add(panel);
        setVisible(true);
    }

    private Category findCategoryById(int categoryId) {
        // Ищем категорию по ID в списке
        List<Category> categories = mainService.getAllCategoryByUserId(mainService.getCurrentUser().getId());
        for (Category category : categories) {
            if (category.getId() == categoryId) {
                return category;
            }
        }
        return null;
    }

    private void saveChanges() {
        try {
            String selectedCategory = (String) categoryComboBox.getSelectedItem();
            int categoryId = mainService.getCategoryIdByUserIdAndCategoryName(mainService.getCurrentUser().getId(), selectedCategory);
            Double amount = Double.parseDouble(amountField.getText());

            // Обновляем план
            mainService.updatePlan(selectedPlan.getId(), categoryId, amount);

            JOptionPane.showMessageDialog(this, "План обновлен успешно", "Успех", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Закрываем окно после успешного сохранения изменений
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Пожалуйста, введите корректную сумму.", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
