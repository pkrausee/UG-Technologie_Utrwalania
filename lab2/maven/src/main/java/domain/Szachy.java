package domain;

public class Szachy {
    private int id;
    private String nazwa;
    private String gracz;
    private int stanowisko;

    public Szachy() {
    }

    public Szachy(String nazwa, String gracz, int stanowisko) {
        this.id = 0;
        this.nazwa = nazwa;
        this.gracz = gracz;
        this.stanowisko = stanowisko;
    }

    public Szachy(int id, String nazwa, String gracz, int stanowisko) {
        this.id = id;
        this.nazwa = nazwa;
        this.gracz = gracz;
        this.stanowisko = stanowisko;
    }

    @Override
    public String toString() {
        return "Szachy{ " +
                "id=" + id +
                ", nazwa='" + nazwa + '\'' +
                ", gracz='" + gracz + '\'' +
                ", stanowisko=" + stanowisko +
                " }";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getGracz() {
        return gracz;
    }

    public void setGracz(String gracz) {
        this.gracz = gracz;
    }

    public int getStanowisko() {
        return stanowisko;
    }

    public void setStanowisko(int stanowisko) {
        this.stanowisko = stanowisko;
    }
}
