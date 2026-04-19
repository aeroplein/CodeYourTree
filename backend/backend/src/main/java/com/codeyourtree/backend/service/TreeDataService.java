package com.codeyourtree.backend.service;

import com.codeyourtree.backend.model.TreeData;
import com.codeyourtree.backend.repository.TreeDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.codeyourtree.backend.repository.UserRepository;
import com.codeyourtree.backend.model.User;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor //Constructor'ı ve Dependency Injection otomatik yapar
public class TreeDataService {
    private final TreeDataRepository treeRepo;
    private final UserRepository userRepo;

    @Transactional
    public TreeData waterTree(String username) {
        User user = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        TreeData tree = user.getTreeData();
        LocalDate today = LocalDate.now();
        LocalDate lastActionDate = tree.getLastActionDate();

        if (lastActionDate == null) { //ilk defa suladık.
            tree.setStreak(1);
            tree.setXp(tree.getXp() + 100);
            tree.setLastActionDate(today);
            return treeRepo.save(tree);
        
        }else if (lastActionDate.isEqual(today)) { //bugün zaten sulamışız.
            throw new RuntimeException("You already saved your code today. Come again tomorrow.");
        }else{ //daha önce sulamışız ama bugün değil o yüzden farka bakıyoruz.
            long diffDays = lastActionDate == null ? 0 : ChronoUnit.DAYS.between(lastActionDate, today);

            if (diffDays > 1) {
                tree.setStreak(1);
                tree.setMaxDepth(3);
            } else { // dün sulamışız yani 1 gün geçtiyse.
                tree.setStreak(tree.getStreak() + 1);
                tree.setXp(tree.getXp() + 100);

                if (tree.getStreak() % 3 == 0 && tree.getMaxDepth() < 9) {
                    tree.setMaxDepth(tree.getMaxDepth() + 1);
                }
            }

        }

        tree.setLastActionDate(today);
        return treeRepo.save(tree);


    }

    public TreeData getTreeByUsername(String username){
        User user = userRepo.findByUsername(username).orElseThrow(
            ()->new RuntimeException("User not found!")
            );

            return user.getTreeData();
    }
}
