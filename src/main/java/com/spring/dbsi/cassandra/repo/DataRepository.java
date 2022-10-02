package com.spring.dbsi.cassandra.repo;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.spring.dbsi.cassandra.dao.StudentGrade;

public interface DataRepository extends CassandraRepository<StudentGrade, UUID>{

}
