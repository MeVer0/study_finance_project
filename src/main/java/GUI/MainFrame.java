package GUI;

import Services.MainService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private MainService mainService;

    public MainFrame() {
        setTitle("Главная страница");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainService = new MainService(); // Сервис для работы с пользователями

        JButton loginButton = new JButton("Войти");
        JButton registerButton = new JButton("Зарегистрироваться");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginFrame(mainService); // Открываем окно логина
                setVisible(false); // Закрываем главное окно
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistrationFrame(mainService); // Открываем окно регистрации
                setVisible(false); // Закрываем главное окно
            }
        });

        setLayout(new FlowLayout());
        add(loginButton);
        add(registerButton);

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
