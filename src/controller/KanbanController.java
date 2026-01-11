package controller;

import model.Coluna;
import model.KanbanBoard;
import model.Tarefa;
import view.KanbanView;

import javax.swing.*;
import java.awt.*;

public class KanbanController {

    private KanbanBoard board;
    private KanbanView view;

    public KanbanController(KanbanBoard board, KanbanView view) {
        this.board = board;
        this.view = view;

        // Listener para adicionar nova coluna
        view.addPropertyChangeListener("adicionarColuna", evt -> {
            Object[] dados = (Object[]) evt.getNewValue();
            String nome = (String) dados[0];
            Color cor = (Color) dados[1];

            // CANCELAR ou X, acho q resolvi não estava verificando NULL
            if (nome == null) {
                return;
            }

            nome = nome.trim();

            // Nome vazio
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(
                        view,
                        "O nome da coluna eh obrigatorio!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            board.adicionarColuna(new Coluna(nome, cor));
            view.atualizarColunas(board.getColunas());
        });

        view.addPropertyChangeListener("excluirColuna", evt -> {
            int colunaIndex = (int) evt.getNewValue();

            int opcao = JOptionPane.showConfirmDialog(
                    view,
                    "Tem certeza que deseja excluir esta coluna?",
                    "Confirmar exclusao",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcao == JOptionPane.YES_OPTION) {
                board.removerColuna(colunaIndex);
                view.atualizarColunas(board.getColunas());
            }
        });

        view.addPropertyChangeListener("editarColuna", evt -> {
            int colunaIndex = (int) evt.getNewValue();
            editarColuna(colunaIndex);
        });

        // Listener para adicionar post-it (nova tarefa)
        view.addPropertyChangeListener("adicionarPostIt", evt -> {
            int colunaIndex = (int) evt.getNewValue(); // qual coluna clicou
            criarNovaTarefa(colunaIndex);
        });

        inicializar();
    }

    private void inicializar() {
        board.adicionarColuna(new Coluna("A Fazer", new Color(220, 53, 69)));
        board.adicionarColuna(new Coluna("Em Progresso", new Color(255, 193, 7)));
        board.adicionarColuna(new Coluna("Concluido", new Color(40, 167, 69)));

        view.atualizarColunas(board.getColunas());
        view.setVisible(true);
    }

    private void editarColuna(int colunaIndex) {

        Coluna coluna = board.getColunas().get(colunaIndex);

        JTextField nomeField = new JTextField(coluna.getNome());
        JButton corButton = new JButton("Escolher cor");

        final Color[] corSelecionada = {coluna.getCor()};

        corButton.addActionListener(e -> {
            Color novaCor = JColorChooser.showDialog(
                    view,
                    "Escolha a cor da coluna",
                    corSelecionada[0]
            );

            if (novaCor != null) {
                corSelecionada[0] = novaCor;
            }
        });

        JPanel panel = new JPanel(new GridLayout(0, 1, 6, 6));
        panel.add(new JLabel("Nome da coluna:"));
        panel.add(nomeField);
        panel.add(corButton);

        int resultado = JOptionPane.showConfirmDialog(
                view,
                panel,
                "Editar Coluna",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (resultado != JOptionPane.OK_OPTION) {
            return;
        }

        String novoNome = nomeField.getText().trim();

        if (novoNome.isEmpty()) {
            JOptionPane.showMessageDialog(
                    view,
                    "O nome da coluna eh obrigatorio!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        coluna.setNome(novoNome);
        coluna.setCor(corSelecionada[0]);

        view.atualizarColunas(board.getColunas());
    }

    private void criarNovaTarefa(int colunaIndex) {

        JPanel panel = new JPanel(new GridLayout(0, 1, 6, 6));

        JTextField tituloField = new JTextField();
        JTextField descricaoField = new JTextField();

        String[] prioridades = {"Alta", "Media", "Baixa"};
        JComboBox<String> prioridadeBox = new JComboBox<>(prioridades);

        panel.add(new JLabel("Titulo:"));
        panel.add(tituloField);

        panel.add(new JLabel("Descricao:"));
        panel.add(descricaoField);

        panel.add(new JLabel("Prioridade:"));
        panel.add(prioridadeBox);

        int resultado = JOptionPane.showConfirmDialog(
                view,
                panel,
                "Nova Tarefa",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // Se clicar em Cancelar ou no X, não faz nada
        if (resultado != JOptionPane.OK_OPTION) {
            return;
        }

        String titulo = tituloField.getText().trim();
        String descricao = descricaoField.getText().trim();
        String prioridade = (String) prioridadeBox.getSelectedItem();

        // Validação
        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(
                    view,
                    "O título eh obrigatorio!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Cria a tarefa e adiciona no model
        Tarefa novaTarefa = new Tarefa(titulo, descricao, prioridade);
        board.getColunas().get(colunaIndex).adicionarTarefa(novaTarefa);

        // Atualiza a view
        view.atualizarColunas(board.getColunas());
    }
}
