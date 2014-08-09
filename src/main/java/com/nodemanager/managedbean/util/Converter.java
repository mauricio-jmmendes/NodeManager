package com.nodemanager.managedbean.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Converter {

  public String primeiroParametro;
  public String segundoParametro;
  public String terceiroParametro;

  public String getPrimeiroParametro() {

    return this.primeiroParametro;
  }

  public String getSegundoParametro() {
    return this.segundoParametro;
  }

  public List<String> getUpOrDownList() {
    StringTokenizer stringTokenizer = new StringTokenizer(terceiroParametro, ", ");

    List<String> upOrDownList = new ArrayList<>(this.terceiroParametro.length());

    while (stringTokenizer.hasMoreElements()) {
      upOrDownList.add(stringTokenizer.nextToken());
    }

    return upOrDownList;
  }

}
