package meta.metarch.infra.http.rest;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import meta.metarch.core.model.Projeto;
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
@RequestMapping(value = "/projetos")
public class ProjetoResource {
    private final CriarProjetoCase criar;
    private final AlterarProjetoCase alterar;
    private final RemoverProjetoCase remover;
    private final ObterProjetoCase obter;
    private final ListarProjetoCase listar;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Projeto create(@RequestBody final CriarProjetoCase.In projeto,
                          final HttpServletResponse response){
        final var created = criar.execute(projeto);
        response.setHeader(HttpHeaders.LOCATION, ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri()
                .toString());
        return created;
    }

    @PutMapping("/{id}")
    public void put(@PathVariable final Long id,
                    @Valid @RequestBody final AlterarProjetoCase.In projeto) {
        alterar.execute(id,projeto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final Long id){
        remover.execute(id);
    }

    @GetMapping("/{id}")
    public Projeto get(@PathVariable final Long id){
        return obter.execute(id);
    }

    @GetMapping
    public Page<Projeto> list(@RequestParam(required = false) final String nome,
                              @PageableDefault final Pageable pageable) {
        return listar.execute(nome, pageable);
    }


}
