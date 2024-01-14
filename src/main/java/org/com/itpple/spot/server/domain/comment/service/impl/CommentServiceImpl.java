package org.com.itpple.spot.server.domain.comment.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.domain.comment.dto.CommentDto;
import org.com.itpple.spot.server.domain.comment.dto.request.CreateCommentRequest;
import org.com.itpple.spot.server.domain.comment.dto.request.UpdateCommentRequest;
import org.com.itpple.spot.server.domain.comment.dto.response.CreateCommentResponse;
import org.com.itpple.spot.server.domain.comment.entity.Comment;
import org.com.itpple.spot.server.domain.comment.exception.CommentIdNotFoundException;
import org.com.itpple.spot.server.domain.comment.exception.CommentWriterNotMatchException;
import org.com.itpple.spot.server.domain.pot.entity.Pot;
import org.com.itpple.spot.server.domain.user.entity.User;
import org.com.itpple.spot.server.domain.comment.exception.ParentCommentNotFoundException;
import org.com.itpple.spot.server.domain.comment.exception.CommentPotNotMatchException;
import org.com.itpple.spot.server.domain.pot.exception.PotIdNotFoundException;
import org.com.itpple.spot.server.domain.user.exception.UserIdNotFoundException;
import org.com.itpple.spot.server.domain.comment.repository.CommentRepository;
import org.com.itpple.spot.server.domain.pot.repository.PotRepository;
import org.com.itpple.spot.server.domain.user.repository.UserRepository;
import org.com.itpple.spot.server.domain.comment.service.CommentService;
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

        return CreateCommentResponse.from(savedComment);
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

    @Transactional
    @Override
    public void updateComment(
        Long userId, Long commentId, UpdateCommentRequest request
    ) {
        if (!userRepository.existsById(userId)) {
            throw new UserIdNotFoundException("PK = " + userId);
        }

        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new CommentIdNotFoundException("PK = " + commentId));

        if (comment.getWriter().getId() != userId) {
            throw new CommentWriterNotMatchException();
        }

        comment.updateContent(request.content());
    }

    private Comment getParentComment(Long potId, Long parentCommentId) {
        Comment parentComment = null;

        if (parentCommentId != null) {
            parentComment = commentRepository.findById(parentCommentId)
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
