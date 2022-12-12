package meta.metarch.infra.http.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import meta.metarch.core.model.Disciplina;
import meta.metarch.core.usecase.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping(value = "/disciplinas")

public class DisciplinaResource {

    private final CriarDisciplinaCase criarDisciplina;
    private final AlterarDisciplinaCase alterarDisciplina;
    private final RemoverDisciplinaCase removerDisciplina;
    private final ObterDisciplinaCase obterDisciplina;

    private final ListarDisciplinaCase listarDisciplina;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Disciplina create(@RequestBody CriarDisciplinaCase.In disciplina, HttpServletResponse response){
        final var created = criarDisciplina.execute(disciplina);
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
        alterarDisciplina.execute(id,disciplina);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final Long id){
        removerDisciplina.execute(id);
    }

    @GetMapping("/{id}")
    public Disciplina get(@PathVariable final Long id){
        return obterDisciplina.execute(id);
    }

    @GetMapping
    public Page<Disciplina> list(@RequestParam(required = false) final String nome,
                                 @PageableDefault final Pageable pageable) {
        return listarDisciplina.execute(nome, pageable);
    }

}

