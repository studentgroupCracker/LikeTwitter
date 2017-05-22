package twitter.listeners;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import twitter.constants.InitialPhotoSettings;
import twitter.constants.RolesAndPrivileges;
import twitter.dao.IUserProfileDAO;
import twitter.entity.Privilege;
import twitter.entity.Role;
import twitter.entity.User;
import twitter.entity.UserProfile;
import twitter.service.image.ImageService;
import twitter.service.privilage.PrivilegeService;
import twitter.service.role.RoleService;
import twitter.service.storage.StorageException;
import twitter.service.user.UserService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Load and creates users privileges and roles
 *
 * @author Aliaksei Chorny
 */
@Component
public class InitialDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {
    private boolean alreadySetup = false;

    private RoleService roleService;

    private PrivilegeService privilegeService;

    private UserService userService;

    private ImageService imageService;

    private IUserProfileDAO userProfileDAO;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    @Autowired
    @Qualifier("roleService")
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    @Qualifier("privilegeService")
    public void setPrivilegeService(PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup) {
            return;
        }
        Privilege viewPagesPrivilege = createPrivilegeIfNotFound(RolesAndPrivileges
                .VIEW_PAGES_PRIVILEGE);
        List<Privilege> userPrivileges = Arrays
                .asList(viewPagesPrivilege);
        createRoleIfNotFound(RolesAndPrivileges.ROLE_ADMIN, userPrivileges);
        createRoleIfNotFound(RolesAndPrivileges.ROLE_USER, userPrivileges);
        createAdminIfNotExists();
        createDefaultImgIfDoesNotExist(InitialPhotoSettings.DEFAULT_IMG_PATH);
        alreadySetup = true;
    }

    @Transactional
    private Privilege createPrivilegeIfNotFound(String privilegeName) {
        Privilege privilege = privilegeService.findByName(privilegeName);
        if (privilege == null) {
            privilege = new Privilege(privilegeName);
            privilegeService.addPrivilege(privilege);
        }
        return privilege;
    }

    @Transactional
    private void createRoleIfNotFound(
            String name, List<Privilege> privileges) {
        Role role = roleService.findByName(name);
        if (role == null) {
            role = new Role(name, privileges);
            roleService.addRole(role);
        }
    }

    @Transactional
    private void createAdminIfNotExists() {
        User user = userService.getUserByUsername("admin");
        if (user == null) {
            user = new User();
            UserProfile userProfile = new UserProfile();
            userProfileDAO.create(userProfile);
            user.setUserProfile(userProfile);
            user.setEmail("liketwidsftter.admdfsdfsdin@gmail.com");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setUsername("admin");
            user.setRole(roleService.findByName(RolesAndPrivileges.ROLE_ADMIN));
            userService.addUser(user);
        }
    }

    @Transactional
    private void createDefaultImgIfDoesNotExist(String pathToFile) {
        try {
            InputStream input = getClass().getResourceAsStream(pathToFile);
            MultipartFile multipartFile = new MockMultipartFile("mock", IOUtils.toByteArray(input));
            imageService.storeOriginalImage(multipartFile, InitialPhotoSettings.DEFAULT_IMG_NAME);
            imageService.storeResizedImage(multipartFile, InitialPhotoSettings.DEFAULT_MINI_IMG_NAME
                    , InitialPhotoSettings.MINI_IMG_WIDTH, InitialPhotoSettings.MINI_IMG_HEIGHT);
        } catch (StorageException e) {
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Default image not found for path:" + pathToFile, e);
        } catch (IOException e) {
            throw new RuntimeException("Can't create default image", e);
        }
    }


    @Autowired
    public void setUserProfileDAO(IUserProfileDAO userProfileDAO) {
        this.userProfileDAO = userProfileDAO;
    }
}
