package com.nttdata.rrss.Controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.nttdata.rrss.Services.ReactionService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/reactions")
public class ReactionController {
	
	private final ReactionService reactionService;
	
	public ReactionController(ReactionService reactionService) {
		this.reactionService = reactionService;
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@PostMapping("/save-reaction")
	public void save(@RequestBody Map<String, Object> payload) {
		reactionService.saveReaction(payload);
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@DeleteMapping("/delete-reaction")
	public void delete(@RequestParam Long id) {
		reactionService.deleteReaction(id);
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@DeleteMapping("/delete-all-reactions")
	public void deleteAll(@RequestParam Long id) {
		reactionService.deleteAllByUser(id);
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/find-reactions")
	public ResponseEntity<JsonElement> findPublication(@RequestParam Long id) {
		return ResponseEntity.ok().body(new Gson().toJsonTree(reactionService.findById(id)));
	}
}
