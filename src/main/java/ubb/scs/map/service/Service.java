package ubb.scs.map.service;

import ubb.scs.map.domain.Prietenie;
import ubb.scs.map.domain.Utilizator;
import ubb.scs.map.exceptions.ServiceException;
import ubb.scs.map.repository.Repository;

import java.util.*;

public class Service {
    private static Service instance = null;
    private final Repository<Long, Utilizator> repoUtilizator;
    private final Repository<Long, Prietenie> repoPrietenie;

    private Service(Repository<Long, Utilizator> repoUtilizator, Repository<Long, Prietenie> repoPrietenie) {
        this.repoUtilizator = repoUtilizator;
        this.repoPrietenie = repoPrietenie;
    }

    public static Service getInstance(Repository<Long, Utilizator> repoUtilizator, Repository<Long, Prietenie> repoPrietenie) {
        if (instance == null) {
            instance = new Service(repoUtilizator, repoPrietenie);
        }
        return instance;
    }

    public void addUtilizator(String firstName, String lastName) {
        Utilizator utilizator = new Utilizator(firstName, lastName);
        utilizator.setId(repoUtilizator.giveNewId());
        repoUtilizator.save(utilizator);
    }

    public Optional<Utilizator> removeUtilizator(Long id) {
        for (Prietenie prietenie : repoPrietenie.findAll()) {
            if (prietenie.getUtilizator1Id().equals(id) || prietenie.getUtilizator2Id().equals(id)) {
                repoPrietenie.delete(prietenie.getId());
            }
        }
        return repoUtilizator.delete(id);
    }

    public Optional<Utilizator> getUtilizator(Long id) {
        return repoUtilizator.findOne(id);
    }

    public Iterable<Utilizator> getAllUtilizatori() {
        return repoUtilizator.findAll();
    }

    public void addPrietenie(Long user1Id, Long user2Id) {
        if (repoUtilizator.findOne(user1Id).isEmpty() || repoUtilizator.findOne(user2Id).isEmpty()) {
            throw new ServiceException("Id doesn't exist!");
        }
        if (user1Id.equals(user2Id)) {
            throw new ServiceException("Cannot add prietenie between same utilizator!");
        }

        Prietenie prietenie = new Prietenie(user1Id, user2Id);
        prietenie.setId(repoPrietenie.giveNewId());
        repoPrietenie.save(prietenie);
    }

    public Optional<Prietenie> removePrietenie(Long id) {
        return repoPrietenie.delete(id);
    }

    public Iterable<Prietenie> getAllPrietenii() {
        return repoPrietenie.findAll();
    }

    public int numberOfCommunities() {
        Iterable<Utilizator> utilizatori = repoUtilizator.findAll();
        Set<Long> vizUtilizatori = new HashSet<>();
        int count = 0;

        for (Utilizator u : utilizatori)
            if (!vizUtilizatori.contains(u.getId())) {
                ++count;
                dfs(u.getId(), vizUtilizatori);
            }

        return count;
    }

    private List<Long> dfs(Long utilizatorId, Set<Long> vizUtilizatori) {
        List<Long> component = new ArrayList<>();
        vizUtilizatori.add(utilizatorId);
        component.add(utilizatorId);

        for (Prietenie p : repoPrietenie.findAll()) {
            if (p.getUtilizator1Id().equals(utilizatorId) && !vizUtilizatori.contains(p.getUtilizator2Id())) {
                component.addAll(dfs(p.getUtilizator2Id(), vizUtilizatori));
            } else if (p.getUtilizator2Id().equals(utilizatorId) && !vizUtilizatori.contains(p.getUtilizator1Id())) {
                component.addAll(dfs(p.getUtilizator1Id(), vizUtilizatori));
            }
        }

        return component;
    }

    public Iterable<Utilizator> mostSociableCommunity() {
        Iterable<Long> sociableCommunity = new ArrayList<>();
        Iterable<Utilizator> utilizatori = repoUtilizator.findAll();
        Set<Long> vizUtilizatori = new HashSet<>();

        int max = -1;
        for (Utilizator u : utilizatori) {
            if (!vizUtilizatori.contains(u.getId())) {
                List<Long> aux = dfs(u.getId(), vizUtilizatori);
                int l = longestPath(aux);
                if (l > max) {
                    sociableCommunity = aux;
                    max = l;
                }
            }
        }

        List<Utilizator> sociableCommunityUtilizatori = new ArrayList<>();
        for (Long u : sociableCommunity) {
            Optional<Utilizator> utilizator = repoUtilizator.findOne(u);
            utilizator.ifPresent(sociableCommunityUtilizatori::add);
        }

        return sociableCommunityUtilizatori;
    }

    private int longestPath(List<Long> community) {
        int maxLength = 0;
        for (Long u : community) {
            Set<Long> visited = new HashSet<>();
            maxLength = Math.max(maxLength, dfsLongestPath(u, visited));
        }
        return maxLength;
    }

    private int dfsLongestPath(Long utilizatorId, Set<Long> visited) {
        visited.add(utilizatorId);
        int maxLength = 0;

        for (Prietenie p : repoPrietenie.findAll()) {
            Long neighborId = null;
            if (p.getUtilizator1Id().equals(utilizatorId) && !visited.contains(p.getUtilizator2Id())) {
                neighborId = p.getUtilizator2Id();
            } else if (p.getUtilizator2Id().equals(utilizatorId) && !visited.contains(p.getUtilizator1Id())) {
                neighborId = p.getUtilizator1Id();
            }

            if (neighborId != null) {
                maxLength = Math.max(maxLength, 1 + dfsLongestPath(neighborId, visited));
            }
        }

        visited.remove(utilizatorId);
        return maxLength;
    }
}
