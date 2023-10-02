package fi.ukkosnetti.faunanflora.db.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Metadata {

  public List<MetadataField> metadata = new ArrayList<>();

  public Metadata() {};

  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class MetadataField {

    @JsonProperty("key")
    public String key;

    @JsonProperty("value")
    public String value;

    public MetadataField() {}
    
    public MetadataField(String key, String value) {
      this.key = key;
      this.value = value;
    }
  }
}
