package com.aquarium;

import com.aquarium.ui.AquariumFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Запускаємо графічний інтерфейс у безпечному для Swing потоці
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AquariumFrame frame = new AquariumFrame();
                frame.setVisible(true); // Робимо вікно видимим
            }
        });
    }
}