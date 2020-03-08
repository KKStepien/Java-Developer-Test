package com.example.javadevelopertest.reportResult;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportResultRepository extends Repository<ReportResult, Long> {

  ReportResult save(ReportResult reportResult);

  ReportResult findById(Long id);

  @Query("FROM ReportResult r WHERE r.report.id=:id")
  List<ReportResult> findByReport(@Param("id") Long id);

  @Query("FROM ReportResult r WHERE r.report.id=:id")
  void deleteByReport(@Param("id") Long id);

  void deleteById(Long id);
}
