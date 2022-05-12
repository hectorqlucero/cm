CREATE TABLE aventuras_link (
    id int unsigned NOT NULL AUTO_INCREMENT,
    aventuras_id INT unsigned NOT NULL,
    comments text,
    PRIMARY KEY (id),
    KEY fk_aventuras_link_aventuras (aventuras_id),
    CONSTRAINT fk_aventuras_link_aventuras FOREIGN KEY (aventuras_id) REFERENCES aventuras (id) ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb3