package com.ys.springcloud.util;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBatisPlusGenerator {
	
	/**
	 * 数据库地址
	 */
	private static String jdbcUrl = "localhost:3306";
	/**
	 * 数据库名称
	 */
	private static String db_name = "db2019";
	/**
	 * 数据库账号
	 */
	private static String db_username = "root";
	/**
	 * 数据库密码
	 */
	private static String db_password = "root";

	public static void main(String[] args) throws SQLException,
			FileNotFoundException {
		//生成的文件存放的工程目录
		String packageName = "com.ys.springcloud.entities";
		//需要生成的表数组
		String[] tables = new String[]{"payment"};
		//作者
		String author = "yangsong";
		//自动生成
		create(tables,packageName,author);
	}
	/**
	 * 
	 * 描述:自动生成表对应的业务
	 *
	 * @author:   yumaochun
	 * @date:     2019年10月25日 下午6:00:08
	 *
	 * @param tables      需要生成的表数组
	 * @param packageDir  生成的文件存放的工程目录
	 */
	public static void create(String[] tables,String packageDir,String author){
		String path = System.getProperty("user.dir");
        //String path = "/Users/mac/Documents/gitWork/ALSB_PS";
        AutoGenerator mpg = new AutoGenerator();
        // 选择 freemarker 引擎，默认 Veloctiy
        // mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(path + "/cloud-provider-payment8001/src/main/java");
        gc.setFileOverride(true);
        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        //gc.setKotlin(true);//是否生成 kotlin 代码
        gc.setAuthor(author);

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sDao");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert() {
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                return super.processTypeConvert(fieldType);
            }
        });
        //连接地址
        String url = "jdbc:mysql://"+jdbcUrl+"/"+db_name+"?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true";
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(db_username);
        dsc.setPassword(db_password);
        dsc.setUrl(url);
        mpg.setDataSource(dsc);
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 全局大写命名 ORACLE 注意
        // strategy.setCapitalMode(true);
        // 此处可以修改为您的表前缀
        //strategy.setTablePrefix(new String[] { "tlog_", "tsys_" });
        // 表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setInclude(tables);
        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
        strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        //strategy.setEntityBuilderModel(true);
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(packageDir);
        pc.setController("controller");
        pc.setEntity("model");
        pc.setMapper("dao");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setXml("mapper");
        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】  ${cfg.abc}
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        };

        // 自定义 xxListIndex.html 生成
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        focList.add(new FileOutConfig("/template/list.html.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return "E://test//html//" + tableInfo.getEntityName() + "ListIndex.html";
            }
        });
//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);

        // 自定义  xxAdd.html 生成
        focList.add(new FileOutConfig("/template/add.html.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return "E://test//html//" + tableInfo.getEntityName() + "Add.html";
            }
        });
//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);

        //  自定义 xxUpdate.html生成
        focList.add(new FileOutConfig("/template/update.html.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return "E://test//html//" + tableInfo.getEntityName() + "Update.html";
            }
        });
//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);

        // 关闭默认 xml 生成，调整生成 至 根目录
        /*TemplateConfig tc = new TemplateConfig();
        tc.setXml(null);
        mpg.setTemplate(tc);*/

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        TemplateConfig tc = new TemplateConfig();
        tc.setController("/template/controller.java.vm");
        tc.setService("/template/service.java.vm");
        tc.setServiceImpl("/template/serviceImpl.java.vm");
        tc.setEntity("/template/entity.java.vm");
        tc.setMapper("/template/mapper.java.vm");
        tc.setXml("/template/mapper.xml.vm");
        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。
        mpg.setTemplate(tc);
        // 执行生成
        mpg.execute();
	}

}
