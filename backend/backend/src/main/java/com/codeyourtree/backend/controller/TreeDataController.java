package com.codeyourtree.backend.controller;
import com.codeyourtree.backend.model.TreeData;
import com.codeyourtree.backend.service.TreeDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trees")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
public class TreeDataController {
    private final TreeDataService treeDataService;
    @PostMapping("water/{username}")
    public ResponseEntity<?> waterTree(@PathVariable String username){
        try{
            TreeData updatedTree = treeDataService.waterTree(username);
            return ResponseEntity.ok(updatedTree);
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getTreeData(@PathVariable String username){
        try{
            TreeData tree=treeDataService.getTreeByUsername(username);
            return ResponseEntity.ok(tree);
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
    
