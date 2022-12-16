package meta.metarch.infra.http.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import meta.metarch.core.model.Assessment;
import meta.metarch.core.usecase.MontarAssessmentCase;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping(value = "/assessment")
public class AssessmentResource {
    private final MontarAssessmentCase montar;

    @GetMapping("/{projetoId}")
    public Assessment list(@PathVariable final Long projetoId,
                           @RequestParam(required = false) final Long disciplinaId) {
        return montar.execute(projetoId,disciplinaId);
    }

    @GetMapping("/{projetoId}/radar")
    public Assessment.Radar getRadar(@PathVariable final Long projetoId){
        return null;
    }

}