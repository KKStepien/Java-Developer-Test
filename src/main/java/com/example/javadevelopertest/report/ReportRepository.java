package com.example.javadevelopertest.report;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends Repository<Report, Long> {
  Report save(Report report);

  Optional<Report> findById(Long id);

  boolean existsById(Long id);

  List<Report> findAll();

  void deleteById(Long id);

  void deleteAll();
}
