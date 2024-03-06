package com.cineschoolproject.exceptions

const val MIN_THRESHOLD_NOTE = 0
const val MAX_THRESHOLD_NOTE = 5
const val FORMAT_DATE = "dd/MM/yyyy"
const val MIN_YEAR = 2000
enum class MovieSeenExceptionMessages(val message: String) {
    EMPTY_FIELDS("Tous les champs doivent être remplis."),
    INVALID_THRESHOLD_NOTE("La note doit être entre $MIN_THRESHOLD_NOTE et $MAX_THRESHOLD_NOTE."),
    INVALID_FORMAT_DATE("La date doit être au format $FORMAT_DATE"),
    INVALID_DATE_RANGE("La date doit être comprise entre $MIN_YEAR et maintenant")
}