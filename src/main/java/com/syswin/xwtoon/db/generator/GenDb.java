package com.syswin.xwtoon.db.generator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

/**
 * 功能描述:自动代码生成器之数据库操作字段
 * @date 2016年7月22日 下午4:41:18
 * @version 2.2.0
 * @author llh
 */// TODO: 2016/8/16  
public class GenDb{
	private static final GenDb db=new GenDb();
	//数据库名
	private static final String DB_NAME="XJBPMAPP";
	private static final String DB_URL="jdbc:oracle:thin:@172.28.5.145:1521/orapro";
	private static final String DB_USER_NAME="xjbpmapp";
	private static final String DB_USER_PWD="xjbpmapp";
	private Connection conn=null;
	private final Map<String,String> dateTypeMap=new HashMap<String,String>();
	private final Map<String,String> dbTypeMap=new HashMap<String,String>();
	private GenDb(){
		  try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			conn = DriverManager.getConnection(DB_URL,DB_USER_NAME,DB_USER_PWD);
		    //数据初始化集合对象
			dateTypeMap.put("number","Integer");
			dateTypeMap.put("varchar","String");
			dateTypeMap.put("varchar2","String");
			dateTypeMap.put("char","String");
			dateTypeMap.put("timestamp","Date");
			dateTypeMap.put("date","Date");
			dateTypeMap.put("int","Integer");


			dbTypeMap.put("number","INTEGER");
			dbTypeMap.put("varchar2","VARCHAR");
			dbTypeMap.put("longtext","LONGVARCHAR");
			dbTypeMap.put("date","TIMESTAMP");
			dbTypeMap.put("datetime","TIMESTAMP");
			dbTypeMap.put("double","NUMERIC");
		  } catch (Exception e) {
			e.printStackTrace();
		}    
	}
	
	public static GenDb getInstance(){ 
		return db;
	}
	
	/**
	 * @Description 获取数据库的所有表 ，并且根据前缀过滤
	 * @author: llh 
	 * @since: 2016年7月22日  下午12:03:12
	 */
	public List<String> getTableName(String filterPrefix) throws Exception{
		 List<String> list=Lists.newArrayList();
		 PreparedStatement ps = conn.prepareStatement("select TABLE_NAME from all_tables"
		 		+ " WHERE owner='"+DB_NAME+"'");
		ResultSet rs = ps.executeQuery();
         if(StringUtils.isEmpty(filterPrefix)){
        	 while(rs.next()){
            	list.add(rs.getString(1));
             } 
         }else{
        	 while(rs.next()){
            	 String table=rs.getString(1);
            	 if(!table.startsWith(filterPrefix)){
            		 list.add(table);
            	 } 
              } 
         }
         
         return list;
	}
	
	/**
	 * 获取数据库的所有表
	 * @Description�? TODO
	 * @author: llh 
	 * @since:  2016年7月22日 下午12:03:12
	 */
	public List<String> getAllTableName() throws Exception{
		 List<String> list=Lists.newArrayList();
		PreparedStatement ps = conn.prepareStatement("select TABLE_NAME from all_tables"
				+ " WHERE owner='"+DB_NAME+"'");
         ResultSet rs = ps.executeQuery(); 
         while(rs.next()){ 
        	  list.add(rs.getString(1)); 
          } 
         return list;
	}
	
	/**
	 * 获取单表的字段名称，注释
	 * @Description�? TODO
	 * @author: llh 
	 * @since:2016年7月22日 下午1:30:16
	 */
	public List<GenColumn> getTableNameColumn(String tableName) throws Exception{
		 List<GenColumn> list=Lists.newArrayList();
		 PreparedStatement ps = conn.prepareStatement("select COLUMN_NAME,DATA_TYPE from user_tab_columns "
		 		+ "where Table_Name='"+tableName+"' ORDER BY COLUMN_ID ASC ");
        ResultSet rs = ps.executeQuery(); 
        GenColumn column=null;
		Map<String,String> map = getTableColComment(tableName);
        while(rs.next()){
        	   column=new GenColumn();
        	   column.setColumnName(GenUtils.replaceUnderlineAndfirstToUpper(rs.getString(1).toLowerCase(),"_",""));
        	   column.setDbName(rs.getString(1));
        	   if (map.get(GenUtils.replaceUnderlineAndfirstToUpper(rs.getString(1).toLowerCase(),"_",""))!="null") {
				   column.setColumnComment(map.get(GenUtils.replaceUnderlineAndfirstToUpper(rs.getString(1).toLowerCase(), "_", "")));
			   }
        	   column.setDataType(getDataType(rs.getString(2)));
        	   column.setDbType(getDbType(rs.getString(2)));
			   list.add(column);
         } 
        return list;
	}

	public Map<String,String> getTableColComment(String tableName) throws Exception{
		List<GenColumn> list=Lists.newArrayList();
		PreparedStatement ps = conn.prepareStatement("select column_name,comments from user_col_comments  "
				+ "where table_name='"+tableName+"'");
		ResultSet rs = ps.executeQuery();
		GenColumn column=null;
		Map<String,String> map = new HashMap<>();
		while(rs.next()){
			map.put(GenUtils.replaceUnderlineAndfirstToUpper(rs.getString(1).toLowerCase(),"_",""),rs.getString(2));
		}
		return map;
	}
	private  String getDataType(String dataType){
		dataType =dataType.toLowerCase();
		if(dateTypeMap.containsKey(dataType))
			return dateTypeMap.get(dataType);
		return "String";
	}
	private  String getDbType(String dbType){
		dbType = dbType.toLowerCase();
		if(dbTypeMap.containsKey(dbType))
			return dbTypeMap.get(dbType);
		return dbType;
	}
    
	   
}
