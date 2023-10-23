import javax.xml.bind.annotation.*;
import java.util.List;


public class Applicant {
    private String name;
    private List<Subject> subjects;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

}