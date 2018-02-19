package com.dhcc.icore.tools.mybatis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;

/**
 * Create on 2017/3/13.
 *
 * @author luo
 */
public class Main {
	public static void main(String[] args) {
		oneGennerator();
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
