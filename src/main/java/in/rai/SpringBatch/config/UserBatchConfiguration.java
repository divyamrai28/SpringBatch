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

import in.rai.SpringBatch.domain.User;
import in.rai.SpringBatch.listener.JobCompletionNotificationListenerOfUser;
import in.rai.SpringBatch.model.UserDetail;
import in.rai.SpringBatch.processor.UserItemProcessor;
import in.rai.SpringBatch.reader.UserItemReader;


@Configuration
@EnableBatchProcessing
public class UserBatchConfiguration{
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;


    @Bean
    public ItemReader<UserDetail> userItemReader() {
        return new UserItemReader();
    }

    @Bean
    public MongoItemWriter<User> userWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<User>().template(mongoTemplate).collection("user")
                .build();
    }

    @Bean
    public UserItemProcessor userProcessor() {
        return new UserItemProcessor();
    }

    @Bean
    public Step userStep1(ItemReader<UserDetail> itemReader, MongoItemWriter<User> itemWriter)
            throws Exception {

        return this.stepBuilderFactory.get("Userstep1").<UserDetail, User>chunk(5).reader(itemReader)
                .processor(userProcessor()).writer(itemWriter).build();
    }

    @Bean
    public Job updateUserJob(JobCompletionNotificationListenerOfUser listener, Step userStep1)
            throws Exception {

        return this.jobBuilderFactory.get("updateUserJob").incrementer(new RunIdIncrementer())
                .listener(listener).start(userStep1).build();
    }

}
