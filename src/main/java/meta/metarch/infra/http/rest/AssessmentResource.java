package meta.metarch.infra.http.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import meta.metarch.core.usecase.MontarRadarCase;
import meta.metarch.core.model.Assessment;
import meta.metarch.core.usecase.MontarAssessmentCase;

import java.util.List;

@Validated
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping(value = "/assessment")
public class AssessmentResource {
    private final MontarAssessmentCase montarAssessment;
    private final MontarRadarCase montarRadar;

    @GetMapping("/{projetoId}")
    public Assessment list(@PathVariable final Long projetoId,
                           @RequestParam(required = false) final Long disciplinaId) {
        return montarAssessment.execute(projetoId,disciplinaId);
    }

    @GetMapping("/{projetoId}/radar")
    public List<Assessment.Radar> getRadar(@PathVariable final Long projetoId){
        return montarRadar.execute(projetoId);
    }

}