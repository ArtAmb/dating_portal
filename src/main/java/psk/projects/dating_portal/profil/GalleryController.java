package psk.projects.dating_portal.profil;

import lombok.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
class UploadImageDTO {
    MultipartFile file;
    String description;
    Long galleryId;
}

@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
class UpdateGalleryDTO {
    Long galleryId;
    String galleryName;
    GalleryAccessibility accessScope;
}

@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
class AddGalleryDTO {
    String galleryName;
}


@RestController
@AllArgsConstructor
public class GalleryController {
    private final GalleryService galleryService;
    private final UserProfilService userProfilService;

    @GetMapping("user/galleries")
    public List<Gallery> findGalleries(Principal principal) {
        return galleryService.findAllByUser(principal.getName());
    }


    @PostMapping("/user/galleries/add")
    public void addGallery(@RequestBody AddGalleryDTO dto, Principal principal) {
        galleryService.addNewGallery(dto, principal.getName());
    }

    @PostMapping("gallery/update")
    public void changeGalleryName(@RequestBody UpdateGalleryDTO updateGalleryDTO) {
        galleryService.updateGallery(updateGalleryDTO);
    }

    @PostMapping("upload-image")
    public ImageInfo publishImage(@ModelAttribute UploadImageDTO dto, Principal principal) throws IOException {
        return galleryService.publishImage(dto);
    }

    @PostMapping("upload-avatar-image")
    public ImageInfo publishAvatarImage(@ModelAttribute MultipartFile file, Principal principal) throws IOException {
        return userProfilService.publishAvatar(file, principal);
    }

    @GetMapping("image/{imageId}")
    public void findImage(@PathVariable Long imageId, HttpServletResponse response) throws IOException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(galleryService.findImage(imageId).getBinaryImage())) {
            IOUtils.copy(bais, response.getOutputStream());
        } catch (Exception ex) {
            throw ex;
        }
    }
}
