package com.example.javadevelopertest.report;

import com.example.javadevelopertest.reportResult.ReportResult;
import com.example.javadevelopertest.reportResult.ReportResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReportService {

  @Autowired
  private ReportRepository reportRepository;

  @Autowired
  private ReportResultService reportResultService;

  public Report create(String characterPhrase, String planetName, Long id) {
    if (characterPhrase == null || characterPhrase.equals("")) {
      throw new IllegalArgumentException("Character phrase is null or empty");
    }
    if (planetName == null || planetName.equals("")) {
      throw new IllegalArgumentException("Planet name is null or empty");
    }
    Report report = new Report();
    report.setCharacterPhrase(characterPhrase);
    report.setPlanetName(planetName);
    report.setId(id);
    reportRepository.save(report);
    List<ReportResult> reportResults = reportResultService.foundResult(characterPhrase, planetName, id);
    report.setResult(reportResults);
    return report;
  }

  public Report update(UpdateReport updatereport, Long id) {
    if (updatereport == null) {
      throw new IllegalArgumentException("UpdateReport is null");
    }
    if (updatereport.getCharacterPhrase() == null || updatereport.getCharacterPhrase().equals("")) {
      throw new IllegalArgumentException("Character phrase is null or empty");
    }
    if (updatereport.getPlanetName() == null || updatereport.getPlanetName().equals("")) {
      throw new IllegalArgumentException("Planet name is null or empty");
    }
    Report report = reportRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Report not found"));
    report.setCharacterPhrase(updatereport.getCharacterPhrase());
    report.setPlanetName(updatereport.getPlanetName());
    reportResultService.deleteByReportId(id);
    List<ReportResult> reportResults = reportResultService.foundResult(updatereport.getCharacterPhrase(),
        updatereport.getPlanetName(), report.getId());
    report.setResult(reportResults);
    return reportRepository.save(report);
  }
}
