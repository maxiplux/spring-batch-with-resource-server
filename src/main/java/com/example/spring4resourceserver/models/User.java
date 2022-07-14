package com.example.spring4resourceserver.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//https://www.jackrutorial.com/2018/03/spring-boot-batch-read-from-mysql-database-and-write-into-a-csv-file-tutorial.html
public class User {
    private Integer id;
    private String name;


}
