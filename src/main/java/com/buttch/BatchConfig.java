package com.buttch;

import java.util.Collections;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.transaction.PlatformTransactionManager;

import com.thoughtworks.xstream.security.ExplicitTypePermission;

//@Configuration
public class BatchConfig {

	/**
	 * reader1 XML
	 */
	@Bean
	public StaxEventItemReader<CommandeItem> reader() {

		XStreamMarshaller marshaller = new XStreamMarshaller() {{
			setAliases(Collections.singletonMap("CommandeItem", CommandeItem.class));
		}};
		ExplicitTypePermission typePermission = new ExplicitTypePermission(new Class[] { CommandeItem.class });
		marshaller.setTypePermissions(typePermission);
		
		return new StaxEventItemReaderBuilder<CommandeItem>()
		.name("readerXML")
		.resource(new FileSystemResource("D:/spring_output/commande_item/found_commandeItems.xml"))
		.unmarshaller(marshaller)
		.addFragmentRootElements("CommandeItem")
		.build();
	}
	
	
	@Bean
	public JdbcCursorItemReader<CommandeItem> commandeItemReader(DataSource datasource) {	//depuis bdd locale
		JdbcCursorItemReaderBuilder<CommandeItem> builder = new JdbcCursorItemReaderBuilder<>();
		builder.dataSource(datasource);
		builder.name(CommandeConst.READER_NAME);
		builder.sql(CommandeConst.READER_QUERY);
		builder.rowMapper(new BeanPropertyRowMapper<>(CommandeItem.class));
		return builder.build();
	}
	
	
	@Bean
	public JdbcBatchItemWriter<CommandeItem> commandeItemWriterToDB(DataSource datasource) {
		System.err.println("Starting "+CommandeConst.WRITER_NAME_DB);
		return new JdbcBatchItemWriterBuilder<CommandeItem>()
			.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
			.dataSource(datasource)
			.sql(CommandeConst.WRITER_QUERY)
			.build();
	}
	
	
	@Bean
	StaxEventItemReader<CommandeItem> reader1() {
		
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
	 * reader2
	 */
	
	/**
	 * processor
	 */
	
	@Bean
	public CommandeItemProcessor commandeItemProcessor() {
		return new CommandeItemProcessor();
	}
	
	
	/**
	 * writer
	 */
	@Bean 
	public FlatFileItemWriter<CommandeItem> writer() {
		return new FlatFileItemWriterBuilder<CommandeItem>()
			.name("writerCMD")
			.resource(new FileSystemResource("D:/spring_output/found_commandeItems.csv"))
			.delimited()
			.names(new String[]  { "commandeID", "commandeitemID", "nom", "prixUnite", "qte", "thumbnail" })
			.build();
	}
	
	
	/**
	 * step
	 */
	@Bean
	public Step step1(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
		return new StepBuilder("stepBuilder",jobRepository)
				.<CommandeItem, CommandeItem>chunk(10, platformTransactionManager)
				.reader(reader())
				.processor(commandeItemProcessor())
				.writer(writer())
				.build();
	}
	
	
	/**
	 * job
	 */
	 @Bean 
	 public Job job(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
		 return new JobBuilder("jobBUilder", jobRepository)
			.start(step1(jobRepository, platformTransactionManager))
			.build();
	 }

}
