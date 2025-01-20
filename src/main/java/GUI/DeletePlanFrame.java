package GUI;

import Services.MainService;
import DB.Models.Plan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DeletePlanFrame extends JFrame {

    private MainService mainService;

    public DeletePlanFrame(MainService mainService) {
        this.mainService = mainService;

        setTitle("Удаление плана");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Метка для выбора плана
        JLabel selectPlanLabel = new JLabel("Выберите план для удаления:");

        // Выпадающий список с планами
        JComboBox<String> planComboBox = new JComboBox<>();
        List<Plan> plans = mainService.getAllPlanByUserId(mainService.getCurrentUser().getId());

        if (plans.isEmpty()) {
            planComboBox.addItem("Нет доступных планов");
            planComboBox.setEnabled(false);
        } else {
            for (Plan plan : plans) {
                planComboBox.addItem(plan.getName()); // Добавляем план в выпадающий список
            }
        }

        // Кнопка для удаления плана
        JButton deleteButton = new JButton("Удалить");

        // Обработчик для кнопки удаления
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedPlanName = (String) planComboBox.getSelectedItem();
                if ("Нет доступных планов".equals(selectedPlanName)) {
                    JOptionPane.showMessageDialog(null, "Нет доступных планов для удаления.");
                    return;
                }

                int planId = mainService.getPlanIdByUserIdAndPlanName(mainService.getCurrentUser().getId(), selectedPlanName);

                mainService.deletePlan(planId);
                JOptionPane.showMessageDialog(null, "План успешно удален.");
                dispose(); // Закрыть окно
            }
        });

        // Компоновка компонентов
        setLayout(new FlowLayout());
        add(selectPlanLabel);
        add(planComboBox);
        add(deleteButton);

        setVisible(true);
    }
}
