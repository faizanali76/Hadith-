-- Hadith Database: MySQL 8.0.16 or later
-- Glossary entries belong to books only.

CREATE DATABASE hadith_database
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE hadith_database;

CREATE TABLE collections (
    collection_id INT AUTO_INCREMENT PRIMARY KEY,
    collection_name VARCHAR(255) NOT NULL,
    compiler VARCHAR(255),
    description TEXT,
    edition VARCHAR(255),
    publication_details TEXT,
    CHECK (CHAR_LENGTH(TRIM(collection_name)) > 0)
) ENGINE=InnoDB;

CREATE TABLE books (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    collection_id INT NOT NULL,
    book_number VARCHAR(50) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    UNIQUE (collection_id, book_number),
    FOREIGN KEY (collection_id) REFERENCES collections(collection_id)
        ON DELETE CASCADE,
    CHECK (CHAR_LENGTH(TRIM(book_number)) > 0),
    CHECK (CHAR_LENGTH(TRIM(title)) > 0)
) ENGINE=InnoDB;

CREATE TABLE chapters (
    chapter_id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT NOT NULL,
    chapter_number VARCHAR(50) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    UNIQUE (book_id, chapter_number),
    FOREIGN KEY (book_id) REFERENCES books(book_id)
        ON DELETE CASCADE,
    CHECK (CHAR_LENGTH(TRIM(chapter_number)) > 0),
    CHECK (CHAR_LENGTH(TRIM(title)) > 0)
) ENGINE=InnoDB;

CREATE TABLE languages (
    language_id INT AUTO_INCREMENT PRIMARY KEY,
    language_name VARCHAR(100) NOT NULL UNIQUE,
    CHECK (CHAR_LENGTH(TRIM(language_name)) > 0)
) ENGINE=InnoDB;

CREATE TABLE hadiths (
    hadith_id INT AUTO_INCREMENT PRIMARY KEY,
    chapter_id INT NOT NULL,
    hadith_number VARCHAR(50) NOT NULL,
    arabic_text TEXT NOT NULL,
    source_reference TEXT NOT NULL,
    isnad TEXT,
    matn TEXT,
    admin_notes TEXT,
    UNIQUE (chapter_id, hadith_number),
    FOREIGN KEY (chapter_id) REFERENCES chapters(chapter_id)
        ON DELETE CASCADE,
    CHECK (CHAR_LENGTH(TRIM(hadith_number)) > 0),
    CHECK (CHAR_LENGTH(TRIM(arabic_text)) > 0),
    CHECK (CHAR_LENGTH(TRIM(source_reference)) > 0)
) ENGINE=InnoDB;

CREATE TABLE translations (
    translation_id INT AUTO_INCREMENT PRIMARY KEY,
    hadith_id INT NOT NULL,
    language_id INT NOT NULL,
    translated_text TEXT NOT NULL,
    translator_name VARCHAR(255),
    translation_source TEXT,
    UNIQUE (hadith_id, language_id),
    FOREIGN KEY (hadith_id) REFERENCES hadiths(hadith_id)
        ON DELETE CASCADE,
    FOREIGN KEY (language_id) REFERENCES languages(language_id)
        ON DELETE RESTRICT,
    CHECK (CHAR_LENGTH(TRIM(translated_text)) > 0)
) ENGINE=InnoDB;

CREATE TABLE book_glossary (
    glossary_id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT NOT NULL,
    arabic_term VARCHAR(255) NOT NULL,
    urdu_meaning TEXT NOT NULL,
    urdu_explanation TEXT,
    FOREIGN KEY (book_id) REFERENCES books(book_id)
        ON DELETE CASCADE,
    CHECK (CHAR_LENGTH(TRIM(arabic_term)) > 0),
    CHECK (CHAR_LENGTH(TRIM(urdu_meaning)) > 0)
) ENGINE=InnoDB;

-- Java must save each Hadith and at least one translation in one transaction,
-- and prevent deletion of its last translation.
-- Java must validate required fields and request confirmation before deletion.
