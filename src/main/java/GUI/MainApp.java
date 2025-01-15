import DB.Models.Category;
import DB.Models.Transaction;
import GUI.AddCategoryDialog;
import GUI.AddTransactionDialog;
import Services.MainService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainApp {
    private MainService mainService;
    private JFrame frame;
    private JPanel panel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel statusLabel;
    private JButton registerButton, loginButton, logoutButton;

    public MainApp() throws Exception {
        mainService = new MainService();
        frame = new JFrame("My Finance App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));

        usernameField = new JTextField();
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);

        passwordField = new JPasswordField();
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        registerButton = new JButton("Регистрация");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                mainService.new RegistrationNLogin().registerUser(username, password);
                statusLabel.setText("Регистрация успешна");
            }
        });
        panel.add(registerButton);

        loginButton = new JButton("Логин");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                try {
                    mainService.new RegistrationNLogin().login(username, password);
                    statusLabel.setText("Логин успешен");
                    showMainButtons();
                } catch (Exception ex) {
                    statusLabel.setText("Ошибка логина: " + ex.getMessage());
                }
            }
        });
        panel.add(loginButton);

        logoutButton = new JButton("Выход");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainService.new RegistrationNLogin().logout();
                statusLabel.setText("Выход выполнен");
                showLoginScreen();
            }
        });
        logoutButton.setVisible(false);
        panel.add(logoutButton);

        statusLabel = new JLabel("");
        panel.add(statusLabel);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void showMainButtons() {
        panel.removeAll();

        JButton transactionsButton = new JButton("Показать историю транзакций");
        transactionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTransactionsHistory();
            }
        });
        panel.add(transactionsButton);

        JButton categoriesButton = new JButton("Показать категории");
        categoriesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCategories();
            }
        });
        panel.add(categoriesButton);

        JButton addTransactionButton = new JButton("Добавить транзакцию");
        addTransactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddTransactionDialog(frame, mainService).setVisible(true);
            }
        });
        panel.add(addTransactionButton);

        JButton addCategoryButton = new JButton("Добавить категорию");
        addCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddCategoryDialog(frame, mainService).setVisible(true);
            }
        });
        panel.add(addCategoryButton);

        logoutButton.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }

    private void showTransactionsHistory() {
        // Получаем список транзакций пользователя
        List<Transaction> transactions = mainService.new TransactionCRUD().getAllTransactionsByUserId();
        String[] columnNames = {"ID", "Дата", "Сумма", "Категория"};
        Object[][] data = new Object[transactions.size()][4];

        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            data[i][0] = transaction.getId();
            data[i][1] = transaction.getDate();
            data[i][2] = transaction.getSum();
            // Используем CategoryCRUD для получения названия категории по ID
            String categoryName = mainService.new CategoryCRUD().getAllTransactionsByUserId().stream()
                    .filter(c -> c.getId() == transaction.getCategoryId())
                    .map(Category::getName)
                    .findFirst()
                    .orElse("Неизвестно");
            data[i][3] = categoryName;
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        JOptionPane.showMessageDialog(frame, scrollPane, "История транзакций", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showCategories() {
        // Получаем список категорий пользователя
        List<Category> categories = mainService.new CategoryCRUD().getAllTransactionsByUserId();
        String[] categoryNames = categories.stream().map(Category::getName).toArray(String[]::new);
        JOptionPane.showMessageDialog(frame, categoryNames, "Категории", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showLoginScreen() {
        panel.removeAll();

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        panel.add(registerButton);
        panel.add(loginButton);

        logoutButton.setVisible(false);

        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new MainApp();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
