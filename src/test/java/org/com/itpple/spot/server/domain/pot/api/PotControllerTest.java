package org.com.itpple.spot.server.domain.pot.api;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.com.itpple.spot.server.domain.location.dto.PointDTO;
import org.com.itpple.spot.server.domain.pot.domain.hashtag.service.HashtagService;
import org.com.itpple.spot.server.domain.pot.dto.PotDTO;
import org.com.itpple.spot.server.domain.pot.dto.SearchCondition.SearchRange;
import org.com.itpple.spot.server.domain.pot.dto.request.CreatePotRequest;
import org.com.itpple.spot.server.domain.pot.dto.request.UploadImageRequest;
import org.com.itpple.spot.server.domain.pot.dto.response.CreatePotResponse;
import org.com.itpple.spot.server.domain.pot.dto.response.UploadImageResponse;
import org.com.itpple.spot.server.domain.pot.service.PotService;
import org.com.itpple.spot.server.global.auth.util.AuthUserUtil;
import org.com.itpple.spot.server.global.common.constant.PotType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = PotController.class)
class PotControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PotService potService;
    @MockBean
    private HashtagService hashTagService;

    @Test
    void uploadImageUsingPreSignedUrl_Invalid_Image_Extension() throws Exception {
        final var url = "/api/v1/pot/image/pre-signed-url";
        final var uploadImageRequest = new UploadImageRequest("test.txt");

        final ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(AuthUserUtil.getCustomUserDetails()))
                .with(csrf())
                .content(objectMapper.writeValueAsString(uploadImageRequest)));

        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void uploadImageUsingPreSignedUrl_Success() throws Exception {
        final var url = "/api/v1/pot/image/pre-signed-url";
        final var uploadImageRequest = new UploadImageRequest("test.jpg");
        final var customUserDetails = AuthUserUtil.getCustomUserDetails();

        doReturn(UploadImageResponse.of(null, null)).when(potService)
                .uploadImage(any(), anyString());
        final ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(customUserDetails))
                .with(csrf())
                .content(objectMapper.writeValueAsString(uploadImageRequest)));

        resultActions.andExpect(status().isOk());
    }

    @Test
    void createPot_Invalid_Image_Extension() throws Exception {
        final var url = "/api/v1/pot";
        final var createPotRequest = new CreatePotRequest(
                1L, "test.txt", PotType.IMAGE, new PointDTO(0.0, 0.0), null, List.of());

        final ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(AuthUserUtil.getCustomUserDetails()))
                .with(csrf())
                .content(objectMapper.writeValueAsString(createPotRequest)));

        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void createPot_Success() throws Exception {
        final var url = "/api/v1/pot";
        final var createPotRequest = new CreatePotRequest(
                1L, "test.jpg", PotType.IMAGE, new PointDTO(0.0, 0.0), null, List.of());

        doReturn(CreatePotResponse.builder()
                .build()).when(potService).createPot(any(), any(CreatePotRequest.class));
        final ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(AuthUserUtil.getCustomUserDetails()))
                .with(csrf())
                .content(objectMapper.writeValueAsString(createPotRequest))
        );

        resultActions.andExpect(status().isCreated());
    }

    private final List<PotDTO> potDTOList = (List.of(
            new PotDTO(1L, 1L, List.of(1L), PotType.IMAGE, null, new PointDTO(2.0, 2.0), "test.jpg",
                    LocalDateTime.now().plusDays(1), List.of()),
            new PotDTO(2L, 1L, List.of(1L), PotType.IMAGE, null, new PointDTO(2.0, 2.0), "test.jpg",
                    LocalDateTime.now().plusDays(1), List.of()),
            new PotDTO(3L, 1L, List.of(1L), PotType.IMAGE, null, new PointDTO(2.0, 2.0), "test.jpg",
                    LocalDateTime.now().plusDays(1), List.of())
    ));

    @Test
    void getPotList_Success_no_type() throws Exception {
        final var url = "/api/v1/pot";

        when(potService.getPotList(any(SearchRange.class), anyLong(), any())).thenReturn(potDTOList);
        final ResultActions resultActions = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(AuthUserUtil.getCustomUserDetails()))
                .with(csrf())
                .queryParam("categoryId", String.valueOf(1L))
                .queryParam("radius", String.valueOf(1.0))
                .queryParam("lat", String.valueOf(2.0))
                .queryParam("lon", String.valueOf(2.0))
        );

        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void getPotList_Success_circle() throws Exception {
        final var url = "/api/v1/pot";

        when(potService.getPotList(any(SearchRange.class), anyLong(), any())).thenReturn(potDTOList);
        final ResultActions resultActions = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(AuthUserUtil.getCustomUserDetails()))
                .with(csrf())
                .queryParam("type", "CIRCLE")
                .queryParam("categoryId", String.valueOf(1L))
                .queryParam("diameterInMeters", String.valueOf(1.0))
                .queryParam("lat", String.valueOf(2.0))
                .queryParam("lon", String.valueOf(2.0))
        );

        resultActions.andExpect(status().isOk());
    }

    @Test
    void getPotList_Fail_Rectangle_Min_Size() throws Exception {
        final var url = "/api/v1/pot";

        when(potService.getPotList(any(SearchRange.class), anyLong(), any())).thenReturn(potDTOList);
        final ResultActions resultActions = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(AuthUserUtil.getCustomUserDetails()))
                .with(csrf())
                .queryParam("type", "RECTANGLE")
                .queryParam("categoryId", String.valueOf(1L))
                .queryParam("locations", "0.0,0.0")
                .queryParam("locations", "2.0,0.0")
                .queryParam("locations", "2.0,2.0")
        );

        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void getPotList_Success_Rectangle() throws Exception {
        final var url = "/api/v1/pot";

        when(potService.getPotList(any(SearchRange.class), anyLong(), any())).thenReturn(potDTOList);
        final ResultActions resultActions = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(AuthUserUtil.getCustomUserDetails()))
                .with(csrf())
                .queryParam("type", "RECTANGLE")
                .queryParam("categoryId", String.valueOf(1L))
                .queryParam("locations", "0.0,0.0")
                .queryParam("locations", "2.0,0.0")
                .queryParam("locations", "2.0,2.0")
                .queryParam("locations", "0.0,2.0")
        );

        resultActions.andExpect(status().isOk());
    }

    @Test
    void getPotList_Failed_Rectangle_distance_max() throws Exception {
        final var url = "/api/v1/pot";

        when(potService.getPotList(any(SearchRange.class), anyLong(), any())).thenReturn(potDTOList);
        final ResultActions resultActions = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(AuthUserUtil.getCustomUserDetails()))
                .with(csrf())
                .queryParam("type", "CIRCLE")
                .queryParam("categoryId", String.valueOf(1L))
                .queryParam("diameterInMeters", String.valueOf(3002))
                .queryParam("lat", String.valueOf(2.0))
                .queryParam("lon", String.valueOf(2.0))
        );

        resultActions.andExpect(status().isBadRequest());
    }
}