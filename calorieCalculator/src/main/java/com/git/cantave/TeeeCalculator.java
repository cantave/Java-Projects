package com.git.cantave;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.util.Scanner;

public class TeeeCalculator {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Full Name: ");
        String fullName = scanner.nextLine();
        System.out.println("Enter Age: ");
        int age = scanner.nextInt();
        System.out.println("Enter Gender (M/F): ");
        String gender = scanner.next();
        System.out.println("Enter height (inches): ");
        int height = scanner.nextInt();
        System.out.println("Enter weight (lbs): ");
        int weight = scanner.nextInt();
        System.out.println("Enter activity level (1-5): ");
        int activityLvl = scanner.nextInt();

        double wtInKg = weight / 2.2;
        double htInCm = height / 2.54;

        double bmrHarrisBenEq;
        if ("M".equalsIgnoreCase(gender)) {
            bmrHarrisBenEq = 88.362 + (13.397 * wtInKg) + (4.799 * htInCm) - (5.677 * age);
        } else {
            bmrHarrisBenEq = 447.593 + (9.247 * wtInKg) + (3.098 * htInCm) - (4.330 * age);
        }

        double bmrMifflinStJeor;
        if("M".equalsIgnoreCase(gender)){
            bmrMifflinStJeor = (10 * wtInKg) + (6.25 * htInCm) - (5 * age) + 5;
        } else {
            bmrMifflinStJeor = (10*wtInKg) + (6.25 * htInCm) - (5 * age) - 161;
        }

        double multiplier = 1.0;
        switch (activityLvl) {
            case 1:
                multiplier = 1.2;
                break;
            case 2:
                multiplier = 1.375;
                break;
            case 3:
                multiplier = 1.55;
                break;
            case 4:
                multiplier = 1.725;
                break;
            case 5:
                multiplier = 1.9;
                break;
            default:
                System.out.println("Invalid activity level. Setting to default (1.2)");
                break;
        }

        double teeeHarrisBenEq = bmrHarrisBenEq * multiplier;
        double loseOneLbsHarris = teeeHarrisBenEq - 500;
        double loseTwoLbsHarris = teeeHarrisBenEq - 1000;
        double teeeMifflinStJeor = bmrMifflinStJeor * multiplier;
        double loseOneLbsMifflin = teeeMifflinStJeor - 500;
        double loseTwoLbsMifflin = teeeMifflinStJeor - 1000;

        double estProteinHarris = (teeeHarrisBenEq * (0.25)) / 4;
        double estCarbsHarris = (teeeHarrisBenEq * (0.50)) / 4;
        double estFatHarriss = (teeeHarrisBenEq * (0.25)) / 4;

        double estProteinMifflin = (teeeMifflinStJeor * (0.25)) / 4;
        double estCarbsMifflin = (teeeMifflinStJeor * (0.50)) / 4;
        double estFatMifflin = (teeeMifflinStJeor * (0.25)) / 4;


        XWPFDocument document = new XWPFDocument();
        FileOutputStream output = new FileOutputStream(fullName + "_TEEE_Report.docx");

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("Total Estimated Energy Expenditure (TEEE) Report");
        run.addBreak();
        run.addBreak();
        run.setText("Name: " + fullName);
        run.addBreak();
        run.setText("Age: " + age + " years young");
        run.addBreak();
        run.setText("Gender: " + gender);
        run.addBreak();
        run.setText("Height: " + height + " inches");
        run.addBreak();
        run.setText("Weight: " + weight + " pounds");
        run.addBreak();
        run.setText("Activity Level: " + activityLvl);
        run.addBreak();
        run.setText("Calculated TEEE (Harris-Benedict): " + String.format("%.2f", teeeHarrisBenEq) + " calories/day");
        run.addBreak();
        run.setText("Estimated Carbohydrate Intake: " + String.format("%.2f",estCarbsHarris) + " grams per/day");
        run.addBreak();
        run.setText("Estimated Protein Intake: " + String.format("%.2f", estProteinHarris) + " grams per/day");
        run.addBreak();
        run.setText("Estimated Fat Intake: " + String.format("%.2f", estFatHarriss) + " grams per/day");
        run.addBreak();
        run.setText("To lose 1 lb. per week: " + String.format("%.2f", loseOneLbsHarris) + "calories/day");
        run.addBreak();
        run.setText("To lose 2 lbs. per week: " + String.format("%.2f", loseTwoLbsHarris) + "calories/day");
        run.addBreak();
        run.setText("Calculated TEEE (Mifflin St. Jeor): " + String.format("%.2f", teeeMifflinStJeor) + " calories/day");
        run.addBreak();
        run.setText("Estimated Carbohydrate Intake: " + String.format("%.2f",estCarbsMifflin) + " grams per/day");
        run.addBreak();
        run.setText("Estimated Protein Intake: " + String.format("%.2f", estProteinMifflin) + " grams per/day");
        run.addBreak();
        run.setText("Estimated Fat Intake: " + String.format("%.2f", estFatMifflin) + " grams per/day");
        run.addBreak();
        run.setText("To lose 1 lb. per week: " + String.format("%.2f", loseOneLbsMifflin) + " calories/day");
        run.addBreak();
        run.setText("To lose 2 lbs. per week: " + String.format("%.2f", loseTwoLbsMifflin) + " calories/day");

        document.write(output);
        output.close();
        System.out.println("TEEE reported generated successfully.");

        scanner.close();
    }
}