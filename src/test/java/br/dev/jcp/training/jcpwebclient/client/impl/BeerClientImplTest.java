package br.dev.jcp.training.jcpwebclient.client.impl;

import br.dev.jcp.training.jcpwebclient.client.BeerClient;
import br.dev.jcp.training.jcpwebclient.model.BeerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.await;

@SpringBootTest
class BeerClientImplTest {

    @Autowired
    BeerClient beerClient;

    @Test
    void testListBeerAsString() {
        AtomicBoolean done = new AtomicBoolean(false);
        beerClient.listBeer().subscribe(response -> {
            System.out.println(response);
            done.set(true);
        });
        await().untilTrue(done);
    }

    @Test
    void testListBeerAsMap() {
        AtomicBoolean done = new AtomicBoolean(false);
        beerClient.listBeerMap().subscribe(response -> {
            System.out.println(response);
            done.set(true);
        });
        await().untilTrue(done);
    }

    @Test
    void testListBeerAsJson() {
        AtomicBoolean done = new AtomicBoolean(false);
        beerClient.listBeerJsonNode().subscribe(response -> {
            System.out.println(response);
            done.set(true);
        });
        await().untilTrue(done);
    }

    @Test
    void testListBeerAsDTO() {
        AtomicBoolean done = new AtomicBoolean(false);
        beerClient.listBeerDTO().subscribe(response -> {
            System.out.println(response.toString());
            done.set(true);
        });
        await().untilTrue(done);
    }

    @Test
    void testGetBeerById() {
        AtomicBoolean done = new AtomicBoolean(false);
        beerClient.listBeerDTO()
                .flatMap(beerDTO -> beerClient.getBeerById(beerDTO.getId()))
                .subscribe(beerDTO -> {
                    System.out.println(beerDTO.getBeerName());
                    done.set(true);
                });
        await().untilTrue(done);
    }

    @Test
    void testListBeerByBeerStyle() {
        AtomicBoolean done = new AtomicBoolean(false);
        beerClient.listBeerByBeerStyle("Pale Ale")
                .subscribe(beerDTO -> {
                    System.out.println(beerDTO.getBeerName());
                    done.set(true);
                });
        await().untilTrue(done);
    }

    @Test
    void testCreateBeer() {
        AtomicBoolean done = new AtomicBoolean(false);
        BeerDTO newBeer = BeerDTO.builder()
                .beerName("Indica")
                .beerStyle("IPA")
                .price(BigDecimal.TEN)
                .quantityOnHand(110)
                .upc("123456789001")
                .build();

        beerClient.createBeer(newBeer)
                .subscribe(beerDTO -> {
                    System.out.println(beerDTO.toString());
                    done.set(true);
                });
        await().untilTrue(done);
    }

    @Test
    void testUpdateBeer() {
        String beerNameUpdated = " - Updated";
        AtomicBoolean done = new AtomicBoolean(false);
        beerClient.listBeerDTO()
                .next()
                .doOnNext(beerDTO -> beerDTO.setBeerName(beerDTO.getBeerName() + beerNameUpdated))
                .flatMap(beerDTO -> beerClient.updateBeer(beerDTO))
                .subscribe(updatedBeer -> {
                    System.out.println(updatedBeer.toString());
                    done.set(true);
                });
        await().untilTrue(done);
    }

    @Test
    void testPatchBeer() {
        String beerNameUpdated = " - Patched";
        AtomicBoolean done = new AtomicBoolean(false);
        beerClient.listBeerDTO()
                .next()
                .doOnNext(beerDTO -> beerDTO.setBeerName(beerDTO.getBeerName() + beerNameUpdated))
                .flatMap(beerDTO -> beerClient.patchBeer(beerDTO))
                .subscribe(updatedBeer -> {
                    System.out.println(updatedBeer.toString());
                    done.set(true);
                });
        await().untilTrue(done);
    }

    @Test
    void testDeleteBeer() {
        AtomicBoolean done = new AtomicBoolean(false);
        beerClient.listBeerDTO()
                .next()
                .flatMap(beerDTO -> beerClient.deleteBeer(beerDTO))
                .doOnSuccess(response -> done.set(true))
                .subscribe();
        await().untilTrue(done);
    }
}