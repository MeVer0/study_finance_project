package GUI;

import DB.Models.Category;
import Services.MainService;
import Services.TransactionService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddTransactionDialog extends JDialog {
    private JTextField sumField;
    private JComboBox<String> categoryComboBox;
    private JComboBox<String> transactionTypeComboBox; // Для выбора типа транзакции
    private JButton submitButton;
    private JButton cancelButton;

    public AddTransactionDialog(JFrame parent, MainService mainService) {
        super(parent, "Добавить транзакцию", true);
        setLayout(new GridLayout(4, 2)); // Обновляем сетку на 4 строки
        setSize(300, 250);
        setLocationRelativeTo(parent);

        // Поле для суммы
        add(new JLabel("Сумма:"));
        sumField = new JTextField();
        add(sumField);

        // Поле для категории
        add(new JLabel("Категория:"));
        List<Category> categories = mainService.new CategoryCRUD().getAllTransactionsByUserId();
        categoryComboBox = new JComboBox<>(categories.stream().map(Category::getName).toArray(String[]::new));
        add(categoryComboBox);

        // Поле для типа транзакции (доход/трата)
        add(new JLabel("Тип транзакции:"));
        transactionTypeComboBox = new JComboBox<>(new String[]{"Доход", "Трата"});
        add(transactionTypeComboBox);

        // Кнопки подтверждения и отмены
        submitButton = new JButton("Добавить");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double sum = Double.parseDouble(sumField.getText());
                    String category = (String) categoryComboBox.getSelectedItem();
                    String transactionTypeString = (String) transactionTypeComboBox.getSelectedItem();
                    TransactionService.TransactionType transactionType =
                            transactionTypeString.equals("Доход") ?
                                    TransactionService.TransactionType.INCOME : TransactionService.TransactionType.SPEND;

                    // Находим id категории по имени
                    int categoryId = categories.stream()
                            .filter(c -> c.getName().equals(category))
                            .map(Category::getId)
                            .findFirst()
                            .orElse(0);

                    // Добавляем транзакцию
                    mainService.new TransactionCRUD().createTransaction(categoryId, transactionType, sum);
                    JOptionPane.showMessageDialog(parent, "Транзакция добавлена!");
                    dispose(); // Закрыть диалог
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(parent, "Введите корректную сумму!");
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
