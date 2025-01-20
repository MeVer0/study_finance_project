package GUI;

import DB.Models.Transaction;
import DB.Models.Category;
import Services.MainService;
import Services.TransactionService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EditTransactionFrame extends JFrame {

    private MainService mainService;
    private int transactionId;

    private JComboBox<String> categoryComboBox;
    private JComboBox<String> transactionTypeComboBox;
    private JTextField sumTextField;

    public EditTransactionFrame(MainService mainService, int transactionId) {
        this.mainService = mainService;
        this.transactionId = transactionId;

        setTitle("Редактировать транзакцию");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Получаем транзакцию для редактирования
        Transaction transaction = mainService.getTransactionById(transactionId);

        if (transaction == null) {
            JOptionPane.showMessageDialog(this, "Транзакция не найдена!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        // Получаем категории для выпадающего списка
        List<Category> categories = mainService.getAllCategoryByUserId(mainService.getCurrentUser().getId());

        JLabel categoryLabel = new JLabel("Выберите категорию:");
        categoryComboBox = new JComboBox<>();
        for (Category category : categories) {
            categoryComboBox.addItem(category.getName());
        }

        // Устанавливаем текущую категорию транзакции
        Category currentCategory = mainService.getCategoryById(transaction.getCategoryId());
        if (currentCategory != null) {
            categoryComboBox.setSelectedItem(currentCategory.getName());
        }

        JLabel transactionTypeLabel = new JLabel("Выберите тип транзакции:");
        transactionTypeComboBox = new JComboBox<>();
        transactionTypeComboBox.addItem("Доход");
        transactionTypeComboBox.addItem("Трата");

        // Устанавливаем текущий тип транзакции
        transactionTypeComboBox.setSelectedItem(transaction.getType());

        JLabel sumLabel = new JLabel("Введите сумму:");
        sumTextField = new JTextField(String.valueOf(transaction.getSum()), 20);

        JButton updateButton = new JButton("Обновить");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTransaction();
            }
        });

        JButton cancelButton = new JButton("Отмена");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Закрыть окно
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.add(categoryLabel);
        panel.add(categoryComboBox);
        panel.add(transactionTypeLabel);
        panel.add(transactionTypeComboBox);
        panel.add(sumLabel);
        panel.add(sumTextField);
        panel.add(updateButton);
        panel.add(cancelButton);

        add(panel);
        setVisible(true);
    }

    private void updateTransaction() {
        try {
            // Получаем новые данные
            String selectedCategoryName = (String) categoryComboBox.getSelectedItem();
            String selectedTransactionType = (String) transactionTypeComboBox.getSelectedItem();
            double newSum = Double.parseDouble(sumTextField.getText());

            int categoryId = mainService.getCategoryIdByUserIdAndCategoryName(mainService.getCurrentUser().getId(), selectedCategoryName);
            TransactionService.TransactionType transactionType = selectedTransactionType.equals("Доход")
                    ? TransactionService.TransactionType.INCOME
                    : TransactionService.TransactionType.SPEND;

            try {
                mainService.updateTransaction(transactionId, categoryId, transactionType, newSum);
                JOptionPane.showMessageDialog(this, "Транзакция успешно обновлена!", "Успех", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Закрыть окно после успешного обновления
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Ошибка при обновлении транзакции!", "Ошибка", JOptionPane.ERROR_MESSAGE);

            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ошибка: Пожалуйста, введите корректную сумму.", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
