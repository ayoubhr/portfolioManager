package com.nttdata.rrss.Services;

import com.nttdata.rrss.Entity.PortfolioEntity;
import com.nttdata.rrss.Repository.PortfolioRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    public HashMap<String, String> savePortfolio(Map<String, Object> payload) throws Exception {
        PortfolioEntity portfolio = new PortfolioEntity();
        HashMap<String, String> response = new HashMap<>();

        try {
            portfolio.setName((String) payload.get("name"));
            portfolio.setAuthor(Long.parseLong((String) payload.get("author")));

            ArrayList c = (ArrayList) payload.get("composition");
            portfolio.setComposition(c);

            if(portfolioRepository.save(portfolio) != null) {
                response.put("status", "portfolio saved");
            }  else {
                throw new Exception("Something went wrong");
            }
        } catch (Exception e) {
            response.put("403", "Bad Request");
            response.put("error", e.getMessage());
        }

        return response;
    }

    public List<PortfolioEntity> findAllPortfolios() {
        List<PortfolioEntity> portfolios = portfolioRepository.findAll();

        return portfolios;
    }

    public PortfolioEntity findPortfolio(Long id) {
        Optional<PortfolioEntity> p = portfolioRepository.findById(id);
        PortfolioEntity portfolio = p.get();

        return portfolio;
    }

    public PortfolioEntity findPortfolioByAuthor(Long author) {
        Optional<PortfolioEntity> p = portfolioRepository.findByAuthor(author);
        PortfolioEntity portfolio = p.get();

        return portfolio;
    }

    public void deletePortfolio(Long id) {
        portfolioRepository.deleteById(id);
    }

    public void deletePortfolioByAuthor(Long author) {
        portfolioRepository.deleteByAuthor(author);
    }
}
