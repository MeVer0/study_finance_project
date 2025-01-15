package GUI;

import DB.Models.Transaction;
import DB.Models.Category;
import Services.CategoryService;
import Services.MainService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TransactionsTableDialog extends JDialog {
    private JTable transactionTable;
    private JButton deleteButton;
    private JButton editButton;

    public TransactionsTableDialog(JFrame parent, MainService mainService) throws Exception {
        super(parent, "История транзакций", true);
        setLayout(new BorderLayout());
        setSize(600, 400);
        setLocationRelativeTo(parent);

        // Получаем все транзакции пользователя
        List<Transaction> transactions = mainService.new TransactionCRUD().getAllTransactionsByUserId();

        // Получаем CategoryService для получения имени категории по id
        CategoryService categoryService = new CategoryService();

        // Данные для таблицы
        String[] columnNames = {"ID", "Категория", "Тип", "Сумма"};
        Object[][] data = new Object[transactions.size()][4];
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);

            // Получаем категорию по id из транзакции
            Category category = categoryService.getCategoryById(transaction.getCategoryId());
            String categoryName = category != null ? category.getName() : "Неизвестная категория";

            // Заполняем данные для таблицы
            data[i][0] = transaction.getId();
            data[i][1] = categoryName;  // Имя категории
            data[i][2] = transaction.getTransactionType().getDescription();
            data[i][3] = transaction.getSum();
        }

        // Создаем таблицу
        transactionTable = new JTable(new DefaultTableModel(data, columnNames));
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        add(scrollPane, BorderLayout.CENTER);

        // Кнопки для удаления и редактирования транзакции
        JPanel buttonPanel = new JPanel();
        deleteButton = new JButton("Удалить");
        deleteButton.addActionListener(e -> deleteTransaction(mainService));
        buttonPanel.add(deleteButton);

        editButton = new JButton("Редактировать");
        editButton.addActionListener(e -> editTransaction(mainService));
        buttonPanel.add(editButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void deleteTransaction(MainService mainService) {
        int selectedRow = transactionTable.getSelectedRow();
        if (selectedRow >= 0) {
            int transactionId = (int) transactionTable.getValueAt(selectedRow, 0);
            mainService.new TransactionCRUD().deleteTransaction(transactionId);
            JOptionPane.showMessageDialog(this, "Транзакция удалена!");
            dispose(); // Закрыть окно
        } else {
            JOptionPane.showMessageDialog(this, "Выберите транзакцию для удаления.");
        }
    }

    private void editTransaction(MainService mainService) {
        int selectedRow = transactionTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Логика редактирования транзакции, если необходимо
        } else {
            JOptionPane.showMessageDialog(this, "Выберите транзакцию для редактирования.");
        }
    }
}
