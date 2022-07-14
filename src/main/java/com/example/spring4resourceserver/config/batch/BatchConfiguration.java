package com.example.spring4resourceserver.config.batch;

import com.example.spring4resourceserver.batch.JobCompletionListener;
import com.example.spring4resourceserver.batch.UserItemProcessor;
import com.example.spring4resourceserver.models.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.batch.api.listener.JobListener;
import javax.sql.DataSource;
import java.awt.print.Book;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Configuration
@Slf4j
@EnableBatchProcessing
public class BatchConfiguration {
    private static final Integer CHUNK = 100;
    private static final String EXPORT_FILENAME = "/Users/juanfmosquera/Documents/projects/spring4over15boot/spring4over15boot-resourcesserver/export.xlsx";

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Bean
    public JdbcCursorItemReader<User> reader(){
        JdbcCursorItemReader<User> reader = new JdbcCursorItemReader<User>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT id,name FROM user");
        reader.setRowMapper(new UserRowMapper());

        return reader;
    }

    @Bean
    public UserItemProcessor processor(){
        return new UserItemProcessor();
    }
    /*@Bean
    public FlatFileItemWriter<User> writer2(){
        FlatFileItemWriter<User> writer = new FlatFileItemWriter<User>();
        writer.setResource(new FileSystemResource("/Users/juanfmosquera/Documents/projects/spring4over15boot/spring4over15boot-resourcesserver/users.csv"));
        writer.setAppendAllowed(true);

        writer.setLineAggregator(new DelimitedLineAggregator<User>() {{
            setDelimiter(",");
            setFieldExtractor(new BeanWrapperFieldExtractor<User>() {{

                setNames(new String[] { "id", "name" });
            }});
        }});

        return writer;
    }

     */


    public Step step1(SXSSFSheet sheet) throws IOException {
        return stepBuilderFactory.get("step1").<User, User> chunk(1)
                .reader(this.reader())
                .writer(this.bookWriter(sheet))
                .build();
    }


    public Step step2(SXSSFSheet sheet) throws IOException {
        return stepBuilderFactory.get("step2").<User, User> chunk(1)
                .reader(this.reader())
                .processor(this.processor())
                .writer(this.writeSecondPage(sheet))
                .build();
    }

    private ItemWriter<User> writeSecondPage(SXSSFSheet sheet) {

        return new Sheet2Writer(sheet);
    }


    @Bean
    public Job exportUserJob() throws IOException {
        SXSSFWorkbook workbook=new SXSSFWorkbook(CHUNK);

        SXSSFSheet sheet = workbook.createSheet("Books");
        SXSSFSheet sheet2 = workbook.createSheet("Books2");

        return jobBuilderFactory.get("exportUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(this.jobListener(workbook))
                .start(this.step1(sheet))
                .next(this.step2(sheet2)).build();

    }





    public ItemWriter<User> bookWriter(SXSSFSheet sheet)
    {

        return new BookWriter(sheet);
    }

    @Bean
    public FileOutputStream fileOutputStream() throws FileNotFoundException {
        return new FileOutputStream(EXPORT_FILENAME);
    }


    JobCompletionListener jobListener(SXSSFWorkbook workbook) throws IOException {
        return new JobCompletionListener(workbook, new FileOutputStream(EXPORT_FILENAME));
    }

}
