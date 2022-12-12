package meta.metarch.infra.http.rest;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import meta.metarch.core.model.Disciplina;
import meta.metarch.core.usecase.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping(value = "/disciplinas")

public class DisciplinaResource {

    private final CriarDisciplinaCase criar;
    private final AlterarDisciplinaCase alterar;
    private final RemoverDisciplinaCase remover;
    private final ObterDisciplinaCase obter;

    private final ListarDisciplinaCase listar;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Disciplina create(@RequestBody @Valid final CriarDisciplinaCase.In disciplina, final HttpServletResponse response){
        final var created = criar.execute(disciplina);
        response.setHeader(HttpHeaders.LOCATION, ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri()
                .toString());
        return created;
    }

    @PutMapping("/{id}")
    public void put(@PathVariable final Long id, @Valid @RequestBody final AlterarDisciplinaCase.In disciplina) {
        alterar.execute(id,disciplina);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final Long id){
        remover.execute(id);
    }

    @GetMapping("/{id}")
    public Disciplina get(@PathVariable final Long id){
        return obter.execute(id);
    }

    @GetMapping
    public Page<Disciplina> list(@RequestParam(required = false) final String nome,
                                 @PageableDefault final Pageable pageable) {
        return listar.execute(nome, pageable);
    }

}

