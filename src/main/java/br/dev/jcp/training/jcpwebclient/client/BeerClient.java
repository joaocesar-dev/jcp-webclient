package br.dev.jcp.training.jcpwebclient.client;

import br.dev.jcp.training.jcpwebclient.model.BeerDTO;
import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface BeerClient {
    Flux<String> listBeer();
    Flux<Map> listBeerMap();
    Flux<JsonNode> listBeerJsonNode();
    Flux<BeerDTO> listBeerDTO();
    Mono<BeerDTO> getBeerById(String id);
    Flux<BeerDTO> listBeerByBeerStyle(String beerStyle);
    Mono<BeerDTO> createBeer(BeerDTO beerDTO);
    Mono<BeerDTO> updateBeer(BeerDTO beerDTO);
    Mono<BeerDTO> patchBeer(BeerDTO dto);
    Mono<Void> deleteBeer(BeerDTO dto);
}
