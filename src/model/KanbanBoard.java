package model;

import java.util.ArrayList;
import java.util.List;

public class KanbanBoard {
    private List<Coluna> colunas;

    public KanbanBoard() {
        colunas = new ArrayList<>();
    }

    public void adicionarColuna(Coluna coluna) {
        colunas.add(coluna);
    }

    public List<Coluna> getColunas() {
        return colunas;
    }
}
