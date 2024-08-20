package dev.zvaryyka.recipientservice.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import dev.zvaryyka.recipientservice.models.Recipient;
import dev.zvaryyka.recipientservice.response.UserInfo;
import dev.zvaryyka.recipientservice.repository.RecipientRepository;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class RecipientService {

    private final RecipientRepository recipientRepository;

    @Autowired
    public RecipientService(RecipientRepository recipientRepository) {
        this.recipientRepository = recipientRepository;
    }

    public List<Recipient> getAllRecipientsByUser(UserInfo userInfo) {

        return recipientRepository.findByKeycloakUserId(userInfo.getSub());
    }

    public Recipient addNewRecipient(Recipient recipient, UserInfo userInfo) {

        recipient.setKeycloakUserId(userInfo.getSub());
        recipient.setCreatedAt(Instant.now());
        recipient.setUpdatedAt(Instant.now());
        return recipientRepository.save(recipient);
    }

    public Recipient updateRecipient(UUID id, Recipient recipient) {
        Recipient changedRecipient = recipientRepository.findById(id).get();

        changedRecipient.setName(recipient.getName());
        changedRecipient.setEmail(recipient.getEmail());
        changedRecipient.setPhoneNumber(recipient.getPhoneNumber());
        changedRecipient.setUpdatedAt(Instant.now());
        return recipientRepository.save(changedRecipient);
    }

    public Recipient deleteRecipient(UUID id) {

        Recipient recipient = recipientRepository.findById(id).get();
        recipientRepository.delete(recipient);
        return recipient;
    }


    public String addNewRecipientFromFile(MultipartFile file, UserInfo userInfo) {
        String[] fileName = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        String fileExtension = fileName[fileName.length - 1];
        if (fileExtension.equals("csv")) {
            return addNewRecipientFromCSVFile(file, userInfo);
        } else if (fileExtension.equals("xlsx")) {
            return addNewRecipientFromExcelFile(file, userInfo);
        }

        //TODO REWORK THIS
        return "File type not supported";


    }
    private String addNewRecipientFromCSVFile(MultipartFile file, UserInfo userInfo) {
        int count = 0;

        try (InputStreamReader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
             CSVReader csvReader = new CSVReaderBuilder(reader)
                     .withSkipLines(1)  // Skip the header line
                     .withCSVParser(new com.opencsv.CSVParserBuilder().withSeparator(';').build())  // Use ';' as the separator
                     .build()) {

            // Read all lines from the CSV file
            List<String[]> records = csvReader.readAll();

            for (String[] record : records) {
                if (record.length < 3) {
                    throw new IllegalArgumentException("CSV file does not contain the required number of columns");
                }

                // Extract and trim values from each record
                String name = record[0].trim();
                String email = record[1].trim();
                String phoneNumber = record[2].trim();

                // Create a new recipient object and set its properties
                Recipient recipient = new Recipient();
                recipient.setName(name);
                recipient.setEmail(email);
                recipient.setPhoneNumber(phoneNumber);

                // Add the new recipient to the system
                addNewRecipient(recipient, userInfo);

                count++;
            }

        } catch (IOException | CsvException e) {
            // Handle any errors that occur during file processing
            throw new RuntimeException("Error processing CSV file", e);
        }

        // Return the count of added recipients
        return "Added " + count + " recipients";
    }


    private String addNewRecipientFromExcelFile(MultipartFile file, UserInfo userInfo) {
        int count = 0;

        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            int headerRowIndex = 0;
            for (Row row : sheet) {
                if (row.getCell(0).getStringCellValue().equals("Name") &&
                        row.getCell(1).getStringCellValue().equals("Email") &&
                        row.getCell(2).getStringCellValue().equals("Phone Number")) {
                    headerRowIndex = row.getRowNum();
                    break;
                }
            }

            for (int i = headerRowIndex + 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    String name = getCellValueAsString(row.getCell(0));
                    String email = getCellValueAsString(row.getCell(1));
                    String phoneNumber = getCellValueAsString(row.getCell(2));

                    Recipient recipient = new Recipient();
                    recipient.setName(name);
                    recipient.setEmail(email);
                    recipient.setPhoneNumber(phoneNumber);
                    addNewRecipient(recipient, userInfo);

                    count++;
                }
            }

        } catch (IOException e) {
            //TODO Add normally handled exception
            throw new RuntimeException(e);
        }

        return "Added " + count + " recipients";
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((long) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return cell.toString();
        }
    }

    public Recipient getRecipientById(UUID id) {
        //TODO add handle
        Recipient recipient = recipientRepository.findById(id).get();
        return recipient;
    }

}
