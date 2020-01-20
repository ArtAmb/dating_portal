package psk.projects.dating_portal.profil;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import psk.projects.dating_portal.auth.AppUser;
import psk.projects.dating_portal.auth.UserRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@AllArgsConstructor
public class GalleryService {
    private final GalleryRepository galleryRepository;
    private final UserRepository userRepository;
    private final UserProfilRepo userProfilRepo;
    private final ImageRepository imageRepository;
    private final ImageInfoRepository imageInfoRepository;

    public List<Gallery> findAllByUser(String userLogin) {
        AppUser user = findUserByLogin(userLogin);

        return galleryRepository.findByUserId(user.getId());
    }

    private AppUser findUserByLogin(String userLogin) {
        Assert.notNull(userLogin, "userLogin is required");
        AppUser user = userRepository.findByLogin(userLogin);
        if (user == null)
            throw new IllegalStateException("User not found for login " + userLogin);
        return user;
    }

    @Transactional
    public ImageInfo publishImage(UploadImageDTO dto) throws IOException {
        Assert.notNull(dto.getGalleryId(), "gallery id is required");

        Image image = Image.builder()
                .binaryImage(dto.getFile().getBytes())
                .build();

        Long imageId = imageRepository.save(image).getId();

        ImageInfo imageInfo = ImageInfo.builder()
                .imageId(imageId)
                .description(dto.getDescription())
                .build();

        Gallery gallery = galleryRepository.findById(dto.getGalleryId())
                .orElseThrow(() -> new IllegalStateException("There is no gallery for id == " + dto.getGalleryId()));

        imageInfo = imageInfoRepository.save(imageInfo);
        gallery.getImages().add(imageInfo);
        galleryRepository.save(gallery);

        return imageInfo;
    }

    public void publishGallery(String userLogin, Gallery gallery) {
        AppUser user = findUserByLogin(userLogin);
        gallery.setUserId(user.getId());

        galleryRepository.save(gallery);
    }

    public Image findImage(Long imageId) {
        return imageRepository.findById(imageId).get();

    }

    public void addNewGallery(AddGalleryDTO dto, String login) {
        AppUser user = findUserByLogin(login);

        Gallery gallery = Gallery.builder()
                .title(dto.getGalleryName())
                .userId(user.getId())
                .accessScope(GalleryAccessibility.PUBLIC)
                .build();

        galleryRepository.save(gallery);
    }

    public void updateGallery(UpdateGalleryDTO updateGalleryDTO) {
        Assert.notNull(updateGalleryDTO, "updateGalleryDTO is req");
        Assert.notNull(updateGalleryDTO.getGalleryName(), "GalleryName is req");
        Assert.notNull(updateGalleryDTO.getGalleryId(), "GalleryId is req");
        if (updateGalleryDTO.getGalleryName().trim().isEmpty()) {
            throw new IllegalStateException("GalleryName cannot be empty");
        }

        Gallery gallery = galleryRepository.findById(updateGalleryDTO.getGalleryId()).get();
        gallery.setTitle(updateGalleryDTO.getGalleryName());
        galleryRepository.save(gallery);
    }

}
