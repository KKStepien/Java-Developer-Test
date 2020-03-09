package com.example.javadevelopertest.reportResult;

import com.example.javadevelopertest.film.Film;
import com.example.javadevelopertest.film.FilmService;
import com.example.javadevelopertest.people.People;
import com.example.javadevelopertest.people.PeopleService;
import com.example.javadevelopertest.planet.Planet;
import com.example.javadevelopertest.planet.PlanetService;
import com.example.javadevelopertest.report.Report;
import com.example.javadevelopertest.report.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportResultService {
  @Autowired
  private ReportResultRepository reportResultRepository;

  @Autowired
  private ReportRepository reportRepository;

  @Autowired
  private PeopleService peopleService;

  @Autowired
  private PlanetService planetService;

  @Autowired
  private FilmService filmService;

  public List<ReportResult> foundResult(String characterPhrase, String planetName, Long idResult) {
    if (characterPhrase == null || characterPhrase.equals("")) {
      throw new RuntimeException("Character phrase is null or empty");
    }
    if (planetName == null || planetName.equals("")) {
      throw new RuntimeException("Planet name is null or empty");
    }
    List<People> peopleList = peopleService.getAll();
    List<ReportResult> reportResultList = new ArrayList<>();

    for (int i = 0; i < peopleList.size(); i++) {
      People people = peopleList.get(i);

      if (checkPhrase(characterPhrase, people.getName())) {

        if (checkPlanet(planetName, people.getHomeworld())) {
          Planet planet = foundPlanet(people.getHomeworld());

          if (people.getFilms().size() > 0) {
            for (int j = 0; j < people.getFilms().size(); j++) {
              Film film = filmService.getByID(people.getFilms().get(j));

              ReportResult reportResult = create(film.getId(), film.getTitle(), people.getId(),
                  people.getName(), planet.getId(), planet.getName(), idResult);

              reportResultList.add(reportResult);
            }

          }

        }
      }

    }
    return reportResultList;
  }

  public void deleteByReportId(Long id) {
    List<ReportResult> reportResultList = reportResultRepository.findByReport(id);

    for (int i = 0; i < reportResultList.size(); i++) {
      ReportResult reportResult = reportResultList.get(i);
      reportResultRepository.deleteById(reportResult.getId());
    }
  }

  private Planet foundPlanet(String url) {
    return planetService.getByID(url);
  }

  private boolean checkPhrase(String characterPhrase, String name) {
    return name.contains(characterPhrase);
  }

  private boolean checkPlanet(String planet, String homeworldUrl) {
    Planet planet1 = foundPlanet(homeworldUrl);
    return planet.equals(planet1.getName());
  }

  private ReportResult create(Long filmId, String filmName, Long characterId, String characterName,
                              Long planetId, String planetName, Long idReport) {
    ReportResult reportResult = new ReportResult();
    Report report = reportRepository.findById(idReport);

    reportResult.setFilm_id(filmId);
    reportResult.setFilm_name(filmName);
    reportResult.setCharacter_id(characterId);
    reportResult.setCharacter_name(characterName);
    reportResult.setPlanet_id(planetId);
    reportResult.setPlanet_name(planetName);
    reportResult.setReport(report);

    return reportResultRepository.save(reportResult);
  }
}
