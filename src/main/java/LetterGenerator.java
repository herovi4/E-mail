
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LetterGenerator {
    public static final  String OUT_PATH = "src\\main\\resources\\out\\";
    public static void generateAdmissionLetters(List<Faculty> faculties, List<Applicant> applicants)
    {
        for (Applicant abiturient : applicants) {
            StringBuilder emailText = new StringBuilder("Уважаемый " + abiturient.getName() + "!\n\n");
            boolean isAdmitted = false;
            boolean isPassing = true;
            for (Faculty faculty : faculties) {
                List<Subject> requiredSubjects = faculty.getRequiredSubjects();
                int totalScore = 0;

                for (Subject subjectName : requiredSubjects) {
                    for (Subject subject : abiturient.getSubjects()) {
                        if (subject.getName().equals(subjectName.getName()) && isPassing)
                        {
                            if (subject.getScore() < subjectName.getMinScore())
                                isPassing = false;
                            else totalScore += subject.getScore();
                            break;
                        }
                    }
                }

                if (totalScore >= faculty.getPassingScore() && isPassing) {
                    emailText.append("Поздравляем! Вы поступили на " + faculty.getName() + "\n");
                    isAdmitted = true;
                }
            }

            if (!isAdmitted) {
                emailText.append("К сожалению, вы не прошли ни на один факультет.\n");
            }

            // Сохранение текста письма в файл
            try {
                String txtFileName = abiturient.getName().replaceAll(" ", "_") + ".txt";
                String pdfFileName = abiturient.getName().replaceAll(" ", "_") + ".pdf";
                BufferedWriter writer = new BufferedWriter(new FileWriter(OUT_PATH + txtFileName));
                PDDocument document = new PDDocument();
                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);
                String[] lines = emailText.toString().split("\n"); // Разделить текст на строки по символу новой строки
                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                PDType0Font font = PDType0Font.load(document, new File("C:\\Windows\\Fonts\\Arial.ttf"));
                contentStream.setFont(font, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                for (String line : lines) {
                    contentStream.showText(line);
                    contentStream.newLineAtOffset(0, -12); // Переход на следующую строку
                }
                contentStream.endText();
                contentStream.close();

                document.save(OUT_PATH +pdfFileName); // Сохранение в PDF-файл
                document.close();
                writer.write(emailText.toString());
                writer.close();
                System.out.println("Письмо для " + abiturient.getName() + " сохранено в файл " + txtFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
