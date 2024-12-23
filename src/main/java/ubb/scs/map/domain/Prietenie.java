package ubb.scs.map.domain;

import java.util.Objects;

public class Prietenie extends Entity<Long> {
    private Long utilizator1Id;
    private Long utilizator2Id;

    public Prietenie(Long utilizator1Id, Long utilizator2Id) {
        this.utilizator1Id = utilizator1Id;
        this.utilizator2Id = utilizator2Id;
    }

    /**
     * @return the id of the first user
     */
    public Long getUtilizator1Id() {
        return utilizator1Id;
    }

    /**
     * @param utilizator1Id the new id of the first user
     */
    public void setUtilizator1Id(Long utilizator1Id) {
        this.utilizator1Id = utilizator1Id;
    }

    /**
     * @return the id of the second user
     */
    public Long getUtilizator2Id() {
        return utilizator2Id;
    }

    /**
     * @param utilizator2Id the new id of the second user
     */
    public void setUtilizator2Id(Long utilizator2Id) {
        this.utilizator2Id = utilizator2Id;
    }

    @Override
    public String toString() {
        return "id=" + getId() +
                ", utilizator1Id=" + utilizator1Id +
                ", utilizator2Id=" + utilizator2Id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prietenie prietenie = (Prietenie) o;
        return Objects.equals(utilizator1Id, prietenie.utilizator1Id) && Objects.equals(utilizator2Id, prietenie.utilizator2Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(utilizator1Id, utilizator2Id);
    }
}