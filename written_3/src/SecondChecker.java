public class SecondChecker extends Checker {

    @Override
    public boolean check(Section section) {
        String title = section.getTitle();
        String[] content = section.getContent().split("[.!?,;:\\-]" + " ");
        return !title.contains(" ") && content.length <= 300;
    }
}
