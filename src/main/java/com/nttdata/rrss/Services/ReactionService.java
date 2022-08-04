package com.nttdata.rrss.Services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nttdata.rrss.Entity.ReactionEntity;
import com.nttdata.rrss.Repository.ReactionRepository;

@Service
@Transactional
public class ReactionService {

    private final ReactionRepository reactionRepo;

    public ReactionService(ReactionRepository reactionRepo) {
        this.reactionRepo = reactionRepo;
    }

    public void saveReaction(Map<String, Object> payload) {
        boolean condition = false;
        List<ReactionEntity> reactions = reactionRepo.findAll();

        Long user_id = Long.parseLong((String) payload.get("user"));
        Long publication_id = Long.parseLong((String) payload.get("publication"));
        String reaction_name = (String) payload.get("name");

        for (ReactionEntity reaction : reactions) {
            if (user_id.longValue() == reaction.getUser() && publication_id.longValue() == reaction.getPublication() && !reaction_name.equals(reaction.getName())) {
                deleteReaction(reaction.getId());
                insertReaction(user_id, publication_id, reaction_name);
                condition = false;
                break;
            } else {
                condition = true;
            }
        }

        if (condition || reactions.isEmpty()) {
            insertReaction(user_id, publication_id, reaction_name);
        }
    }

    public void insertReaction(Long user_id, Long publication_id, String reaction_name) {
        ReactionEntity r = new ReactionEntity();

        r.setUser(user_id);
        r.setPublication(publication_id);
        r.setName(reaction_name);
        reactionRepo.save(r);
    }

    public void deleteReaction(Long id) {
        reactionRepo.deleteById(id);
        reactionRepo.flush();
    }

    public List<ReactionEntity> findById(Long id) {
        return reactionRepo.findAllByUser(id);
    }

    public void deleteAllByUser(Long id) {
        reactionRepo.deleteAllByUser(id);
    }
}
