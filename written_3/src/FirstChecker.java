public class FirstChecker extends Checker {
    @Override
    public boolean check(Section section) {
        String[] title = new String[]{section.getTitle()};
        String[] content = section.getContent().split("[.!?]");
        if (title[0].equals(title[0].toUpperCase())) {
            return content.length > 1;
        }
        return false;
    }
}
