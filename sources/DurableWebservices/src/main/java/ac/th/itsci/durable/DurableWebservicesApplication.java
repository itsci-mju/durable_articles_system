package ac.th.itsci.durable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.text.SimpleDateFormat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

//import ac.th.itsci.durable.util.FileStorageProperties;

@SpringBootApplication

//@EnableConfigurationProperties({ FileStorageProperties.class })
@RestController
@EnableScheduling 
@Configuration

//@ComponentScan(basePackages = {"ac.th.itsci.durable.repo.*"})
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})

public class DurableWebservicesApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DurableWebservicesApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(DurableWebservicesApplication.class, args);
	}
	
	Properties properties = new Properties();

	@Scheduled(fixedRateString = "${SET_TIME_MILLISECOND}")
	public void scheduleFixedRateTaskForDeleteReportVerify() throws IOException {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String dateString = simpleDateFormat.format(date);

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
		properties.load(inputStream);
		String urlFileReport = properties.getProperty("uriFileReport");

		File folder = new File(urlFileReport);
		File[] listOfFiles = folder.listFiles();

		if (listOfFiles == null || listOfFiles.length == 0) {
			System.out.println(dateString + " : Don't have file report verify durable in folder.");
		} else {
			for (int i = 0; i < listOfFiles.length; i++) {
				File file = new File(urlFileReport + "/" + listOfFiles[i].getName());
				if (file.delete()) {
					System.out.println(dateString + " " + listOfFiles[i].getName()
							+ " : File deleted from Project DurableWebservice directory");
				} else
					System.out.println(dateString + " " + listOfFiles[i].getName()
							+ " : File doesn't exist in the project DurableWebservice directory");
			}
		}
	}

	@Scheduled(fixedRateString = "${SET_TIME_MILLISECOND}")
	public void scheduleFixedRateTaskForDelectImageQrcode() throws IOException {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String dateString = simpleDateFormat.format(date);

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
		properties.load(inputStream);
		String urlFileReport = properties.getProperty("uriQRCode");
//		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
//		properties.load(inputStream);
//		String urlFileReport = properties.getProperty("uriGenQrcode");
		//String urlFileReport = "/usr/share/apache-tomcat-9.0.0.M21/webapps/DurableWebservices/images/qrcode";
		
		File folder = new File(urlFileReport);
		File[] listOfFiles = folder.listFiles();

		if (listOfFiles == null || listOfFiles.length == 0) {
			System.out.println(dateString + " : Don't have file image qrcode in folder.");
		} else {
			for (int i = 0; i < listOfFiles.length; i++) {
				File file = new File(urlFileReport + "/" + listOfFiles[i].getName());
				if (file.delete()) {
					System.out.println(dateString + " " + listOfFiles[i].getName()
							+ " : File deleted from Project DurableWebservice directory");
				} else
					System.out.println(dateString + " " + listOfFiles[i].getName()
							+ " : File doesn't exist in the project DurableWebservice directory");
			}
		}

	}

}
