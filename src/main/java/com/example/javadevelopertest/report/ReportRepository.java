package com.example.javadevelopertest.report;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface ReportRepository extends Repository<Report, Long> {
  Report save(Report report);

  Report findById(Long id);

  List<Report> findAll();

  void deleteById(Long id);

  void deleteAll();
}
