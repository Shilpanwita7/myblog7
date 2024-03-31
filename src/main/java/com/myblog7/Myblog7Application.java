package com.myblog7;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication //default configuration class
public class Myblog7Application {

	public static void main(String[] args)
	{
		SpringApplication.run(Myblog7Application.class, args);
	}

//	config to external library object is loaded in spring ioc, model mapper is external library
	@Bean //@Bean can be used only in config classes
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

}

