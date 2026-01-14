package action;

import domain.Identifiable;
import repository.IRepository;

public class ActionRemove<ID, T extends Identifiable<ID>> implements IAction {
    private IRepository<ID, T> repo;
    private T removedElem;

    public ActionRemove(IRepository<ID, T> repo, T removedElem) {
        this.repo = repo;
        this.removedElem = removedElem;
    }

    @Override
    public void executeUndo() {
        repo.addElement(removedElem.getId(), removedElem);
    }

    @Override
    public void executeRedo() {
        repo.removeElement(removedElem.getId());
    }
}
