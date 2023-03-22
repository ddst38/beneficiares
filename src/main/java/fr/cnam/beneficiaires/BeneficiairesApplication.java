package fr.cnam.beneficiaires;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnCloudPlatform;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class BeneficiairesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeneficiairesApplication.class, args);
	}
}

@Component
@ConditionalOnCloudPlatform(CloudPlatform.KUBERNETES)
class KS8Runner{
	@EventListener(ApplicationReadyEvent.class)
	public void run(){
		System.out.println("Salut Kube !!");
	}
}

@Controller
@ResponseBody
class HealthController{
	private final ApplicationContext context;
	HealthController(ApplicationContext context){
		this.context = context;
	}
	@GetMapping("/down")
	void down(){
		AvailabilityChangeEvent.publish(this.context, LivenessState.BROKEN);
	}
}

@Controller
@ResponseBody
class BenenficiaireRestController{
	private final BeneficiaireRepository repository;

	BenenficiaireRestController(BeneficiaireRepository repository){
		this.repository = repository;
	}
	 @GetMapping("/beneficiaires")
	 Flux<Beneficiaire> get(){
		return this.repository.findAll();
	 }
}
interface BeneficiaireRepository extends ReactiveCrudRepository <Beneficiaire, Integer>{}
record Beneficiaire (@Id Integer id, String nom){}

