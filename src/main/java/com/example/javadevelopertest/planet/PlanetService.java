package com.example.javadevelopertest.planet;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PlanetService {

  public Planet getByID(String url) {
    if (url == null || url.equals("")) {
      throw new RuntimeException("URL is null or empty");
    }

    RestTemplate rest = new RestTemplate();
    Planet planet = rest.getForObject(url, Planet.class);
    planet.setId(Long.parseLong(planet.getUrl().replaceAll("\\D+", "")));
    return planet;
  }

}
