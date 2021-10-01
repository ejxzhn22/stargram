package com.sujin.stargram.Service;


import com.sujin.stargram.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;

    @Transactional
    public void likes( long imageId, long principalId){
        likesRepository.mLikes(imageId,principalId);
    }

    @Transactional
    public void unLikes( long imageId, long principalId){
        likesRepository.mUnLikes(imageId, principalId);
    }
}
