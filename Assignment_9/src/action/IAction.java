package action;

import domain.Identifiable;

public interface IAction {
    public void executeUndo();
    public void executeRedo();
}
