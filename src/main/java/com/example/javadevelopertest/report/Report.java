package com.example.javadevelopertest.report;


import com.example.javadevelopertest.reportResult.ReportResult;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
public class Report {
  @Id
  private Long id;
  private String characterPhrase;
  private String planetName;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "report")
  private List<ReportResult> result;
}
