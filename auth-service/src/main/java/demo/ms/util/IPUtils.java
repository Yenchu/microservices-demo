package demo.ms.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IPUtils {
	
	private static final Logger log = LoggerFactory.getLogger(IPUtils.class);

	public static Map<String, String> getHostnameAndAddress() {
		Map<String, String> info = new LinkedHashMap<>();
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String serverAddress = inetAddress.getHostAddress();
			String serverName = inetAddress.getHostName();
			
			info.put("authServiceAddress", serverAddress);
			info.put("authServiceName", serverName);
		} catch (UnknownHostException e) {
			log.error(e.getMessage(), e);
		}
		return info;
	}
}
