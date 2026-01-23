public class Section {
    private String title;
    private String content;
    private Checker checker;

    public Section(String title, String content, Checker checker) {
        this.title = title;
        this.content = content;
        this.checker = checker;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String generate(){
        if (checker.check(this)){
            return String.format("Section: %s\n%s", title, content);
        }
        return "Section content is not valid.";
    }
}
