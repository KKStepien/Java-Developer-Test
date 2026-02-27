package com.example.javadevelopertest.report;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
  void shouldReturnNotFoundWhenReportDoesNotExist() throws Exception {
    given(reportRepository.findById(999L)).willReturn(java.util.Optional.empty());

    mvc.perform(get("/api/report/999")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnReportById() throws Exception {
    Report report = new Report();
    report.setId(200L);
    report.setCharacterPhrase("Luke");
    report.setPlanetName("Tatooine");
    report.setResult(new java.util.ArrayList<>());

    given(reportRepository.findById(200L)).willReturn(java.util.Optional.of(report));

    mvc.perform(get("/api/report/200")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void shouldCreate() throws Exception {
    given(reportRepository.existsById(200L)).willReturn(false);

    mvc.perform(put("/api/report/200")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"id\": 200," +
                 "\"characterPhrase\": \"Luke\"," +
                 " \"planetName\": \"Tatooine\"}")
    )
        .andExpect(status().isOk());

    verify(reportRepository).existsById(200L);
    verify(reportService).create("Luke", "Tatooine", 200L);
  }

  @Test
  void shouldNotCreateWhenCharacterPhraseIsNull() throws Exception {
    given(reportService.create(isNull(), anyString(), anyLong()))
        .willThrow(IllegalArgumentException.class);

    mvc.perform(put("/api/report/200")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"id\": 200," +
                 "\"characterPhrase\": null," +
                 " \"planetName\": \"Tatooine\"}")
    )
        .andExpect(status().isBadRequest());

    verify(reportService).create(null, "Tatooine", 200L);
  }

  @Test
  void shouldNotCreateWhenCharacterPhraseIsEmpty() throws Exception {
    given(reportService.create(anyString(), anyString(), anyLong()))
        .willThrow(IllegalArgumentException.class);

    mvc.perform(put("/api/report/200")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"id\": 200," +
                 "\"characterPhrase\": \"\"," +
                 " \"planetName\": \"Tatooine\"}")
    )
        .andExpect(status().isBadRequest());

    verify(reportService).create("", "Tatooine", 200L);
  }

  @Test
  void shouldNotCreateWhenPlanetNameIsNull() throws Exception {
    given(reportService.create(anyString(), isNull(), anyLong()))
        .willThrow(IllegalArgumentException.class);

    mvc.perform(put("/api/report/200")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"id\": 200," +
                 "\"characterPhrase\": \"Luke\"," +
                 " \"planetName\": null}")
    )
        .andExpect(status().isBadRequest());

    verify(reportService).create("Luke", null, 200L);
  }

  @Test
  void shouldNotCreateWhenPlanetNameIsEmpty() throws Exception {
    given(reportService.create(anyString(), anyString(), anyLong()))
        .willThrow(IllegalArgumentException.class);

    mvc.perform(put("/api/report/200")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"id\": 200," +
                 "\"characterPhrase\": \"Luke\"," +
                 " \"planetName\": \"\"}")
    )
        .andExpect(status().isBadRequest());

    verify(reportService).create("Luke", "", 200L);
  }

  @Test
  void shouldUpdate() throws Exception {
    given(reportRepository.existsById(200L))
        .willReturn(true);

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
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldNotUpdateWhenUpdateCharacterPhraseIsNull() throws Exception{
    given(reportService.update(any(UpdateReport.class), anyLong()))
        .willThrow(IllegalArgumentException.class);

    given(reportRepository.existsById(200L))
        .willReturn(true);

    mvc.perform(put("/api/report/200")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"id\": 200," +
                 "\"characterPhrase\": null," +
                 " \"planetName\": \"Tatooine\"}")
    )
        .andExpect(status().isBadRequest());

    verify(reportService).update(updateReportArgumentCaptor.capture(), eq(200L));

    UpdateReport updateReport = updateReportArgumentCaptor.getValue();
    assertThat(updateReport.getCharacterPhrase()).isEqualTo(null);
    assertThat(updateReport.getPlanetName()).isEqualTo("Tatooine");
  }

  @Test
  void shouldNotUpdateWhenUpdateCharacterPhraseIsEmpty() throws Exception{
    given(reportService.update(any(UpdateReport.class), anyLong()))
        .willThrow(IllegalArgumentException.class);

    given(reportRepository.existsById(200L))
        .willReturn(true);

    mvc.perform(put("/api/report/200")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"id\": 200," +
                 "\"characterPhrase\": \"\"," +
                 " \"planetName\": \"Tatooine\"}")
    )
        .andExpect(status().isBadRequest());

    verify(reportService).update(updateReportArgumentCaptor.capture(), eq(200L));

    UpdateReport updateReport = updateReportArgumentCaptor.getValue();
    assertThat(updateReport.getCharacterPhrase()).isEqualTo("");
    assertThat(updateReport.getPlanetName()).isEqualTo("Tatooine");
  }

  @Test
  void shouldNotUpdateWhenUpdatePlanetNameIsNull() throws Exception{
    given(reportService.update(any(UpdateReport.class), anyLong()))
        .willThrow(IllegalArgumentException.class);

    given(reportRepository.existsById(200L))
        .willReturn(true);

    mvc.perform(put("/api/report/200")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"id\": 200," +
                 "\"characterPhrase\": \"Luke\"," +
                 " \"planetName\": null}")
    )
        .andExpect(status().isBadRequest());

    verify(reportService).update(updateReportArgumentCaptor.capture(), eq(200L));

    UpdateReport updateReport = updateReportArgumentCaptor.getValue();
    assertThat(updateReport.getCharacterPhrase()).isEqualTo("Luke");
    assertThat(updateReport.getPlanetName()).isEqualTo(null);
  }

  @Test
  void shouldNotUpdateWhenUpdatePlanetNameIsEmpty() throws Exception{
    given(reportService.update(any(UpdateReport.class), anyLong()))
        .willThrow(IllegalArgumentException.class);

    given(reportRepository.existsById(200L))
        .willReturn(true);

    mvc.perform(put("/api/report/200")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"id\": 200," +
                 "\"characterPhrase\": \"Luke\"," +
                 " \"planetName\": \"\"}")
    )
        .andExpect(status().isBadRequest());

    verify(reportService).update(updateReportArgumentCaptor.capture(), eq(200L));

    UpdateReport updateReport = updateReportArgumentCaptor.getValue();
    assertThat(updateReport.getCharacterPhrase()).isEqualTo("Luke");
    assertThat(updateReport.getPlanetName()).isEqualTo("");
  }
}
