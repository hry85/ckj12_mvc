 package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Note;

@Repository
public interface NoteRepository extends CrudRepository<Note, Integer> {
	// SELECT * FROM notes WHERE label LIKE '%word%' OR message LIKE '%word%'
	public List<Note> findByLabelContainingOrMessageContaining(String label, String msg);
}
