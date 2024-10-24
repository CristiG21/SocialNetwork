package ubb.scs.map;

import ubb.scs.map.domain.Prietenie;
import ubb.scs.map.domain.Utilizator;
import ubb.scs.map.domain.validators.PrietenieValidator;
import ubb.scs.map.domain.validators.UtilizatorValidator;
import ubb.scs.map.repository.Repository;
import ubb.scs.map.repository.file.PrietenieRepository;
import ubb.scs.map.repository.file.UtilizatorRepository;
import ubb.scs.map.service.Service;
import ubb.scs.map.ui.ConsoleUI;
import ubb.scs.map.ui.UI;

public class Main {
    public static void main(String[] args) {
        Repository<Long, Utilizator> repoUtilizator = new UtilizatorRepository(new UtilizatorValidator(), "./data/utilizatori.txt");
        Repository<Long, Prietenie> repoPrietenie = new PrietenieRepository(new PrietenieValidator(), "./data/prietenii.txt");
        Service service = Service.getInstance(repoUtilizator, repoPrietenie);
        UI consoleUI = ConsoleUI.getInstance(service);
        consoleUI.run();
    }
}