package org.com.itpple.spot.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.dto.comment.CommentDto;
import org.com.itpple.spot.server.dto.comment.request.CreateCommentRequest;
import org.com.itpple.spot.server.dto.comment.response.CreateCommentResponse;
import org.com.itpple.spot.server.entity.Comment;
import org.com.itpple.spot.server.entity.Pot;
import org.com.itpple.spot.server.entity.User;
import org.com.itpple.spot.server.exception.comment.ParentCommentNotFoundException;
import org.com.itpple.spot.server.exception.comment.CommentPotNotMatchException;
import org.com.itpple.spot.server.exception.pot.PotIdNotFoundException;
import org.com.itpple.spot.server.exception.user.UserIdNotFoundException;
import org.com.itpple.spot.server.repository.CommentRepository;
import org.com.itpple.spot.server.repository.PotRepository;
import org.com.itpple.spot.server.repository.UserRepository;
import org.com.itpple.spot.server.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PotRepository potRepository;

    @Transactional
    @Override
    public CreateCommentResponse addComment(Long userId, Long potId, CreateCommentRequest request) {
        User writer = userRepository.findById(userId)
            .orElseThrow(() -> new UserIdNotFoundException("PK = " + userId));

        Pot pot = potRepository.findById(potId)
            .orElseThrow(() -> new PotIdNotFoundException("PK = " + potId));

        Comment comment = Comment.builder()
            .writer(writer)
            .pot(pot)
            .parentComment(getParentComment(potId, request.parentCommentId()))
            .content(request.content())
            .build();

        Comment savedComment = commentRepository.save(comment);

        return CreateCommentResponse.from(writer, savedComment);
    }

    @Override
    public List<CommentDto> getCommentList(Long userId, Long potId) {
        if (!userRepository.existsById(userId)) {
            throw new UserIdNotFoundException("PK = " + userId);
        }

        if (!potRepository.existsById(potId)) {
            throw new PotIdNotFoundException("PK = " + userId);
        }

        List<Comment> commentList = commentRepository.findAllComment(potId);

        return convertToHierarchy(commentList);
    }

    private Comment getParentComment(Long potId, Long parentCommentId) {
        Comment parentComment = null;

        if (parentCommentId != null) {
            parentComment = commentRepository.findByIdWithPot(parentCommentId)
                .orElseThrow(() -> new ParentCommentNotFoundException("PK = " + parentCommentId));
            if (parentComment.getPot().getId() != potId) {
                throw new CommentPotNotMatchException();
            }
        }

        return parentComment;
    }

    private List<CommentDto> convertToHierarchy(List<Comment> commentList) {
        List<CommentDto> result = new ArrayList<>();
        Map<Long, CommentDto> commentDtoHashMap = new HashMap<>();

        for (Comment comment : commentList) {
            CommentDto commentDto = CommentDto.from(comment);
            commentDtoHashMap.put(commentDto.getCommentId(), commentDto);

            Comment parentComment = comment.getParentComment();

            if (parentComment != null) {
                commentDtoHashMap.get(parentComment.getId())
                    .getChildrenComments()
                    .add(commentDto);
            } else {
                result.add(commentDto);
            }
        }

        return result;
    }
}
