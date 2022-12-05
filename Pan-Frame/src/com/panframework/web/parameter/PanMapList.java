package com.panframework.web.parameter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PanMapList extends LinkedHashMap<Object, Object> implements Serializable {
  private static final long serialVersionUID = 496702765517420534L;
  
  private int fieldIndex;
  
  private HashMap<String, Integer> entityKey;
  
  private String name;
  
  public PanMapList() {
    this.fieldIndex = 0;
    this.entityKey = null;
  }
  
  public PanMapList(String name) {
    this.fieldIndex = 0;
    this.entityKey = null;
    this.name = name;
  }
  
  public PanMapList(int initialCapacity, float loadFactor) {
    super(initialCapacity, loadFactor);
    this.fieldIndex = 0;
    this.entityKey = null;
  }
  
  public PanMapList(int initialCapacity) {
    super(initialCapacity);
    this.fieldIndex = 0;
    this.entityKey = null;
  }
  
  public PanMapList(Map<?, ?> m) {
    super(m);
    this.fieldIndex = 0;
    this.entityKey = null;
  }
  
  public void add(Object key, Object value) {
    if (!containsKey(key)) {
      List<Object> arrayList = new ArrayList();
      arrayList.add(value);
      put(key, arrayList);
    } else {
      List<Object> arrayList = (ArrayList)get(key);
      arrayList.add(value);
    } 
  }
  
  public void addString(Object key, String value) {
    if (!containsKey(key)) {
      List<String> arrayList = new ArrayList<String>();
      arrayList.add(value);
      put(key, arrayList);
    } else {
      List<String> arrayList = (ArrayList)get(key);
      arrayList.add(value);
    } 
  }
  
  public void addInt(Object key, int value) {
    Integer valueInt = new Integer(value);
    if (!containsKey(key)) {
      List<Integer> arrayList = new ArrayList<Integer>();
      arrayList.add(valueInt);
      put(key, arrayList);
    } else {
      List<Integer> arrayList = (ArrayList)get(key);
      arrayList.add(valueInt);
    } 
  }
  
  public void addDouble(Object key, double value) {
    Double valueDouble = new Double(value);
    if (!containsKey(key)) {
      List<Double> arrayList = new ArrayList<Double>();
      arrayList.add(valueDouble);
      put(key, arrayList);
    } else {
      List<Double> arrayList = (ArrayList)get(key);
      arrayList.add(valueDouble);
    } 
  }
  
  public void addFloat(Object key, float value) {
    Float valueFloat = new Float(value);
    if (!containsKey(key)) {
      List<Float> arrayList = new ArrayList<Float>();
      arrayList.add(valueFloat);
      put(key, arrayList);
    } else {
      List<Float> arrayList = (ArrayList)get(key);
      arrayList.add(valueFloat);
    } 
  }
  
  public void addLong(Object key, long value) {
    Long valueLong = new Long(value);
    if (!containsKey(key)) {
      List<Long> arrayList = new ArrayList<Long>();
      arrayList.add(valueLong);
      put(key, arrayList);
    } else {
      List<Long> arrayList = (ArrayList)get(key);
      arrayList.add(valueLong);
    } 
  }
  
  public void addShort(Object key, short value) {
    Short valueShort = new Short(value);
    if (!containsKey(key)) {
      List<Short> arrayList = new ArrayList<Short>();
      arrayList.add(valueShort);
      put(key, arrayList);
    } else {
      List<Short> arrayList = (ArrayList)get(key);
      arrayList.add(valueShort);
    } 
  }
  
  public void addBoolean(Object key, boolean value) {
    Boolean valueBoolean = new Boolean(value);
    if (!containsKey(key)) {
      List<Boolean> arrayList = new ArrayList<Boolean>();
      arrayList.add(valueBoolean);
      put(key, arrayList);
    } else {
      List<Boolean> arrayList = (ArrayList)get(key);
      arrayList.add(valueBoolean);
    } 
  }
  
  public void addBigDecimal(Object key, BigDecimal value) {
    if (!containsKey(key)) {
      List<BigDecimal> arrayList = new ArrayList<BigDecimal>();
      arrayList.add(value);
      put(key, arrayList);
    } else {
      List<BigDecimal> arrayList = (ArrayList)get(key);
      arrayList.add(value);
    } 
  }
  
  public void addPanMap(PanMap map) {
    Set<?> tempSet = map.keySet();
    for (Iterator<?> iterator = tempSet.iterator(); iterator.hasNext(); ) {
      Object key = iterator.next();
      if (containsKey(key)) {
        int field_size = ((ArrayList)get(key)).size();
        if (field_size != this.fieldIndex)
          for (int i = field_size; i < this.fieldIndex; i++)
            add(key, (Object)null);  
        add(key, map.get(key));
        continue;
      } 
      for (int inx = 0; inx < this.fieldIndex; inx++)
        add(key, (Object)null); 
      add(key, map.get(key));
    } 
    this.fieldIndex++;
  }
  
  public void addPanMap(String dataName, PanMap map) {
    int entitySize = 0;
    if (this.entityKey == null) {
      this.entityKey = new HashMap<String, Integer>(5);
    } else if (this.entityKey.containsKey(dataName)) {
      entitySize = ((Integer)this.entityKey.get(dataName)).intValue();
    } 
    Set<?> tempSet = map.keySet();
    for (Iterator<?> iterator = tempSet.iterator(); iterator.hasNext(); ) {
      Object key = iterator.next();
      String dataKey = dataName + "." + key;
      if (containsKey(dataKey)) {
        int fieldSize = ((ArrayList)get(dataKey)).size();
        if (fieldSize != entitySize)
          for (int i = fieldSize; i < entitySize; i++)
            add(dataKey, (Object)null);  
        add(dataKey, map.get(key));
        continue;
      } 
      for (int inx = 0; inx < entitySize; inx++)
        add(dataKey, (Object)null); 
      add(dataKey, map.get(key));
    } 
    this.entityKey.put(dataName, new Integer(entitySize + 1));
  }
  
  public Object get(Object key, int index) {
    return getObject(key, index);
  }
  
  public BigDecimal getBigDecimal(Object key, int index) {
    Object obj = getObject(key, index);
    if (obj == null)
      return new BigDecimal(0.0D); 
    return (BigDecimal)obj;
  }
  
  public boolean getBoolean(Object key, int index) {
    Object obj = getObject(key, index);
    if (obj == null)
      return false; 
    Class<? extends Object> classType = (Class)obj.getClass();
    if (classType == Boolean.class)
      return ((Boolean)obj).booleanValue(); 
    if (classType == String.class)
      return Boolean.parseBoolean(obj.toString()); 
    return false;
  }
  
  public double getDouble(Object key, int index) {
    Object obj = getObject(key, index);
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
        System.out.println("PanMapList value of the key(" + key + ") type(double) does not match : It's type is not double");
      }  
    return 0.0D;
  }
  
  public float getFloat(Object key, int index) {
    Object obj = getObject(key, index);
    if (obj == null)
      return 0.0F; 
    Class<? extends Object> classType = (Class)obj.getClass();
    if (classType == Float.class)
      return ((Float)obj).floatValue(); 
    if (classType == String.class || classType == BigDecimal.class)
      try {
        return Float.parseFloat(obj.toString());
      } catch (NumberFormatException nfe) {
        System.out.println("PanMapList value of the key(" + key + ") type(float) does not match : It's type is not float");
      }  
    return 0.0F;
  }
  
  public int getInt(Object key, int index) {
    Object obj = getObject(key, index);
    if (obj == null)
      return 0; 
    Class<? extends Object> classType = (Class)obj.getClass();
    if (classType == Integer.class)
      return ((Integer)obj).intValue(); 
    if (classType == Short.class)
      return ((Short)obj).shortValue(); 
    if (classType == String.class || classType == BigDecimal.class)
      try {
        return Integer.parseInt(obj.toString());
      } catch (NumberFormatException nfe) {
        System.out.println("PanMapList value of the key(" + key + ") type(int) does not match : It's type is not int");
      }  
    return 0;
  }
  
  public long getLong(Object key, int index) {
    Object obj = getObject(key, index);
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
      } catch (NumberFormatException nfe) {
        System.out.println("PanMapList value of the key(" + key + ") type(long) does not match : It's type is not long");
      }  
    return 0L;
  }
  
  public PanMap getPanMap(int index) {
    PanMap map = new PanMap();
    Set<?> tempSet = keySet();
    for (Iterator<?> iterator = tempSet.iterator(); iterator.hasNext(); ) {
      String key = iterator.next().toString();
      Object obj = getObject(key, index);
      map.put(key, obj);
    } 
    return map;
  }
  
  public PanMap getPanMap(String dataName, int index) {
    PanMap map = new PanMap(dataName);
    String prefix = dataName + ".";
    Set<?> tempSet = keySet();
    for (Iterator<?> iterator = tempSet.iterator(); iterator.hasNext(); ) {
      String key = iterator.next().toString();
      int key_index = key.indexOf(".");
      String realKey = key.substring(key_index + 1);
      if (key.startsWith(prefix)) {
        Object obj = getObject(key, index);
        map.put(realKey, obj);
      } 
    } 
    return map;
  }
  
  private Object getObject(Object key, int index) {
    Object obj = null;
    List<?> arrayList = (ArrayList)get(key);
    if (arrayList == null)
      return null; 
    try {
      if (index >= arrayList.size())
        return null; 
      obj = arrayList.get(index);
    } catch (IndexOutOfBoundsException iobe) {
      System.out.println("PanMapList index(" + index + ") in PanMapList(" + this.name + ") is out of Bounds.");
    } 
    return obj;
  }
  
  public short getShort(Object key, int index) {
    Object obj = getObject(key, index);
    if (obj == null)
      return 0; 
    Class<? extends Object> classType = (Class)obj.getClass();
    if (classType == Short.class)
      return ((Short)obj).shortValue(); 
    if (classType == String.class || classType == BigDecimal.class)
      try {
        return Short.parseShort(obj.toString());
      } catch (NumberFormatException nfe) {
        System.out.println("PanMapList value of key(" + key + ")" + " type(short) does not match : It's type is not short");
      }  
    return 0;
  }
  
  public String getString(Object key, int index) {
    Object obj = getObject(key, index);
    if (obj == null)
      return ""; 
    return obj.toString();
  }
  
  public int keySize(Object key) {
    if (containsKey(key))
      return ((ArrayList)get(key)).size(); 
    return 0;
  }
  
  public Object remove(Object key, int index) {
    if (containsKey(key))
      return ((ArrayList)get(key)).remove(index); 
    return null;
  }
  
  public int size() {
    Set<?> tempSet = keySet();
    Iterator<?> iterator = tempSet.iterator();
    if (iterator.hasNext()) {
      String key = iterator.next().toString();
      return ((ArrayList)get(key)).size();
    } 
    return 0;
  }
}
