package twitter.service.image;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import twitter.entity.User;

/**
 * Created by Nikolay on 24.04.2017.
 */
@Service
public interface ImageService {
  void storeImage(MultipartFile file, User user);

  void storeOriginalImage(MultipartFile file, String name);

  void storeResizedImage(MultipartFile file, String name, Integer width, Integer height);
}
