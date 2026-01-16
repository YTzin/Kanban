package model;


import java.util.ArrayList;

public class KanbanBoard {
    private ArrayList<Coluna> colunas;

    public KanbanBoard() {
        colunas = new ArrayList<>();
    }

    public void adicionarColuna(Coluna coluna) {
        colunas.add(coluna);
    }

    public ArrayList<Coluna> getColunas() {
        return colunas;
    }
    public void removerColuna(int index) {
    if (index >= 0 && index < colunas.size()) {
        colunas.remove(index);
    }
}
}
