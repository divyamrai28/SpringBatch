package in.rai.SpringBatch.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import in.rai.SpringBatch.domain.PrimeUser;
import in.rai.SpringBatch.model.PrimeUserDetail;

public class PrimeUserItemProcessor implements ItemProcessor<PrimeUserDetail, PrimeUser> {
    private static final Logger log = LoggerFactory.getLogger(PrimeUserItemProcessor.class);

    @Override
    public PrimeUser process(PrimeUserDetail item) throws Exception {

        log.info("processing Prime users data.....{}", item);

        PrimeUser transformedPrimeUser = new PrimeUser();
        transformedPrimeUser.setEmail(item.getEmail());
        transformedPrimeUser.setFirstName(item.getFirstName());
        transformedPrimeUser.setLastName(item.getLastName());
        transformedPrimeUser.setMobileNumber(item.getMobileNumber());
        transformedPrimeUser.setAmountPaid(item.getAmountPaid());
        return transformedPrimeUser;
    }

}