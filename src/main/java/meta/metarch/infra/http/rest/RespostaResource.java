package meta.metarch.infra.http.rest;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import meta.metarch.core.usecase.SalvarRespostaCase;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Validated
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping(value = "/resposta")
public class RespostaResource {

    private final SalvarRespostaCase salvar;

    @PostMapping("/{projetoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@PathVariable final Long projetoId,
                       @Valid @RequestBody final SalvarRespostaCase.In resposta,
                       final HttpServletResponse response){
        final var created = salvar.execute(projetoId,resposta);
        response.setHeader(HttpHeaders.LOCATION, ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(String.format("/%s/{id}",projetoId))
                .buildAndExpand(created.getId())
                .toUri()
                .toString());
        return created.getId();
    }

}
