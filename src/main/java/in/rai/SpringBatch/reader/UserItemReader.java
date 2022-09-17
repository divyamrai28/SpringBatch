package in.rai.SpringBatch.reader;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.batch.item.ItemReader;

import in.rai.SpringBatch.model.UserDetail;

public class UserItemReader implements ItemReader<UserDetail>{
	
	private int nextUserIndex;
	private List<UserDetail> users;
	
	public UserItemReader() {
		initialize();
	}
	
	private void initialize() {
		
		UserDetail first = new UserDetail();
		first.setEmail("UserEmail1");
		first.setFirstName("UserFirstName1");
		first.setLastName("UserLastName1");
		first.setMobileNumber("UserMobileNumber1");
		
		UserDetail second = new UserDetail();
		second.setEmail("UserEmail2");
		second.setFirstName("UserFirstName2");
		second.setLastName("UserLastName2");
		second.setMobileNumber("UserMobileNumber2");
		
		UserDetail third = new UserDetail();
		third.setEmail("UserEmail3");
		third.setFirstName("UserFirstName3");
		third.setLastName("UserLastName3");
		third.setMobileNumber("UserMobileNumber3");
		
		users = Collections.unmodifiableList(Arrays.asList(first, second, third));
		nextUserIndex = 0;
	}
	
	@Override
	public UserDetail read() throws Exception{
		
		UserDetail nextUser = null;
		
		if(nextUserIndex < users.size()) {
			nextUser = users.get(nextUserIndex);
			nextUserIndex++;
		}
		else {
			nextUserIndex = 0;
		}
		return nextUser;
	}
}
