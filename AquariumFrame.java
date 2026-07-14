package com.aquarium.ui;

import com.aquarium.model.Fish;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AquariumFrame extends JFrame {
    // Поля для введення розмірів
    private JTextField lengthField, widthField, heightField, countField;
    private JComboBox<Fish> fishComboBox;
    private JTextArea resultArea;

    public AquariumFrame() {
        // Налаштування самого вікна
        setTitle("Aquarium Helper / Помічник Акваріуміста");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // по центру екрана
        setLayout(new BorderLayout(10, 10));

        // Панель введення даних (форма)
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Довжина акваріума (см):"));
        lengthField = new JTextField("50");
        inputPanel.add(lengthField);

        inputPanel.add(new JLabel("Ширина акваріума (см):"));
        widthField = new JTextField("40");
        inputPanel.add(widthField);

        inputPanel.add(new JLabel("Висота акваріума (см):"));
        heightField = new JTextField("50");
        inputPanel.add(heightField);

        inputPanel.add(new JLabel("Оберіть вид рибки:"));
        // Додаємо кілька базових видів рибок для вибору
        Fish[] fishTypes = {
                new Fish("Tetra (Neon)", 2.0, "Peaceful"),
                new Fish("Ancistrus (Catfish)", 15.0, "Peaceful"),
                new Fish("Barbus", 5.0, "Semi-aggressive"),
                new Fish("Cichlid", 40.0, "Aggressive")
        };
        fishComboBox = new JComboBox<>(fishTypes);
        inputPanel.add(fishComboBox);

        inputPanel.add(new JLabel("Кількість рибок (шт):"));
        countField = new JTextField("10");
        inputPanel.add(countField);

        // Кнопка розрахунку
        JButton calculateButton = new JButton("Розрахувати сумісність та об'єм");
        inputPanel.add(calculateButton);

        add(inputPanel, BorderLayout.NORTH);

        // Область для виведення результату
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Логіка кнопки розрахунку
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processCalculation();
            }
        });
    }

    private void processCalculation() {
        try {
            // Зчитуємо габарити
            double length = Double.parseDouble(lengthField.getText());
            double width = Double.parseDouble(widthField.getText());
            double height = Double.parseDouble(heightField.getText());
            int fishCount = Integer.parseInt(countField.getText());
            Fish selectedFish = (Fish) fishComboBox.getSelectedItem();

            // Рахуємо літраж: (Д * Ш * В) / 1000.
            // Множимо на 0.9, бо вода зазвичай не заливається до самого верху (мінус 10%)
            double grossVolume = (length * width * height) / 1000.0;
            double netVolume = grossVolume * 0.9;

            // Рахуємо, скільки літрів потрібно для вказаної кількості рибок
            double totalRequiredVolume = selectedFish.getRequiredLiters() * fishCount;

            // Формуємо вивід результату
            StringBuilder result = new StringBuilder();
            result.append(String.format("Повний об'єм: %.1f л%n", grossVolume));
            result.append(String.format("Чистий об'єм води (90%%): %.1f л%n", netVolume));
            result.append(String.format("Для %d шт. %s потрібно: %.1f л%n", fishCount, selectedFish.getName(), totalRequiredVolume));
            result.append("---------------------------------------------\n");

            // Перевіряємо, чи вистачить рибкам місця
            if (netVolume >= totalRequiredVolume) {
                result.append(" СТАТУС: Об'єму акваріума ДОСТАТНЬО!\n");
            } else {
                result.append(" УВАГА: Перенаселення! Потрібно менше рибок\n або більший акваріум.\n");
            }

            // Додаємо пораду щодо сумісності типів риб
            result.append(" Порада по сумісності: ");
            if (selectedFish.getType().equals("Aggressive")) {
                result.append("Хижа риба. Не рекомендується селити з іншими мирними видами.");
            } else {
                result.append("Мирна риба. Добре уживається з іншими тетрами та сомиками.");
            }

            resultArea.setText(result.toString());

        } catch (NumberFormatException ex) {
            resultArea.setText("Помилка: будь ласка, вводьте лише числа у поля габаритів та кількості!");
        }
    }
}