package org.com.itpple.spot.server.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.com.itpple.spot.server.util.GeometryUtil.createPoint;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.List;
import org.com.itpple.spot.server.constant.PotType;
import org.com.itpple.spot.server.constant.Role;
import org.com.itpple.spot.server.dto.Location;
import org.com.itpple.spot.server.dto.pot.SearchCondition.CircleSearchRange;
import org.com.itpple.spot.server.dto.pot.SearchCondition.SearchRange;
import org.com.itpple.spot.server.entity.Category;
import org.com.itpple.spot.server.entity.Pot;
import org.com.itpple.spot.server.entity.User;
import org.com.itpple.spot.server.exception.GlobalExceptionHandler;
import org.com.itpple.spot.server.repository.PotRepository;
import org.com.itpple.spot.server.service.impl.PotServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Polygon;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class PotServiceTest {

    MockMvc mockMvc;


    @InjectMocks
    PotServiceImpl target;

    @Mock
    PotRepository potRepository;

    User user = User.builder().socialId("test_1234").role(Role.USER).build();
    Category category = Category.builder().id(1L).name("test").build();
    Category category2 = Category.builder().id(2L).name("test2").build();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(target)
                .setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    public void POT_리스트_조회하기() {
        //given
        when(potRepository.findAll()).thenReturn(List.of(
                Pot.builder().id(1L).user(user).category(category).imageKey("test.jpg").potType(
                        PotType.IMAGE).location(createPoint(new Location(2.0, 2.0))).build(),
                Pot.builder().id(2L).user(user).category(category).imageKey("test.jpg").potType(
                        PotType.IMAGE).location(createPoint(new Location(2.0, 2.0))).build(),
                Pot.builder().id(3L).user(user).category(category).imageKey("test.jpg").potType(
                        PotType.IMAGE).location(createPoint(new Location(2.0, 2.0))).build()
        ));
        SearchRange searchRange = new CircleSearchRange(1.0, new Location(2.0, 2.0));
        Long categoryId = 1L;

        //when
        final var result = target.getPotListWithoutExpired(searchRange, categoryId);

        //then
        assertThat(result).hasSize(3);
    }

    @Test
    public void POT_리스트_조회하기_범위() {
        //given
        when(potRepository.findByLocationAndCategory(any(Polygon.class), anyLong())).thenReturn(
                List.of(
                        Pot.builder().id(1L).user(user).category(category).imageKey("test.jpg")
                                .potType(
                                        PotType.IMAGE).location(createPoint(new Location(1.0, 2.0)))
                                .build()
                ));
        SearchRange searchRange = new CircleSearchRange(1.0, new Location(2.0, 2.0));
        Long categoryId = 1L;

        //when
        final var result = target.getPotList(searchRange, categoryId);

        //then
        assertThat(result).hasSize(1);
    }

}
