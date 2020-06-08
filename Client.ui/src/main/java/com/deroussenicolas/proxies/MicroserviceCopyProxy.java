package com.deroussenicolas.proxies;

import java.util.List;




import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deroussenicolas.beans.CopyBean;

//For local testing on IDE you need that url below
@FeignClient(name="bibliotheque-microservice", url = "localhost:8081")

//For deploying on tomcat you need that url below
//@FeignClient(name="bibliotheque-microservice", url = "http://localhost:8081/Bibliotheque-microservice-0.0.1-SNAPSHOT")
public interface MicroserviceCopyProxy {
	
	@GetMapping(value="/Copies")
	@ResponseBody List<CopyBean> listOfAllCopies();
			
	@GetMapping(value = "/Copy/{id}")
	@ResponseBody CopyBean oneCopyWithId(@PathVariable("id") int id);
	
	@GetMapping(value = "/CopyOfReservation/{id}")
	@ResponseBody CopyBean oneCopyOfReservationWithReservationId(@PathVariable int id);
    
	@GetMapping(value="/CopiesAvailable")
	@ResponseBody List<CopyBean> listOfAllCopiesAvailable();
	
	@GetMapping(value = "/CopiesOfUser/{email}")
	@ResponseBody List<CopyBean> allCopiesWithUserEmail(@PathVariable String email);
	
	@GetMapping(value = "/numberOfCopiesNotAvailableForEachBook")
	@ResponseBody List<Integer> numberOfCopiesNotAvailableForEachBook();

}
