package net.stage.Server.Config;

import net.stage.Server.Entity.CustomerEntity;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JobRepository jobRepository;

    @Bean
    public Job exportCustomerJob() {
        return new JobBuilder("exportCustomerJob", jobRepository)
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .<CustomerEntity, CustomerEntity>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public JdbcCursorItemReader<CustomerEntity> reader() {
        return new JdbcCursorItemReaderBuilder<CustomerEntity>()
                .dataSource(dataSource)
                .name("customerReader")
                .sql("SELECT id, name, email, address, phone_number FROM customers")
                .rowMapper(new BeanPropertyRowMapper<>(CustomerEntity.class))
                .build();
    }

    @Bean
    public ItemProcessor<CustomerEntity, CustomerEntity> processor() {
        return customer -> customer; // No processing needed, just pass through
    }

    @Bean
    public FlatFileItemWriter<CustomerEntity> writer() {
        return new FlatFileItemWriterBuilder<CustomerEntity>()
                .name("customerWriter")
                .resource(new FileSystemResource("customers.csv"))
                .lineAggregator(new DelimitedLineAggregator<CustomerEntity>() {{
                    setDelimiter(",");
                    setFieldExtractor(new BeanWrapperFieldExtractor<CustomerEntity>() {{
                        setNames(new String[]{"id", "name", "email", "address", "phoneNumber"});
                    }});
                }})
                .build();
    }
}
