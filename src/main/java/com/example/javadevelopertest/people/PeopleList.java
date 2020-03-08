package com.example.javadevelopertest.people;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PeopleList {
  String next;
  List<People> results;
}
