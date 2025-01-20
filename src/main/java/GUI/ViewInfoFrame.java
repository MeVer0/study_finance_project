package GUI;

import DB.Models.Transaction;
import DB.Models.Plan;
import DB.Models.Category;
import Services.MainService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class ViewInfoFrame extends JFrame {

    private MainService mainService;
    private JTable infoTable;
    private DefaultTableModel tableModel;

    public ViewInfoFrame(MainService mainService) {
        this.mainService = mainService;

        setTitle("Общая информация");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Получаем данные
        List<Category> categories = mainService.getAllCategoryByUserId(mainService.getCurrentUser().getId());
        List<Plan> plans = mainService.getAllPlanByUserId(mainService.getCurrentUser().getId());
        List<Transaction> incomeTransactions = mainService.getAllIncomeByUserId(mainService.getCurrentUser().getId());
        List<Transaction> spendTransactions = mainService.getAllSpendsByUserId(mainService.getCurrentUser().getId());

        // Подсчитываем сумму доходов и расходов для каждой категории
        Map<Integer, Double> incomeByCategory = mainService.calculateSumByCategory(incomeTransactions);
        Map<Integer, Double> spendByCategory = mainService.calculateSumByCategory(spendTransactions);

        // Настраиваем модель таблицы
        String[] columnNames = {"Категория", "План", "Сумма доходов", "Сумма расходов", "Разница (план - расходы)", "Разница (план - расходы + доходы)"};
        tableModel = new DefaultTableModel(columnNames, 0);
        infoTable = new JTable(tableModel);

        // Заполняем таблицу данными
        for (Category category : categories) {
            int categoryId = category.getId();
            String categoryName = category.getName();

            double incomeSum = incomeByCategory.getOrDefault(categoryId, 0.0);
            double spendSum = spendByCategory.getOrDefault(categoryId, 0.0);

            double planSum = plans.stream()
                    .filter(plan -> plan.getCategoryId() == categoryId)
                    .mapToDouble(Plan::getSum)
                    .sum();

            double difference = planSum - spendSum;
            double adjustedDifference = planSum - spendSum + incomeSum;

            Object[] rowData = {
                    categoryName,
                    planSum,
                    incomeSum,
                    spendSum,
                    difference,
                    adjustedDifference
            };

            tableModel.addRow(rowData);
        }

        // Устанавливаем кастомный рендерер для изменения цвета строки
        infoTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                double adjustedDifference = (double) tableModel.getValueAt(row, 5);
                if (adjustedDifference < 0) {
                    c.setBackground(new Color(255, 0, 0, 128)); // Полупрозрачный красный
                } else {
                    c.setBackground(Color.WHITE); // Обычный белый фон
                }

                if (isSelected) {
                    c.setBackground(new Color(173, 216, 230)); // Светло-голубой фон для выделенных строк
                }

                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(infoTable);

        // Кнопка закрытия окна
        JButton closeButton = new JButton("Закрыть");
        closeButton.addActionListener(e -> dispose());

        // Настройка компоновки
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(closeButton, BorderLayout.SOUTH);

        setVisible(true);
    }
}
