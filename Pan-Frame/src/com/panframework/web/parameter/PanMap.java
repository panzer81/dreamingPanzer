package com.panframework.web.parameter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class PanMap extends LinkedHashMap<Object, Object> implements Serializable {
  private static final long serialVersionUID = 4686410411375373904L;
  
  private String name;
  
  public PanMap() {}
  
  public PanMap(String name) {
    this();
    this.name = name;
  }
  
  public Object get(Object key) {
    Object obj = super.get(key);
    if (obj == null)
      return ""; 
    return obj;
  }
  
  public void set(Object key, Object value) {
    put(key, value);
  }
  
  public BigDecimal getBigDecimal(Object key) {
    Object obj = super.get(key);
    if (obj == null)
      return new BigDecimal(0.0D); 
    return (BigDecimal)obj;
  }
  
  public void setBigDecimal(Object key, BigDecimal value) {
    put(key, value);
  }
  
  public boolean getBoolean(Object key) {
    Object obj = super.get(key);
    if (obj == null)
      return false; 
    Class<? extends Object> classType = (Class)obj.getClass();
    if (classType == Boolean.class)
      return ((Boolean)obj).booleanValue(); 
    if (classType == String.class)
      return Boolean.parseBoolean(obj.toString()); 
    return false;
  }
  
  public void setBoolean(Object key, boolean value) {
    Boolean bool = new Boolean(value);
    put(key, bool);
  }
  
  public double getDouble(Object key) {
    Object obj = super.get(key);
    if (obj == null)
      return 0.0D; 
    Class<? extends Object> classType = (Class)obj.getClass();
    if (classType == Double.class)
      return ((Double)obj).doubleValue(); 
    if (classType == Float.class)
      return ((Float)obj).floatValue(); 
    if (classType == String.class || classType == BigDecimal.class)
      try {
        return Double.parseDouble(obj.toString());
      } catch (NumberFormatException nfe) {
        System.out.println("PanMap key(" + key + ")" + "'s type(double) does not match : It's type is not double.");
      }  
    System.out.println("PanMap value's type(double) does not match : It's type is not double.");
    return 0.0D;
  }
  
  public void setDouble(Object key, double value) {
    Double dou = new Double(value);
    put(key, dou);
  }
  
  public float getFloat(Object key) {
    Object obj = super.get(key);
    if (obj == null)
      return 0.0F; 
    Class<? extends Object> classType = (Class)obj.getClass();
    if (classType == Float.class)
      return ((Float)obj).floatValue(); 
    if (classType == String.class || classType == BigDecimal.class)
      try {
        return Float.parseFloat(obj.toString());
      } catch (NumberFormatException e) {
        System.out.println("PanMap key(" + key + ")'s type(float) does not match : It's type is not float.");
      }  
    System.out.println("PanMap value's type(float) does not match : It's type is not float.");
    return 0.0F;
  }
  
  public void setFloat(Object key, float value) {
    Float flo = new Float(value);
    put(key, flo);
  }
  
  public int getInt(Object key) {
    Object obj = super.get(key);
    if (obj == null)
      return 0; 
    Class<? extends Object> classType = (Class)obj.getClass();
    if (classType == Integer.class || classType == BigInteger.class)
      return ((Integer)obj).intValue(); 
    if (classType == Short.class)
      return ((Short)obj).shortValue(); 
    if (classType == String.class || classType == BigDecimal.class)
      try {
        return Integer.parseInt(obj.toString());
      } catch (NumberFormatException nfe) {
        System.out.println("PanMap key(" + key + ")" + "'s type(int) does not match : It's type is not double.");
      }  
    System.out.println("PanMap value's type(int) does not match : It's type is not int.");
    return 0;
  }
  
  public void setInt(Object key, int value) {
    Integer integer = new Integer(value);
    put(key, integer);
  }
  
  public long getLong(Object key) {
    Object obj = super.get(key);
    if (obj == null)
      return 0L; 
    Class<? extends Object> classType = (Class)obj.getClass();
    if (classType == Long.class)
      return ((Long)obj).longValue(); 
    if (classType == Integer.class)
      return ((Integer)obj).intValue(); 
    if (classType == Short.class)
      return ((Short)obj).shortValue(); 
    if (classType == String.class || classType == BigDecimal.class)
      try {
        return Long.parseLong(obj.toString());
      } catch (NumberFormatException e) {
        System.out.println("PanMap key(" + key + ")'s type(long) does not match : It's type is not long.");
      }  
    System.out.println("PanMap value's type(long) does not match : It's type is not float.");
    return 0L;
  }
  
  public void setLong(Object key, long value) {
    Long lon = new Long(value);
    put(key, lon);
  }
  
  @Deprecated
  public Object getObject(Object key) {
    return super.get(key);
  }
  
  public short getShort(Object key) {
    Object obj = super.get(key);
    if (obj == null)
      return 0; 
    Class<? extends Object> classType = (Class)obj.getClass();
    if (classType == Short.class)
      return ((Short)obj).shortValue(); 
    if (classType == String.class || classType == BigDecimal.class)
      try {
        return Short.parseShort(obj.toString());
      } catch (NumberFormatException e) {
        System.out.println("PanMap key(" + key + ")'s type(short) does not match : It's type is not long.");
      }  
    System.out.println("PanMap value's type(short) does not match : It's type is not long.");
    return 0;
  }
  
  public void setShort(Object key, short value) {
    Short shor = new Short(value);
    put(key, shor);
  }
  
  public String getString(Object key) {
    Object obj = super.get(key);
    if (obj == null)
      return ""; 
    return obj.toString();
  }
  
  public void setString(Object key, String value) {
    put(key, value);
  }
  
  public Object getKeyWithIndex() {
    Set<Map.Entry<Object, Object>> set = entrySet();
    Object obj = null;
    Iterator<Map.Entry<Object, Object>> itr = set.iterator();
    for (int i = 0; i <= set.size(); i++) {
      Map.Entry<Object, Object> entry = itr.next();
      obj = entry.getKey();
    } 
    return obj;
  }
}
