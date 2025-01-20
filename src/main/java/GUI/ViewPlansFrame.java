package GUI;

import DB.Models.Category;
import DB.Models.Plan;
import Services.MainService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewPlansFrame extends JFrame {

    private MainService mainService;
    private JTable plansTable;
    private DefaultTableModel tableModel;

    public ViewPlansFrame(MainService mainService) {
        this.mainService = mainService;

        setTitle("Просмотр планов");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Получаем все планы пользователя
        List<Plan> plans = mainService.getAllPlanByUserId(mainService.getCurrentUser().getId());

        // Настраиваем модель таблицы
        String[] columnNames = {"ID", "Категория", "План", "Сумма"};
        tableModel = new DefaultTableModel(columnNames, 0);
        plansTable = new JTable(tableModel);

        // Заполняем таблицу данными
        for (Plan plan : plans) {
            Category category = mainService.getCategoryById(plan.getCategoryId());
            String categoryName = category != null ? category.getName() : "Неизвестно";
            Object[] rowData = {
                    plan.getId(),
                    categoryName,
                    plan.getName(),
                    plan.getSum()
            };
            tableModel.addRow(rowData);
        }

        JScrollPane scrollPane = new JScrollPane(plansTable);

        // Кнопка для добавления плана
        JButton addButton = new JButton("Добавить план");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreatePlanFrame(mainService); // Открываем окно для создания плана
                dispose(); // Закрываем текущий фрейм, чтобы обновить таблицу при возврате
            }
        });

        // Кнопка для редактирования плана
        JButton editButton = new JButton("Редактировать");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editPlan();
            }
        });

        // Кнопка для удаления плана
        JButton deleteButton = new JButton("Удалить");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePlan();
            }
        });

        // Кнопка для закрытия окна
        JButton closeButton = new JButton("Закрыть");
        closeButton.addActionListener(e -> dispose());

        // Панель с кнопками
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(closeButton);

        // Добавляем компоненты в фрейм
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void editPlan() {
        int selectedRow = plansTable.getSelectedRow();
        if (selectedRow != -1) {
            int planId = (int) tableModel.getValueAt(selectedRow, 0); // Получаем ID плана
            new EditPlanFrame(mainService, planId); // Открываем окно редактирования
        } else {
            JOptionPane.showMessageDialog(this, "Выберите план для редактирования", "Ошибка", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deletePlan() {
        int selectedRow = plansTable.getSelectedRow();
        if (selectedRow != -1) {
            int planId = (int) tableModel.getValueAt(selectedRow, 0); // Получаем ID плана
            int response = JOptionPane.showConfirmDialog(this, "Вы уверены, что хотите удалить этот план?", "Подтверждение удаления", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                mainService.deletePlan(planId); // Удаляем план через сервис
                tableModel.removeRow(selectedRow); // Удаляем строку из таблицы
                JOptionPane.showMessageDialog(this, "План успешно удален!", "Успех", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Выберите план для удаления", "Ошибка", JOptionPane.WARNING_MESSAGE);
        }
    }
}
