package fr.cnam.beneficiaires;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
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
