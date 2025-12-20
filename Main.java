import javax.swing.SwingUtilities;
import src.model.KanbanBoard;
import src.view.KanbanView;
import src.controller.KanbanController;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            KanbanBoard board = new KanbanBoard();
            KanbanView view = new KanbanView();
            new KanbanController(board, view);
        });
    }
}
