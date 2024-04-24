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
    private static final Logger logger = LogManager.getLogger(YellowPagesScraper.class);

    public static void main(String[] args) {
        String baseUrl = "https://www.yellowpages.com/search?search_terms=%22flower+shop%22+OR+%22florist%22&geo_location_terms=MS";
        int totalPages = 24; // Total number of pages to scrape

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Listings");

            // Create a header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Name");
            headerRow.createCell(1).setCellValue("Info");
            headerRow.createCell(2).setCellValue("Address");
            headerRow.createCell(3).setCellValue("Locality");
            headerRow.createCell(4).setCellValue("Phone");

            int rowNum = 1; // Start at 1 to account for header row

            for (int page = 1; page <= totalPages; page++) {
                String url = baseUrl + "&page=" + page;
                Document doc = Jsoup.connect(url).get();

                Elements listings = doc.select(".info"); // Adjusted selector based on actual HTML

                for (Element listing : listings) {
                    String name = listing.select(".business-name").text();
                    String info = listing.select(".categories").text();
                    String address = listing.select(".street-address").text();
                    String locality = listing.select(".locality").text();
                    String phone = listing.select(".phones.phone.primary").text();

                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(name);
                    row.createCell(1).setCellValue(info);
                    row.createCell(2).setCellValue(address);
                    row.createCell(3).setCellValue(locality);
                    row.createCell(4).setCellValue(phone);
                }
            }

            // Write the workbook to a file
            try (FileOutputStream outputStream = new FileOutputStream("Listings.xlsx")) {
                workbook.write(outputStream);
                logger.info("Spreadsheet generated successfully with data from all pages.");
            }
        } catch (IOException e) {
            logger.error("An error occurred: ", e);
        }
    }
}



