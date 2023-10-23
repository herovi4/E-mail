import org.w3c.dom.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ReaderFromFile {
    public static List<Applicant> ReadApplicantFromXML(String XMLName) {
        List<Applicant> applicants = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(new File(XMLName));

            Element root = document.getDocumentElement();

            NodeList applicantList = root.getElementsByTagName("applicant");

            for (int i = 0; i < applicantList.getLength(); i++) {
                Element applicant = (Element) applicantList.item(i);
                Applicant applicant1 = new Applicant();
                List<Subject> subjects = new ArrayList<>();
                // Получаем имя абитуриента
                applicant1.setName(applicant.getElementsByTagName("name").item(0).getTextContent());

                // Получаем список экзаменов и баллов
                NodeList exams = applicant.getElementsByTagName("exam");
                for (int j = 0; j < exams.getLength(); j++) {
                    Element exam = (Element) exams.item(j);
                    Subject sub = new Subject();
                    sub.setName(exam.getAttribute("subject"));
                    sub.setScore(Integer.valueOf(exam.getAttribute("score")));
                    subjects.add(sub);
                }
                applicant1.setSubjects(subjects);
                applicants.add(applicant1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return applicants;
    }

    public static List<Faculty> ReadFacultyFromXML(String XMLName) {
        List<Faculty> faculties = new ArrayList<>();
        try {
            // Создаем фабрику для создания парсера
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Считываем XML-документ
            Document document = builder.parse(new File(XMLName));

            Element root = document.getDocumentElement();

            // Получаем список всех элементов <applicant>
            NodeList facultyList = root.getElementsByTagName("faculty");

            // Перебираем абитуриентов
            for (int i = 0; i < facultyList.getLength(); i++) {
                Element faculty = (Element) facultyList.item(i);
                Faculty faculty1 = new Faculty();
                List<Subject> subjects = new ArrayList<>();
                // Получаем имя абитуриента
                faculty1.setName(faculty.getElementsByTagName("name").item(0).getTextContent());

                // Получаем список экзаменов и баллов
                NodeList exams = faculty.getElementsByTagName("subject");
                for (int j = 0; j < exams.getLength(); j++) {
                    Element exam = (Element) exams.item(j);
                    Subject sub = new Subject();
                    sub.setName(exam.getAttribute("name"));
                    sub.setMinScore(Integer.valueOf(exam.getAttribute("min_score")));
                    subjects.add(sub);
                }
                faculty1.setPassingScore(Integer.valueOf(faculty.getElementsByTagName("passing_score").item(0).getTextContent()));
                faculty1.setRequiredSubjects(subjects);
                faculties.add(faculty1);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return faculties;
    }
    private static List<Applicant> loadApplicantsFromJson(String filename) {
        try (Reader reader = new FileReader(filename)) {
            return new Gson().fromJson(reader, new TypeToken<List<Applicant>>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<Faculty> loadFacultiesFromJson(String filename) {
        try (FileReader reader = new FileReader(filename)) {
            return new Gson().fromJson(reader, new TypeToken<List<Faculty>>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static List<Applicant> ReadApplicantFromJson(String JsonName){
        List<Applicant> applicants = loadApplicantsFromJson(JsonName);
        return  applicants;
    }
    public static List<Faculty> ReadFacultyFromJson(String JsonName){
        List<Faculty> applicants = loadFacultiesFromJson(JsonName);
        return  applicants;
    }

}
