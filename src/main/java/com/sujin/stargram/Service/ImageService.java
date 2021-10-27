package com.sujin.stargram.Service;

import com.sujin.stargram.config.auth.PrincipalDetails;
import com.sujin.stargram.domain.Image;
import com.sujin.stargram.dto.ImageUploadDto;
import com.sujin.stargram.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Transactional(readOnly = true)
    public List<Image> popular() {
        return imageRepository.mPopular();
    }

    @Transactional(readOnly = true)//readOnly = true -> 영속성컨텍스트에서 변경감지를 해서, 더티체킹, flush(반영) x
    public Page<Image> imageStory(long principalId, Pageable pageable) {
        Page<Image> images = imageRepository.mStory(principalId, pageable);

        //images에 좋아요 상태 담기
        images.forEach((image)-> {
            image.getLikes().forEach((like)->{

                int likeCount = image.getLikes().size();
                image.setLikeCount(likeCount);
                image.setLikeState(false);
                if(like.getUser().getId() == principalId){ // 해당 이미지에 좋아요한 사람들을 찾아서 현재 로그인 사람이 좋아요 한 것인지 비교
                    image.setLikeState(true);
                    System.out.println("========================="+image.getLikeState());
                }
            });
        });

        return images;
    }

    // yml에 적어둔 경로  file -> path
    @Value("${file.path}")
    private String uploadFolder;

    public void uploadImage(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
        //UUID 네트워크 상에서 고유성이 보장되는 ID를 만들기 위한 표준 규약
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid+"-"+imageUploadDto.getFile().getOriginalFilename(); // 1.jpg
        System.out.println("imageFileName = " + imageFileName);

        Path imageFilePath = Paths.get(uploadFolder+imageFileName);


        //통신 , I/O -> 예외가 발생할 수 있다.
        try {
            Files.write(imageFilePath,imageUploadDto.getFile().getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }

        //image  테이블에 저장
        Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
        Image imageEntity = imageRepository.save(image);

    }

}
