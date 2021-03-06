package com.nttdata.rrss.Controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.nttdata.rrss.Entity.PortfolioEntity;
import com.nttdata.rrss.Services.PortfolioService;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/portfolios")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @PostMapping("/save-portfolio")
    public ResponseEntity<Object> savePortfolio(@RequestBody Map<String, Object> payload) throws Exception {
        return ResponseEntity.ok().body(portfolioService.savePortfolio(payload));
    }

    @GetMapping("/find-all-portfolios")
    public ResponseEntity<List<PortfolioEntity>> findAllPortfolios() {
        return ResponseEntity.ok().body(portfolioService.findAllPortfolios());
    }

    @GetMapping("/find-portfolio")
    public ResponseEntity<PortfolioEntity> findPortfolio(@RequestParam Long id) {
        return ResponseEntity.ok().body(portfolioService.findPortfolio(id));
    }

    @GetMapping("/find-portfolio-author")
    public ResponseEntity<List<PortfolioEntity>> findPortfolioByAuthor(@RequestParam Long author) {
        return ResponseEntity.ok().body(portfolioService.findPortfolioByAuthor(author));
    }

    @DeleteMapping("/delete-portfolio")
    public ResponseEntity.BodyBuilder deletePortfolio(@RequestParam Long id) {
        portfolioService.deletePortfolio(id);
        return ResponseEntity.ok();
    }

    @DeleteMapping("/delete-portfolio-author")
    public ResponseEntity.BodyBuilder deletePortfolioByAuthor(@RequestParam Long author) {
        portfolioService.deletePortfolioByAuthor(author);
        return ResponseEntity.ok();
    }

    @GetMapping("/find-portfolio-reactions")
    public ResponseEntity<JsonElement> findPublicationObjects(@RequestParam Long id) throws JSONException {
        return ResponseEntity.ok().body(new Gson().toJsonTree(portfolioService.portfolioReactions(id)));
    }
}
