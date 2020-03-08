package com.example.javadevelopertest.report;

import com.example.javadevelopertest.reportResult.ReportResult;
import com.example.javadevelopertest.reportResult.ReportResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ReportController {
  @Autowired
  private ReportService reportService;

  @PutMapping("/api/report/{id}")
  public void put(@RequestBody UpdateReportDTO updateReportDTO, @PathVariable Long id) {
    List<Report> reportList = reportService.getAll();
    UpdateReport updateReport = new UpdateReport();
    updateReport.setCharacterPhrase(updateReportDTO.getCharacterPhrase());
    updateReport.setPlanetName(updateReportDTO.getPlanetName());

    if (findId(reportList, id)) {
      reportService.update(updateReport, id);
    } else {
      reportService.create(updateReport.getCharacterPhrase(), updateReport.getPlanetName(), id);
    }
  }

  private boolean findId(List<Report> reportList, Long id) {
    for (int i = 0; i < reportList.size(); i++) {
      if (id == reportList.get(i).getId()) {
        return true;
      }
    }
    return false;
  }

  @DeleteMapping("/api/report/{id}")
  public void deleteById(@PathVariable Long id) {
    reportService.deleteById(id);
  }

  @DeleteMapping("/api/report")
  public void delete() {
    reportService.delete();
  }

  @GetMapping("/api/report")
  public List<ReportDTO> getAll() {
    List<Report> reportList = reportService.getAll();
    List<ReportDTO> reportDTOS = new ArrayList<>();

    for (int i = 0; i < reportList.size(); i++) {
      ReportDTO reportDTO = getReportDTO(reportList.get(i));
      reportDTOS.add(reportDTO);
    }
    return reportDTOS;
  }

  @GetMapping("/api/report/{id}")
  public ReportDTO getById(@PathVariable Long id) {
    Report report = reportService.getById(id);
    return getReportDTO(report);
  }

  private ReportDTO getReportDTO(Report report) {
    ReportDTO reportDTO = new ReportDTO();
    reportDTO.setId(report.getId());
    reportDTO.setCharacterPhrase(report.getCharacterPhrase());
    reportDTO.setPlanetName(report.getPlanetName());
    reportDTO.setResult(getListReportResultDTO(report.getResult()));
    return reportDTO;
  }

  private ReportResultDTO getReportResultDTO(ReportResult reportResult) {
    ReportResultDTO reportResultDTO = new ReportResultDTO();
    reportResultDTO.setFilm_id(reportResult.getFilm_id());
    reportResultDTO.setFilm_name(reportResult.getFilm_name());
    reportResultDTO.setCharacter_id(reportResult.getCharacter_id());
    reportResultDTO.setCharacter_name(reportResult.getCharacter_name());
    reportResultDTO.setPlanet_id(reportResult.getPlanet_id());
    reportResultDTO.setPlanet_name(reportResult.getPlanet_name());
    return reportResultDTO;
  }

  private List<ReportResultDTO> getListReportResultDTO(List<ReportResult> reportResults) {
    List<ReportResultDTO> reportResultDTOS = new ArrayList<>();
    for (int i = 0; i < reportResults.size(); i++) {
      reportResultDTOS.add(getReportResultDTO(reportResults.get(i)));
    }
    return reportResultDTOS;
  }

}