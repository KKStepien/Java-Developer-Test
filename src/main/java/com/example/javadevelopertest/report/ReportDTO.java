package com.example.javadevelopertest.report;

import com.example.javadevelopertest.reportResult.ReportResultDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReportDTO {
  private Long id;
  private String characterPhrase;
  private String planetName;
  private List<ReportResultDTO> result;
}
