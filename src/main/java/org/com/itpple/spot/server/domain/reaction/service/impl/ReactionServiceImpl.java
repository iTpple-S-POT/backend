package org.com.itpple.spot.server.domain.reaction.service.impl;

import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.domain.pot.entity.Pot;
import org.com.itpple.spot.server.domain.pot.exception.PotIdNotFoundException;
import org.com.itpple.spot.server.domain.pot.repository.PotRepository;
import org.com.itpple.spot.server.domain.reaction.dto.request.CreateReactionRequest;
import org.com.itpple.spot.server.domain.reaction.dto.response.CreateReactionResponse;
import org.com.itpple.spot.server.domain.reaction.entity.Reaction;
import org.com.itpple.spot.server.domain.reaction.exception.AddMultipleReactionException;
import org.com.itpple.spot.server.domain.reaction.exception.ReactionIdNotFoundException;
import org.com.itpple.spot.server.domain.reaction.repository.ReactionRepository;
import org.com.itpple.spot.server.domain.reaction.service.ReactionService;
import org.com.itpple.spot.server.domain.user.entity.User;
import org.com.itpple.spot.server.domain.user.exception.UserIdNotFoundException;
import org.com.itpple.spot.server.domain.user.repository.UserRepository;
import org.com.itpple.spot.server.global.common.constant.ReactionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReactionServiceImpl implements ReactionService {

    private final ReactionRepository reactionRepository;
    private final UserRepository userRepository;
    private final PotRepository potRepository;

    @Transactional
    @Override
    public CreateReactionResponse addReaction(Long userId, CreateReactionRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserIdNotFoundException("PK = " + userId));

        Pot pot = potRepository.findById(request.potId())
            .orElseThrow(() -> new PotIdNotFoundException("PK = " + request.potId()));

        if (reactionRepository.existsByPotAndUser(pot, user)) {
            throw new AddMultipleReactionException();
        }

        Reaction reaction = Reaction.builder()
            .pot(pot)
            .user(user)
            .reactionType(ReactionType.valueOf(request.reactionType()))
            .build();

        Reaction savedReaction = reactionRepository.save(reaction);

        return CreateReactionResponse.from(savedReaction);
    }

    @Transactional
    @Override
    public void deleteReaction(Long userId, Long reactionId) {
        if (!userRepository.existsById(userId)) {
            throw new UserIdNotFoundException("PK = " + userId);
        }

        Reaction reaction = reactionRepository.findByIdAndUserId(reactionId, userId)
            .orElseThrow(() -> new ReactionIdNotFoundException("PK = " + reactionId));

        reactionRepository.delete(reaction);
    }
}
