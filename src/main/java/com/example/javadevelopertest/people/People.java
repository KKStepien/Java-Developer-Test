package com.example.javadevelopertest.people;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class People {
  Long id;
  String url;
  String homeworld;
  String name;
  List<String> films;

}
