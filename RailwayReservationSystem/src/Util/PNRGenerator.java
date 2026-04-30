package Util;

import java.util.concurrent.atomic.AtomicLong;

public class PNRGenerator {
	
	public static final AtomicLong counter = new AtomicLong(System.currentTimeMillis() % 1000000);
	
	public static String generatePNR() {
		
		return "PNR" + counter.incrementAndGet();
		
	}

}
