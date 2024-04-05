package com.git.cantave;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;

public class YellowPagesScraper {
    // Create a Logger instance for this class
    private static final Logger logger = LogManager.getLogger(YellowPagesScraper.class);

    public static void main(String[] args) {
        try {
            String url = "https://www.yellowpages.com/search?search_terms=farm+supply&geo_location_terms=mississippi";
            Document doc = Jsoup.connect(url).get();

            Elements listings = doc.select(".info"); // Adjust selector based on actual HTML

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Listings");

                // Create a header row
                Row headerRow = sheet.createRow(0);

                // Creating header cells
                headerRow.createCell(0).setCellValue("Name");
                headerRow.createCell(1).setCellValue("Info");
                headerRow.createCell(2).setCellValue("Address");
                headerRow.createCell(3).setCellValue("Locality");
                headerRow.createCell(4).setCellValue("Phone");

                int rowNum = 1;

                for (Element listing : listings) {
// Replace the selectors below based on the actual HTML structure of Yellow Pages listings
                    String name = listing.select(".business-name").text(); // Example: ".business-name a"
                    String info = listing.select(".categories").text();
                    String address = listing.select(".street-address").text(); // Example: ".address"
                    String locality = listing.select(".locality").text();
                    String phone = listing.select(".phones.phone.primary").text();// Example: ".phone"

                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(name);
                    row.createCell(1).setCellValue(info);
                    row.createCell(2).setCellValue(address);
                    row.createCell(3).setCellValue(locality);
                    row.createCell(4).setCellValue(phone);
                }

                try (FileOutputStream outputStream = new FileOutputStream("Listings.xlsx")) {
                    workbook.write(outputStream);
                    logger.info("Spreadsheet generated successfully.");
                }
            }
        } catch (IOException e) {
            logger.error("An error occurred: ", e);
        }
    }
}



