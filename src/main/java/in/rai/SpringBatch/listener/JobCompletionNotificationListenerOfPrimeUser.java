package in.rai.SpringBatch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@ Component
public class JobCompletionNotificationListenerOfPrimeUser extends JobExecutionListenerSupport {

    private static final Logger log =
            LoggerFactory.getLogger(JobCompletionNotificationListenerOfUser.class);

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED for Prime Users! Time to verify the results");
        }
    }
}