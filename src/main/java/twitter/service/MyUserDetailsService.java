package twitter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import twitter.entity.Privilege;
import twitter.entity.Role;
import twitter.entity.User;
import twitter.service.user.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Service serve for set user creating user details
 * @author Aliaksei Chorny
 */
@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {
  private UserService userService;

  @Autowired
  @Qualifier("userService")
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  public UserDetails loadUserByUsername(String username) throws
      UsernameNotFoundException {
    try {
      User user = userService.getUserByUsername(username);
      if (user == null) {
        throw new UsernameNotFoundException(
                "Username does not exist: " + username);
      }
      return new org.springframework.security.core.userdetails.User(
              user.getUsername(), user.getPassword(), true,
              true, true,
              !user.getBaned(), getAuthorities(Arrays.asList(user.getRole())));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private Collection<? extends GrantedAuthority> getAuthorities
      (Collection<Role> roles) {
    return getGrantedAuthorities(getPrivileges(roles));
  }

  private Collection<GrantedAuthority> getGrantedAuthorities(
      List<String> privileges) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (String privilege: privileges) {
      authorities.add(new SimpleGrantedAuthority(privilege));
    }
    return authorities;
  }

  private List<String> getPrivileges(Collection<Role> roles) {
    List<String> privileges = new ArrayList<>();
    List<Privilege> collection = new ArrayList<>();
    for (Role role: roles) {
      collection.addAll(role.getPrivileges());
    }
    for (Privilege item: collection) {
      privileges.add(item.getName());
    }
    return privileges;
  }
}
