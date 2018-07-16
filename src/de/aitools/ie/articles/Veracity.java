package de.aitools.ie.articles;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum Veracity {

  @XmlEnumValue("mostly true")
  MOSTLY_TRUE("mostly true"),
  
  @XmlEnumValue("mixture of true and false")
  MIXTURE_OF_TRUE_AND_FALSE("mixture of true and false"),
  
  @XmlEnumValue("mostly false")
  MOSTLY_FALSE("mostly false"),
  
  @XmlEnumValue("no factual content")
  NO_FACTUAL_CONTENT("no factual content");
  
  private final String value;

  Veracity(final String value) {
    this.value = value;
  }

  public String value() {
    return this.value;
  }
  
  @Override
  public String toString() {
    return this.value;
  }

  public static Veracity fromValue(final String value) {
    for (final Veracity type : Veracity.values()) {
      if (type.value.equals(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException(value);
  }

}
