package com.panframework.web.ui.nexacro;

import java.math.BigDecimal;
import java.util.Date;

public class PanNexacroDataType {
  public static int getPlatformDataType(Object obj) {
    if (obj == null)
      return 2; 
    Class<? extends Object> clz = (Class)obj.getClass();
    return getPlatformDataType(clz);
  }
  
  public static int getPlatformDataType(Class<? extends Object> clz) {
    int dataType = 2;
    if (clz == null)
      return dataType; 
    String typeName = clz.getName();
    if (typeName.equals(String.class.getName())) {
      dataType = 2;
    } else if (typeName.equals(Integer.class.getName())) {
      dataType = 3;
    } else if (typeName.equals(Boolean.class.getName())) {
      dataType = 3;
    } else if (typeName.equals(BigDecimal.class.getName())) {
      dataType = 8;
    } else if (typeName.equals(Long.class.getName())) {
      dataType = 8;
    } else if (typeName.equals(Double.class.getName())) {
      dataType = 6;
    } else if (typeName.equals(Date.class.getName())) {
      dataType = 11;
    } else if (typeName.equals(byte[].class.getName())) {
      dataType = 12;
    } 
    return dataType;
  }
  
  public static String getDataType(Object obj) {
    if (obj == null)
      return String.class.getName(); 
    Class<? extends Object> clz = (Class)obj.getClass();
    if (clz == null)
      return String.class.getName(); 
    String typeName = clz.getName();
    return typeName;
  }
  
  public static int getPlatformDataTypeFromSqlType(int type) {
    int dataType = 2;
    switch (type) {
      case 1:
      case 12:
        dataType = 2;
        return dataType;
      case -5:
        dataType = 5;
        return dataType;
      case 3:
        dataType = 5;
        return dataType;
      case -6:
      case 4:
      case 5:
        dataType = 3;
        return dataType;
      case 2:
        dataType = 5;
        return dataType;
      case -7:
      case 16:
        dataType = 4;
        return dataType;
      case 92:
        dataType = 10;
        return dataType;
      case 93:
        dataType = 11;
        return dataType;
      case 91:
        dataType = 9;
        return dataType;
      case 6:
      case 8:
        dataType = 6;
        return dataType;
      case -2:
        dataType = 12;
        return dataType;
    } 
    dataType = 2;
    return dataType;
  }
}