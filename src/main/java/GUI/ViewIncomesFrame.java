package GUI;

import DB.Models.Transaction;
import Services.MainService;
import Services.TransactionService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewIncomesFrame extends JFrame {

    private MainService mainService;

    public ViewIncomesFrame(MainService mainService) {
        this.mainService = mainService;

        setTitle("Просмотр доходов");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Получаем все доходы пользователя
        List<Transaction> incomeTransactions = mainService.getAllIncomeByUserId(mainService.getCurrentUser().getId());

        // Если доходов нет, показываем сообщение
        if (incomeTransactions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "У вас нет доходов.", "Информация", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Создаем таблицу для отображения доходов
        String[] columnNames = {"ID", "Категория", "Сумма", "Дата"};
        Object[][] data = new Object[incomeTransactions.size()][4];

        for (int i = 0; i < incomeTransactions.size(); i++) {
            Transaction transaction = incomeTransactions.get(i);
            // Заполняем данные о доходах в таблицу
            data[i][0] = transaction.getId();
            data[i][1] = transaction.getCategoryId(); // Можно добавить логику для отображения названия категории
            data[i][2] = transaction.getSum();
            data[i][3] = transaction.getDate(); // Предположим, что у транзакции есть поле с датой
        }

        JTable incomeTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(incomeTable);
        add(scrollPane, BorderLayout.CENTER);

        // Кнопка закрытия окна
        JButton closeButton = new JButton("Закрыть");
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);

        setVisible(true);
    }
}
