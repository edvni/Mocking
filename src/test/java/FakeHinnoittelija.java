import org.example.Asiakas;
import org.example.IHinnoittelija;
import org.example.Tuote;

public class FakeHinnoittelija implements IHinnoittelija {

    private float alennus;

    public FakeHinnoittelija(float alennus) {
        this.alennus = alennus;
    }

    public float getAlennusProsentti(Asiakas asiakas, Tuote tuote) {
        return alennus;
    }
    @Override
    public void setAlennusProsentti(Asiakas asiakas, float prosentti) {
        alennus = prosentti;
    }

    public void aloita() {
        System.out.println("Aloitetaan hinnoittelu.");
    }

    public void lopeta() {
        System.out.println("Lopetetaan hinnoittelu.");
    }
}
