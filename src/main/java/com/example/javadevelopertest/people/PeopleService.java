package com.example.javadevelopertest.people;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class PeopleService {

  public List<People> getAll() {
    RestTemplate rest = new RestTemplate();
    List<People> people = new ArrayList<>();
    PeopleList peopleList;

    String next = "page1";

    while (next != null) {
      if (next.equals("page1")) {
        peopleList = rest.getForObject("https://swapi.co/api/people/?page=1", PeopleList.class);
      } else {
        peopleList = rest.getForObject(next, PeopleList.class);
      }
      next = peopleList.getNext();
      for (int i = 0; i < peopleList.getResults().size(); i++) {
        people.add(peopleList.getResults().get(i));
        String id = (people.get(people.size() - 1).getUrl()).replaceAll("\\D+", "");
        people.get(people.size() - 1).setId(Long.parseLong(id));
      }
    }

    return people;
  }
}
