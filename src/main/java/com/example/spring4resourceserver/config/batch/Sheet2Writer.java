package com.example.spring4resourceserver.config.batch;

import com.example.spring4resourceserver.models.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

import static java.util.Arrays.asList;

@Slf4j
public class Sheet2Writer implements ItemWriter<User> {
    private final Sheet sheet;
    private  Integer  row=0;

    Sheet2Writer(Sheet sheet) {
        this.sheet = sheet;
        this.row=0;
    }

    @Override
    public void write(List<? extends User> list) {
        for (int i = 0; i < list.size(); i++)
        {
            log.info("@@@@@@@@@@@@ Que paso write   writeRow {} global row {}" , i, this.row);
            writeRow(this.row, list.get(i));

        }
        this.row=this.row+1;
    }

    private void writeRow(int currentRowNumber, User book) {
        List<String> columns = prepareColumns(book);

        Row row = this.sheet.createRow(currentRowNumber);
        int i = 0;
        for (i = 0; i < columns.size(); i++)
        {

            writeCell(row, i, columns.get(i));
        }
        log.info("@@@@@@@@@@@@ Que paso con el loop {}" , i);
    }

    private List<String> prepareColumns(User book) {
        return asList(
                book.getId().toString(),
            book.getName()
        );
    }

    private void writeCell(Row row, int currentColumnNumber, String value) {
        Cell cell = row.createCell(currentColumnNumber);
        cell.setCellValue(value+"LO TENEMOS PAPI LO TENEMOS");

    }
}
