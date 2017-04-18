package twitter.dao.impl;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import twitter.beans.Entity;
import twitter.dao.IGenericDAO;
import twitter.dao.exception.DAOException;
import twitter.dao.mapper.EntityRowMapper;
import twitter.dao.query.SqlQuery;

/**
 * Created by Nikolay on 14.04.2017.
 */
public abstract class AbstractGenericDAOImpl<T extends Entity> extends
    JdbcDaoSupport implements
    IGenericDAO<T> {

  private enum INSERT_TYPE {
    CREATE, UPDATE
  }

  private EntityRowMapper<T> rowMapper;

  private String[] columIdNames;

  private String objectType;
  private String relatedObjType;

  private final DataSource dataSource;

  @PostConstruct
  protected void initialize() {
    setDataSource(dataSource);
    rowMapper.setJdbcTemplate(getJdbcTemplate());
  }

  @Autowired
  protected AbstractGenericDAOImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  protected void setObjectType(String objectType) {
    this.objectType = objectType;
  }

  protected void setRelatedObjType(String relatedObjType) {
    this.relatedObjType = relatedObjType;
  }

  protected void setColumIdNames(String[] columIdNames) {
    this.columIdNames = columIdNames;
  }

  protected void setRowMapper(EntityRowMapper<T> rowMapper) {
    this.rowMapper = rowMapper;
  }

  public Long create(T instance) {
    createEntity(instance);
    createOrUpdateAttrs(instance, INSERT_TYPE.CREATE);
    insertReferences(instance);
    return null;
  }

  private Long createEntity(T instance) {
    KeyHolder kh = new GeneratedKeyHolder();
    getJdbcTemplate().update(connection -> {
      PreparedStatement pst =
          connection.prepareStatement(SqlQuery.INSERT_ENTITY.getQuery(), columIdNames);
      pst.setString(1, objectType);
      return pst;
    }, kh);
    instance.setId((Long) kh.getKey());
    return (Long) kh.getKey();
  }

  private void createOrUpdateAttrs(T instance, INSERT_TYPE insertType) {
    Set<Entry<String, String>> entrySet = getAttrValueMap(instance).entrySet();
    boolean isCreate = insertType == INSERT_TYPE.CREATE;
    String query =
        isCreate ? SqlQuery.INSERT_ATTRIBUTE.getQuery() : SqlQuery.UPDATE_ATTRIBUTE.getQuery();
    getJdbcTemplate().batchUpdate(query, entrySet, entrySet.size(),
        (preparedStatement, entry) -> {
          preparedStatement.setLong(isCreate ? 1 : 2, instance.getId());
          preparedStatement.setString(isCreate ? 2 : 3, entry.getKey());
          preparedStatement.setString(isCreate ? 3 : 1, entry.getValue());
        });
  }

  protected Collection<Long> getReferencesIds(T instance) {
    return null;
  }

  private void insertReferences(T instance) {
    Collection<Long> ids = getReferencesIds(instance);
    if (ids == null) {
      return;
    }
    getJdbcTemplate().batchUpdate(SqlQuery.INSERT_REFERENCE.getQuery(), ids, ids.size(),
        (preparedStatement, id) -> {
          preparedStatement.setLong(1, instance.getId());
          preparedStatement.setLong(2, id);
        });
  }

  protected abstract Map<String, String> getAttrValueMap(T instance);

  @Override
  public T read(Long id) throws DAOException {
    String query = getReadQuery() + " AND obj.entity_id=?";
    T instance;
    try {
      instance = getJdbcTemplate().queryForObject(query, rowMapper, id);
    }catch(EmptyResultDataAccessException e){
      return null;
    }
    return instance;
  }

  protected T readBy(String attr, String value) throws DAOException {
    Long objId = getObjectIdByAttr(attr, value, objectType);
    if(objId==null){
      return null;
    }
    T instance;
    try {
      instance = read(objId);
    }catch(Exception e){
      return null;
    }
    return instance;
  }

  protected abstract String getReadQuery();

  private Long getObjectIdByAttr(String attr, String value, String objectType) {
    Long id;
    try {
      id = getJdbcTemplate()
          .queryForObject(SqlQuery.READ_OBJECT_ID_BY_VALUE.getQuery(), Long.class, attr, value,
              objectType);
    }catch(EmptyResultDataAccessException e){
      return null;
    }catch(IncorrectResultSizeDataAccessException e){
      throw new DAOException("Duplicate "+attr+": "+value+" in database!",e);
    }
    return id;
  }

  @Override
  public void update(T instance) {
    createOrUpdateAttrs(instance, INSERT_TYPE.UPDATE);
  }

  //TODO: related obj
  @Override
  public void delete(Long id) {
    getJdbcTemplate().update(SqlQuery.DELETE_ENTITY.getQuery(), id);
    deleteRelatedObjects(id);
  }

  @Override
  public List<T> getAll(){
    List<T> all=getJdbcTemplate().query(getReadQuery(),rowMapper);
    return all;
  }

  protected void deleteRelatedObjects(Long id) {
  }
}
