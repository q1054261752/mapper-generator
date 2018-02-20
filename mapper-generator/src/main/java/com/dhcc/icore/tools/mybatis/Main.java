package com.dhcc.icore.tools.mybatis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;

/**
 * Create on 2017/3/13.
 *
 * @author luo
 */
public class Main {
	public static void main(String[] args) {
		mysqlGenneratorkOne(args[0]);   //mysql生成dao层
		//oneGennerator();    // oracle生成dao层
	}

	/**
	 * mysql数据库生成数据库操作
	 */
	public static void mysqlGenneratorkOne(String args){

		if( args == null ){

			throw new RuntimeException("文件yml不能为空!");
		}

		File f = new File(".");
		StringBuilder sb = new StringBuilder();

		String absolutePath = f.getAbsolutePath();
		absolutePath = absolutePath.replace(".","");
		sb.append(absolutePath);


		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

		sb.append(args);
		System.out.println(sb.toString());

		try {
//			InputStream inputStream=Resources.getResourceAsStream(sb.toString());
			InputStream inputStream = new FileInputStream(sb.toString());
			GeneratorConfig config = mapper.readValue(inputStream, GeneratorConfig.class);
			config.afterPropertiesSet();
			MybatisMysqlMapperGenerator generator = new MybatisMysqlMapperGenerator(config);
			generator.generate();
		} catch (Exception e) {
			throw new IllegalStateException("Generate for file table failed : " + e.getMessage(), e);
		}
	}

	public static void allGennerator(){
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		try {
			InputStream inputStream=Resources.getResourceAsStream("table.yml");
			GeneratorConfig config = mapper.readValue(inputStream, GeneratorConfig.class);
			config.afterPropertiesSet();
			MybatisMapperGenerator generator = new MybatisMapperGenerator(config);
			generator.generate();
		} catch (Exception e) {
			throw new IllegalStateException("Generate for file table failed : " + e.getMessage(), e);
		}
	}

	public static void oneGennerator(){
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		try {
			InputStream inputStream=Resources.getResourceAsStream("table_one.yml");
			GeneratorConfig config = mapper.readValue(inputStream, GeneratorConfig.class);
			config.afterPropertiesSet();
			MybatisMapperGenerator generator = new MybatisMapperGenerator(config);
			generator.generate();
		} catch (Exception e) {
			throw new IllegalStateException("Generate for file table failed : " + e.getMessage(), e);
		}
	}
}
