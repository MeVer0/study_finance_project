package GUI;

import DB.Models.Category;
import Services.MainService;
import Services.TransactionService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CreateTransactionFrame extends JFrame {

    private MainService mainService;

    public CreateTransactionFrame(MainService mainService) {
        this.mainService = mainService;

        setTitle("Создание транзакции");
        setSize(300, 250);  // Увеличим размер окна для комфортного размещения всех компонентов
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- Категории ---
        JLabel categoryLabel = new JLabel("Выберите категорию:");
        JComboBox<String> categoryComboBox = new JComboBox<>();
        List<Category> categories = mainService.getAllCategoryByUserId(mainService.getCurrentUser().getId());

        if (categories.isEmpty()) {
            categoryComboBox.addItem("Нет доступных категорий");
            categoryComboBox.setEnabled(false);
        } else {
            for (Category category : categories) {
                categoryComboBox.addItem(category.getName());
            }
        }

        // --- Тип транзакции ---
        JLabel transactionTypeLabel = new JLabel("Выберите тип транзакции:");
        JComboBox<String> transactionTypeComboBox = new JComboBox<>();
        transactionTypeComboBox.addItem(TransactionService.TransactionType.INCOME.getDescription());
        transactionTypeComboBox.addItem(TransactionService.TransactionType.SPEND.getDescription());

        // --- Сумма ---
        JLabel sumLabel = new JLabel("Сумма:");
        JTextField sumField = new JTextField(15);

        // --- Кнопка "Создать" ---
        JButton createButton = new JButton("Создать");
        createButton.setEnabled(!categories.isEmpty());  // Включаем кнопку, если есть категории

        // --- Слушатель изменения выбранной категории ---
        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Если выбрана категория "Нет доступных категорий", отключаем кнопку
                if ("Нет доступных категорий".equals(categoryComboBox.getSelectedItem())) {
                    createButton.setEnabled(false);
                } else {
                    // Включаем кнопку, если выбрана валидная категория
                    createButton.setEnabled(true);
                }
            }
        });

        // --- Обработчик кнопки "Создать" ---
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String categoryName = (String) categoryComboBox.getSelectedItem();
                String transactionTypeName = (String) transactionTypeComboBox.getSelectedItem();

                // Преобразуем строку в TransactionType
                TransactionService.TransactionType transactionType =
                        transactionTypeName.equals(TransactionService.TransactionType.INCOME.getDescription()) ?
                                TransactionService.TransactionType.INCOME :
                                TransactionService.TransactionType.SPEND;

                // Получаем ID категории
                int categoryId = mainService.getCategoryIdByUserIdAndCategoryName(mainService.getCurrentUser().getId(), categoryName);

                try {
                    // Преобразуем строку в число
                    Double sum = Double.parseDouble(sumField.getText());

                    // Создаем транзакцию
                    mainService.createTransaction(categoryId, transactionType, sum);

                    // Уведомление об успешном создании
                    JOptionPane.showMessageDialog(null, "Транзакция успешно создана!");

                    // Закрываем окно
                    dispose();
                } catch (NumberFormatException ex) {
                    // Обрабатываем случай, когда сумма введена некорректно
                    JOptionPane.showMessageDialog(null, "Пожалуйста, введите правильную сумму!");
                }
            }
        });

        // --- Расположение компонентов ---
        setLayout(new FlowLayout());

        add(categoryLabel);
        add(categoryComboBox);
        add(transactionTypeLabel);
        add(transactionTypeComboBox);
        add(sumLabel);
        add(sumField);
        add(createButton);

        setVisible(true);
    }
}
