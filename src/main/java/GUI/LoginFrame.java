package GUI;

import Services.MainService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    private MainService mainService;

    public LoginFrame(MainService mainService) {
        this.mainService = mainService;

        setTitle("Вход");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel usernameLabel = new JLabel("Логин:");
        JTextField usernameField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Пароль:");
        JPasswordField passwordField = new JPasswordField(15);

        JButton loginButton = new JButton("Войти");
        JButton backButton = new JButton("Назад");

        // Обработчик кнопки "Войти"
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                try {
                    mainService.login(username, password);
                    JOptionPane.showMessageDialog(null, "Вход успешен!");
                    dispose(); // Закрываем окно логина
                    new MainFunctionalityFrame(mainService); // Открываем главное окно с функционалом
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ошибка входа: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Обработчик кнопки "Назад"
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Закрываем окно логина
                new MainFrame(); // Возвращаемся на главную страницу
            }
        });

        // Настройка компоновки
        setLayout(new GridLayout(3, 2, 10, 10));
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        add(backButton);

        setVisible(true);
    }
}
