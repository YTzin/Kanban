package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import model.Tarefa;

public class PostItPanel extends JPanel {

    private static final int ARC = 20; // quanto mais, mais arredondado

    public PostItPanel(Tarefa tarefa) {
        setOpaque(false); 
        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(220, 80));
        setPreferredSize(new Dimension(220, 80));

        JLabel titulo = new JLabel(tarefa.getTitulo(), SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        titulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        add(titulo, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(new Color(5, 180, 180)); // cor do post-it
        g2.fill(new RoundRectangle2D.Float(
            0, 0,
            getWidth(),
            getHeight(),
            ARC, ARC
        ));

        g2.dispose();
        super.paintComponent(g);
    }
}
