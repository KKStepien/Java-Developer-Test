package com.example.javadevelopertest.report;

import com.example.javadevelopertest.reportResult.ReportResultService;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {
  @Mock
  private ReportRepository reportRepository;

  @Mock
  private ReportResultService reportResultService;

  @InjectMocks
  private ReportService reportService;

  @Test
  void shouldCreate() {
    given(reportRepository.save(any(Report.class)))
        .willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

    Report result = reportService.create("Luke", "Tatooine", 100L);

    verify(reportRepository, times(2)).save(result);
    assertThat(result.getCharacterPhrase()).isEqualTo("Luke");
    assertThat(result.getPlanetName()).isEqualTo("Tatooine");
    assertThat(result.getId()).isEqualTo(100L);
  }

  @Test
  void shouldNotCreateWhenCharacterPhraseIsNull(){
    ThrowableAssert.ThrowingCallable create = () -> reportService.create(null, "Tatooine", 100L);
    assertThatThrownBy(create)
        .hasMessage("Character phrase is null or empty");
  }

  @Test
  void shouldNotCreateWhenCharacterPhraseIsEmpty(){
    ThrowableAssert.ThrowingCallable create = () -> reportService.create("", "Tatooine", 100L);
    assertThatThrownBy(create)
        .hasMessage("Character phrase is null or empty");
  }

  @Test
  void shouldNotCreateWhenPlanetNameIsNull(){
    ThrowableAssert.ThrowingCallable create = () -> reportService.create("Luke", null, 100L);
    assertThatThrownBy(create)
        .hasMessage("Planet name is null or empty");
  }

  @Test
  void shouldNotCreateWhenPlanetNameIsEmpty(){
    ThrowableAssert.ThrowingCallable create = () -> reportService.create("Luke", "", 100L);
    assertThatThrownBy(create)
        .hasMessage("Planet name is null or empty");
  }

  @Test
  void shouldUpdate(){
    Report report = new Report();
    given(reportRepository.findById(100L)).willReturn(report);
    //given(reportRepository.save(any(Report.class)))
      //  .willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

    UpdateReport updateReport = new UpdateReport();
    updateReport.setCharacterPhrase("Luke");
    updateReport.setPlanetName("Tatooine");

    reportService.update(updateReport, 100L);

    verify(reportRepository).save(report);
    assertThat(report.getCharacterPhrase()).isEqualTo("Luke");
    assertThat(report.getPlanetName()).isEqualTo("Tatooine");
  }


  @Test
  void shouldNotUpdateWhenUpdateReportIsNull(){
    ThrowableAssert.ThrowingCallable update = () -> reportService.update(null, 100L);
    assertThatThrownBy(update)
        .hasMessage("UpdateReport is null");
  }

  @Test
  void shouldNotUpdateWhenCharacterPhraseIsNull(){
    UpdateReport updateReport = new UpdateReport();
    updateReport.setCharacterPhrase(null);
    updateReport.setPlanetName("Tatooine");
    ThrowableAssert.ThrowingCallable update = () -> reportService.update(updateReport, 100L);
    assertThatThrownBy(update)
        .hasMessage("Character phrase is null or empty");
  }

  @Test
  void shouldNotUpdateWhenCharacterPhraseIsEmpty(){
    UpdateReport updateReport = new UpdateReport();
    updateReport.setCharacterPhrase("");
    updateReport.setPlanetName("Tatooine");
    ThrowableAssert.ThrowingCallable update = () -> reportService.update(updateReport, 100L);
    assertThatThrownBy(update)
        .hasMessage("Character phrase is null or empty");
  }

  @Test
  void shouldNotUpdateWhenPlanetNameIsNull(){
    UpdateReport updateReport = new UpdateReport();
    updateReport.setCharacterPhrase("Luke");
    updateReport.setPlanetName(null);
    ThrowableAssert.ThrowingCallable update = () -> reportService.update(updateReport, 100L);
    assertThatThrownBy(update)
        .hasMessage("Planet name is null or empty");
  }

  @Test
  void shouldNotUpdateWhenPlanetNameIsEmpty(){
    UpdateReport updateReport = new UpdateReport();
    updateReport.setCharacterPhrase("Luke");
    updateReport.setPlanetName("");
    ThrowableAssert.ThrowingCallable update = () -> reportService.update(updateReport, 100L);
    assertThatThrownBy(update)
        .hasMessage("Planet name is null or empty");
  }

}
