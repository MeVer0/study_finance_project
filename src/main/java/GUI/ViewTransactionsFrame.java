package GUI;

import DB.Models.Transaction;
import Services.MainService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewTransactionsFrame extends JFrame {

    private MainService mainService;
    private JTable transactionsTable;
    private DefaultTableModel tableModel;

    public ViewTransactionsFrame(MainService mainService) {
        this.mainService = mainService;

        setTitle("Просмотр транзакций");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Получаем все транзакции для текущего пользователя
        List<Transaction> transactions = mainService.getAllTransactionsByUserId(mainService.getCurrentUser().getId());

        // Настраиваем модель таблицы
        String[] columnNames = {"ID", "Категория", "Тип транзакции", "Сумма", "Дата"};
        tableModel = new DefaultTableModel(columnNames, 0);
        transactionsTable = new JTable(tableModel);

        // Заполняем таблицу данными
        for (Transaction transaction : transactions) {
            String categoryName = mainService.getCategoryNameByUserIdAndCatId(
                    mainService.getCurrentUser().getId(),
                    transaction.getCategoryId()
            );
            Object[] rowData = {
                    transaction.getId(),
                    categoryName,
                    transaction.getType().getDescription(),
                    transaction.getSum(),
                    transaction.getDate().toString()
            };
            tableModel.addRow(rowData);
        }

        JScrollPane scrollPane = new JScrollPane(transactionsTable);

        // Кнопка для создания транзакции
        JButton createButton = new JButton("Создать транзакцию");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateTransactionFrame(mainService); // Открываем окно создания транзакции
                dispose(); // Закрываем текущий фрейм, чтобы обновить таблицу при возврате
            }
        });

        // Кнопка для редактирования транзакции
        JButton editButton = new JButton("Редактировать");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editTransaction();
            }
        });

        // Кнопка для удаления транзакции
        JButton deleteButton = new JButton("Удалить");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTransaction();
            }
        });

        // Кнопка для закрытия окна
        JButton closeButton = new JButton("Закрыть");
        closeButton.addActionListener(e -> dispose());

        // Дополнительные кнопки
        JButton showIncomesButton = new JButton("Показать доходы");
        showIncomesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewIncomesFrame(mainService); // Показать доходы
                dispose();
            }
        });

        JButton showExpensesButton = new JButton("Показать расходы");
        showExpensesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewSpendsFrame(mainService); // Показать расходы
                dispose();
            }
        });

        JButton showInfoButton = new JButton("Показать общую информацию");
        showInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewInfoFrame(mainService); // Показать общую информацию
                dispose();
            }
        });

        // Панель с кнопками
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(closeButton);

        JPanel additionalButtonPanel = new JPanel();
        additionalButtonPanel.add(showIncomesButton);
        additionalButtonPanel.add(showExpensesButton);
        additionalButtonPanel.add(showInfoButton);

        // Добавляем компоненты в фрейм
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(additionalButtonPanel, BorderLayout.NORTH);

        setVisible(true);
    }

    private void editTransaction() {
        int selectedRow = transactionsTable.getSelectedRow();
        if (selectedRow != -1) {
            int transactionId = (int) tableModel.getValueAt(selectedRow, 0); // Получаем ID транзакции
            new EditTransactionFrame(mainService, transactionId); // Открываем окно редактирования транзакции
            dispose(); // Закрываем текущий фрейм
        } else {
            JOptionPane.showMessageDialog(this, "Выберите транзакцию для редактирования", "Ошибка", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteTransaction() {
        int selectedRow = transactionsTable.getSelectedRow();
        if (selectedRow != -1) {
            int transactionId = (int) tableModel.getValueAt(selectedRow, 0); // Получаем ID транзакции
            int response = JOptionPane.showConfirmDialog(this, "Вы уверены, что хотите удалить эту транзакцию?", "Подтверждение удаления", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                mainService.deleteTransaction(transactionId); // Удаляем транзакцию через сервис
                tableModel.removeRow(selectedRow); // Удаляем строку из таблицы
                JOptionPane.showMessageDialog(this, "Транзакция успешно удалена!", "Успех", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Выберите транзакцию для удаления", "Ошибка", JOptionPane.WARNING_MESSAGE);
        }
    }
}
