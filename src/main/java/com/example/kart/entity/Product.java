package com.example.kart.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;


@Data
public class Product {
	private String name;
	private String type;
	private double amount;
	private int count;

}
