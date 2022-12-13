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

import meta.metarch.core.model.Pergunta;
import meta.metarch.core.usecase.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping(value = "/perguntas")
public class PerguntaResource {
    private final CriarPerguntaCase criar;
    private final AlterarPerguntaCase alterar;
    private final RemoverPerguntaCase remover;
    private final ObterPerguntaCase obter;
    private final ListarPerguntaCase listar;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pergunta create(@RequestBody final CriarPerguntaCase.In pergunta,
                           final HttpServletResponse response){
        final var created = criar.execute(pergunta);
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
                    @Valid @RequestBody final AlterarPerguntaCase.In pergunta) {
        alterar.execute(id,pergunta);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final Long id){
        remover.execute(id);
    }

    @GetMapping("/{id}")
    public Pergunta get(@PathVariable final Long id){
        return obter.execute(id);
    }

    @GetMapping
    public Page<Pergunta> list(@RequestParam(required = false) final String texto,
                               @RequestParam(required = false) final Long disciplinaId,
                               @PageableDefault final Pageable pageable) {
        return listar.execute(texto, disciplinaId, pageable);
    }

}

