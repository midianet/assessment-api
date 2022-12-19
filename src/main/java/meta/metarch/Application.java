package meta.metarch;

import jakarta.annotation.PostConstruct;
import meta.metarch.core.usecase.MontarAssessmentCase;
import meta.metarch.core.usecase.MontarRadarCase;
import meta.metarch.infra.database.RespostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {


//    @Autowired
//    private MontarRadarCase montarRadar;
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    //@PostConstruct
    public void teste(){
        //var x = montarRadar.execute(2L);
        //System.out.println(x);
   }

}