package spring.hibernate;

import spring.hibernate.entities.SingerSummary;

import java.util.List;

public interface SingerSummaryService {
    List<SingerSummary> findAll();
}
