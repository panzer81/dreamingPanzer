package com.panframework.web.ui.nexacro;

import com.nexacro.java.xapi.data.DataSet;
import com.nexacro.java.xapi.data.DataSetList;
import com.nexacro.java.xapi.data.Variable;
import com.nexacro.java.xapi.data.VariableList;
import com.panframework.web.parameter.PanMap;
import com.panframework.web.parameter.PanParameterUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.util.StringUtils;

public class PanNexacroData implements Serializable {
  private static final long serialVersionUID = 3481979254318566755L;
  
  private static final Logger log = LoggerFactory.getLogger(PanNexacroData.class);
  
  public static final String ERROR_CODE = "ErrorCode";
  
  public static final String ERROR_MSG = "ErrorMsg";
  
  public static final String REQUIRED_KEY = "RequiredKey";
  
  private HttpServletRequest request;
  
  private PanMap reqVarParamMap;
  
  private PanMap reqDataSetParamMap;
  
  private String errorCd;
  
  private String errorMsg;
  
  private String requiredKey;
  
  private PanMap resVarMap;
  
  private PanMap resDataMap;
  
  public PanMap getRequestParameterMap() {
    return PanParameterUtil.getParameterMap(this.request);
  }
  
  public void setRequestParameter(HttpServletRequest request) {
    this.request = request;
  }
  
  public PanMap getVariableParameterMap() {
    return this.reqVarParamMap;
  }
  
  public void setVariableParameterMap(VariableList varList) {
    this.reqVarParamMap = new PanMap();
    int size = varList.size();
    if (size > 0)
      for (int i = 0; i < size; i++) {
        Variable var = varList.get(i);
        this.reqVarParamMap.set(var.getName(), convertParameter(var.getString(var.getName())));
      }  
  }
  
  public PanMap getDataSetParameterMap(String dsName) {
    return getDataSetParameterMapList(dsName).get(0);
  }
  
  public List<PanMap> getDataSetParameterMapList(String dsName) {
    Iterator<Object> itr = this.reqDataSetParamMap.keySet().iterator();
    boolean hasDsName = false;
    while (itr.hasNext()) {
      if (dsName.equals(itr.next())) {
        hasDsName = true;
        break;
      } 
    } 
    if (!hasDsName)
      throw new IllegalStateException("There is no such a dataset name (" + dsName + "). Please check a dataset name."); 
    return (List<PanMap>)this.reqDataSetParamMap.get(dsName);
  }
  
  public void setDataSetParameterMap(DataSetList dsList) {
    this.reqDataSetParamMap = new PanMap();
    if (dsList == null)
      return; 
    int dsSize = dsList.size();
    for (int i = 0; i < dsSize; i++) {
      DataSet ds = dsList.get(i);
      this.reqDataSetParamMap.set(ds.getName(), convertDataSetToListMap(ds));
    } 
  }
  
  private List<PanMap> convertDataSetToListMap(DataSet ds) {
    List<PanMap> listMap = new ArrayList<>();
    if (ds == null)
      return listMap; 
    int removedRowCnt = ds.getRemovedRowCount();
    log.debug("DataSet.getRemovedRowCount(): {}", Integer.valueOf(removedRowCnt));
    if (removedRowCnt > 0)
      for (int i = 0; i < removedRowCnt; i++)
        listMap.add(convertDataSetToMap(ds, i, 3));  
    int rowCnt = ds.getRowCount();
    log.debug("DataSet.getRowCount(): {}", Integer.valueOf(rowCnt));
    if (rowCnt > 0)
      for (int i = 0; i < rowCnt; i++)
        listMap.add(convertDataSetToMap(ds, i, ds.getRowType(i)));  
    return listMap;
  }
  
