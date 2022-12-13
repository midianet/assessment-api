package meta.metarch.infra.http.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public Page<Assessment> list(@PathVariable final Long projetoId,
                                 @RequestParam(required = false) final Long disciplinaId,
                                 @PageableDefault final Pageable pageable) {
        return montar.execute(projetoId,disciplinaId, pageable);
    }

}