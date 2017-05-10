package twitter.service.comment;

import java.util.List;
import javax.json.JsonArray;
import javax.json.JsonObject;
import twitter.beans.Comment;
import twitter.beans.Role;
import twitter.web.dto.CommentDto;

/**
 * Created by Nikolay on 28.04.2017.
 */
public interface CommentService {

    void addComment(CommentDto commentDto);

    void updateComment(Comment comment);

    List<Comment> listComment();

    void removeComment(Long id);

    Comment getById(Long id);

    JsonArray getCommentsByTweetId(Long tweetId);
}
