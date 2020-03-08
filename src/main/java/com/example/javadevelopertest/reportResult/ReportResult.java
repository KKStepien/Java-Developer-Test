package com.example.javadevelopertest.reportResult;

import com.example.javadevelopertest.report.Report;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class ReportResult {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long film_id;
  private String film_name;
  private Long character_id;
  private String character_name;
  private Long planet_id;
  private String planet_name;
  @ManyToOne
  @JoinColumn(name = "id_report")
  private Report report;
}
