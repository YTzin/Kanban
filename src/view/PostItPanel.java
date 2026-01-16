package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import model.Tarefa;

public class PostItPanel extends JPanel {

    private static final int ARC = 20;

    private Tarefa tarefa;

    public PostItPanel(Tarefa tarefa) {
        this.tarefa = tarefa;

        setOpaque(false);
        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(220, 110));
        setPreferredSize(new Dimension(220, 110));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JButton btnExcluir = new JButton("X");
        btnExcluir.setForeground(Color.RED);
        btnExcluir.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnExcluir.setFocusPainted(false);
        btnExcluir.setBorderPainted(false);
        btnExcluir.setContentAreaFilled(false);
        btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnExcluir.addActionListener(e ->
            firePropertyChange("excluirTarefa", null, tarefa)
        );

        topPanel.add(btnExcluir, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        JLabel titulo = new JLabel(tarefa.getTitulo(), SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        titulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        add(titulo, BorderLayout.CENTER);

        JButton detalhesButton = new JButton("Detalhes");
        detalhesButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        detalhesButton.setFocusPainted(false);
        detalhesButton.addActionListener(e -> mostrarDetalhes());
        add(detalhesButton, BorderLayout.SOUTH);
    }

    private void mostrarDetalhes() {
        JDialog dialog = new JDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this),
                "Detalhes da Tarefa",
                true
        );

        dialog.setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        infoPanel.add(new JLabel("Titulo:"));
        JTextField tituloField = new JTextField(tarefa.getTitulo());
        infoPanel.add(tituloField);

        infoPanel.add(new JLabel("Descricao:"));
        JTextArea descricaoArea = new JTextArea(tarefa.getDescricao());
        descricaoArea.setLineWrap(true);
        descricaoArea.setWrapStyleWord(true);
        infoPanel.add(new JScrollPane(descricaoArea));

        infoPanel.add(new JLabel("Prioridade:"));
        JComboBox<String> prioridadeBox =
                new JComboBox<>(new String[]{"Alta", "Media", "Baixa"});
        prioridadeBox.setSelectedItem(tarefa.getPrioridade());
        infoPanel.add(prioridadeBox);

        infoPanel.add(new JLabel("Data de Criacao: " + tarefa.getDataCriacao()));

        dialog.add(infoPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton salvar = new JButton("Salvar");

        // Verificacao para n deixar o titulo ser vazio, estava quebrando o codigo,
        salvar.addActionListener(ev -> {
            String titulo = tituloField.getText().trim();

            if (titulo.isEmpty()) {
                JOptionPane.showMessageDialog(
                        dialog,
                        "O título eh obrigatorio!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Object[] dados = new Object[]{
                    tarefa,
                    titulo,
                    descricaoArea.getText(),
                    prioridadeBox.getSelectedItem()
            };

            firePropertyChange("editarTarefa", null, dados);
            dialog.dispose();
        });

        buttonPanel.add(salvar);

        JButton fechar = new JButton("Fechar");
        fechar.addActionListener(ev -> dialog.dispose());
        buttonPanel.add(fechar);

        JButton esquerda = new JButton("<-");
        esquerda.setFont(new Font("Segoe UI", Font.BOLD, 14));
        esquerda.addActionListener(ev -> {
            firePropertyChange("moverTarefaAnterior", null, tarefa);
            dialog.dispose();
        });

        JButton direita = new JButton("->");
        direita.setFont(new Font("Segoe UI", Font.BOLD, 14));
        direita.addActionListener(ev -> {
            firePropertyChange("moverTarefaProximo", null, tarefa);
            dialog.dispose();
        });

        buttonPanel.add(esquerda);
        buttonPanel.add(direita);

        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(new Color(5, 180, 180));
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
