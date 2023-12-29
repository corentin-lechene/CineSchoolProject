package com.cineschoolproject.exceptions

enum class MovieSeenExceptionMessages(val message: String) {
    EMPTY_FIELDS("Tous les champs doivent être remplis."),
    INVALID_NOTE("La note doit être un nombre valide."),
    INVALID_THRESHOLD_NOTE("La note doit être entre 0 et 5."),
    INVALID_FORMAT_DATE("La date doit être au format dd-mm-yyyy")
}