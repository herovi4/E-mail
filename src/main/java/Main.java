import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.io.FilenameUtils;


public class Main {
    public static final String IN_PATH = "src\\main\\resources\\in\\";
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String ApplicantFileName;
        String FacultyFileName;
        List<Applicant> applicants = new ArrayList<>();
        List<Faculty> faculties = new ArrayList<>();
        String choice = "";

        System.out.println("1. lf - самостоятельно ввести названия файлов.\n" +
                "2. xml - считать из стандартных xml файлов.\n" +
                "3. json - считать из стандартных json файлов.");
        choice = sc.nextLine();
        String[] command = choice.split(" ");
        switch (command[0]) {
            case "lf" -> {
                String extensionApplicant = FilenameUtils.getExtension(IN_PATH + command[1]);
                String extensionFaculty = FilenameUtils.getExtension(IN_PATH + command[2]);
                switch (extensionApplicant) {
                    case "xml" -> applicants = ReaderFromFile.ReadApplicantFromXML(IN_PATH +command[1]);

                    case "json" -> applicants = ReaderFromFile.ReadApplicantFromJson(IN_PATH +command[1]);

                }
                switch (extensionFaculty) {
                    case "xml" -> faculties = ReaderFromFile.ReadFacultyFromXML(IN_PATH +command[2]);

                    case "json" -> faculties = ReaderFromFile.ReadFacultyFromJson(IN_PATH +command[2]);
                }
            }
            case "xml" -> {
               ApplicantFileName = IN_PATH +"Applicant.xml";
                FacultyFileName = IN_PATH +"Faculty.xml";
                applicants = ReaderFromFile.ReadApplicantFromXML(ApplicantFileName);
                faculties = ReaderFromFile.ReadFacultyFromXML(FacultyFileName);
            }
            case "json" -> {
                ApplicantFileName = IN_PATH +"Applicants.json";
                FacultyFileName = IN_PATH + "Faculties.json";
                applicants = ReaderFromFile.ReadApplicantFromJson(ApplicantFileName);
                faculties = ReaderFromFile.ReadFacultyFromJson(FacultyFileName);
            }
        }
        LetterGenerator.generateAdmissionLetters(faculties, applicants);
    }
}
