package demo.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@EnableHystrixDashboard
public class MonitorDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitorDashboardApplication.class, args);
	}
	
	@Controller
	static class WebController {

//		@Autowired
//		private DiscoveryClient discoveryClient;
//		
//		private String clusterName;
//
//		public WebController(@Value("${turbine.aggregator.clusterConfig}") String clusterName) {
//			this.clusterName = clusterName;
//		}
//		
//		private String getTurbineServerUrl() {
//			List<ServiceInstance> serviceInstances = discoveryClient.getInstances("turbine-server");
//			if (CollectionUtils.isEmpty(serviceInstances)) {
//				throw new IllegalArgumentException("No turbine server found!");
//			}
//			
//			ServiceInstance inst = serviceInstances.get(0);
//			return inst.getUri().toString();
//		}
		
		@GetMapping({"", "/"})
		public String index() {
			//return "redirect:/hystrix/monitor?stream=" + getTurbineServerUrl() + "?cluster=" + clusterName;
			return "redirect:/hystrix";
		}
	}
}