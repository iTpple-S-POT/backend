package org.com.itpple.spot.server.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.com.itpple.spot.server.constant.PotType;
import org.com.itpple.spot.server.dto.PointDTO;
import org.com.itpple.spot.server.dto.pot.request.CreatePotRequest;
import org.com.itpple.spot.server.dto.pot.request.UploadImageRequest;
import org.com.itpple.spot.server.dto.pot.response.CreatePotResponse;
import org.com.itpple.spot.server.dto.pot.response.UploadImageResponse;
import org.com.itpple.spot.server.service.PotService;
import org.com.itpple.spot.server.util.AuthUserUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = PotController.class)
class PotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PotService potService;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void uploadImageUsingPreSignedUrl_Invalid_Image_Extension() throws Exception {
        final var url = "/pot/image/pre-signed-url";
        final var uploadImageRequest = new UploadImageRequest("test.txt");

        final ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(AuthUserUtil.getUserDetailsCustom()))
                .with(csrf())
                .content(objectMapper.writeValueAsString(uploadImageRequest)));

        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void uploadImageUsingPreSignedUrl_Success() throws Exception {
        final var url = "/pot/image/pre-signed-url";
        final var uploadImageRequest = new UploadImageRequest("test.jpg");
        final var userDetailsCustom = AuthUserUtil.getUserDetailsCustom();

        doReturn(UploadImageResponse.of(null, null)).when(potService)
                .uploadImage(any(), anyString());
        final ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(userDetailsCustom))
                .with(csrf())
                .content(objectMapper.writeValueAsString(uploadImageRequest)));

        resultActions.andExpect(status().isOk());
    }

    @Test
    void createPot_Invalid_Image_Extension() throws Exception {
        final var url = "/pot";
        final var createPotRequest = new CreatePotRequest(
                1L, "test.txt", PotType.IMAGE, new PointDTO(0.0, 0.0), null);

        final ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(AuthUserUtil.getUserDetailsCustom()))
                .with(csrf())
                .content(objectMapper.writeValueAsString(createPotRequest)));

        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void createPot_Success() throws Exception {
        final var url = "/pot";
        final var createPotRequest = new CreatePotRequest(
                1L, "test.jpg", PotType.IMAGE, new PointDTO(0.0, 0.0), null);

        doReturn(CreatePotResponse.builder()
                .build()).when(potService).createPot(any(), any(CreatePotRequest.class));
        final ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(AuthUserUtil.getUserDetailsCustom()))
                .with(csrf())
                .content(objectMapper.writeValueAsString(createPotRequest))
        );

        resultActions.andExpect(status().isOk());
    }
}