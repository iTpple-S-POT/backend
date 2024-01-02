package org.com.itpple.spot.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.constant.ReactionType;
import org.com.itpple.spot.server.dto.reaction.request.CreateReactionRequest;
import org.com.itpple.spot.server.dto.reaction.response.CreateReactionResponse;
import org.com.itpple.spot.server.entity.Pot;
import org.com.itpple.spot.server.entity.Reaction;
import org.com.itpple.spot.server.entity.User;
import org.com.itpple.spot.server.exception.pot.PotIdNotFoundException;
import org.com.itpple.spot.server.exception.reaction.AddMultipleReactionException;
import org.com.itpple.spot.server.exception.user.UserIdNotFoundException;
import org.com.itpple.spot.server.repository.PotRepository;
import org.com.itpple.spot.server.repository.ReactionRepository;
import org.com.itpple.spot.server.repository.UserRepository;
import org.com.itpple.spot.server.service.ReactionService;
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
}
