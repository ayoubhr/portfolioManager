package com.nttdata.rrss.Services;

import com.nttdata.rrss.Entity.PortfolioEntity;
import com.nttdata.rrss.Entity.ReactionEntity;
import com.nttdata.rrss.Entity.UserEntity;
import com.nttdata.rrss.Repository.PortfolioRepository;
import com.nttdata.rrss.Repository.ReactionRepository;
import com.nttdata.rrss.Repository.UserRepository;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;
    private final ReactionRepository reactionRepository;

    public PortfolioService(PortfolioRepository portfolioRepository, UserRepository userRepository, ReactionRepository reactionRepository) {
        this.portfolioRepository = portfolioRepository;
        this.userRepository = userRepository;
        this.reactionRepository = reactionRepository;
    }

    public HashMap<String, String> savePortfolio(Map<String, Object> payload) {
        PortfolioEntity portfolio = new PortfolioEntity();
        HashMap<String, String> response = new HashMap<>();

        Optional<PortfolioEntity> pe;
        PortfolioEntity portfolio_existant;

        if(payload.get("id") != null){
            Double id = (Double) payload.get("id");
            pe = portfolioRepository.findById(id.longValue());
            if(pe.isPresent()){
                portfolio_existant = pe.get();
                portfolio_existant.setComposition((ArrayList<Object>) payload.get("composition"));

                portfolioRepository.deleteById(id.longValue());
                portfolioRepository.save(portfolio_existant);
                response.put("status", "portfolio updated");
            }
        } else {
            try {
                System.out.println(payload);
                portfolio.setName((String) payload.get("name"));
                portfolio.setAuthor(Long.parseLong((String) payload.get("author")));

                portfolio.setComposition((ArrayList<Object>) payload.get("composition"));

                portfolioRepository.save(portfolio);
                response.put("status", "portfolio saved");

            } catch (Exception e) {
                response.put("403", "Bad Request");
                response.put("error", e.getMessage());
            }
        }

        return response;
    }

    public List<PortfolioEntity> findAllPortfolios() {
        return portfolioRepository.findAll();
    }

    public PortfolioEntity findPortfolio(Long id) {
        Optional<PortfolioEntity> p = portfolioRepository.findById(id);
        PortfolioEntity portfolio = new PortfolioEntity();

        if(p.isPresent()) {
            portfolio = p.get();
        }
        return portfolio;
    }

    public List<PortfolioEntity> findPortfolioByAuthor(Long author) {
        Optional<List<PortfolioEntity>> p = portfolioRepository.findByAuthor(author);
        List<PortfolioEntity> portfolio = null;

        if(p.isPresent()) {
            portfolio = p.get();
        }
        return portfolio;
    }

    public void deletePortfolio(Long id) {
        portfolioRepository.deleteById(id);
    }

    public void deletePortfolioByAuthor(Long author) {
        portfolioRepository.deleteByAuthor(author);
    }

    public List<Object> portfolioReactions(Long id) throws JSONException {
        Optional<UserEntity> u = userRepository.findById(id);
        UserEntity user = u.get();

        List<PortfolioEntity> ports = portfolioRepository.findAll();

        List<Object> objList = new ArrayList<>();

        for(PortfolioEntity port: ports) {
            HashMap<String, Object> objectNode = new HashMap<>();

            List<ReactionEntity> reactions = reactionRepository.findByUserAndPublication(id, port.getId());

            objectNode.put("user_id", user.getId());
            objectNode.put("portfolio", port.getId());
            objectNode.put("reactions", reactions);

            objList.add(objectNode);
        }
        return objList;
    }
}
