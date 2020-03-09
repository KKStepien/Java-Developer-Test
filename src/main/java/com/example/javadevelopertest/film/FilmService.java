package com.example.javadevelopertest.film;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FilmService {

  public Film getByID(String url) {
    if (url == null || url.equals("")) {
      throw new RuntimeException("URL is null or empty");
    }

    RestTemplate rest = new RestTemplate();
    Film film = rest.getForObject(url, Film.class);
    film.setId(Long.parseLong(film.getUrl().replaceAll("\\D+", "")));
    return film;
  }
}
