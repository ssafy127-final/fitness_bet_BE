package com.fitnessbet.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.fitnessbet.user.model.dao", "com.fitnessbet.mission.model.dao","com.fitnessbet.betting.model.dao", "com.fitnessbet.product.model.dao"})
public class DBConfig {

}
