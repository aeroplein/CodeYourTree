package com.codeyourtree.backend.service;

import com.codeyourtree.backend.model.TreeData;
import com.codeyourtree.backend.model.User;
import com.codeyourtree.backend.repository.TreeDataRepository;
import com.codeyourtree.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TreeDataServiceTest {

    @Mock
    private TreeDataRepository treeDataRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TreeDataService treeDataService;

    @Test
    void waterTree_firstWatering_initializesDailyProgress() {
        // Arrange
        String username = "test-user";
        User user = new User();
        user.setUsername(username);

        TreeData treeData = new TreeData();
        user.setTreeData(treeData);
        treeData.setUser(user);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(treeDataRepository.save(treeData)).thenReturn(treeData);

        LocalDate beforeCall = LocalDate.now();

        // Act
        TreeData result = treeDataService.waterTree(username);

        LocalDate afterCall = LocalDate.now();

        // Assert
        assertSame(treeData, result);
        assertEquals(1, result.getStreak());
        assertEquals(200, result.getXp());
        assertEquals(3, result.getMaxDepth());
        assertFalse(result.getLastActionDate().isBefore(beforeCall));
        assertFalse(result.getLastActionDate().isAfter(afterCall));
        verify(treeDataRepository, times(1)).save(treeData);
    }

    @Test
    void waterTree_alreadyWateredToday_throwsException() {
        // Arrange
        String username = "test-user";
        LocalDate today = LocalDate.now();

        User user = new User();
        user.setUsername(username);

        TreeData treeData = new TreeData();
        treeData.setStreak(4);
        treeData.setXp(500);
        treeData.setMaxDepth(4);
        treeData.setLastActionDate(today);
        user.setTreeData(treeData);
        treeData.setUser(user);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> treeDataService.waterTree(username));

        // Assert
        assertEquals("You already saved your code today. Come again tomorrow.", exception.getMessage());
        assertEquals(4, treeData.getStreak());
        assertEquals(500, treeData.getXp());
        assertEquals(4, treeData.getMaxDepth());
        assertEquals(today, treeData.getLastActionDate());
        verify(treeDataRepository, never()).save(any(TreeData.class));
    }

    @Test
    void waterTree_consecutiveDay_incrementsStreakAndXp() {
        // Arrange
        String username = "test-user";
        LocalDate today = LocalDate.now();

        User user = new User();
        user.setUsername(username);

        TreeData treeData = new TreeData();
        treeData.setStreak(1);
        treeData.setXp(100);
        treeData.setMaxDepth(3);
        treeData.setLastActionDate(today.minusDays(1));
        user.setTreeData(treeData);
        treeData.setUser(user);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(treeDataRepository.save(treeData)).thenReturn(treeData);

        // Act
        TreeData result = treeDataService.waterTree(username);

        // Assert
        assertEquals(2, result.getStreak());
        assertEquals(200, result.getXp());
        assertEquals(3, result.getMaxDepth());
        assertEquals(today, result.getLastActionDate());
        assertSame(treeData, result);
        verify(treeDataRepository, times(1)).save(treeData);
    }

    @Test
    void waterTree_thirdConsecutiveDay_increasesMaxDepth() {
        // Arrange
        String username = "test-user";
        LocalDate today = LocalDate.now();

        User user = new User();
        user.setUsername(username);

        TreeData treeData = new TreeData();
        treeData.setStreak(2);
        treeData.setXp(200);
        treeData.setMaxDepth(3);
        treeData.setLastActionDate(today.minusDays(1));
        user.setTreeData(treeData);
        treeData.setUser(user);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(treeDataRepository.save(treeData)).thenReturn(treeData);

        // Act
        TreeData result = treeDataService.waterTree(username);

        // Assert
        assertEquals(3, result.getStreak());
        assertEquals(300, result.getXp());
        assertEquals(4, result.getMaxDepth());
        assertEquals(today, result.getLastActionDate());
        assertSame(treeData, result);
        verify(treeDataRepository, times(1)).save(treeData);
    }

    @Test
    void waterTree_afterMissedDay_resetsStreakAndDepth() {
        // Arrange
        String username = "test-user";
        LocalDate today = LocalDate.now();

        User user = new User();
        user.setUsername(username);

        TreeData treeData = new TreeData();
        treeData.setStreak(7);
        treeData.setXp(700);
        treeData.setMaxDepth(6);
        treeData.setLastActionDate(today.minusDays(2));
        user.setTreeData(treeData);
        treeData.setUser(user);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(treeDataRepository.save(treeData)).thenReturn(treeData);

        // Act
        TreeData result = treeDataService.waterTree(username);

        // Assert
        assertEquals(1, result.getStreak());
        assertEquals(3, result.getMaxDepth());
        assertEquals(700, result.getXp());
        assertEquals(today, result.getLastActionDate());
        assertSame(treeData, result);
        verify(treeDataRepository, times(1)).save(treeData);
    }
}
