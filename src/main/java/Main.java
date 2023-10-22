import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.io.FilenameUtils;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String ApplicantFileName;
        String FacultyFileName;
        List<Applicant> applicants = new ArrayList<>();
        List<Faculty> faculties = new ArrayList<>();
        String choice = "";

        choice = sc.nextLine();
        String[] command = choice.split(" ");
        switch (command[0]) {
            case "lf" -> {
                String extensionApplicant = FilenameUtils.getExtension(command[1]);
                String extensionFaculty = FilenameUtils.getExtension(command[2]);
                switch (extensionApplicant) {
                    case "xml" -> applicants = ReaderFromFile.ReadApplicantFromXML(command[1]);

                    case "json" -> applicants = ReaderFromFile.ReadApplicantFromJson(command[1]);

                }
                switch (extensionFaculty) {
                    case "xml" -> faculties = ReaderFromFile.ReadFacultyFromXML(command[2]);

                    case "json" -> faculties = ReaderFromFile.ReadFacultyFromJson(command[2]);
                }
            }
            case "xml" -> {
                ApplicantFileName = "Applicant.xml";
                FacultyFileName = "Faculty.xml";
                applicants = ReaderFromFile.ReadApplicantFromXML(ApplicantFileName);
                faculties = ReaderFromFile.ReadFacultyFromXML(FacultyFileName);
            }
            case "json" -> {
                ApplicantFileName = "Applicants.json";
                FacultyFileName = "Faculties.json";
                applicants = ReaderFromFile.ReadApplicantFromJson(ApplicantFileName);
                faculties = ReaderFromFile.ReadFacultyFromJson(FacultyFileName);
            }
        }
        LetterGenerator.generateAdmissionLetters(faculties, applicants);
    }
}
