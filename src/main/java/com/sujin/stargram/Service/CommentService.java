package com.sujin.stargram.Service;

import com.sujin.stargram.domain.Comment;
import com.sujin.stargram.domain.Image;
import com.sujin.stargram.domain.User;
import com.sujin.stargram.handler.ex.CustomApiException;
import com.sujin.stargram.repository.CommentRepository;
import com.sujin.stargram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public Comment newComment(String content, long imageId, long userId) {

        //TIP (객체를 만들때 id값만 담아서 insert 할 수 있다.
        // 대신 return 시에 image객체와 user객체는 id값만 가지고 있는 빈 객체를 리턴받는다.
        Image image = new Image();
        image.setId(imageId);
        User userEntity = userRepository.findById(userId).orElseThrow(() ->
        {
            throw  new CustomApiException("유저 아이디를 찾을 수 없습니다.");
        });

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setImage(image);
        comment.setUser(userEntity);

        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(long id) {
        try{
            commentRepository.deleteById(id);
        }catch (Exception e){
            throw new CustomApiException(e.getMessage());
        }
    }
}
