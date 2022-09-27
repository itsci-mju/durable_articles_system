package ac.th.itsci.durable.service;

import java.util.List;


import ac.th.itsci.durable.entity.*;


public interface EncodeService {
	
	public List<Durable> EncodeDurable(List<Durable> durableList);

	public List<Borrowing> EncodeBorrowing(List<Borrowing> borrowing);
	
	public Durable EncoderDurable(Durable durable);
}
