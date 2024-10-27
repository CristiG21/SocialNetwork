package ubb.scs.map.repository.file;

import ubb.scs.map.domain.Prietenie;
import ubb.scs.map.domain.validators.Validator;

import java.util.Collections;

public class PrietenieRepository extends AbstractFileRepository<Long, Prietenie> {
    public PrietenieRepository(Validator<Prietenie> validator, String fileName) {
        super(validator, fileName);
    }

    @Override
    public Prietenie createEntity(String line) {
        String[] splitted = line.split(";");
        Prietenie u = new Prietenie(Long.parseLong(splitted[1]), Long.parseLong(splitted[2]));
        u.setId(Long.parseLong(splitted[0]));
        return u;
    }

    @Override
    public String saveEntity(Prietenie entity) {
        return entity.getId() + ";" + entity.getUtilizator1Id() + ";" + entity.getUtilizator2Id();
    }

    @Override
    public Long giveNewId() {
        return (entities.isEmpty()) ? 0 : (Collections.max(entities.keySet()) + 1);
    }
}
