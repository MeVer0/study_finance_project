package GUI;

import DB.Models.Category;
import Services.MainService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CreatePlanFrame extends JFrame {

    private MainService mainService;
    private JComboBox<String> categoryComboBox;
    private JTextField sumTextField;
    private JTextField nameTextField;

    public CreatePlanFrame(MainService mainService) {
        this.mainService = mainService;

        setTitle("Создать план");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Получаем все категории для текущего пользователя
        List<Category> categories = mainService.getAllCategoryByUserId(mainService.getCurrentUser().getId());

        // Создаем выпадающий список для категорий
         categoryComboBox= new JComboBox<String>();
         for (Category category : categories) {
             categoryComboBox.addItem(category.getName());
         }

        // Поле для ввода суммы
        sumTextField = new JTextField(20);

        nameTextField = new JTextField(20);

        // Кнопка для создания плана
        JButton createButton = new JButton("Создать");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createPlan();
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

        // Layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(new JLabel("Выберите категорию:"));
        panel.add(categoryComboBox);
        panel.add(new JLabel("Введите сумму:"));
        panel.add(sumTextField);
        panel.add(new JLabel("Введите название:"));
        panel.add(nameTextField);

        panel.add(createButton);
        panel.add(cancelButton);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void createPlan() {
        try {
            // Получаем выбранную категорию и сумму
            String selectedCategory = (String) categoryComboBox.getSelectedItem();
            int categoryId = mainService.getCategoryIdByUserIdAndCategoryName(mainService.getCurrentUser().getId(), selectedCategory);
            Double sum = Double.parseDouble(sumTextField.getText());
            String name = nameTextField.getText();

            // Вызываем метод для создания плана
            mainService.createPlan(mainService.getCurrentUser().getId(), categoryId, sum, name);

            JOptionPane.showMessageDialog(this, "План успешно создан!", "Успех", JOptionPane.INFORMATION_MESSAGE);

            // Закрываем окно после успешного создания плана
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ошибка: Пожалуйста, введите корректную сумму.", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
