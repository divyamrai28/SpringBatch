package in.rai.SpringBatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import in.rai.SpringBatch.domain.PrimeUser;
import in.rai.SpringBatch.listener.JobCompletionNotificationListenerOfUser;
import in.rai.SpringBatch.model.PrimeUserDetail;
import in.rai.SpringBatch.processor.PrimeUserItemProcessor;
import in.rai.SpringBatch.reader.PrimeUserItemReader;


@Configuration
@EnableBatchProcessing
public class PrimeUserBatchConfiguration{
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;


    @Bean
    public ItemReader<PrimeUserDetail> primeUserItemReader() {
        return new PrimeUserItemReader();
    }

    @Bean
    public MongoItemWriter<PrimeUser> PrimeUserwriter(MongoTemplate mongoTemplate) {								// name all these methods differently than what's there in User batch config
        return new MongoItemWriterBuilder<PrimeUser>().template(mongoTemplate).collection("PrimeUser")	// as earlier it was also having the name writer so it was giving error
                .build();
    }

    @Bean
    public PrimeUserItemProcessor PrimeUserprocessor() {			// this also changed from processor -> PrimeUserprocessor, to make it different from UserBatchConfig
        return new PrimeUserItemProcessor();
    }

    @Bean
    public Step primeUserStep1(ItemReader<PrimeUserDetail> itemReader, MongoItemWriter<PrimeUser> itemWriter)
            throws Exception {

        return this.stepBuilderFactory.get("PrimeUserstep1").<PrimeUserDetail, PrimeUser>chunk(5).reader(itemReader)
                .processor(PrimeUserprocessor()).writer(itemWriter).build();
    }

    @Bean
    public Job updatePrimeUserJob(JobCompletionNotificationListenerOfUser listener, Step primeUserStep1)
            throws Exception {

        return this.jobBuilderFactory.get("updatePrimeUserJob").incrementer(new RunIdIncrementer())
                .listener(listener).start(primeUserStep1).build();
    }

}