package GUI;

import DB.Models.Category;
import Services.MainService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewCategoriesFrame extends JFrame {

    private MainService mainService;
    private JTable categoriesTable;
    private DefaultTableModel tableModel;

    public ViewCategoriesFrame(MainService mainService) {
        this.mainService = mainService;

        setTitle("Список категорий");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Получаем все категории пользователя
        List<Category> categories = mainService.getAllCategoryByUserId(mainService.getCurrentUser().getId());

        // Настраиваем модель таблицы
        String[] columnNames = {"Категория"};
        tableModel = new DefaultTableModel(columnNames, 0);
        categoriesTable = new JTable(tableModel);

        // Заполняем таблицу данными
        for (Category category : categories) {
            Object[] rowData = {
                    category.getName()
            };
            tableModel.addRow(rowData);
        }

        JScrollPane scrollPane = new JScrollPane(categoriesTable);

        // Кнопка для добавления категории
        JButton addButton = new JButton("Добавить категорию");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateCategoryFrame(mainService); // Открываем окно для добавления категории
                dispose(); // Закрываем текущий фрейм, чтобы обновить таблицу при возврате
            }
        });

        // Кнопка для редактирования категории
        JButton editButton = new JButton("Редактировать");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editCategory();
            }
        });

        // Кнопка для удаления категории
        JButton deleteButton = new JButton("Удалить");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCategory();
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

    private void editCategory() {
        int selectedRow = categoriesTable.getSelectedRow();
        if (selectedRow != -1) {
            String categoryName = (String) tableModel.getValueAt(selectedRow, 0); // Получаем имя категории
            int categoryId = mainService.getCategoryIdByUserIdAndCategoryName(mainService.currentUser.getId(), categoryName); // Предполагается метод для получения ID категории
            new EditCategoryFrame(mainService, categoryId); // Открываем окно редактирования
            dispose(); // Закрываем текущий фрейм
        } else {
            JOptionPane.showMessageDialog(this, "Выберите категорию для редактирования", "Ошибка", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteCategory() {
        int selectedRow = categoriesTable.getSelectedRow();
        if (selectedRow != -1) {
            String categoryName = (String) tableModel.getValueAt(selectedRow, 0); // Получаем имя категории
            int categoryId = mainService.getCategoryIdByUserIdAndCategoryName(mainService.currentUser.getId(), categoryName); // Предполагается метод для получения ID категории
            int response = JOptionPane.showConfirmDialog(this, "Вы уверены, что хотите удалить эту категорию?", "Подтверждение удаления", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                mainService.deleteCategory(categoryId); // Удаляем категорию через сервис
                tableModel.removeRow(selectedRow); // Удаляем строку из таблицы
                JOptionPane.showMessageDialog(this, "Категория успешно удалена!", "Успех", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Выберите категорию для удаления", "Ошибка", JOptionPane.WARNING_MESSAGE);
        }
    }
}
