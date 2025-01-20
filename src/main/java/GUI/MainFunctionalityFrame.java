package GUI;

import Services.MainService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFunctionalityFrame extends JFrame {

    private MainService mainService;

    public MainFunctionalityFrame(MainService mainService) {
        this.mainService = mainService;

        setTitle("Основное окно");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Кнопки для работы с транзакциями
        JButton createTransactionButton = new JButton("Создать транзакцию");
        JButton viewTransactionsButton = new JButton("Просмотреть транзакции");
        JButton viewIncomesButton = new JButton("Посмотреть доходы");
        JButton viewSpendsButton = new JButton("Посмотреть траты");
        JButton viewInfoButton = new JButton("Посмотреть общую информацию");

        // Кнопки для работы с планами
        JButton createPlanButton = new JButton("Создать план");
        JButton viewPlansButton = new JButton("Посмотреть планы");
        JButton updatePlanButton = new JButton("Редактировать план");
        JButton deletePlanButton = new JButton("Удалить план");

        // Кнопки для работы с категориями
        JButton createCategoryButton = new JButton("Создать категорию");
        JButton viewCategoriesButton = new JButton("Посмотреть категории");

        // Кнопка "Выйти из профиля"
        JButton logoutButton = new JButton("Выйти из профиля");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainService.logout(); // Завершаем сессию пользователя
                JOptionPane.showMessageDialog(null, "Вы успешно вышли из профиля.");
                dispose(); // Закрываем текущее окно
                new MainFrame(); // Возвращаемся на главное окно
            }
        });

        // Обработчики событий для кнопок

        createTransactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateTransactionFrame(mainService); // Создание транзакции
            }
        });

        viewTransactionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewTransactionsFrame(mainService); // Просмотр транзакций
            }
        });

        viewIncomesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewIncomesFrame(mainService); // Просмотр доходов
            }
        });

        viewSpendsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewSpendsFrame(mainService); // Просмотр трат
            }
        });

        viewInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewInfoFrame(mainService); // Общая информация
            }
        });

        createPlanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreatePlanFrame(mainService); // Создание плана
            }
        });

        viewPlansButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewPlansFrame(mainService); // Просмотр планов
            }
        });

//        updatePlanButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new UpdatePlanFrame(mainService); // Редактировать план
//            }
//        });

        deletePlanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeletePlanFrame(mainService); // Удалить план
            }
        });

        createCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateCategoryFrame(mainService); // Создание категории
            }
        });

        viewCategoriesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewCategoriesFrame(mainService); // Просмотр категорий
            }
        });



        // Настройка компоновки кнопок
        setLayout(new GridLayout(8, 2)); // Используем 8 строк и 2 столбца для кнопок
        add(viewTransactionsButton);
        add(viewPlansButton);
        add(viewCategoriesButton);
        add(logoutButton);
        setVisible(true);
    }
}
