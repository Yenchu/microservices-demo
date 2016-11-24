package demo.ms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
//import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.cloud.netflix.turbine.stream.EnableTurbineStream;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableHystrixDashboard
@EnableTurbineStream
@EnableDiscoveryClient
public class MonitorDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitorDashboardApplication.class, args);
	}
	
	@Controller
	static class WebController {
		
		private Integer streamPort;
		
		private String clusterName;
			    
		@Autowired
		public WebController(@Value("${turbine.stream.port}") Integer streamPort, 
				@Value("${turbine.aggregator.clusterConfig}") String clusterName) {
			this.streamPort = streamPort;
			this.clusterName = clusterName;
		}
		
		@RequestMapping(path = {"", "/"})
		public String index() {
			// turbine stream aggregator is installed in the same node as hystrix dashboard
			return "redirect:/hystrix/monitor?stream=http://localhost:" + streamPort + "/turbine.stream?cluster=" + clusterName;
		}
	}
}