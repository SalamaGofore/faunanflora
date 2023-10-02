package fi.ukkosnetti.faunanflora;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.ukkosnetti.faunanflora.db.models.Metadata;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

@Configuration
public class DatabaseConfiguration extends AbstractJdbcConfiguration {

  @Autowired
  private ApplicationContext applicationContext;

  private final static List<Class<?>> JSON_CLASSES = asList(Metadata.class);

  @Override
  @Bean
  public JdbcCustomConversions jdbcCustomConversions() {
    List<GenericConverter> converters = new ArrayList<>();
    JSON_CLASSES.forEach(clazz -> {
      converters.add(new ObjectArrayToJSONB<>(clazz));
      converters.add(new JSONBToObjectArray<>(clazz));
    });
    return new JdbcCustomConversions(converters);
  }

  @WritingConverter
  class ObjectArrayToJSONB <S> implements GenericConverter {

    private final Class<S> sourceClazz;

    private final ObjectMapper mapper;

    ObjectArrayToJSONB(Class<S> sourceClazz) {
      this.sourceClazz = sourceClazz;
      mapper = applicationContext.getBean(ObjectMapper.class);
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
      return Collections.singleton(new GenericConverter.ConvertiblePair(sourceClazz, PGobject.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
      Object sourceObject = sourceClazz.cast(source);
      PGobject pgJsonObject = new PGobject();
      pgJsonObject.setType("jsonb");
      try {
        pgJsonObject.setValue(mapper.writeValueAsString(sourceObject));
      } catch (SQLException | JsonProcessingException e) {
        throw new RuntimeException(e);
      }
      return pgJsonObject;
    }
  }

  @ReadingConverter
  class JSONBToObjectArray <S> implements GenericConverter {
    private final Class<S> targetClazz;

    private final ObjectMapper mapper;

    JSONBToObjectArray(Class<S> targetClazz) {
      this.targetClazz = targetClazz;
      mapper = applicationContext.getBean(ObjectMapper.class);
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
      return Collections.singleton(new GenericConverter.ConvertiblePair(PGobject.class, targetClazz));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
      PGobject pgObject = (PGobject) source;
      try {
        return mapper.readValue(pgObject.getValue(), targetClazz);
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
