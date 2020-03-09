package com.example.javadevelopertest.report;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportController.class)
public class ReportControllerTest {
  @Autowired
  private MockMvc mvc;

  @MockBean
  private ReportService reportService;
  @MockBean
  private ReportRepository reportRepository;

  @Captor
  private ArgumentCaptor<UpdateReport> updateReportArgumentCaptor;

  @Test
  void shouldCreate() throws Exception {
    mvc.perform(put("/api/report/200")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"id\": 200," +
                 "\"characterPhrase\": \"Luke\"," +
                 " \"planetName\": \"Tatooine\"}")
    )
        .andExpect(status().isOk());

    verify(reportService).create("Luke", "Tatooine", 200L);
  }

  @Test
  void shouldNotCreateWhenCharacterPhraseIsNull() throws Exception {
    given(reportService.create(isNull(), anyString(), anyLong()))
        .willThrow(RuntimeException.class);

    mvc.perform(put("/api/report/200")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"id\": 200," +
                 "\"characterPhrase\": null," +
                 " \"planetName\": \"Tatooine\"}")
    )
        .andExpect(status().isInternalServerError());

    verify(reportService).create(null, "Tatooine", 200L);
  }

  @Test
  void shouldNotCreateWhenCharacterPhraseIsEmpty() throws Exception {
    given(reportService.create(anyString(), anyString(), anyLong()))
        .willThrow(RuntimeException.class);

    mvc.perform(put("/api/report/200")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"id\": 200," +
                 "\"characterPhrase\": \"\"," +
                 " \"planetName\": \"Tatooine\"}")
    )
        .andExpect(status().isInternalServerError());

    verify(reportService).create("", "Tatooine", 200L);
  }

  @Test
  void shouldNotCreateWhenPlanetNameIsNull() throws Exception {
    given(reportService.create(anyString(), isNull(), anyLong()))
        .willThrow(RuntimeException.class);

    mvc.perform(put("/api/report/200")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"id\": 200," +
                 "\"characterPhrase\": \"Luke\"," +
                 " \"planetName\": null}")
    )
        .andExpect(status().isInternalServerError());

    verify(reportService).create("Luke", null, 200L);
  }

  @Test
  void shouldNotCreateWhenPlanetNameIsEmpty() throws Exception {
    given(reportService.create(anyString(), anyString(), anyLong()))
        .willThrow(RuntimeException.class);

    mvc.perform(put("/api/report/200")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"id\": 200," +
                 "\"characterPhrase\": \"Luke\"," +
                 " \"planetName\": \"\"}")
    )
        .andExpect(status().isInternalServerError());

    verify(reportService).create("Luke", "", 200L);
  }

  @Test
  void shouldUpdate() throws Exception {
    List<Report> mockedReports = new ArrayList<>();
    Report report = new Report();
    report.setId(200L);
    mockedReports.add(report);
    given(reportRepository.findAll())
        .willReturn(mockedReports);

    mvc.perform(put("/api/report/200")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"id\": 200," +
                 "\"characterPhrase\": \"Luke\"," +
                 " \"planetName\": \"Tatooine\"}")
    )
        .andExpect(status().isOk());

    verify(reportService).update(updateReportArgumentCaptor.capture(), eq(200L));

    UpdateReport updateReport = updateReportArgumentCaptor.getValue();
    assertThat(updateReport.getCharacterPhrase()).isEqualTo("Luke");
    assertThat(updateReport.getPlanetName()).isEqualTo("Tatooine");
  }

  @Test
  void shouldNotUpdateWhenUpdateReportIsNull() throws Exception{
    mvc.perform(put("/api/report/200")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)

    )
        .andExpect(status().isInternalServerError());
  }

  @Test
  void shouldNotUpdateWhenUpdateCharacterPhraseIsNull() throws Exception{
    given(reportService.update(any(UpdateReport.class), anyLong()))
        .willThrow(RuntimeException.class);

    List<Report> mockedReports = new ArrayList<>();
    Report report = new Report();
    report.setId(200L);
    mockedReports.add(report);
    given(reportRepository.findAll())
        .willReturn(mockedReports);

    mvc.perform(put("/api/report/200")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"id\": 200," +
                 "\"characterPhrase\": null," +
                 " \"planetName\": \"Tatooine\"}")
    )
        .andExpect(status().isInternalServerError());

    verify(reportService).update(updateReportArgumentCaptor.capture(), eq(200L));

    UpdateReport updateReport = updateReportArgumentCaptor.getValue();
    assertThat(updateReport.getCharacterPhrase()).isEqualTo(null);
    assertThat(updateReport.getPlanetName()).isEqualTo("Tatooine");
  }

  @Test
  void shouldNotUpdateWhenUpdateCharacterPhraseIsEmpty() throws Exception{
    given(reportService.update(any(UpdateReport.class), anyLong()))
        .willThrow(RuntimeException.class);

    List<Report> mockedReports = new ArrayList<>();
    Report report = new Report();
    report.setId(200L);
    mockedReports.add(report);
    given(reportRepository.findAll())
        .willReturn(mockedReports);

    mvc.perform(put("/api/report/200")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"id\": 200," +
                 "\"characterPhrase\": \"\"," +
                 " \"planetName\": \"Tatooine\"}")
    )
        .andExpect(status().isInternalServerError());

    verify(reportService).update(updateReportArgumentCaptor.capture(), eq(200L));

    UpdateReport updateReport = updateReportArgumentCaptor.getValue();
    assertThat(updateReport.getCharacterPhrase()).isEqualTo("");
    assertThat(updateReport.getPlanetName()).isEqualTo("Tatooine");
  }

  @Test
  void shouldNotUpdateWhenUpdatePlanetNameIsNull() throws Exception{
    given(reportService.update(any(UpdateReport.class), anyLong()))
        .willThrow(RuntimeException.class);

    List<Report> mockedReports = new ArrayList<>();
    Report report = new Report();
    report.setId(200L);
    mockedReports.add(report);
    given(reportRepository.findAll())
        .willReturn(mockedReports);

    mvc.perform(put("/api/report/200")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"id\": 200," +
                 "\"characterPhrase\": \"Luke\"," +
                 " \"planetName\": null}")
    )
        .andExpect(status().isInternalServerError());

    verify(reportService).update(updateReportArgumentCaptor.capture(), eq(200L));

    UpdateReport updateReport = updateReportArgumentCaptor.getValue();
    assertThat(updateReport.getCharacterPhrase()).isEqualTo("Luke");
    assertThat(updateReport.getPlanetName()).isEqualTo(null);
  }

  @Test
  void shouldNotUpdateWhenUpdatePlanetNameIsEmpty() throws Exception{
    given(reportService.update(any(UpdateReport.class), anyLong()))
        .willThrow(RuntimeException.class);

    List<Report> mockedReports = new ArrayList<>();
    Report report = new Report();
    report.setId(200L);
    mockedReports.add(report);
    given(reportRepository.findAll())
        .willReturn(mockedReports);

    mvc.perform(put("/api/report/200")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"id\": 200," +
                 "\"characterPhrase\": \"Luke\"," +
                 " \"planetName\": \"\"}")
    )
        .andExpect(status().isInternalServerError());

    verify(reportService).update(updateReportArgumentCaptor.capture(), eq(200L));

    UpdateReport updateReport = updateReportArgumentCaptor.getValue();
    assertThat(updateReport.getCharacterPhrase()).isEqualTo("Luke");
    assertThat(updateReport.getPlanetName()).isEqualTo("");
  }
}
