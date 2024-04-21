import org.example.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TilaustenKasittelyMockitoTest {

    private TilaustenKasittely tilaustenKasittely;

    float alkuSaldo = 100.0f;
    float listaHinta = 30.0f;
    float listaHintaYli100 = 120.0f;
    float alennus = 20.0f;
    float loppuSaldoAlle100 = alkuSaldo - (listaHinta * (1 - alennus / 100));
    float loppuSaldoYli100 = alkuSaldo - (listaHintaYli100 * (1 - alennus / 100));

    @Mock
    IHinnoittelija hinnoittelijaMock;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        tilaustenKasittely = new TilaustenKasittely();
        tilaustenKasittely.setHinnoittelija(hinnoittelijaMock);
    }

    @Test
    public void testKasitteleHintaAlle100() {
        Asiakas asiakas = new Asiakas(alkuSaldo);
        Tuote tuote = new Tuote("TTD in action", listaHinta);

        when(hinnoittelijaMock.getAlennusProsentti(asiakas, tuote)).thenReturn(alennus);

        tilaustenKasittely.kasittele(new Tilaus(asiakas, tuote));

        verify(hinnoittelijaMock).aloita();
        verify(hinnoittelijaMock).lopeta();
        verify(hinnoittelijaMock, times(2)).getAlennusProsentti(asiakas, tuote);
        assertEquals(loppuSaldoAlle100, asiakas.getSaldo()); // uusi saldo

    }

    @Test
    public void testKasitteleHintaYliTaiYhtasuuriKuin100() {
        Asiakas asiakas = new Asiakas(alkuSaldo);
        Tuote tuote = new Tuote("TTD in action", listaHintaYli100); // hinta yli 100

        when(hinnoittelijaMock.getAlennusProsentti(asiakas, tuote)).thenReturn(alennus);

        tilaustenKasittely.kasittele(new Tilaus(asiakas, tuote));

        verify(hinnoittelijaMock).aloita();
        verify(hinnoittelijaMock, times(2)).getAlennusProsentti(asiakas, tuote);
        assertEquals(loppuSaldoYli100, asiakas.getSaldo()); // uusi saldo
    }

    /*@Test
    public void testaaKasittelijaWithMockitoHinnoittelija() {
        // arrange
        float alkuSaldo = 100.0f;
        float listaHinta = 30.0f;
        float alennus = 20.0f;
        float loppuSaldo = alkuSaldo - (listaHinta * (1 - alennus / 100));

        Asiakas asiakas = new Asiakas(alkuSaldo);
        Tuote tuote = new Tuote("TTD in action", listaHinta);
        // record
        when(hinnoittelijaMock.getAlennusProsentti(asiakas, tuote)).thenReturn(alennus);

        // act
        TilaustenKasittely kasittelija = new TilaustenKasittely();
        kasittelija.setHinnoittelija(hinnoittelijaMock);
        kasittelija.kasittele(new Tilaus(asiakas, tuote));

        // assert
        assertEquals(loppuSaldo, asiakas.getSaldo(), 0.001);
        verify(hinnoittelijaMock).getAlennusProsentti(asiakas, tuote);
    }*/
}
