package GUI;

import DB.Models.Transaction;
import Services.MainService;
import Services.TransactionService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewSpendsFrame extends JFrame {

    private MainService mainService;

    public ViewSpendsFrame(MainService mainService) {
        this.mainService = mainService;

        setTitle("Просмотр расходов");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Получаем все расходы пользователя
        List<Transaction> spendTransactions = mainService.getAllSpendsByUserId(mainService.getCurrentUser().getId());

        // Если расходов нет, показываем сообщение
        if (spendTransactions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "У вас нет расходов.", "Информация", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Создаем таблицу для отображения расходов
        String[] columnNames = {"ID", "Категория", "Сумма", "Дата"};
        Object[][] data = new Object[spendTransactions.size()][4];

        for (int i = 0; i < spendTransactions.size(); i++) {
            Transaction transaction = spendTransactions.get(i);
            // Заполняем данные о расходах в таблицу
            data[i][0] = transaction.getId();
            data[i][1] = transaction.getCategoryId(); // Можно добавить логику для отображения названия категории
            data[i][2] = transaction.getSum();
            data[i][3] = transaction.getDate(); // Предположим, что у транзакции есть поле с датой
        }

        JTable spendTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(spendTable);
        add(scrollPane, BorderLayout.CENTER);

        // Кнопка закрытия окна
        JButton closeButton = new JButton("Закрыть");
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);

        setVisible(true);
    }
}
