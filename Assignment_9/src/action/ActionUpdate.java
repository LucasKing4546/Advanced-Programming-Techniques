package action;

import domain.Identifiable;
import repository.IRepository;

public class ActionUpdate<ID, T extends Identifiable<ID>> implements IAction {
    private IRepository<ID, T> repo;
    private T oldElem;
    private T newElem;

    public ActionUpdate(IRepository<ID, T> repo, T newElem, T oldElem) {
        this.repo = repo;
        this.newElem = newElem;
        this.oldElem = oldElem;
    }

    @Override
    public void executeUndo() {
        repo.removeElement(newElem.getId());
        repo.addElement(oldElem.getId(), oldElem);
    }

    @Override
    public void executeRedo() {
        repo.addElement(newElem.getId(), newElem);
        repo.removeElement(oldElem.getId());
    }
}
