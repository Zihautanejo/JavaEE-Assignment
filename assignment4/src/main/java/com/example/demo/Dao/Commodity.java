package com.example.demo.Dao;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Commodity {

    private int id;

    private String type;

    private double price;

    private int amount;

    private LocalDate proDate;

    private int sellByDate;
}
