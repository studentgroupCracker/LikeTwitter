package twitter.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import twitter.entity.User;
import twitter.web.constants.AttributeNamesConstants;
import twitter.web.constants.PageNamesConstants;
import twitter.web.constants.URLConstants;
import twitter.web.constants.WebConstants;

/**
 * Represent an error page
 *
 * @author Aliaksei Chorny
 */
@Controller
public class ErrorController {
  @RequestMapping(value = WebConstants.SLASH + URLConstants.EMAIL_ERROR, method = RequestMethod.GET)
  public String emailError() {
    return PageNamesConstants.EMAIL_ERROR_PAGE_NAME;
  }

  @RequestMapping(value = WebConstants.SLASH + URLConstants.NOT_FOUND, method = RequestMethod.GET)
  public String notFound() {
    return PageNamesConstants.NOT_FOUND;
  }

  @RequestMapping(value = WebConstants.SLASH + URLConstants.ACCESS_DENIED)
  public String accessDenied(@SessionAttribute(AttributeNamesConstants.USER_ATTRIBUTE_NAME) User user) {
    return WebConstants.REDIRECT + user.getUsername();
  }

  @RequestMapping(value = WebConstants.SLASH + URLConstants.BAD_USER,method = RequestMethod.GET)
  public String badUser() {
    return PageNamesConstants.BAD_USER;
  }

  @RequestMapping(value = WebConstants.SLASH + URLConstants.LOCKED_USER,method = RequestMethod.GET)
  public String lockedUser() {
    return PageNamesConstants.LOCKED_USER;
  }
}

