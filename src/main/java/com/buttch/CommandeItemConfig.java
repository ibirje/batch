package com.buttch;

import java.util.Collections;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.transaction.PlatformTransactionManager;

import com.thoughtworks.xstream.security.ExplicitTypePermission;

@Configuration
public class CommandeItemConfig {

	
	/**
	 * reader
	 */
	@Bean
	StaxEventItemReader<CommandeItem> reader() {
		
		return new StaxEventItemReaderBuilder<CommandeItem>()
			.name("commandeItemReaderXML")
			.unmarshaller(marshaller())
			.resource(new FileSystemResource("D:/spring_output/commande_item/new_cmd_items.xml"))
			.addFragmentRootElements("CommandeItem")
			.build();
	}

	@Bean
	public XStreamMarshaller marshaller() {

		return new XStreamMarshaller() {{
			setAliases(Collections.singletonMap("CommandeItem", CommandeItem.class));
			setTypePermissions(new ExplicitTypePermission( new Class[] { CommandeItem.class }));
		}};
	}
	
	/**
	 * processor
	 */
	@Bean
	public CommandeItemProcessor processor() {
		return new CommandeItemProcessor();
	}
	
	
	
	/**
	 * writer
	 */
	@Bean
	public StaxEventItemWriter<CommandeItem> writer() {
		return new StaxEventItemWriterBuilder<CommandeItem>()
				.name("commandeItemWriterXML")
				.resource(new FileSystemResource("D:/spring_output/commande_item/new_cmd_items_2.xml"))
				.marshaller(marshaller())
				.rootTagName("records")
				.build();
	}
	
	/**
	 * step
	 */
	@Bean
	public Step step(JobRepository jobrepo, PlatformTransactionManager platformTransactionManager) {
		return new StepBuilder("step1", jobrepo)
			.<CommandeItem,CommandeItem>chunk(10, platformTransactionManager)
			.reader(reader())
			.processor(processor())
			.writer(writer())
			.build();
	}
	@Bean
	public Job job(JobRepository jobrepo, PlatformTransactionManager platformTransactionManager) {
		return new JobBuilder("job1", jobrepo)
				.start(step(jobrepo, platformTransactionManager))
				.build();
	}
	
	
	
	/**
	 * job
	 */
}
