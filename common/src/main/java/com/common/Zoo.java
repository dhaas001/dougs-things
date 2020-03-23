package com.common;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Zoo {
  @XmlElementWrapper
  @XmlAnyElement
  public List<Animal> onExhibit = new ArrayList<Animal>();
  @XmlElementWrapper
  @XmlAnyElement
  public List<Animal> resting = new ArrayList<Animal>();
}
