package com.uniovi.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import com.uniovi.entities.*;
import com.uniovi.repositories.MarksRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MarksService {
	@Autowired
	private MarksRepository marksRepository;

	@Autowired
	private HttpSession httpSession;

	public Page<Mark> getMarks(Pageable pageable) {
		Page<Mark> marks = marksRepository.findAll(pageable);
		return marks;
	}

	public Mark getMark(Long id) {
		Set<Mark> consultedList = (Set<Mark>) httpSession.getAttribute("consultedList");
		if (consultedList == null) {
			consultedList = new HashSet<Mark>();
		}
		Mark markObtained = marksRepository.findOne(id);
		consultedList.add(markObtained);
		httpSession.setAttribute("consultedList", consultedList);
		return markObtained;
	}

	public void addMark(Mark mark) {
		marksRepository.save(mark);
	}

	public void deleteMark(Long id) {
		marksRepository.delete(id);
	}

	public void setMarkResend(boolean revised, Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String dni = auth.getName();
		Mark mark = marksRepository.findOne(id);
		if (mark.getUser().getDni().equals(dni)) {
			marksRepository.updateResend(revised, id);
		}

	}

	public Page<Mark> getMarksForUser(Pageable pageable, User user) {
		Page<Mark> marks = new PageImpl<Mark>(new LinkedList<Mark>());
		if (user.getRole().equals("ROLE_STUDENT")) {
			marks = marksRepository.findAllByUser(pageable, user);
		}
		if (user.getRole().equals("ROLE_PROFESSOR")) {
			marks = getMarks(pageable);
		}
		return marks;
	}

	public Page<Mark> searchMarksByDescriptionAndNameForUser(Pageable pageable, String searchText, User user) {
		Page<Mark> marks = new PageImpl<Mark>(new LinkedList<Mark>());
		searchText = "%" + searchText + "%";
		if (user.getRole().equals("ROLE_STUDENT")) {
			marks = marksRepository.searchByDescriptionNameAndUser(pageable, searchText, user);
		}
		if (user.getRole().equals("ROLE_PROFESSOR")) {
			marks = marksRepository.searchByDescriptionAndName(pageable, searchText);
		}
		return marks;
	}

}
