package com.example.javadevelopertest.people;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class People {
  private Long id;
  private String url;
  private String homeworld;
  private String name;
  private List<String> films;

}
