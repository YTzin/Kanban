import javax.swing.SwingUtilities;
import model.KanbanBoard;
import view.KanbanView;
import controller.KanbanController;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            KanbanBoard board = new KanbanBoard();
            KanbanView view = new KanbanView();
            new KanbanController(board, view);
        });
    }
}
