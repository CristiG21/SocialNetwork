package ubb.scs.map;

import ubb.scs.map.domain.Prietenie;
import ubb.scs.map.domain.Utilizator;
import ubb.scs.map.domain.validators.PrietenieValidator;
import ubb.scs.map.domain.validators.UtilizatorValidator;
import ubb.scs.map.repository.Repository;
import ubb.scs.map.repository.database.PrietenieDbRepository;
import ubb.scs.map.repository.database.UtilizatorDbRepository;
import ubb.scs.map.service.Service;
import ubb.scs.map.ui.ConsoleUI;
import ubb.scs.map.ui.UI;

public class Main {
    public static void main(String[] args) {
        String url="jdbc:postgresql://localhost:5433/socialnetwork";
        String username="postgres";
        String password="1234.AbCd";
        Repository<Long, Utilizator> repoUtilizator = new UtilizatorDbRepository(url, username, password,new UtilizatorValidator());
        Repository<Long, Prietenie> repoPrietenie = new PrietenieDbRepository(url, username, password, new PrietenieValidator());
        Service service = Service.getInstance(repoUtilizator, repoPrietenie);
        UI consoleUI = ConsoleUI.getInstance(service);
        consoleUI.run();
    }
}