package com.nodemanager.managedbean.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Converter {

  private String stringConectorList;
  private String stringNumConector;
  private String stringTypeStream;
  private String stringNameUpOrDownList;

  public List<String> getUpOrDownList() {
    StringTokenizer stringTokenizer = new StringTokenizer(stringNameUpOrDownList, ", ");

    List<String> upOrDownList = new ArrayList<>(this.stringNameUpOrDownList.length());

    while (stringTokenizer.hasMoreElements()) { // 0 1 2 ...
      upOrDownList.add(stringTokenizer.nextToken());
    }

    return upOrDownList;
  }

  public List<String> getConectorList() {
    StringTokenizer stringTokenizer = new StringTokenizer(stringConectorList, ", ");

    List<String> conectorList = new ArrayList<>(this.stringConectorList.length());

    while (stringTokenizer.hasMoreElements()) { // 0 1 2 ...
      conectorList.add(stringTokenizer.nextToken());
    }

    return conectorList;

  }

  /**
   * @return the stringConectorList
   */
  public String getStringConectorList() {
    return stringConectorList;
  }

  /**
   * @param stringConectorList the stringConectorList to set
   */
  public void setStringConectorList(String stringConectorList) {
    this.stringConectorList = stringConectorList;
  }

  /**
   * @return the stringConector
   */
  public String getStringNumConector() {
    return stringNumConector;
  }

  /**
   * @param stringNumConector the stringConector to set
   */
  public void setStringNumConector(String stringNumConector) {
    this.stringNumConector = stringNumConector;
  }

  /**
   * @return the stringTypeStream
   */
  public String getStringTypeStream() {
    return stringTypeStream;
  }

  /**
   * @param stringTypeStream the stringTypeStream to set
   */
  public void setStringTypeStream(String stringTypeStream) {
    this.stringTypeStream = stringTypeStream;
  }

  /**
   * @return the stringNameUpOrDownList
   */
  public String getStringNameUpOrDownList() {
    return stringNameUpOrDownList;
  }

  /**
   * @param stringNameUpOrDownList the stringNameUpOrDownList to set
   */
  public void setStringNameUpOrDownList(String stringNameUpOrDownList) {
    this.stringNameUpOrDownList = stringNameUpOrDownList;
  }

}
