package org.com.itpple.spot.server.domain.reaction.service;

import org.assertj.core.api.Assertions;
import org.com.itpple.spot.server.domain.pot.entity.Pot;
import org.com.itpple.spot.server.domain.pot.repository.PotRepository;
import org.com.itpple.spot.server.domain.reaction.dto.request.CreateReactionRequest;
import org.com.itpple.spot.server.domain.reaction.dto.response.CreateReactionResponse;
import org.com.itpple.spot.server.domain.reaction.entity.Reaction;
import org.com.itpple.spot.server.domain.reaction.exception.AddMultipleReactionException;
import org.com.itpple.spot.server.domain.reaction.exception.ReactionIdNotFoundException;
import org.com.itpple.spot.server.domain.reaction.repository.ReactionRepository;
import org.com.itpple.spot.server.domain.reaction.service.impl.ReactionServiceImpl;
import org.com.itpple.spot.server.domain.user.entity.User;
import org.com.itpple.spot.server.domain.user.repository.UserRepository;
import org.com.itpple.spot.server.global.common.constant.ReactionType;
import org.com.itpple.spot.server.util.PotTestUtil;
import org.com.itpple.spot.server.util.ReactionTestUtil;
import org.com.itpple.spot.server.util.UserTestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ReactionServiceTest {

    @InjectMocks
    private ReactionServiceImpl sut;

    @Mock
    private ReactionRepository reactionRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PotRepository potRepository;

    private final ReactionType REACTION_TYPE = ReactionType.GOOD;

    @Test
    void 반응_타입을_입력받아_POT에_반응을_추가한다() {
        // given
        User user = UserTestUtil.create();
        Pot pot = PotTestUtil.create();
        Reaction expectSavedReaction = ReactionTestUtil.create(REACTION_TYPE);
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(potRepository.findById(anyLong())).willReturn(Optional.of(pot));
        given(reactionRepository.existsByPotAndUser(pot, user)).willReturn(false);
        given(reactionRepository.save(any(Reaction.class))).willReturn(expectSavedReaction);

        // when
        CreateReactionResponse actualSavedReaction = sut.addReaction(anyLong(), createReactionRequest(REACTION_TYPE));

        // then
        assertThat(actualSavedReaction.reactionType()).isEqualTo(expectSavedReaction.getReactionType());
    }

    @Test
    void 반응을_추가했던_POT에_추가로_반응을_남기려고_하면_에러가_발생한다() {
        // given
        User user = UserTestUtil.create();
        Pot pot = PotTestUtil.create();
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(potRepository.findById(anyLong())).willReturn(Optional.of(pot));
        given(reactionRepository.existsByPotAndUser(pot, user)).willReturn(true);

        // when
        Throwable throwable = Assertions.catchThrowable(
            () -> sut.addReaction(anyLong(), createReactionRequest(REACTION_TYPE)));

        // then
        assertThat(throwable).isInstanceOf(AddMultipleReactionException.class);
    }

    @Test
    void 삭제할_반응의_PK와_요청을_보낸_사용자의_PK를_받아_반응을_삭제한다() {
        // given
        Long userId = 1L;
        User user = UserTestUtil.create();
        Pot pot = PotTestUtil.create();
        Reaction reaction = ReactionTestUtil.create(pot, user, REACTION_TYPE, false);
        given(userRepository.existsById(anyLong())).willReturn(true);
        given(reactionRepository.findByIdAndUserId(anyLong(), anyLong())).willReturn(Optional.of(reaction));
        willDoNothing().given(reactionRepository).delete(any(Reaction.class));

        // when
        sut.deleteReaction(userId, anyLong());

        // then
        then(reactionRepository).should().delete(any(Reaction.class));
    }

    @Test
    void 반응_삭제_요청을_보낸_사용자가_추가한_반응이_아니라면_반응_조회에_실패한다() {
        // given
        Long reactionId = 1L;
        Long wrongUserId = 50L;
        given(userRepository.existsById(wrongUserId)).willReturn(true);
        given(reactionRepository.findByIdAndUserId(reactionId, wrongUserId)).willReturn(Optional.empty());

        // when
        Throwable throwable = Assertions.catchThrowable(() -> sut.deleteReaction(wrongUserId, reactionId));

        // then
        assertThat(throwable).isInstanceOf(ReactionIdNotFoundException.class);
    }

    private CreateReactionRequest createReactionRequest(ReactionType reactionType) {
        return new CreateReactionRequest(1L, String.valueOf(reactionType));
    }
}
