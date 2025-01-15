package GUI;

import DB.Models.Category;
import Services.MainService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CategoriesTableDialog extends JDialog {
    private JTable categoryTable;
    private JButton deleteButton;
    private JButton editButton;

    public CategoriesTableDialog(JFrame parent, MainService mainService) {
        super(parent, "Категории", true);
        setLayout(new BorderLayout());
        setSize(600, 400);
        setLocationRelativeTo(parent);

        // Получаем все категории пользователя
        List<Category> categories = mainService.new CategoryCRUD().getAllTransactionsByUserId();

        // Данные для таблицы
        String[] columnNames = {"ID", "Категория"};
        Object[][] data = new Object[categories.size()][2];
        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);
            data[i][0] = category.getId();
            data[i][1] = category.getName();
        }

        // Создаем таблицу
        categoryTable = new JTable(new DefaultTableModel(data, columnNames));
        JScrollPane scrollPane = new JScrollPane(categoryTable);
        add(scrollPane, BorderLayout.CENTER);

        // Кнопки для удаления и редактирования категорий
        JPanel buttonPanel = new JPanel();
        deleteButton = new JButton("Удалить");
        deleteButton.addActionListener(e -> deleteCategory(mainService));
        buttonPanel.add(deleteButton);

        editButton = new JButton("Редактировать");
        editButton.addActionListener(e -> editCategory(mainService));
        buttonPanel.add(editButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void deleteCategory(MainService mainService) {
        int selectedRow = categoryTable.getSelectedRow();
        if (selectedRow >= 0) {
            int categoryId = (int) categoryTable.getValueAt(selectedRow, 0);
            mainService.new CategoryCRUD().deleteCategory(categoryId);
            JOptionPane.showMessageDialog(this, "Категория удалена!");
            dispose(); // Закрыть окно
        } else {
            JOptionPane.showMessageDialog(this, "Выберите категорию для удаления.");
        }
    }

    private void editCategory(MainService mainService) {
        int selectedRow = categoryTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Логика редактирования категории, если необходимо
        } else {
            JOptionPane.showMessageDialog(this, "Выберите категорию для редактирования.");
        }
    }
}
