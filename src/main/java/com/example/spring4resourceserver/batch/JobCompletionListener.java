package com.example.spring4resourceserver.batch;

import lombok.extern.log4j.Log4j;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

import java.io.FileOutputStream;
import java.io.IOException;

@Log4j
public class JobCompletionListener extends JobExecutionListenerSupport {

    private final SXSSFWorkbook workbook;
    private final FileOutputStream fileOutputStream;



    public JobCompletionListener(SXSSFWorkbook workbook, FileOutputStream fileOutputStream) {
        this.fileOutputStream = fileOutputStream;
        this.workbook=workbook;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED)
        {
            try {
                workbook.write(fileOutputStream);
                fileOutputStream.close();
                workbook.dispose();
            } catch (IOException e)
            {
                log.error(e.getMessage(), e);
            }
          log.info("BATCH JOB COMPLETED SUCCESSFULLY");
        }
    }

}

