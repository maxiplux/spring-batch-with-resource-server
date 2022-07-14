package com.example.spring4resourceserver.batch;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

@Slf4j
public class Writer implements ItemWriter<String> {

    @Override
    public void write(List<? extends String> messages) throws Exception {

        for (String msg : messages) {
            log.info("Que esta pasandooooooo {}", msg);
            System.out.println("Writing the data " + msg);
        }
    }

}
