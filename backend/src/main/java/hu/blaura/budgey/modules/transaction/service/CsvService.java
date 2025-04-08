package hu.blaura.budgey.modules.transaction.service;

import hu.blaura.budgey.modules.transaction.model.Transaction;
import hu.blaura.budgey.modules.transaction.model.TransactionCategory;
import hu.blaura.budgey.modules.transaction.model.dto.TransactionDto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvService {
    public String generateTransactionsCsv(List<Transaction> transactions) {
        StringBuilder csv = new StringBuilder("Date,Title,Amount,Category\n");
        transactions.forEach(t -> {
            csv.append(t.getDate()).append(",")
                    .append(escapeCsv(t.getTitle())).append(",")
                    .append(t.getAmount()).append(",")
                    .append(t.getCategory() != null ? escapeCsv(t.getCategory().toString()) : "")
                    .append("\n");
        });
        return csv.toString();
    }

    private String escapeCsv(String input) {
        return input == null ? "" :
                input.contains(",") ? "\"" + input + "\"" : input;
    }

    public List<TransactionDto> importTransactions(MultipartFile file) throws IOException {
        return parseCsvFile(file);
    }

    private List<TransactionDto> parseCsvFile(MultipartFile file) throws IOException {
        try (BufferedReader fileReader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<TransactionDto> csvData = new ArrayList<>();

            for (CSVRecord csvRecord : csvParser) {
                TransactionDto dto = new TransactionDto();
                dto.setDate(csvRecord.get("Date"));
                dto.setTitle(csvRecord.get("Title"));
                dto.setAmount(Double.parseDouble(csvRecord.get("Amount")));
                dto.setCategory(TransactionCategory.valueOf(csvRecord.get("Category")));
                csvData.add(dto);
            }

            return csvData;
        }
    }
}
