package com.flashcards.models;

enum FileField {
    PROPERTIES("---"),
    QUESTION("???"),
    ANSWER("===");

    String separator;
    FileField(String separator) { this.separator = separator; }
}
