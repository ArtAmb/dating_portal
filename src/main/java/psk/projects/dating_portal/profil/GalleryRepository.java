package psk.projects.dating_portal.profil;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GalleryRepository extends CrudRepository<Gallery, Long> {
    List<Gallery> findByUserId(long userId);
}



interface ImageRepository extends CrudRepository<Image, Long> {

}


interface ImageInfoRepository extends CrudRepository<ImageInfo, Long> {

}