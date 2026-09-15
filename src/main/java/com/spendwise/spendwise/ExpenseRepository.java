package com.spendwise.spendwise;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

      @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e " +
              "WHERE e.user = :user AND e.date >= :from AND e.date <= :to")
      Double getTotalForPeriod(@Param("user") User user,
                               @Param("from") LocalDate from,
                               @Param("to") LocalDate to);

      @Query("SELECT e.category, SUM(e.amount) FROM Expense e " +
              "WHERE e.user = :user AND e.date >= :from AND e.date <= :to " +
              "GROUP BY e.category")
      List<Object[]> getCategoryBreakdown(@Param("user") User user,
                                          @Param("from") LocalDate from,
                                          @Param("to") LocalDate to);

      @Query("SELECT e FROM Expense e WHERE e.user = :user"+
             " AND (:category IS NULL OR e.category = :category)"+
             " AND (:from IS NULL OR e.date >= :from)"+
             " AND (:to IS NULL OR e.date <= :to)")

      List<Expense> findWithFilters(@Param("user") User user,
                                    @Param("category") String category,
                                    @Param("from") LocalDate from,
                                    @Param("to") LocalDate to);
      List<Expense> findByUser(User user);

      @Query("SELECT e FROM Expense e WHERE e.user = :user " +
              "AND (:category IS NULL OR e.category = :category) " +
              "AND (:from IS NULL OR e.date >= :from) " +
              "AND (:to IS NULL OR e.date <= :to)")
      Page<Expense> findWithFilters(@Param("user") User user,
                                    @Param("category") String category,
                                    @Param("from") LocalDate from,
                                    @Param("to") LocalDate to,
                                    Pageable pageable);
}