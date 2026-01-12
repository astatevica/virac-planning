import axios from "axios";

const API_URL = "http://localhost:8080/api/journal";

export const getAllJournals = () => axios.get(API_URL);

export const getJournalById = (id) =>
  axios.get(`${API_URL}/${id}`);

export const createJournal = (journal) =>
  axios.post(API_URL, journal);

export const updateJournal = (id, journal) =>
  axios.put(`${API_URL}/${id}`, journal);

export const deleteJournal = (id) =>
  axios.delete(`${API_URL}/${id}`);
