package com.example.javadevelopertest.report;

import com.example.javadevelopertest.reportResult.ReportResult;
import com.example.javadevelopertest.reportResult.ReportResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

  @Autowired
  private ReportRepository reportRepository;

  @Autowired
  private ReportResultService reportResultService;

  public Report create(String characterPhrase, String planetName, Long id) {
    Report report = new Report();
    report.setCharacterPhrase(characterPhrase);
    report.setPlanetName(planetName);
    report.setId(id);
    reportRepository.save(report);
    List<ReportResult> reportResults = reportResultService.foundResult(characterPhrase, planetName, id);
    report.setResult(reportResults);
    return reportRepository.save(report);
  }

  public Report update(UpdateReport updatereport, Long id) {
    Report report = reportRepository.findById(id);
    report.setCharacterPhrase(updatereport.getCharacterPhrase());
    report.setPlanetName(updatereport.getPlanetName());
    reportResultService.deleteByReportId(id);
    List<ReportResult> reportResults = reportResultService.foundResult(updatereport.getCharacterPhrase(),
        updatereport.getPlanetName(), report.getId());
    report.setResult(reportResults);
    return reportRepository.save(report);
  }

  public Report getById(Long id) {
    return reportRepository.findById(id);
  }

  public List<Report> getAll() {
    return reportRepository.findAll();
  }

  public void delete() {
    reportRepository.deleteAll();
  }

  public void deleteById(Long id) {
    reportRepository.deleteById(id);
  }
}
