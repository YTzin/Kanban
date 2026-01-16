package view;

import javax.swing.*;
import java.awt.*;

public class AdicionarColunaPanel extends JPanel {

    public AdicionarColunaPanel(Runnable onClick) {
        setPreferredSize(new Dimension(260, 120));
        setBackground(new Color(220, 220, 220));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createDashedBorder(Color.GRAY));

        JLabel label = new JLabel("+ Adicionar outra lista", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(Color.DARK_GRAY);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                onClick.run();
            }
        });

        add(label, BorderLayout.CENTER);
    }
}
