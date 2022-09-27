package com.durable.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootApplication
@ComponentScan(basePackages = {"ac.th.itsci.durable.controller"})
public class DurableWebservicesApplicationTests {

	@Test
	public void contextLoads() {
	}

}
