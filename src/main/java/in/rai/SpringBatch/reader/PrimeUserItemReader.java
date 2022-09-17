package in.rai.SpringBatch.reader;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.batch.item.ItemReader;

import in.rai.SpringBatch.model.PrimeUserDetail;

public class PrimeUserItemReader implements ItemReader<PrimeUserDetail>{
	
	private int nextPrimeUserIndex;
	private List<PrimeUserDetail> primeusers;
	
	public PrimeUserItemReader() {
		initialize();
	}
	
	private void initialize() {
		
		PrimeUserDetail first = new PrimeUserDetail();
		first.setEmail("PrimeUserEmail1");
		first.setFirstName("PrimeUserFirstName1");
		first.setLastName("PrimeUserLastName1");
		first.setMobileNumber("PrimeUserMobileNumber1");
		first.setAmountPaid(5000);
		
		PrimeUserDetail second = new PrimeUserDetail();
		second.setEmail("PrimeUserEmail2");
		second.setFirstName("PrimeUserFirstName2");
		second.setLastName("PrimeUserLastName2");
		second.setMobileNumber("PrimeUserMobileNumber2");
		second.setAmountPaid(6000);
		
		PrimeUserDetail third = new PrimeUserDetail();
		third.setEmail("PrimeUserEmail3");
		third.setFirstName("PrimeUserFirstName3");
		third.setLastName("PrimeUserLastName3");
		third.setMobileNumber("PrimeUserMobileNumber3");
		third.setAmountPaid(10000);
		
		primeusers = Collections.unmodifiableList(Arrays.asList(first, second, third));
		nextPrimeUserIndex = 0;
	}
	
	@Override
	public PrimeUserDetail read() throws Exception{
		
		PrimeUserDetail nextPrimeUser = null;
		
		if(nextPrimeUserIndex < primeusers.size()) {
			nextPrimeUser = primeusers.get(nextPrimeUserIndex);
			nextPrimeUserIndex++;
		}
		else {
			nextPrimeUserIndex = 0;
		}
		return nextPrimeUser;
	}
}