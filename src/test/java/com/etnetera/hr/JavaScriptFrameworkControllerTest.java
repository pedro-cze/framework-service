package com.etnetera.hr;

import com.etnetera.hr.controller.JavaScriptFrameworkController;
import com.etnetera.hr.dto.UpdateResult;
import com.etnetera.hr.entity.JavaScriptFramework;
import com.etnetera.hr.error.FrameworkNotFoundException;
import com.etnetera.hr.mapper.DomainMapper;
import com.etnetera.hr.service.JavascriptFrameworkService;
import com.etnetera.util.FileReader;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(JavaScriptFrameworkController.class)
public class JavaScriptFrameworkControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    JavascriptFrameworkService javascriptFrameworkService;
    @MockBean
    DomainMapper domainMapper;

    @Test
    @DisplayName("GET /frameworks -> 200 OK")
    public void testGetAllFrameworks_resultOk() throws Exception {
        final ResultActions resultActions = mockMvc.perform(get("/frameworks"));
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json("[]"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    @DisplayName("POST /frameworks -> 201 CREATED")
    public void testCreateFramework_expectHeader_resultCreated() throws Exception {
        final var frameworkEntityMock = mock(JavaScriptFramework.class);

        when(javascriptFrameworkService.createFramework(any())).thenReturn(frameworkEntityMock);
        when(frameworkEntityMock.getId()).thenReturn(1L);

        final ResultActions resultActions = mockMvc.perform(post("/frameworks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(FileReader.readFile("createRequest.json")));

        resultActions
                .andExpect(status().isCreated())
                .andExpect(header().string("Location","/frameworks/1"));
    }

    @Test
    @DisplayName("PUT /frameworks/{id} -> 201 CREATED")
    public void testUpdateNonExistingFramework_resultCreated() throws Exception {

        when(javascriptFrameworkService.updateFramework(anyLong(), any())).thenReturn(UpdateResult.CREATED);

        final ResultActions resultActions = mockMvc.perform(put("/frameworks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(FileReader.readFile("updateRequest.json")));

        resultActions
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("PUT /frameworks/{id} -> 204 NO_CONTENT")
    public void testUpdateExistingFramework_resultNoContent() throws Exception {
        when(javascriptFrameworkService.updateFramework(anyLong(), any())).thenReturn(UpdateResult.UPDATED_NO_CONTENT);

        final ResultActions resultActions = mockMvc.perform(put("/frameworks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(FileReader.readFile("updateRequest.json")));

        resultActions
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /frameworks/{id} -> 404 NOT_FOUND")
    public void testDeleteNonExistingFramework_resultNotFound() throws Exception {
        doThrow(FrameworkNotFoundException.class).when(javascriptFrameworkService).deleteFramework(anyLong());
        final ResultActions resultActions = mockMvc.perform(delete("/frameworks/1"));
        resultActions
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /frameworks/{id} -> 200 OK")
    public void testDeleteExistingFramework_resultOk() throws Exception {
        final ResultActions resultActions = mockMvc.perform(delete("/frameworks/1"));
        resultActions
                .andExpect(status().isNoContent());
    }
}