  private PanMap convertDataSetToMap(DataSet ds, int rowIdx, int dsType) {
    PanMap dataMap = new PanMap();
    dataMap.setInt("CRUD", dsType);
    int colCnt = ds.getColumnCount();
    for (int colIdx = 0; colIdx < colCnt; colIdx++) {
      String colNm = ds.getColumn(colIdx).getName();
      log.debug("columnName: {}", colNm);
      switch (ds.getColumnDataType(colNm)) {
        case 3:
          try {
            if (dsType == 3) {
              dataMap.setInt(colNm, ds.getRemovedIntData(rowIdx, colNm));
              break;
            } 
            dataMap.setInt(colNm, ds.getInt(rowIdx, (ds.getObject(rowIdx, colNm) == null) ? null : colNm));
          } catch (NumberFormatException nfe) {
            try {
              if (dsType == 3) {
                dataMap.setDouble(colNm, ds.getRemovedDoubleData(rowIdx, colNm));
                break;
              } 
              dataMap.setDouble(colNm, ds.getDouble(rowIdx, colNm));
            } catch (NumberFormatException nfe1) {
              dataMap.setInt(colNm, 0);
            } 
          } 
          break;
        case 4:
          if (dsType == 3) {
            dataMap.setBoolean(colNm, ds.getRemovedBooleanData(rowIdx, colNm));
            break;
          } 
          dataMap.setBoolean(colNm, ds.getBoolean(rowIdx, colNm));
          break;
        case 5:
          try {
            if (dsType == 3) {
              dataMap.setLong(colNm, ds.getRemovedLongData(rowIdx, colNm));
              break;
            } 
            dataMap.setLong(colNm, ds.getLong(rowIdx, colNm));
          } catch (NumberFormatException nfe) {
            dataMap.setInt(colNm, 0);
          } 
          break;
        case 6:
          try {
            if (dsType == 3) {
              dataMap.setFloat(colNm, ds.getRemovedFloatData(rowIdx, colNm));
              break;
            } 
            dataMap.setFloat(colNm, ds.getFloat(rowIdx, colNm));
          } catch (NumberFormatException nfe) {
            dataMap.setInt(colNm, 0);
          } 
          break;
        case 7:
          try {
            if (dsType == 3) {
              dataMap.setDouble(colNm, ds.getRemovedDoubleData(rowIdx, colNm));
              break;
            } 
            dataMap.setDouble(colNm, ds.getDouble(rowIdx, colNm));
          } catch (NumberFormatException nfe) {
            dataMap.setInt(colNm, 0);
          } 
          break;
        case 8:
          if (dsType == 3) {
            dataMap.setBigDecimal(colNm, ds.getRemovedBigDecimalData(rowIdx, colNm));
            break;
          } 
          dataMap.setBigDecimal(colNm, ds.getBigDecimal(rowIdx, colNm));
          break;
        case 12:
          if (dsType == 3) {
            dataMap.set(colNm, ds.getRemovedBlobData(rowIdx, colNm));
            break;
          } 
          dataMap.set(colNm, ds.getBlob(rowIdx, colNm));
          break;
        case 9:
        case 11:
          if (dsType == 3) {
            dataMap.set(colNm, ds.getRemovedDateTimeData(rowIdx, colNm));
            break;
          } 
          dataMap.set(colNm, ds.getDateTime(rowIdx, colNm));
          break;
        default:
          if (dsType == 3) {
            dataMap.setString(colNm, convertParameter(ds.getRemovedStringData(rowIdx, colNm)));
            break;
          } 
          dataMap.setString(colNm, convertParameter(ds.getString(rowIdx, colNm)));
          break;
      } 
    } 
    return dataMap;
  }
  
  public String getErrorCode() {
    return this.errorCd;
  }
  
  public String getErrorMsg() {
    return this.errorMsg;
  }
  
  public void of(String successCd, String successMsg) {
    this.errorCd = successCd;
    this.errorMsg = successMsg;
  }
  
  public void error(String errorCd, String errorMsg) {
    this.errorCd = errorCd;
    this.errorMsg = errorMsg;
  }
  
  public PanMap getResponseData() {
    return this.resDataMap;
  }
  
  public void setResponseData(String key, Object value) {
    if (this.resDataMap == null)
      this.resDataMap = new PanMap(); 
    this.resDataMap.set(key, value);
  }
  
  public void addResponseData(String key, Object value) {
    setResponseData(key, value);
  }
  
  public String getRequiredKey() {
    return this.requiredKey;
  }
  
  public void setRequiredKey(String requiredKey) {
    this.requiredKey = requiredKey;
  }
  
  private String convertParameter(String value) {
    if (StringUtils.isEmpty(value))
      return value; 
    value = value.replaceAll("<", "&lt;");
    value = value.replaceAll(">", "&gt;");
    return value;
  }
  
  public PanMap getResponseVariable() {
    return this.resVarMap;
  }
  
  public void setResponseVariable(String key, Object value) {
    if (this.resVarMap == null)
      this.resVarMap = new PanMap(); 
    this.resVarMap.set(key, value);
  }
  
  public void addResponseVariable(String key, Object value) {
    setResponseVariable(key, value);
  }
  
  public void setResponseEnvironment(String dsName, Environment env) {
    PanMap envMap = new PanMap();
    for (Iterator<PropertySource<?>> itr = ((AbstractEnvironment)env).getPropertySources().iterator(); itr.hasNext(); ) {
      PropertySource<?> propertySource = itr.next();
      if (propertySource instanceof MapPropertySource && 
        propertySource.getName().startsWith("applicationConfig"))
        envMap.putAll((Map)((MapPropertySource)propertySource).getSource()); 
    } 
    addResponseData(dsName, envMap);
  }
}
