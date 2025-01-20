package GUI;

import Services.MainService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationFrame extends JFrame {

    private MainService mainService;

    public RegistrationFrame(MainService mainService) {
        this.mainService = mainService;

        setTitle("Регистрация");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel usernameLabel = new JLabel("Логин:");
        JTextField usernameField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Пароль:");
        JPasswordField passwordField = new JPasswordField(15);

        JButton registerButton = new JButton("Зарегистрироваться");
        JButton backButton = new JButton("Назад");

        // Обработчик для кнопки регистрации
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Пожалуйста, заполните все поля!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } else {
                    mainService.registerUser(username, password);
                    JOptionPane.showMessageDialog(null, "Регистрация прошла успешно!");
                    dispose(); // Закрываем окно регистрации
                    new MainFrame(); // Возвращаемся на главную страницу
                }
            }
        });

        // Обработчик для кнопки "Назад"
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Закрываем окно регистрации
                new MainFrame(); // Возвращаемся на главную страницу
            }
        });

        // Настройка компоновки
        setLayout(new GridLayout(3, 2, 10, 10));
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(registerButton);
        add(backButton);

        setVisible(true);
    }
}
